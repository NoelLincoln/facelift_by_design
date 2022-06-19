package com.facelift.admin;

import com.facelift.admin.user.RoleRepository;
import com.facelift.admin.user.UserRepository;
import com.facelift.admin.user.UserService;
import com.facelift.common.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import com.facelift.common.entity.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EntityScan({"com.facelift.common.entity"})
public class FaceliftBackEndApplication  {

		public static void main(String[] args) {
		SpringApplication.run(FaceliftBackEndApplication.class, args); }



	}


