import firebasecloud.FirebaseStorageUploader;

import java.io.IOException;

public class main {
    public static void main(String[] args) {
        try {
            FirebaseStorageUploader.uploadImage("C:\\Learn\\Ki5\\Web\\final_project\\Final_Poject_BookStore\\src\\main\\java\\firebasecloud\\cong_chua_nho.jpg", "uploads/example.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
