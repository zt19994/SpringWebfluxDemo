package com.zt1994.springwebfluxdemo.controller;

import com.zt1994.springwebfluxdemo.entity.User;
import com.zt1994.springwebfluxdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author zhongtao
 * @date 2022/5/15 11:27
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @GetMapping("/user/{id}")
    public Mono<User> getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    /**
     * 根据id查询用户
     *
     * @return
     */
    @GetMapping("/user")
    public Flux<User> getUsers() {
        return userService.getAllUser();
    }

    /**
     * 根据id查询用户
     *
     * @return
     */
    @PostMapping("/user/save")
    public Mono<Void> saveUser(@RequestBody User user) {
        Mono<User> userMono = Mono.just(user);
        return userService.saveUserInfo(userMono);
    }

}
