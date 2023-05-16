package com.sefaunal.WeatherApp;

import com.sefaunal.WeatherApp.Model.User;
import com.sefaunal.WeatherApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class WeatherAppApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(WeatherAppApplication.class, args);

		UserService userService = configurableApplicationContext.getBean(UserService.class);

		List<User> adminList = userService.findByUserRole("ADMIN");
		if (adminList.size() == 0){
			User user = new User();

			user.setUserName("Admin");
			user.setUserSurname("Auto Generated");
			user.setUserMail("sefa@admin.com");
			user.setUserPassword(new BCryptPasswordEncoder().encode("123456"));
			user.setUserRole("ADMIN");
			user.setUserCreationDate(LocalDateTime.now());
			user.setUserImageURL("https://st3.depositphotos.com/6672868/13701/v/600/depositphotos_137014128-stock-illustration-user-profile-icon.jpg");

			userService.createUser(user);
		}
	}

}
