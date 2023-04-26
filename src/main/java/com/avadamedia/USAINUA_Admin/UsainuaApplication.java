package com.avadamedia.USAINUA_Admin;

import com.avadamedia.USAINUA_Admin.entity.Users;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UsainuaApplication {
	public static Users user;
	public static void main(String[] args) {
		SpringApplication.run(UsainuaApplication.class, args);
	}

}
