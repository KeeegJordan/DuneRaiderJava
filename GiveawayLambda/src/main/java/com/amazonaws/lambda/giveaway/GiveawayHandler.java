package com.amazonaws.lambda.giveaway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GiveawayHandler implements RequestHandler<String, String> {

	private Context context;
	
	@Override
	public String handleRequest(String input, Context context) {
		this.context = context;
		context.getLogger().log("getting token for user: " + input.toString());
		String accessToken = getTokenFromDynamoDB(input);
		context.getLogger().log("Found token in DynamoDB: " + accessToken.substring(0, 6) + "...");
		
		context.getLogger().log("Grabbing posts from Instagram API");
		String postJson = getAllPosts(accessToken);
		context.getLogger().log("Recieved respose from Instagram: " + postJson);
		//String giveawayPosts = giveawayPosts(postJson);
		
		//context.getLogger().log("Returning giveaway posts");
		return "";
	}
	
	private String getTokenFromDynamoDB(String userId) {
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
				.withRegion(Regions.US_EAST_1)
				.build();
		
		Map<String, AttributeValue> key = new HashMap<String, AttributeValue>();
		AttributeValue value = new AttributeValue();
		value.setS(userId);
		key.put("username", value);
		
		GetItemRequest req = new GetItemRequest("auth_keys", key);
		GetItemResult res = client.getItem(req);
		
		//TODO: throw error on empty result
		return res.getItem().get("token").getS();
	}
	
	private String getAllPosts(String accessToken) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = 
            		new HttpGet("https://api.instagram.com/v1/users/self/media/recent/" + 
            					"?access_token=" + accessToken);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            try {
                System.out.println(response.getStatusLine());
                HttpEntity entity = response.getEntity();
                
                BufferedReader in = new BufferedReader(
            		  new InputStreamReader(entity.getContent()));
            		String inputLine;
            		StringBuffer content = new StringBuffer();
            		while ((inputLine = in.readLine()) != null) {
            		    content.append(inputLine);
            		}
            		in.close();
                
            		return content.toString();
            } finally {
                response.close();
            }
        } catch (IOException e) {
			e.printStackTrace();
		} finally {
            try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		return null;
	}
	
	private String giveawayPosts(String allPostJson) {
		JSONObject posts = new JSONObject(allPostJson);
		
		JSONArray postArrays = posts.getJSONArray("posts");

        for (int i = 0; i < postArrays.length(); i++) {
            String s = postArrays.getString(i);
        }
		
		return "Hello";
	}
	
}