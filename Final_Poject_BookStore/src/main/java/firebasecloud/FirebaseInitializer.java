package firebasecloud;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;

import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseInitializer {

    public static void initializeFirebase() throws IOException {
        FileInputStream serviceAccount
                = new FileInputStream(".\\serviceAccountKey.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("imageofbookandauthor.appspot.com") // Thay thế bằng tên bucket của bạn
                .build();

        //
        FirebaseApp.initializeApp(options);

    }
}
