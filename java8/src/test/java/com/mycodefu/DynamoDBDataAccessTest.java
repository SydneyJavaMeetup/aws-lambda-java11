package com.mycodefu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DynamoDBDataAccessTest {

    @Test
    void createRecord() {
        DynamoDBDataAccess dynamoDBDataAccess = new DynamoDBDataAccess("LanguageTest");
        String id = dynamoDBDataAccess.createRecord("hello", "there");
        System.out.println(id);
    }

    @Test
    void readRecord() {
        DynamoDBDataAccess dynamoDBDataAccess = new DynamoDBDataAccess("LanguageTest");
        String value = dynamoDBDataAccess.readRecordValue("hello", "b1658f9e-3c7c-4945-9f9c-ecb75bff9cba");
        System.out.println(value);
    }
}