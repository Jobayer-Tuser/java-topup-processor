package org.topupgateway.thread;

import org.topupgateway.topup.ProcessCampaignClient;
import org.topupgateway.topup.ProcessCampaignEntity;

public class SecondaryThread implements Runnable{
    private final ProcessCampaignEntity campaignEntity;
    private final ProcessCampaignClient campaignClient = new ProcessCampaignClient();
    public SecondaryThread(ProcessCampaignEntity campaignEntity) {
        this.campaignEntity = campaignEntity;
    }

    @Override
    public void run() {
        try{
            String runCampaign = campaignClient.runCampaign(campaignEntity);
//            System.out.println(runCampaign);

        } catch (Exception exception){

        }
    }
}
