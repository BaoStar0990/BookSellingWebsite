package firebasecloud;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FirebaseStorageUploader {
    public static void uploadImage(String localFilePath, String firebaseFilePath) throws IOException {

        FirebaseInitializer.initializeFirebase();
        Bucket bucket = StorageClient.getInstance().bucket();
        byte[] fileBytes = Files.readAllBytes(Paths.get(localFilePath));
        Blob blob = bucket.create(firebaseFilePath, fileBytes, "image/jpeg");

        System.out.println("Upload thành công! URL: " + blob.getMediaLink());
    }
}
