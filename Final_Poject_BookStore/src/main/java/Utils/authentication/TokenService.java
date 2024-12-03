/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils.authentication;

import java.util.HashMap;
import java.util.Map;

public class TokenService {
    // Một map để lưu trữ token cho các addressId, giả định dùng cho ví dụ
    private Map<Integer, String> tokenStore = new HashMap<>();

    // Lưu token kèm theo thông tin người dùng và ID của Address
    public void storeToken(String token, Integer id) {
        // Lưu token vào hệ thống, lưu bộ nhớ tạm
        tokenStore.put(id, token);
        System.out.println("Lưu token: " + token + " cho addressId: " + id);
    }

    // Kiểm tra token (giả sử bạn cần logic để xác thực token)
    public boolean validateToken(String token, Integer id) {
        return token.equals(tokenStore.get(id));
    }

    // Xóa token
    public void deleteToken(Integer id) {
        tokenStore.remove(id);
    }
}