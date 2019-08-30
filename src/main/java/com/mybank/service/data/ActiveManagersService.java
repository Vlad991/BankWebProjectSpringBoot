package com.mybank.service.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ActiveManagersService {
    //    private RedisTemplate<String, HashMap<String, WebSocketSession>> redisActiveUsersTemplate;
    private Map<String, WebSocketSession> activeUsers = new ConcurrentHashMap<>();

//    public ActiveManagersService(RedisTemplate<String, HashMap<String, WebSocketSession>> redisActiveUsersTemplate) {
//        this.redisActiveUsersTemplate = redisActiveUsersTemplate;
//    }

    //    public WebSocketSession getActiveManager(String login) {
//        return (WebSocketSession) redisActiveUsersTemplate.opsForHash().get("active-manager", login);
//    }
    public WebSocketSession getActiveManager(String login) {
        return activeUsers.get(login);
    }

    //    public void addActiveManager(String login, WebSocketSession session) {
//        redisActiveUsersTemplate.opsForHash().put("active-manager", login, session);
//    }
    public void addActiveManager(String login, WebSocketSession session) {
        activeUsers.put(login, session);
    }

    //    public void removeActiveManager(String login) { // todo right!!!!!!!! and check
//        redisActiveUsersTemplate.opsForHash().delete("active-manager", login);
//    }
    public void removeActiveManager(String login) {
        activeUsers.remove(login);
    }

    //    public Set<String> getActiveManagerLogins() {
//        Set<String> logins = new LinkedHashSet<>(); // todo why HashSet?
//        Set<Object> loginObjects = redisActiveUsersTemplate.opsForHash().keys("active-manager");
//        for (Object loginObject : loginObjects) {
//            logins.add(loginObject.toString());
//        }
//        return logins;
//    }
    public Set<String> getActiveManagerLogins() {
        Set<String> logins = activeUsers.keySet();
        return logins;
    }

    //    public List<WebSocketSession> getActiveManagerSessions() {
//        List<WebSocketSession> sessions = new ArrayList<>(); // todo why HashSet?
//        List<Object> sessionObjects = redisActiveUsersTemplate.opsForHash().values("active-manager");
//        for (Object sessionObject : sessionObjects) {
//            sessions.add((WebSocketSession) sessionObject);
//        }
//        return sessions;
//    }
    public List<WebSocketSession> getActiveManagerSessions() {
        List<WebSocketSession> sessions = new ArrayList<>(activeUsers.values());
        return sessions;
    }
}