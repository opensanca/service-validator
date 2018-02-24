package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	private MyComponent component;

	@Autowired
	private MyService service;

	@Test(expected = RuntimeException.class)
	public void shouldValidateWithAInterfaceProxy() {

		service.doSomething(new DTO());
	}

	@Test(expected = RuntimeException.class)
	public void shouldValidateWithAComponent() {

		component.doSomething(new DTO());
	}

	@Test
	public void shouldValidateAComponentWithCorrectDto() {

		DTO dto = new DTO();
		dto.setNumber(2);
		dto.setText("TEST");
		component.doSomething(dto);
	}

}
