package com.raj.customsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.raj.customsapp.model.User;
import com.raj.customsapp.serviceImpl.UserServiceImpl;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserServiceImpl serviceimpl;
    
    @PostMapping("/register")
    public String addUser(@RequestBody User user) {
        try {
        	
        	if (user.getRole() == null || user.getRole().isEmpty()) {
                user.setRole("client");
            }
        	
            serviceimpl.addUser(user);
            return "Success";
        } catch (Exception e) {
            return "Failure";
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
            User UseredUser = serviceimpl.findUserByEmail(user.getEmail());
            if (UseredUser != null && UseredUser.getPassword().equals(user.getPassword())) {
             
            	String role = UseredUser.getRole(); 
            	int id = UseredUser.getUserId();
                return ResponseEntity.ok(new LoginResponse(id,"LoginSuccess", role));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect credentials. Please try again.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error. Please try again later.");
        }
    }
    
    
    public static class LoginResponse {
    	
    	private int id;
    	private String status;
        private String role;
        
        public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

		public LoginResponse(int id, String status, String role) {
			super();
			this.id = id;
			this.status = status;
			this.role = role;
		}

		public LoginResponse() {
			super();
		}
        
    }
}
