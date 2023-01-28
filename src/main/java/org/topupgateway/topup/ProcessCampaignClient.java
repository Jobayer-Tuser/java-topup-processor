package org.topupgateway.topup;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ResourceBundle;

public class ProcessCampaignClient {
    protected ResourceBundle prop = ResourceBundle.getBundle("config");
    public ProcessCampaignClient(){}
    public String runCampaign(ProcessCampaignEntity campaignEntity) throws IOException, InterruptedException {

        String params = createAJsonString(campaignEntity);

        HttpClient campaignClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("accept", "application/json")
                .uri(URI.create(prop.getString("apiUrl")))
                .POST(HttpRequest.BodyPublishers.ofString("hello"))
                .build();

        HttpResponse<String> httpResponse = campaignClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(httpResponse);
        return httpResponse.body();
    }

    public String createAJsonString(ProcessCampaignEntity campaignEntity){
        return "{" + "\"campaignLotId\":\"" + campaignEntity.campaignLotId() + "\"," + "\"referenceId\":\"" + campaignEntity.transactionId() + "\"," + "\"contactNumber\":\"" + campaignEntity.contactNumber() + "\"," + "\"balance\":\"" + campaignEntity.balance() + "\"," + "\"contactType\":\"" + campaignEntity.contactType() + "\"," + "\"contactOperator\":\"" + campaignEntity.contactOperator() + "\"" + "}";
    }

}