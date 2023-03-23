package com.ssafy.ChallenMungs.common.config;

import com.ssafy.ChallenMungs.panel.handler.PanelSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class SocketConfigurer implements WebSocketConfigurer {
    @Autowired
    PanelSocketHandler socketHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketHandler, "/panelSocket").setAllowedOrigins("*");
    }
}
