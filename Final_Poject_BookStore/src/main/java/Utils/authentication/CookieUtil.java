package Utils.authentication;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class CookieUtil {
    public static Cookie getCookie(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(name)) {
                    return cookie; // Trả về cookie nếu tìm thấy
                }
            }
        }
        return null; // Trả về null nếu không tìm thấy
    }
}
