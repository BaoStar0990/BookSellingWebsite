package test;

import firebasecloud.FirebaseStorageUploader;

public class TestUploadingImage {
    public static void main(String[] args)
    {
        /*
        vấn đề ở đây là lúc làm thì trong method uploadImage sẽ có thể sinh ra link,
        Có the làm theo hướng là sẽ set luôn cái link đó cho book
        * */
        //Lúc truyền vào thì phải truyền hai tham số là đường dẫn tuyệt đối (vì giao diện chọn file có thể up đường dẫn tuyệt đối lên  và tên của file muốn lưu trên firebase
       // FirebaseStorageUploader.uploadImage("C:\\Learn\\Ki5\\Web\\data\\bookImage\\dear_darling.jpg","dear_darling");
    }
}
