package com.mybank.configuration;

import com.mybank.controller.websocket.AdminWebSocketController;
import com.mybank.controller.websocket.ClientWebSocketController;
import com.mybank.controller.websocket.ManagerWebSocketController;
import com.mybank.interceptor.AdminSecurityInterceptor;
import com.mybank.interceptor.ClientSecurityInterceptor;
import com.mybank.interceptor.ManagerSecurityInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer { //todo

    @Value("${mybank.serviceName}")
    private String serviceName;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(clientWebSocketController(), "/client-socket")
                .setAllowedOrigins("*")
                .addInterceptors(new ClientSecurityInterceptor(serviceName))
                .withSockJS();
        registry.addHandler(managerWebSocketController(), "/manager-socket")
                .setAllowedOrigins("*")
                .addInterceptors(new ManagerSecurityInterceptor(serviceName))
                .withSockJS();
        registry.addHandler(adminWebSocketController(), "/admin-socket")
                .setAllowedOrigins("*")
                .addInterceptors(new AdminSecurityInterceptor(serviceName))
                .withSockJS();
    }

    @Bean
    public ClientWebSocketController clientWebSocketController(){
        return new ClientWebSocketController();
    }

    @Bean
    public ManagerWebSocketController managerWebSocketController(){
        return new ManagerWebSocketController();
    }

    @Bean
    public AdminWebSocketController adminWebSocketController(){
        return new AdminWebSocketController();
    }
}
