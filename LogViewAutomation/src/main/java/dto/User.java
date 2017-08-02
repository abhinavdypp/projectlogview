package main.java.dto;

public class User {
private int id;
private String username;
private String password;
private String role;
private String status;
public User(String name, String password, String role) {
	// TODO Auto-generated constructor stub
	this.username=name;
	this.password=password;
	this.role=role;
}
public User() {
	// TODO Auto-generated constructor stub
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}

	
	

	}


