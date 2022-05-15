package com.zt1994.springwebfluxdemo;

import com.zt1994.springwebfluxdemo.entity.User;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * Client
 *
 * @author zhongtao
 * @date 2022/5/15 15:53
 */
public class Client {

    public static void main(String[] args) {
        // 调用服务器地址
        WebClient webClient = WebClient.create("http://127.0.0.1:8963");

        // 根据id查询
        String id = "1";
        User block = webClient.get().uri("/users/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(User.class)
                .block();
        System.out.println(block.getName());

        Flux<User> userResult = webClient.get().uri("/users")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(User.class);
        userResult.map(user -> user.getName())
                .buffer()
                .doOnNext(System.out::println).blockFirst();
    }
}
