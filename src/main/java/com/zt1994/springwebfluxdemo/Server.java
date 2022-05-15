package com.zt1994.springwebfluxdemo;

import com.zt1994.springwebfluxdemo.handler.UserHandler;
import com.zt1994.springwebfluxdemo.service.UserService;
import com.zt1994.springwebfluxdemo.service.impl.UserServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.netty.http.server.HttpServer;

import java.io.IOException;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;

/**
 * Server
 *
 * @author zhongtao
 * @date 2022/5/15 15:33
 */
public class Server {

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.createReactorServer();
        System.out.println("enter to exit");
        System.in.read();
    }

    /**
     * 创建Router路由
     *
     * @return
     */
    public RouterFunction<ServerResponse> routingFunction() {
        UserService userService = new UserServiceImpl();
        UserHandler handler = new UserHandler(userService);

        return RouterFunctions.route(
                GET("/users/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::getUserById)
                .andRoute( GET("/users").and(accept(MediaType.APPLICATION_JSON)),  handler::getAllUsers);
    }

    /**
     * 创建服务器完成适配
     */
    public void createReactorServer() {
        // 路由和handler适配
        RouterFunction<ServerResponse> route = routingFunction();
        HttpHandler httpHandler = toHttpHandler(route);
        ReactorHttpHandlerAdapter handlerAdapter = new ReactorHttpHandlerAdapter(httpHandler);

        // 创建服务器
        HttpServer httpServer = HttpServer.create();
        httpServer.handle(handlerAdapter).bindNow();
    }
}
