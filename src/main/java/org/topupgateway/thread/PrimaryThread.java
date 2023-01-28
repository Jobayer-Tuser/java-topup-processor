package org.topupgateway.thread;

import org.topupgateway.topup.ProcessCampaign;
import org.topupgateway.topup.ProcessCampaignEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PrimaryThread implements Runnable{
    private final int numberOfSubThread;
    private final int numberOfPrimaryThread;
    private final ProcessCampaign processCampaign = new ProcessCampaign();

    public PrimaryThread(int numberOfSubThread, int numberOfPrimaryThread) throws SQLException {
        this.numberOfPrimaryThread = numberOfPrimaryThread;
        this.numberOfSubThread = numberOfSubThread;
    }
    @Override
    public void run() {
//        System.out.println("Primary" + numberOfPrimaryThread);
//        System.out.println("Secondary" + numberOfSubThread);

        try {
            List<ProcessCampaignEntity> allContactsForCampaign = processCampaign.getAllContactsForCampaign(numberOfSubThread, numberOfPrimaryThread);
//            System.out.println(allContactsForCampaign);

            if (allContactsForCampaign.size() > 0){
                ExecutorService subexecutor = Executors.newFixedThreadPool(allContactsForCampaign.size());

                for (ProcessCampaignEntity campaignEntity : allContactsForCampaign){
                    Runnable SubThreadWorker = new SecondaryThread(campaignEntity);
                    subexecutor.execute(SubThreadWorker);
                    Thread.sleep(200);
                }
                subexecutor.shutdown();
            } else {
                System.out.println("");
            }

        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
