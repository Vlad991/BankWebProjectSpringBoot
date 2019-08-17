package com.mybank.service.data;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class ActiveClientsService {
    private RedisTemplate<String, HashMap<String, WebSocketSession>> redisTemplate;

    public ActiveClientsService(RedisTemplate<String, HashMap<String, WebSocketSession>> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void addActiveClient(String login, WebSocketSession session) {
        redisTemplate.opsForHash().put("active-client", login, session);
    }

    public void removeActiveClient(String login) { // todo right!!!!!!!! and check
        redisTemplate.opsForHash().delete("active-client", login);
    }

    public Set<String> getActiveClients() {
        Set<String> logins = new LinkedHashSet<>();
        Set<Object> loginObjects = redisTemplate.opsForHash().keys("active-client");
        for (Object loginObject : loginObjects) {
            logins.add(loginObject.toString());
        }
        return logins;
    }
}
