package com.capinfo.cn;

public class Person {
	private  String name;
	private  String phoneNum;
	private String department;
	private String mobile;
	private String email;
	private String job;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public Person(String name, String phoneNum, String department,
			String mobile, String email, String job) {
		super();
		this.name = name;
		this.phoneNum = phoneNum;
		this.department = department;
		this.mobile = mobile;
		this.email = email;
		this.job = job;
	}
public Person(){}
	
	
	

}
