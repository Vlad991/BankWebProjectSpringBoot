package com.mybank.service.data;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ActiveAdminsService {
    //    private RedisTemplate<String, HashMap<String, WebSocketSession>> redisActiveUsersTemplate;
    private Map<String, WebSocketSession> activeUsers = new ConcurrentHashMap<>();

//    public ActiveAdminsService(RedisTemplate<String, HashMap<String, WebSocketSession>> redisActiveUsersTemplate) {
//        this.redisActiveUsersTemplate = redisActiveUsersTemplate;
//    }


    //    public WebSocketSession getActiveAdmin(String login) {
//        return (WebSocketSession) redisActiveUsersTemplate.opsForHash().get("active-admin", login);
//    }
    public WebSocketSession getActiveAdmin(String login) {
        return activeUsers.get(login);
    }

    //    public void addActiveAdmin(String login, WebSocketSession session) {
//        redisActiveUsersTemplate.opsForHash().put("active-admin", login, session);
//    }
    public void addActiveAdmin(String login, WebSocketSession session) {
        activeUsers.put(login, session);
    }

    //    public void removeActiveAdmin(String login) { // todo right!!!!!!!! and check
//        redisActiveUsersTemplate.opsForHash().delete("active-admin", login);
//    }
    public void removeActiveAdmin(String login) { // todo right!!!!!!!! and check
        activeUsers.remove(login);
    }

    //    public Set<String> getActiveAdminLogins() {
//        Set<String> logins = new LinkedHashSet<>(); // todo why HashSet?
//        Set<Object> loginObjects = redisActiveUsersTemplate.opsForHash().keys("active-admin");
//        for (Object loginObject : loginObjects) {
//            logins.add(loginObject.toString());
//        }
//        return logins;
//    }
    public Set<String> getActiveAdminLogins() {
        Set<String> logins = activeUsers.keySet();
        return logins;
    }

    //    public List<WebSocketSession> getActiveAdminSessions() {
//        List<WebSocketSession> sessions = new ArrayList<>(); // todo why HashSet?
//        List<Object> sessionObjects = redisActiveUsersTemplate.opsForHash().values("active-admin");
//        for (Object sessionObject : sessionObjects) {
//            sessions.add((WebSocketSession) sessionObject);
//        }
//        return sessions;
//    }
    public List<WebSocketSession> getActiveAdminSessions() {
        List<WebSocketSession> sessions = new ArrayList<>(activeUsers.values());
        return sessions;
    }
}