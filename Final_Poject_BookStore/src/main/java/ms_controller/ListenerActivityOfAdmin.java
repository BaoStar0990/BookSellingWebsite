/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ms_controller;
import dbmodel.AdminDB;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import model.Admin;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ServerEndpoint("/websocket")
public class ListenerActivityOfAdmin {
    private static final ConcurrentHashMap<String, Long> userLastPing = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    static {
        // Chạy phương thức checkUserStatus mỗi 5 giây
        scheduler.scheduleAtFixedRate(() -> {
            if(checkUserStatus() == false){
                System.out.println("Session has time out");
                Admin admin = AdminDB.getInstance().selectAdmin().get(0);
                admin.setStatus(false);
                AdminDB.getInstance().update(admin);
            }else{
                System.out.println("Session has not time out");
                userLastPing.forEach((sessionId, lastPingTime) -> {
                    System.out.println(sessionId + " - " + lastPingTime);
                });
            }
        }, 5, 5, TimeUnit.SECONDS);
    }
    @OnOpen
    public void onOpen(Session session) {
        userLastPing.put(session.getId(), System.currentTimeMillis());
        System.out.println("WebSocket connected: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Message received: " + message);
        if (message.contains("heartbeat")) {
            userLastPing.put(session.getId(), System.currentTimeMillis());
        }
    }

    @OnClose
    public void onClose(Session session) {
        userLastPing.remove(session.getId());
        System.out.println("WebSocket disconnected: " + session.getId());
    }

    public static boolean checkUserStatus()
    {
        long currentTime = System.currentTimeMillis();
        boolean  status = true;
        userLastPing.forEach((sessionId, lastPingTime) -> {
            if (currentTime - lastPingTime > (10000)) { // 10 giây timeout
                System.out.println("User " + sessionId + " might have closed the browser.");
                userLastPing.remove(sessionId);
            }
        });
        if(userLastPing.size() == 0)
            status = false;
        return status;
    }
}
