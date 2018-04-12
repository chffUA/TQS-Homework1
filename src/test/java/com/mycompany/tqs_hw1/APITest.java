package com.mycompany.tqs_hw1;

import com.google.gson.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hamcrest.CoreMatchers;

/**
 *
 * @author Carlos
 */
public class APITest {
    
    Client client;
    WebTarget target;
    String link = "http://localhost:8080/TQS-Homework1-master/api/convert/get?from=AUD&to=USD&amount=400.27";
    
    Client client2;
    WebTarget target2;
    String link2 = "http://localhost:8080/TQS-Homework1-master/api/convert/get?from=AUD&to=U&amount=400.27";
    
    @Before
    public void initClient() {
    this.client = ClientBuilder.newClient();
    this.target = client.target(link);
    
    this.client2 = ClientBuilder.newClient();
    this.target2 = client2.target(link2);
    }
    
    @Test
    public void noErrorsTest() {
    Response response = target.path("").request( MediaType.APPLICATION_JSON).get();
    assertThat( response.getStatus(), CoreMatchers.is(200) );

    String body = response.readEntity(String.class); 
    JsonElement ele = new JsonParser().parse(body);
    JsonObject json = ele.getAsJsonObject();
    
    assertTrue(json.has("amount"));
    }
    
    @Test
    public void errorTest() {
    Response response = target2.path("").request( MediaType.APPLICATION_JSON).get();
    assertThat( response.getStatus(), CoreMatchers.is(404) );

    String body = response.readEntity(String.class); 
    JsonElement ele = new JsonParser().parse(body);
    JsonObject json = ele.getAsJsonObject();
    
    assertTrue(json.has("error"));
    }
        
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    
    @After
    public void tearDown() {
    }
    
}
