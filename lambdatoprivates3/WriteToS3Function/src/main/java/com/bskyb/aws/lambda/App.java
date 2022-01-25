package com.bskyb.aws.lambda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.bskyb.aws.lambda.factory.AwsObjectFactory;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<String, Map<String, Object>> {
    private final S3Client s3Client;

    private final String BUCKET_NAME = System.getenv("RESULTS_BUCKET");

    public App() {
        this.s3Client = AwsObjectFactory.s3client();
    }

    public Map<String, Object> handleRequest(final String input, final Context context) {
        String fileName = "Results" + DateTime.now();

        String fileContent = input + " written at " + DateTime.now();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(fileName)
                .build();

        this.s3Client.putObject(putObjectRequest, RequestBody.fromString(fileContent));

        /*
        Side effects
        generates additional classes
        non-static method holds reference of the object and cannot be garbage collected
        Map<String, Object> map = new HashMap<String, Object>() {{ put(putObjectRequest.bucket(), putObjectRequest.key()); }};
        */

        Map<String, Object> map = new HashMap<String, Object>();

        map.put(putObjectRequest.bucket(), putObjectRequest.key());

        return map;
    }
}
