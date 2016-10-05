package edu.asu.cse564.POX_FoodMenu_Client;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class MenuClient {

	private WebResource webRes;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/restservices";

    public MenuClient() {
        
        ClientConfig conf = new DefaultClientConfig();
        client = Client.create(conf);
        webRes = client.resource(BASE_URI).path("FoodItem");
    }

    public String postJSONRequest(String requestMessage) throws UniformInterfaceException {
        String retrievedGreeting = webRes.type(MediaType.APPLICATION_JSON).post(String.class, requestMessage);
        return retrievedGreeting;
    }

    public void close() {
        client.destroy();
    }
}
