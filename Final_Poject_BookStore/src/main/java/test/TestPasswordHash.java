package test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Base64;

public class TestPasswordHash {
    public static void main(String[] args){
        
        try {
            // -------- lấy pass -----------
            String salt = getSalt();
            String hashedPassword = HashPassword("8386" + salt);
            System.out.println(salt);
            System.out.println(hashedPassword);
            // ------- Test lấy pass --------
//                String salt = "BKsMuNc0eEIDs/mtecMkrDG7mHIkbixV5GpG6QP1XdA=";
//                String pass = "123456";
//                String hashedPassword = HashPassword(pass + salt);
//                if(hashedPassword.equalsIgnoreCase("2c658c681c61ea7c88694222aec9bf6bf7114df434b730d9bc33c17592c1592e"))
//                    System.out.println("Đăng nhập thành công");
//                else
//                    System.out.println("Đăng nhập không thành công");
                
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        
    }
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
     public static String hashAndSaltPassword(String password)
        throws NoSuchAlgorithmException {
        String salt = getSalt();
        return HashPassword(password + salt);
    }
}
