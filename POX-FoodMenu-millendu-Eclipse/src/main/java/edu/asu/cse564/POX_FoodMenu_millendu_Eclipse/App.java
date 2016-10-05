package edu.asu.cse564.POX_FoodMenu_millendu_Eclipse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/")
public class App 
{
	Map<Integer, Item> existingItems = new HashMap<Integer, Item>();
	int id = 0;
	
    private String addItems(String msg) {
		ObjectMapper mapper = new ObjectMapper();
		if(existingItems == null || existingItems.size() == 0)
			populateExistingItems(mapper);
		Item newItem = new Item();
		try{
			JsonNode node = mapper.readValue(msg, JsonNode.class).get("NewFoodItems");
			for(int id : existingItems.keySet())
			{
				if(existingItems.get(id).getName().equalsIgnoreCase(node.get(0).get("name").asText()) &&
						existingItems.get(id).getCategory().equalsIgnoreCase(node.get(0).get("category").asText()))
				{
					return "{\"ItemExists\": " + mapper.writeValueAsString(new ExistingItem(id)) + "}";
				}
				if(id >= id)
					id++;
			}
			newItem.setId(id);
			id++;
			newItem.setCategory(node.get(0).get("category").asText());
			newItem.setCountry(node.get(0).get("country").asText());
			newItem.setDescription(node.get(0).get("description").asText());
			newItem.setName(node.get(0).get("name").asText());
			newItem.setPrice(node.get(0).get("price").asDouble());
			existingItems.put(newItem.getId(), newItem);
			
			ItemsData data = new ItemsData();
			data.setItemsData(new ArrayList<Item>(existingItems.values()));
			PrintWriter writer = new PrintWriter(
			new File(this.getClass().getResource("/ItemData.json").getPath()));
			writer.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data));
			writer.close();
			
			return "{\"ItemAdded\": " + mapper.writeValueAsString(new ItemAdded(newItem.getId())) + "}";
		}catch(Exception e){
			return "{\"InvalidMessage\"}";
		}
	}
	
    @POST
    @Path("/FoodItem")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response AddItem(String msg)
    {
    	String output = "";
    	if(msg.indexOf("FoodItemId") <= 0)
    		output += addItems(msg);
    	else
    		output += selectItems(msg);
    	
    	return Response.status(200).entity(output).build();
    }
    
    private void populateExistingItems(ObjectMapper objMap) {

		String existingItemsString = "";
		try {
			existingItemsString = IOUtils.toString(App.class.getResourceAsStream("/ItemData.json"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		JsonNode itemData = null;
		try {
			itemData = objMap.readValue(existingItemsString, JsonNode.class).get("FoodItemData");
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int i = 0;
		while(i < itemData.size())
		{
			Item item = new Item();
			item.setId(itemData.get(i).get("id").asInt());
			item.setCountry(itemData.get(i).get("country").asText());
			item.setCategory(itemData.get(i).get("category").asText());
			item.setDescription(itemData.get(i).get("description").asText());
			item.setName(itemData.get(i).get("name").asText());
			item.setPrice(itemData.get(i).get("price").asDouble());
			existingItems.put(item.getId(), item);
			int var = item.getId();
			if(var >= id)
				id = var + 1;
			i++;
		}
    }

    private String selectItems(String msg) {
		List<Integer> itemIds = new ArrayList<Integer>();
		List<Item> items = new ArrayList<Item>();
		List<InvalidItem> invalidItems = new ArrayList<InvalidItem>();
		
		ObjectMapper obj = new ObjectMapper();
		if(existingItems.size() == 0 || existingItems == null)
			populateExistingItems(obj);
		try{
			JsonNode node = obj.readValue(msg, JsonNode.class).get("SelectedFoodItems");
			int i = 0;
			while(i < node.size())
			{
				itemIds.add(node.get(i).get("FoodItemId").asInt());
				i++;
			}
		}catch(Exception e)	{
			return "{\"InvalidMessage\"}";
		}
		
		RetrievedItems retrievedItems = new RetrievedItems();
		for(int i : itemIds)
		{
			if(!existingItems.containsKey(i))
				invalidItems.add(new InvalidItem(i));
			else
				items.add(existingItems.get(i));
		}
		retrievedItems.setInvalidItem(invalidItems);
		retrievedItems.setItem(items);
		String returnStr = "";
		try {
			returnStr = "{\"RetrievedItems\": " + obj.writeValueAsString(retrievedItems) + "}";
		} catch (JsonMappingException e) {
			return "{\"InvalidMessage\"}";
		} catch (JsonGenerationException e) {
			return "{\"InvalidMessage\"}";
		} catch (IOException e) {
			return "{\"InvalidMessage\"}";
		}
		return returnStr;
	}
	
	public static void main( String[] args )
    {
    }
}
