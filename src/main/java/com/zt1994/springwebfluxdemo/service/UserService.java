package com.zt1994.springwebfluxdemo.service;

import com.zt1994.springwebfluxdemo.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * UserService
 *
 * @author zhongtao
 * @date 2022/5/15 11:05
 */
public interface UserService {

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    Mono<User> getUserById(int id);

    /**
     * 查询所有用户
     *
     * @return
     */
    Flux<User> getAllUser();

    /**
     * 添加用户
     *
     * @param userMono
     * @return
     */
    Mono<Void> saveUserInfo(Mono<User> userMono);

}
