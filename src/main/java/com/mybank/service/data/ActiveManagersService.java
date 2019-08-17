package com.mybank.service.data;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

@Service
public class ActiveManagersService {
    private RedisTemplate<String, HashMap<String, WebSocketSession>> redisActiveUsersTemplate;

    public ActiveManagersService(RedisTemplate<String, HashMap<String, WebSocketSession>> redisActiveUsersTemplate) {
        this.redisActiveUsersTemplate = redisActiveUsersTemplate;
    }

    public void addActiveManager(String login, WebSocketSession session) {
        redisActiveUsersTemplate.opsForHash().put("active-manager", login, session);
    }

    public void removeActiveManager(String login) { // todo right!!!!!!!! and check
        redisActiveUsersTemplate.opsForHash().delete("active-manager", login);
    }

    public Set<String> getActiveManagerLogins() {
        Set<String> logins = new LinkedHashSet<>(); // todo why HashSet?
        Set<Object> loginObjects = redisActiveUsersTemplate.opsForHash().keys("active-manager");
        for (Object loginObject : loginObjects) {
            logins.add(loginObject.toString());
        }
        return logins;
    }

    public List<WebSocketSession> getActiveManagerSessions() {
        List<WebSocketSession> sessions = new ArrayList<>(); // todo why HashSet?
        List<Object> sessionObjects = redisActiveUsersTemplate.opsForHash().values("active-manager");
        for (Object sessionObject : sessionObjects) {
            sessions.add((WebSocketSession) sessionObject);
        }
        return sessions;
    }
}
