package com.mybank.service.data;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class ActiveClientsService {
    private RedisTemplate<String, HashMap<String, WebSocketSession>> redisActiveUsersTemplate;

    public ActiveClientsService(RedisTemplate<String, HashMap<String, WebSocketSession>> redisActiveUsersTemplate) {
        this.redisActiveUsersTemplate = redisActiveUsersTemplate;
    }

    public void addActiveClient(String login, WebSocketSession session) {
        redisActiveUsersTemplate.opsForHash().put("active-client", login, session);
    }

    public void removeActiveClient(String login) { // todo right!!!!!!!! and check
        redisActiveUsersTemplate.opsForHash().delete("active-client", login);
    }

    public Set<String> getActiveClients() {
        Set<String> logins = new LinkedHashSet<>(); // todo why HashSet?
        Set<Object> loginObjects = redisActiveUsersTemplate.opsForHash().keys("active-client");
        for (Object loginObject : loginObjects) {
            logins.add(loginObject.toString());
        }
        return logins;
    }
}
