package PasswordUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

public class PasswordUtil {
    public static String HashPassword(String password)
        throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256"); // Sử dụng SHA-256 để băm
        md.update(password.getBytes());
        byte[] mdArray = md.digest();

        StringBuilder sb = new StringBuilder(mdArray.length * 2);
        for (byte b : mdArray) { 
            int v = b & 0xFF; // Đảm bảo lấy giá trị dương từ byte
            if (v < 16) {
                sb.append('0'); // Thêm '0' nếu giá trị nhỏ hơn 16
            }
            sb.append(Integer.toHexString(v)); // Chuyển đổi thành chuỗi hex
        }
        return sb.toString();
    }
    public static String getSalt() {
        Random r = new SecureRandom();
        byte[] saltBytes = new byte[32];
        r.nextBytes(saltBytes); // Lấy 32 byte ngẫu nhiên và lưu vào mảng saltBytes
        return Base64.getEncoder().encodeToString(saltBytes); // Mã hóa mảng byte thành chuỗi Base64
        // Chuỗi Base64 chỉ chứa các ký tự an toàn như A-Z, a-z, 0-9, +, và /
    }
    public static String HashAndSaltPassword(String password)
        throws NoSuchAlgorithmException {
        String salt = getSalt();
        return HashPassword(password + salt);
    }
}
