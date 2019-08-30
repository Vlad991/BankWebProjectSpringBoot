package com.mybank.service.data;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ActiveClientsService {
    //    private RedisTemplate<String, HashMap<String, WebSocketSession>> redisActiveUsersTemplate;
    private Map<String, WebSocketSession> activeUsers = new ConcurrentHashMap<>();

    //    public ActiveClientsService(RedisTemplate<String, HashMap<String, WebSocketSession>> redisActiveUsersTemplate) {
//        this.redisActiveUsersTemplate = redisActiveUsersTemplate;
//    }

    //    public WebSocketSession getActiveClient(String login) {
//        return (WebSocketSession) redisActiveUsersTemplate.opsForHash().get("active-client", login);
//    }
    public WebSocketSession getActiveClient(String login) {
        return activeUsers.get(login);
    }

    //    public void addActiveClient(String login, WebSocketSession session) {
//        redisActiveUsersTemplate.opsForHash().put("active-client", login, session);
//    }
    public void addActiveClient(String login, WebSocketSession session) {
        activeUsers.put(login, session);
    }

    //    public void removeActiveClient(String login) { // todo right!!!!!!!! and check
//        redisActiveUsersTemplate.opsForHash().delete("active-client", login);
//    }
    public void removeActiveClient(String login) { // todo right!!!!!!!! and check
        activeUsers.remove(login);
    }

    //    public Set<String> getActiveClientLogins() {
//        Set<String> logins = new LinkedHashSet<>(); // todo why HashSet?
//        Set<Object> loginObjects = redisActiveUsersTemplate.opsForHash().keys("active-client");
//        for (Object loginObject : loginObjects) {
//            logins.add(loginObject.toString());
//        }
//        return logins;
//    }
    public Set<String> getActiveClientLogins() {
        Set<String> logins = activeUsers.keySet();
        return logins;
    }

    //    public List<WebSocketSession> getActiveClientSessions() {
//        List<WebSocketSession> sessions = new ArrayList<>(); // todo why HashSet?
//        List<Object> sessionObjects = redisActiveUsersTemplate.opsForHash().values("active-client");
//        for (Object sessionObject : sessionObjects) {
//            sessions.add((WebSocketSession) sessionObject);
//        }
//        return sessions;
//    }
    public List<WebSocketSession> getActiveClientSessions() {
        List<WebSocketSession> sessions = new ArrayList<>(activeUsers.values());
        return sessions;
    }
}