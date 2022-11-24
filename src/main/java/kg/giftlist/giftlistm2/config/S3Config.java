package kg.giftlist.giftlistm2.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value("AKIA2HDIMUN776Z3S45T")
    private String accessKey;

    @Value("nnB9tOC/d8DbN6r2IzVS/PRF3/xXyONyLKh++pNT")
    private String secret;

    @Value("eu-central-1")
    private String region;

    @Bean
    public AmazonS3 s3(){

        AWSCredentials awsCredentials=new BasicAWSCredentials(accessKey,secret);

        return AmazonS3ClientBuilder.standard().
                withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).withRegion(region).build();

    }
}
