package restService.com.websystique.springmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;// in play 2.3
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;


//run while server is working
//
public class BranchControllerTest {
//    @Inject
//    BranchRepository repositoryRoleTree;
@Ignore
    @Test
    public void getBranchesWithId() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/ProductCatalog/rest/branch/1",  String.class);
        HttpStatus status = response.getStatusCode();
        String restCall = response.getBody();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(restCall);
        JSONObject jsonObj = (JSONObject) obj;
        JSONArray jo =(JSONArray)  jsonObj.get("listOfItems");
        System.out.println(jsonObj.get("listOfItems"));
        System.out.println(((JSONObject)jo.get(0)).get("naviDate"));
        assertEquals(jsonObj.get("size"),1L);
        assertEquals(((JSONObject)jo.get(0)).get("brncId"),1L);
    }

    @Test
    public void getBranches() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/ProductCatalog/rest/branch/",  String.class);
        HttpStatus status = response.getStatusCode();
        String restCall = response.getBody();
        JSONParser parser = new JSONParser();
        System.out.println(restCall);
        Object obj = parser.parse(restCall);
        JSONObject jsonObj = (JSONObject) obj;
        JSONArray jo =(JSONArray)  jsonObj.get("listOfItems");
        //correct body(i doesn't test count because testing count is test of dao part this project
        assertEquals(jsonObj.get("size"), new Long(jo.size()));
        assertEquals(jsonObj.get("restError"), "OK");
        assertEquals(jsonObj.get("nameTable"), "branch");
    }
}