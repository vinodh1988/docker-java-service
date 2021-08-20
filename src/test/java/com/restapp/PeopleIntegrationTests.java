package com.restapp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.restapp.entities.Person;

@SpringBootTest(classes=RestMicroserviceApplication.class,webEnvironment = WebEnvironment.RANDOM_PORT)
public class PeopleIntegrationTests {
   @LocalServerPort
   private int port;
   
   @Autowired TestRestTemplate restTemplate;
   
   @Test
   public void testPeople()
   {
	 Person[] p= restTemplate.getForObject("http://localhost:"+port+"/api/db/people", Person[].class);
	 
	 
	 assertThat(p[0].getName()).isEqualTo("Harry");
	 assertThat(p.length).isEqualTo(10);
	   
   }
   
   @Test
   public void testAddPerson() {
	   Person p=new Person(10,"Sony","Mumbai");
	   ResponseEntity<Person> response=
			   this.restTemplate.postForEntity("http://localhost:"+port+"/api/db/people", p, Person.class);
	   assertThat(response.getBody().getName()).isEqualTo("Sony");
	   assertEquals(response.getStatusCodeValue(), 201);
   }
   
}
