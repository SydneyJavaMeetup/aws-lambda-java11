'use strict';

const AWS = require('aws-sdk');
AWS.config.update({region: 'ap-southeast-2'});

const uuid = require('uuid/v1');
const dynamoDb = new AWS.DynamoDB.DocumentClient();

const TABLE_NAME = "LanguageTest";

exports.handler = async (event) => {
    const queryStringParameters = event.queryStringParameters ? event.queryStringParameters : {};

    const id = uuid();
    const group = queryStringParameters.group ? queryStringParameters.group : "DefaultGroup";
    const value = queryStringParameters.value ? queryStringParameters.value : "DefaultValue";

    const start = new Date();
    try {
        await dynamoDb.put({
            TableName: "LanguageTest",
            Item: {
                TestGroup: group,
                TestId: id,
                ModifiedDateEpoch: new Date().getTime(),
                Value: value
            }
        }).promise();
    } catch (e) {
        console.log(`Error trying to put:\n${e}`);
    }
    let itemReadBack;
    try {
        itemReadBack = await dynamoDb.get({
            TableName: TABLE_NAME,
            Key: {
                TestGroup: group,
                TestId: id
            }
        }).promise();
    } catch (e) {
        console.log(`Error trying to read:\n${e}`)
    }

    const duration = new Date() - start;

    let response = {
        message: `NodeJS 12 Lambda wrote and read group '${group}', value '${itemReadBack.Item.Value}' in ${duration}ms!`
    };
    if (queryStringParameters.debug === "true") {
        response.event = event;
    }

    return {
        statusCode: 200,
        headers: {
            "content-type": "application/json"
        },
        body: JSON.stringify(response),
    };
};