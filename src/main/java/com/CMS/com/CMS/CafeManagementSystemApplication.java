package com.CMS.com.CMS;

import com.CMS.com.CMS.pojo.Role;
import com.CMS.com.CMS.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;

@SpringBootApplication
public class CafeManagementSystemApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(CafeManagementSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Role role=new Role();
		if(Objects.isNull(roleRepo.findByRole("ADMIN"))){
			role.setRole("ADMIN");
			roleRepo.save(role);
		}
	}
}
