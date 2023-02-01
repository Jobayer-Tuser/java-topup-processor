package org.topupgateway.topup;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class ProcessCampaignClient {
    protected ResourceBundle prop = ResourceBundle.getBundle("config");
    public ProcessCampaignClient(){}
    public String runCampaign(ProcessCampaignEntity campaignEntity) throws IOException, InterruptedException {

        String params = createAJsonString(campaignEntity);

        HttpClient campaignClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("accept", "application/json")
                .uri(URI.create(prop.getString("apiUrl")))
                .POST(HttpRequest.BodyPublishers.ofString(params))
                .build();

        HttpResponse<String> httpResponse = campaignClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
//        System.out.println(httpResponse.body());
        parseJson(httpResponse.body());
        return httpResponse.body();
    }

    private String createAJsonString(ProcessCampaignEntity campaignEntity) {
        return "{" + "\"campaignLotId\":\"" + campaignEntity.campaignLotId() + "\"," + "\"referenceId\":\"" + campaignEntity.transactionId() + "\"," + "\"contactNumber\":\"" + campaignEntity.contactNumber() + "\"," + "\"balance\":\"" + campaignEntity.balance() + "\"," + "\"contactType\":\"" + campaignEntity.contactType() + "\"," + "\"contactOperator\":\"" + campaignEntity.contactOperator() + "\"" + "}";
    }

    private void parseJson(String value) {
        Map<String, Object> map = new HashMap<>();
        String[] pairs = value.replace("{", "").replace("}", "").split(",");

        Arrays.stream(pairs)
                .map(pair -> pair.split(":"))
                .forEach(keyValue -> map.put(keyValue[0], keyValue[1]));

        /* Split the array and put the value in map using foreach
            for (String pair: pairs){
                String keyValue[] = pair.split(":");
                map.put(keyValue[0], keyValue[1]);
            }
         */

            System.out.println(map);
    }

}
