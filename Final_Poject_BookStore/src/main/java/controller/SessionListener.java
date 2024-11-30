package controller;
import dbmodel.AdminDB;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import model.Admin;

@WebListener
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        // Lấy thông tin người dùng từ session
        Admin admin = (Admin) event.getSession().getAttribute("admin");
        if (admin != null) {
            admin.setStatus(false);
            AdminDB.getInstance().update(admin);
            System.out.println("Admin logged out");
        }else{
            System.out.println("From session listener: admin not exists in sesson");
        }
    }
}
