package org.topupgateway.topup;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProcessCampaign extends DBConnect{
    protected ResourceBundle prop = ResourceBundle.getBundle("config");
    public ProcessCampaign() throws SQLException {}
    public List<ProcessCampaignEntity> getAllContactsForCampaign(int numberOfSecondaryThread, int numberOfPrimaryThread) throws SQLException {

        PreparedStatement queryStatement = connection.prepareStatement(SQLToGetContacts(numberOfSecondaryThread, numberOfPrimaryThread));
        ResultSet campaignRecord = queryStatement.executeQuery();

        List<ProcessCampaignEntity> contacts = new ArrayList<>();

        while (campaignRecord.next()){
            ProcessCampaignEntity campaignEntity =
                new ProcessCampaignEntity(
                    campaignRecord.getInt("id"),
                    campaignRecord.getLong("campaign_lot_id"),
                    campaignRecord.getString("contact_number"),
                    campaignRecord.getString("contact_operator"),
                    campaignRecord.getString("service_type"),
                    campaignRecord.getString("contact_type"),
                    campaignRecord.getDouble("balance"),
                    campaignRecord.getString("process_status"),
                    campaignRecord.getString("transaction_id"),
                    campaignRecord.getTimestamp("created_at"),
                    campaignRecord.getTimestamp("updated_at")
                );
            contacts.add(campaignEntity);
        }
        return contacts;
    }

    private String SQLToGetContacts(int numberOfSecondaryThread, int numberOfPrimaryThread){
        String Sql=  "SELECT id, campaign_lot_id, contact_number, contact_operator, service_type, contact_type, balance, process_status , transaction_id, created_at, updated_at " +
                "FROM campaign_processor WHERE id % " + numberOfSecondaryThread + " = " + numberOfPrimaryThread +
                " AND process_status = 'Pending' " +
                "LIMIT " + prop.getString("noOfSubThread");

        return "SELECT id, campaign_lot_id, contact_number, contact_operator, service_type, contact_type, balance, process_status , transaction_id, created_at, updated_at " +
                "FROM campaign_processor WHERE process_status = 'Pending' " + "LIMIT " + prop.getString("noOfSubThread");
    }


}
