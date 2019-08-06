package com.mybank.configuration;

import com.mybank.controller.WebSocketController;
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
        registry.addHandler(getWebSocketController(), "/socket")
                .setAllowedOrigins("*")
                .addInterceptors(new SecurityInterceptor(serviceName))
                .withSockJS();
    }

    @Bean
    public WebSocketController getWebSocketController(){
        return new WebSocketController();
    }
}
