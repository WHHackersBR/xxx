package com.converter;

import java.sql.Timestamp;

/**
 * TabLogin entity. @author MyEclipse Persistence Tools
 */

public class TabLogin implements java.io.Serializable {

	// Fields

	private TabLoginId id;
	private String password;
	private String department;
	private String role;
	private String name;
	private Timestamp eventTime;

	// Constructors

	/** default constructor */
	public TabLogin() {
	}

	/** minimal constructor */
	public TabLogin(TabLoginId id, String department, String role, String name,
			Timestamp eventTime) {
		this.id = id;
		this.department = department;
		this.role = role;
		this.name = name;
		this.eventTime = eventTime;
	}

	/** full constructor */
	public TabLogin(TabLoginId id, String password, String department,
			String role, String name, Timestamp eventTime) {
		this.id = id;
		this.password = password;
		this.department = department;
		this.role = role;
		this.name = name;
		this.eventTime = eventTime;
	}

	// Property accessors

	public TabLoginId getId() {
		return this.id;
	}

	public void setId(TabLoginId id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getEventTime() {
		return this.eventTime;
	}

	public void setEventTime(Timestamp eventTime) {
		this.eventTime = eventTime;
	}

}