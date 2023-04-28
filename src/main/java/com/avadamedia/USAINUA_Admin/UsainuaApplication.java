package com.avadamedia.USAINUA_Admin;

import com.avadamedia.USAINUA_Admin.entity.Users;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class UsainuaApplication extends SpringBootServletInitializer {
	public static Users user;
	public static void main(String[] args) {
		SpringApplication.run(UsainuaApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(UsainuaApplication.class);
	}
}
