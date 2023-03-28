package com.example.sprintToDo.demo.service.fileUpload;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.sprintToDo.demo.dto.UserLogin;
import com.example.sprintToDo.demo.service.email.EmailService;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;

import java.io.File;
import java.util.Date;

@PropertySource("classpath:application.properties")
@RequiredArgsConstructor
public class ExcelFileUpload {
    Dotenv dotenv = Dotenv.load();

    private final EmailService emailService;

    public Boolean s3Upload(UserLogin user, String filePath , String fileName, String fileType) {
        final String AWS_ACCESS_KEY_ID = dotenv.get("AWS_ACCESS_KEY_ID");
        final String AWS_SECRET_ACCESS_KEY = dotenv.get("AWS_SECRET_ACCESS_KEY");
        final String AWS_BUCKET = dotenv.get("AWS_BUCKET");
        File file = new File(filePath);
        Date date = new Date();
        String date2 = date.toString();
        String replaceDate = date2.replace(" ","+");
        // Instantiate an AmazonS3 client with your credentials
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(
                        AWS_ACCESS_KEY_ID,
                        AWS_SECRET_ACCESS_KEY)))
                .build();

        try {
            // Create a PutObjectRequest with the file contents, bucket name, and object key
            PutObjectRequest request = new PutObjectRequest(
                    AWS_BUCKET,
                    fileName + replaceDate + "." + fileType,
                    file
            );

            // Upload the file to Amazon S3 asynchronously
            PutObjectResult result = s3Client.putObject(request);

            final String url = AWS_BUCKET+".s3.amazonaws.com/"+fileName+replaceDate+"."+fileType;
            String urlReplace = url.replace(" ", "+");
            String urlReplace2 = url.replace("+", "%2B");
            String urlReplace3 = urlReplace2.replace(":", "%3A");
            String fileUrl = "https://"+urlReplace3;
            System.out.println(fileUrl);
            emailService.sendExcelEmail(user.getEmail(),fileUrl);
        } catch (AmazonServiceException e) {
            // Handle Amazon S3 service errors
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Handle client errors
            e.printStackTrace();
        }
        return true;
    }



}
