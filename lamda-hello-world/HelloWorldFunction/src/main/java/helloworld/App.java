package helloworld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class App implements RequestHandler<String, Map<String,String>> {

    /*
    * altered the method arguments
    * removed apigateway parameter and used simple string input
    * */
    public Map<String, String> handleRequest(final String input, final Context context) {

        Map<String, String> myMap = new HashMap<>();
        myMap.put("your input to this programme running in lamba was: ", input);
        System.out.println("Profile" + System.getenv("Q_PROFILE"));
        return myMap;
    }

    private String getPageContents(String address) throws IOException{
        URL url = new URL(address);
        try(BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
