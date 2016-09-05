
package restService.com.websystique.springmvc.controller;


import configuration.JpaConfig;
import configuration.RestConfig;
import configuration.ServicesConfig;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import javax.activation.DataSource;
import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RestConfig.class, JpaConfig.class, ServicesConfig.class})
public class RegionControllerTest {

    @Autowired
    private WebApplicationContext wac;


    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void getFoo() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/branch?id=2")).andExpect(status().isOk()).andReturn();
        String restCall = result.getResponse().getContentAsString();
        JSONParser parser = new JSONParser();
        System.out.println(restCall);
        Object obj = parser.parse(restCall);
        JSONObject jsonObj = (JSONObject) obj;
        JSONArray jo = (JSONArray) jsonObj.get("listOfItems");
        System.out.println(jsonObj.get("listOfItems"));
        System.out.println(((JSONObject) jo.get(0)).get("naviDate"));
        assertEquals(jsonObj.get("size"), 1L);
        assertEquals(((JSONObject) jo.get(0)).get("primaryKey"), 2L);
    }
}