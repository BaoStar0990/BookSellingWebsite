package firebasecloud;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FirebaseStorageUploader {
   // StorageClient.getInstance().bucket().create(fileName, inputStream, filePart.getContentType());
    public static String uploadImage(InputStream inputStreamm, String contentType , String nameOfFileOnCloud) {
        try {
            FirebaseInitializer.initializeFirebase();
            Bucket bucket = StorageClient.getInstance().bucket();
//            byte[] fileBytes = Files.readAllBytes(Paths.get(pathOfImage));
//
//            String mimeType = Files.probeContentType(Paths.get(pathOfImage));
            Blob blob = bucket.create(nameOfFileOnCloud, inputStreamm, contentType);

            String url = "https://firebasestorage.googleapis.com/v0/b/imageofbookandauthor.appspot.com/o/"+nameOfFileOnCloud+"?alt=media";
            System.out.println(url);
            return url;
        } catch (IOException e) {
            System.err.println("Lỗi khi upload file: " + e.getMessage());
            return "";
        }

    }
}
