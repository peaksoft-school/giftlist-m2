package kg.giftlist.giftlistm2.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface AmazonS3Service {

     void uploadFileToS3Bucket(MultipartFile multipartFile, boolean enablePublicReadAccess);

     void deleteFileFromS3Bucket(String fileName);
}
