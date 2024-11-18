package Utils.authentication;

import java.util.UUID;

public class CSRFUtil {
    public static String getCSRFToken() {
        // Tạo token CSRF và lưu vào session
        String csrfToken = UUID.randomUUID().toString();
        return csrfToken;
    }


}
