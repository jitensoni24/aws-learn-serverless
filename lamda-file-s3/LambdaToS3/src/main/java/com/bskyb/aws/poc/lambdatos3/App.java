package com.bskyb.aws.poc.lambdatos3;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.joda.time.DateTime;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<String, Map<String, Object>> {

    private final S3Client s3Client;

    private String BUCKET_NAME = System.getenv("ENV_BUCKET_NAME");

    public App() {
        this.s3Client = AwsObjectFactory.s3Client();
    }

    public Map<String, Object> handleRequest(final String text, final Context context) {
        System.out.println("your input to lambda was : " + text);

        Map<String, Object> map = new HashMap<>();

        String fileName = "js-file"+System.currentTimeMillis()+".txt";

        String fileContent = new StringBuilder().append(text).append(" at ").append(DateTime.now()).toString();

        try {
            File sampleFile = createSampleFile(text);

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(fileName)
                .build();

            this.s3Client.putObject(putObjectRequest, RequestBody.fromString(fileContent));

            map.put(putObjectRequest.bucket(), putObjectRequest.key());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    public File createSampleFile(String text) throws IOException {
        File file = File.createTempFile("lambda-to-s3", ".txt");
        file.deleteOnExit();

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
            writer.write(text);
        }

        return file;
    }
}
