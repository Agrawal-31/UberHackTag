package com.uberhack.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uberhack.Constants;
import com.uberhack.model.responses.RouteOptionResponse;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class GoogleMapsClient {

    public static RouteOptionResponse rewardsAndTimeFiller(RouteOptionResponse routeOptionResponse){
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="
                + routeOptionResponse.getPackageSourceX() + "," + routeOptionResponse.getPackageSourceY()
                + "&destinations=" + routeOptionResponse.getPackageDestinationX() + "," + routeOptionResponse.getPackageDestinationY()
                + "&key=" + Constants.MAPS_API_KEY;

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(URI.create(url), String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonNode node = objectMapper.readValue(result, JsonNode.class);
            JsonNode rows = node.get("rows");
            JsonNode row = rows.get(0);
            JsonNode elements = row.get("elements");
            JsonNode element = elements.get(0);
            int reward = (int) Math.round(element.get("distance").get("value").asInt() * Constants.REWARD_PER_METER);
            int duration = element.get("duration").get("value").asInt();
            routeOptionResponse.setDuration(duration);
            routeOptionResponse.setReward(reward);
        }catch (Exception e){

        }
        return routeOptionResponse;
    }
}
