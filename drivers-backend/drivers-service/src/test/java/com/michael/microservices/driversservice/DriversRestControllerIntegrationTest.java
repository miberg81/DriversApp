package com.michael.microservices.driversservice;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.michael.microservices.driversservice.bean.Address;
import com.michael.microservices.driversservice.bean.Driver;
import com.michael.microservices.driversservice.bean.DriversRepository;
import com.michael.microservices.driversservice.bean.Location;
import com.michael.microservices.driversservice.bean.Status;


@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
//@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class DriversRestControllerIntegrationTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
    private DriversRepository repository;
	
	// LoadDatabase class has already loaded 10 drivers in db
	// given array of fetched drivers drivers, 
	// we check a few instances in the array by name
	// we expect driver1 at index 0, driver2 at index 1 etc...
	@Test
	public void getDriversTest() throws Exception {
		mockMvc.perform(get("/drivers")
	       .contentType(MediaType.APPLICATION_JSON))
	       .andExpect(status().isOk())
	       .andExpect(content()
	       .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	       .andExpect(jsonPath("$[0].firstName", is("driver1")))
		   .andExpect(jsonPath("$[1].firstName", is("driver2")))
		   .andExpect(jsonPath("$[9].firstName", is("driver10")));
	}
	
	// check @GetMapping("/drivers/{id}")
	// given array of initial drivers
	// we expect to get "driver5" in response for get request to "/drivers/5"
	// loop through all 10 first drivers to be sure they all work
	@Test
	public void getDriverByIdTest() throws Exception {
		for(int i=1; i<=10; i++) {
			mockMvc.perform(get("/drivers/"+i)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
				.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.firstName", is("driver"+i)));
		}
	}
	
	// after posting a new driver with name newDriver
	// we expect status OK and response with the newly added newDriver
	@Test
    public void addNewDriverTest() throws Exception {
        Driver driver = createTestDriver("newDriver");

        mockMvc.perform(post("/drivers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(driver)))
            .andExpect(status().isOk())
            .andExpect(content()
		    .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		    .andExpect(jsonPath("$.firstName", is("newDriver"))
		);
    }
	
	// we are fetching an existing driver by id
	// then we override it's properties with the newDriver we create
	// so we expect a response to have an updated name newDriver5
	@Test
    public void replaceDriverTest() throws Exception {
        Driver driver = createTestDriver("newDriver5");

        mockMvc.perform(put("/drivers/5")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(driver)))
            .andExpect(status().isOk())
            .andExpect(content()
		    .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		    .andExpect(jsonPath("$.firstName", is("newDriver5")));
    }
	
	// test for @PutMapping("/drivers/{id}/location")
	// we update the existing driver location
	// we expect to get the updated location back, with OK status
	// we loop through 10 first driver instances to update the location
	@Test
    public void updateLocationTest() throws Exception {
        Location location = createTestLocation();
        for(int i=1;i<=10;i++) {
	        	mockMvc.perform(	put("/drivers/" + i + "/location")
		        	.contentType(MediaType.APPLICATION_JSON)
		        	.content(toJson(location)))
		        	.andExpect(status().isOk())
		        	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		        	.andExpect(jsonPath("$.latitude", is(location.getLatitude())))
		        	.andExpect(jsonPath("$.longitude", is(location.getLongitude())));
        }
    }
	
	// test for @PutMapping("/drivers/{id}/address")
	// we update the existing driver address
	// we expect to get the updated address back, with OK status
	// we loop through 10 first driver instances to update the address
	@Test
    public void updateAddressTest() throws Exception {
        Address address = createTestAddress();        
        for(int i=1;i<=10;i++) {
	        	mockMvc.perform(put("/drivers/" + i + "/address")
		        	.contentType(MediaType.APPLICATION_JSON)
		        	.content(toJson(address)))
		        	.andExpect(status().isOk())
		        	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		        	.andExpect(jsonPath("$.comment", is(address.getComment())))
		        	.andExpect(jsonPath("$.houseNum", is(address.getHouseNum())));
        }
    }
	
	// make sure that after getting active drivers
	// all the drivers in the list are indeed active
	@Test
	public void getActiveDrivers() throws Exception {
		for(int i=0;i<5;i++) {
		mockMvc.perform(get("/drivers/active")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content()
			.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$["+i+"].status", is("ACTIVE")));
		}
	}
	
	// The db contains 3 Haifa located drivers who work from 8 to 18
	// So we expect to get 3 results back, with the adress.city=Haifa
	@Test
	public void getDriversWorkingBetweenTest() throws Exception {
		mockMvc.perform(get("/drivers/start/8/0/finish/18/0")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
				.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()", is(3)))
				.andExpect(jsonPath("$[0].address.city", is("Haifa")));
	
	}
	
	// we check the url @GetMapping("/drivers/lat/{latMin}/{latMax}/long/{longMin}/{longMax}")
	// we can roughly check ourselves by supplying
	// approximate Israel state bounds.
	// Then we expect to get all the drivers we have (10)
	@Test
	public void getDriversInMapBoundsTest() throws Exception {
		mockMvc.perform(get("/drivers/lat/32/33/long/34/35")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
				.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()", is(10)));
	}
	
	// we delete a driver, so we expect to not find this record after deletion
//	@Test
//	public void deleteDriverByIdTest() throws Exception {
//			mockMvc.perform(delete("/drivers/10")
//				.contentType(MediaType.APPLICATION_JSON))
//        			.andExpect(status().isOk());
//	}
     
	// create test address which is marked in the comment as "this is new address"
	// and has house, floor, apartment = 100
	private Address createTestAddress() {
		return new Address(
				"Israel", "North", "Haifa", "street1" , 100, 100, 100,
				"11111", "this is new address");
	}

	public Location createTestLocation() {
		return new Location(32.819683, 34.967200);
	}

	public Driver createTestDriver(String firstName) {
		Driver driver = new Driver(
				firstName, "last_name1", Status.ACTIVE,  12, 0, 23, 0, 32.819683, 34.967200,
				21, "email1@gmail.com", "0507656451",  
				new Address("Israel", "North", "Haifa", "street1" , 1, 1, 34, "11111", "i live somewhere1"));
        //repository.saveAndFlush(driver);
		return driver;
    }
	
	/**
	 * Serialize an object into string with fasterxml library
	 * @param obj
	 * @return string - json formatted string representation of the object
	 */
	public static String toJson(final Object obj) {
        try {
	        	ObjectMapper objectMapper = new ObjectMapper();
	        	objectMapper.findAndRegisterModules(); //register the datatype support offered by this library
            String string =  objectMapper.writeValueAsString(obj);
            return string;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
//	@After
//    public void resetDb() {
//        repository.deleteAll();
//    }
}
