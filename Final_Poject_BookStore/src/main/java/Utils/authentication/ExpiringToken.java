package Utils.authentication;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionBindingListener;
import java.util.Timer;
import java.util.TimerTask;
public class ExpiringToken implements HttpSessionBindingListener {
    private final String value;
    private final long expiryTime; // Thời gian tồn tại (ms)
    private  String nameOfTokenSetInSession; // Thời gian tồn tại (ms)


    public ExpiringToken(String value, long expiryTimeInSeconds,String nameOfTokenSetInSession) {
        this.value = value;
        this.expiryTime = System.currentTimeMillis() + expiryTimeInSeconds * 1000;
        this.nameOfTokenSetInSession = nameOfTokenSetInSession;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        // Khi token được thêm vào session
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Xóa token khi hết hạn
                event.getSession().removeAttribute(nameOfTokenSetInSession);
                System.out.println("Token đã hết hạn và bị xóa khỏi session.");
            }
        }, expiryTime - System.currentTimeMillis());
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        // Khi token bị xóa khỏi session
        System.out.println("Token đã được xóa khỏi session.");
    }
}
