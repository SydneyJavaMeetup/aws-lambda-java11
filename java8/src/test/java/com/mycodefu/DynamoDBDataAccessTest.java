package com.mycodefu;

import static org.junit.jupiter.api.Assertions.*;

class DynamoDBDataAccessTest {

    @org.junit.jupiter.api.Test
    void createRecord() {
        DynamoDBDataAccess dynamoDBDataAccess = new DynamoDBDataAccess("LanguageTest");
        dynamoDBDataAccess.createRecord("hello", "there");

    }
}