package com.base.basesetup.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employeegen")
	@SequenceGenerator(name = "employeegen", sequenceName = "employeeseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "employeeid")
	private Long id;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "fullname")
	private String fullName;
	@Column(name = "nickname")
	private String nickName;
	@Column(name = "gender")
	private String gender;
	@Column(name = "dob")
	private LocalDate dob;
	@Column(name = "mobile")
	private String mobile;
	@Column(name = "email")
	private String email;
	@Column(name = "pan")
	private String pan;
	@Column(name = "aadhar")
	private String aadhar;
	@Column(name = "branch")
	private String branch;
	@Column(name = "address")
	private String address;
	@Column(name = "country")
	private String country;
	@Column(name = "state")
	private String state;
	@Column(name = "city")
	private String city;
	@Column(name = "pincode")
	private String pinCode;
	@Column(name = "employeecode")
	private String employeeCode;
	@Column(name = "doj")
	private LocalDate doj;
	@Column(name = "department")
	private String department;
	@Column(name = "designation")
	private String designation;
	@Column(name = "freeze")
	private boolean freeze=false;
	private boolean active;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String modifiedBy;
	

}
