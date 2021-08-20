package com.restapp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.rest.util.RecordAlreadyExists;
import com.restapp.controller.PeopleAPI;
import com.restapp.entities.Person;
import com.restapp.repositories.PeopleRepository;
import com.restapp.services.PeopleService;

@ExtendWith(MockitoExtension.class)
public class PeopleControllerUnitTests{

	

	
	@Mock
	PeopleRepository peopleRepository;
	
	@InjectMocks 
	    private PeopleService people;
	
	@InjectMocks 
	PeopleAPI peopleAPI;
	
	@Mock
	  private PeopleService people2;
	
	
	@Test
	public void testReadAll() {
		List<Person> list = new ArrayList<Person>();
		list.add(new Person(1,"Raj","Chennai"));
		list.add(new Person(2,"Harry","Chennai"));
		
		//mocking 
		when(peopleRepository.findAll()).thenReturn(list);
		when(people2.getPeople2()).thenReturn(list);
		
		
		List<Person> result=people.getPeople2();
		List<Person> result2=peopleAPI.getPeople2();
		System.out.println(result);
		assertThat(result.size()).isEqualTo(2);
		assertThat(result.get(0).getName()).isEqualTo("Raj");
		assertThat(result2.size()).isEqualTo(2);
	}
	
	@Test
	public void testAddPerson() {
		MockHttpServletRequest request=new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		Person person=new Person(1,"Rahul","Chennai");
		
		try {
			when(peopleRepository.findBySno(any(Integer.class))).thenReturn(null);
			when(peopleRepository.save(any(Person.class))).thenReturn(person);
			when(people2.addPeople(person)).thenReturn(true);
			ResponseEntity<Person> result=peopleAPI.addPeople(person);
			assertThat(people.addPeople(person)).isEqualTo(true);
			assertThat(result.getStatusCodeValue()).isEqualTo(201);
			assertThat(result.getBody().getName()).isEqualTo("Rahul");
		} catch (RecordAlreadyExists e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}