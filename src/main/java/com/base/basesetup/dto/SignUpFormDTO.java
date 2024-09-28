/*
 * ========================================================================
 * This file is the intellectual property of GSM Outdoors.it
 * may not be copied in whole or in part without the express written
 * permission of GSM Outdoors.
 * ========================================================================
 * Copyrights(c) 2023 GSM Outdoors. All rights reserved.
 * ========================================================================
 */
package com.base.basesetup.dto;

import java.time.LocalDate;

//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpFormDTO {
	
	private Long id;
	private String userType;
	private Long orgId;
	private String fullName;
	private String nickName;
	private String gender;
	private LocalDate dob;
	private String mobile;
	private String email;
	private String password;
	private String pan;
	private String aadhar;
	private String branch;
	private String address;
	private String country;
	private String state;
	private String city;
	private String pinCode;
	private String jobNature;
	private String selfCompany;
	private String selfCompanyLocation;
	private int monthlyIncome;
	private String employeeCode;
	private LocalDate doj;
	private String department;
	private String designation;
	private boolean active;
	private String createdBy;
}
