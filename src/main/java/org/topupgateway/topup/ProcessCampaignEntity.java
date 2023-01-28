package org.topupgateway.topup;

import java.sql.Timestamp;
public record ProcessCampaignEntity(
        int id, long campaignLotId, String contactNumber, String contactOperator,
        String serviceType, String contactType, double balance, String processStatus, String transactionId,
        Timestamp createdAt, Timestamp updatedAt
) { }
