package kg.giftlist.giftlistm2.config;

import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config {
    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secret;

    @Value("${cloud.aws.reqion.static}")
    private String reqion;

    @Bean
    public AmazonS3 s3() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secret);
        return AmazonS3ClientBuilder.standard().withRegion(region).
                withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();

    }
}