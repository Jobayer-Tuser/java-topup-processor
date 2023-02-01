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

        Map<String, String> mapCampaign = parseJsonResponse(httpResponse.body());
        setObjectPropertyValue(mapCampaign);

        return httpResponse.body();
    }

    private void setObjectPropertyValue(Map<String, String> campaign) {
        new ResponseMapping(campaign.get("contactOperator"), campaign.get("contactNumber"));
    }

    private String createAJsonString(ProcessCampaignEntity campaignEntity) {
        return "{" + "\"campaignLotId\":\"" + campaignEntity.campaignLotId() + "\"," + "\"referenceId\":\"" + campaignEntity.transactionId() + "\"," + "\"contactNumber\":\"" + campaignEntity.contactNumber() + "\"," + "\"balance\":\"" + campaignEntity.balance() + "\"," + "\"contactType\":\"" + campaignEntity.contactType() + "\"," + "\"contactOperator\":\"" + campaignEntity.contactOperator() + "\"" + "}";
    }

    private Map<String, String> parseJsonResponse(String responseData) {

        Map<String, String> records = new HashMap<>();
        String[] splitResponseData = responseData.replace("{\"", "").replace("\"}", "").split(",");

        Arrays.stream(splitResponseData)
                .map(eachResponse -> eachResponse.split(":"))
                .forEach(keyValue -> {

                    String key = keyValue[0].trim().replace("\"", "");
                    String value = keyValue[1].trim().replace("\"", "");

                    records.put(key, value);
                });

        System.out.println(records);
        return records;
    }

}
