package com.simple.api.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.simple.api.models.UserModel;

@RestController
@RequestMapping("/home")
public class HomeController {
	@GetMapping("")
	public ResponseEntity<String> home() {
		return new  ResponseEntity<>("This is Home page",HttpStatus.OK);
		
	}
	@GetMapping("/users")
	public ResponseEntity<List<UserModel>> get_user_list() {
		List<UserModel> userList=new ArrayList<>();
		for(int i=1;i<=10;i++) {
			UserModel user=new UserModel();
			user.setId(i);
			user.setName(getName());
			user.setEmail(getEmail());
			userList.add(user);
		}
		
		return new  ResponseEntity<>(userList,HttpStatus.OK);
		
	}
	
	@GetMapping("/welcome/{uname}")
	public ResponseEntity<String> welcome(@PathVariable("uname") String name){
		
		return new ResponseEntity<>("Welcome "+name,HttpStatus.OK);
		
	}
	
	
	protected String getSaltString(int length) {
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyz";//"ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
	
	protected String getName() {
		return getSaltString(6);
	}
	protected String getEmail() {
		return getSaltString(6)+".email.com";
	}

}
