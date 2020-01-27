package com.mycodefu;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

import java.time.Instant;
import java.util.UUID;

public class DynamoDBDataAccess {
    static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    static DynamoDB dynamoDB = new DynamoDB(client);

    private String tableName;

    public DynamoDBDataAccess(String tableName) {
        this.tableName = tableName;
    }

    public String createRecord(String group, String value) {
        Table table = dynamoDB.getTable(tableName);
        try {

            String id = UUID.randomUUID().toString();
            Item item = new Item()
                    .withPrimaryKey("TestGroup", group, "TestId", id)
                    .withNumber("ModifiedDateEpoch", Instant.now().toEpochMilli())
                    .withString("Value", value);

            table.putItem(item);
            return id;

        } catch (Exception e) {
            throw new RuntimeException("Create item failed.", e);
        }
    }

    public String readRecordValue(String group, String id){
        Table table = dynamoDB.getTable(tableName);
        Item item = table.getItem("TestGroup", group, "TestId", id);
        return item.getString("Value");
    }
}
