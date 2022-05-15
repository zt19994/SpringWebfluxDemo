package com.zt1994.springwebfluxdemo.service.impl;

import com.zt1994.springwebfluxdemo.entity.User;
import com.zt1994.springwebfluxdemo.service.UserService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * UserServiceImpl
 *
 * @author zhongtao
 * @date 2022/5/15 11:14
 */
@Repository
public class UserServiceImpl implements UserService {

    /**
     * 创建map集合存储数据
     */
    private final Map<Integer, User> users = new HashMap<>();

    public UserServiceImpl() {
        this.users.put(1, new User("lucy", "男", 20));
        this.users.put(2, new User("mary", "女", 25));
        this.users.put(3, new User("jack", "男", 30));
    }

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @Override
    public Mono<User> getUserById(int id) {
        return Mono.justOrEmpty(this.users.get(id));
    }

    /**
     * 查询所有用户
     *
     * @return
     */
    @Override
    public Flux<User> getAllUser() {
        return Flux.fromIterable(this.users.values());
    }

    /**
     * 添加用户
     *
     * @param userMono
     * @return
     */
    @Override
    public Mono<Void> saveUserInfo(Mono<User> userMono) {
        return userMono.doOnNext(person -> {
            // 向map集合中放值
            int id = users.size() + 1;
            users.put(id, person);
        }).thenEmpty(Mono.empty());
    }
}
