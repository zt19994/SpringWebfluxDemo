package com.zt1994.springwebfluxdemo.handler;

import com.zt1994.springwebfluxdemo.entity.User;
import com.zt1994.springwebfluxdemo.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

/**
 * @author zhongtao
 * @date 2022/5/15 14:03
 */
public class UserHandler {

    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    /**
     * 根据id查询用户
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> getUserById(ServerRequest request) {
        int userId = Integer.valueOf(request.pathVariable("id"));
        // 空值处理
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        Mono<User> userMono = this.userService.getUserById(userId);
        return userMono.flatMap(person -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(fromObject(person)))
                .switchIfEmpty(notFound);
    }

    /**
     * 查询所有user
     *
     * @return
     */
    public Mono<ServerResponse> getAllUsers(ServerRequest request) {
        Flux<User> users = this.userService.getAllUser();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(users, User.class);
    }

    /**
     * 保存user
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> saveUser(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class);
        return ServerResponse.ok().build(this.userService.saveUserInfo(userMono));
    }
}
