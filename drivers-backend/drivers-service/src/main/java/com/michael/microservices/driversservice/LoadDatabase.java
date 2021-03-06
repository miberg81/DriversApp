package com.michael.microservices.driversservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.michael.microservices.driversservice.bean.Address;
import com.michael.microservices.driversservice.bean.Driver;
import com.michael.microservices.driversservice.bean.Status;
import com.michael.microservices.driversservice.bean.DriversRepository;
import com.michael.microservices.driversservice.bean.Location;

@Configuration
public class LoadDatabase {
	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(DriversRepository repository) {
		return args -> {
			// DRIVERS LOCATED IN HAIFA (work from 8 to 18)
			log.info("Preloading " + repository.save(
						new Driver(
							"driver1", "last_name1", Status.ACTIVE,  8, 0, 18, 0, 32.819683, 34.967200,
							21, "email1@gmail.com", "0507656451",  
							new Address("Israel", "North", "Haifa", "street1" , 1, 1, 34, "11111", "i live somewhere1"))));
			log.info("Preloading " + repository.save(
						new Driver(
							"driver2", "last_name2", Status.ACTIVE, 8, 0, 18, 0, 32.814767, 34.987945,
							21, "email2@gmail.com", "0507656452",  
							new Address("Israel", "North", "Haifa", "street2", 2, 2, 34, "22222", "i live somewhere2"))));
			log.info("Preloading " + repository.save(
						new Driver(
							"driver3", "last_name3", Status.INACTIVE, 8, 0, 18, 0, 32.801188, 34.981083,
							21, "email3@gmail.com", "0507656453",  
							new Address("Israel", "North", "Haifa", "street1", 3, 3, 23, "33333", "i live somewhere3"))));
			
			// DRIVERS LOCATED IN TEL-AVIV (EAST OF AYALON) work from 10 to 20
			log.info("Preloading " + repository.save(
						new Driver(
							"driver4", "last_name4", Status.ACTIVE, 10, 0, 20, 0, 32.088341, 34.981083,
							21, "email4@gmail.com", "0507656454", 
							new Address("Israel", "North", "Tel-Aviv", "street1", 4, 4, 89, "44444", "i live somewhere4"))));
			log.info("Preloading " + repository.save(
						new Driver(
							"driver5", "last_name5", Status.ACTIVE, 10, 0, 20, 0, 32.080469, 34.777150,
							21, "email5@gmail.com", "0507656455", 
							new Address("Israel", "North", "Tel-Aviv", "street5", 5, 5, 234, "555555", "i live somewhere5"))));
			log.info("Preloading " + repository.save(
						new Driver(
							"driver6", "last_name6", Status.INACTIVE, 10, 0, 20, 0, 32.084751, 34.788748,
							21, "email6@gmail.com", "0507656456", 
							new Address("Israel", "North", "Tel-Aviv", "street1", 6, 6, 34, "12345", "i live somewhere6"))));
			
			// DRIVERS LOCATED IN RAMAT-GAN/GIVATAYIM... (WEST OF AYALON) - work from 12 ???? 22
			log.info("Preloading " + repository.save(
					new Driver(
							"driver7", "last_name7", Status.ACTIVE, 12, 0, 22, 0, 32.075244, 34.806397,
							21, "email7@gmail.com", "0507656457", 
							new Address("Israel", "North", "Ramat-Gan", "street1", 7, 7, 34, "77777", "i live somewhere7"))));
			log.info("Preloading " + repository.save(
					new Driver(
							"driver8", "last_name8", Status.ACTIVE, 12, 0, 22, 0, 32.060902, 34.813965,
							21, "email8@gmail.com", "0507656458", 
							new Address("Israel", "North", "Ramat-Gan", "street1", 8, 8, 75, "88888", "i live somewhere8"))));
			log.info("Preloading " + repository.save(
					new Driver(
							"driver9", "last_name9", Status.ACTIVE, 12, 0, 22, 0, 32.066044, 34.827902, 
							21, "email9@gmail.com", "0507656459", 
							new Address("Israel", "North", "Ramat-Gan", "street1", 9, 9, 56, "99999", "i live somewhere9"))));
			log.info("Preloading " + repository.save(
					new Driver(
							"driver10", "last_name10", Status.INACTIVE, 12, 0, 22, 0, 32.086741, 34.814894,
							21, "email10@gmail.com", "0507656410", 
							new Address("Israel", "North", "Ramat-Gan", "street10", 10, 10, 45, "10000", "i live somewhere10"))));
		};
	}
}
