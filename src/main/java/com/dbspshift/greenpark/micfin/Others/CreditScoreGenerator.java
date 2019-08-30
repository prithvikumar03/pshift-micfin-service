package com.dbspshift.greenpark.micfin.Others;

import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import springfox.documentation.spring.web.json.Json;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import static org.springframework.http.HttpHeaders.USER_AGENT;

public class CreditScoreGenerator {

    /*private Json requestCreditScore(){

    }*/

    // HTTP POST request
    /*public void sendPost() throws Exception {

        String url = "https://5xsvu5qi2e.execute-api.ap-southeast-1.amazonaws.com/default/micfin-lambda";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }*/

/*    public Mono<String> sendPostWebClient(){
        WebClient webClient = WebClient.create();

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.set("values","1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8");
        Mono<String> result = webClient.post()
                .uri( "https://5xsvu5qi2e.execute-api.ap-southeast-1.amazonaws.com/default/micfin-lambda" )
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept( MediaType.APPLICATION_JSON )
                .body( BodyInserters.fromFormData(formData))
                .retrieve().bodyToMono(String.class);

        System.out.println(result);
        return result;
    }*/


/*'LIMIT_BAL', 'SEX', 'EDUCATION', 'MARRIAGE', 'AGE', 'RS_0', 'RS_2',
        'RS_3', 'RS_4', 'RS_5', 'RS_6', 'PAY_AMT1', 'PAY_AMT2', 'PAY_AMT3',
        'PAY_AMT4', 'PAY_AMT5', 'PAY_AMT6'*/

    public void getCreditScore() throws Exception {
        URL url = new URL ("https://5xsvu5qi2e.execute-api.ap-southeast-1.amazonaws.com/default/micfin-lambda");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        String jsonInputString = "{\"values\": [1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8]}";

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }
    }

}