package com.mago.security;

import com.mago.security.configuration.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SecurityConfigTest.class)
class SecurityApplicationTests {

	@Test
	void contextLoads() {
	}

}
