package com.ssafy.ChallenMungs.common.config;

import com.ssafy.ChallenMungs.challenge.panel.handler.PanelSocketHandler;
import com.ssafy.ChallenMungs.challenge.treasure.handler.TreasureSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class SocketConfigurer implements WebSocketConfigurer {

    @Autowired
    PanelSocketHandler panelSocketHandler;

    @Autowired
    TreasureSocketHandler treasureSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(panelSocketHandler, "/panelSocket").setAllowedOrigins("*");
        registry.addHandler(treasureSocketHandler, "/treasureSocket").setAllowedOrigins("*");
    }
}