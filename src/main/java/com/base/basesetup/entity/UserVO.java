package com.base.basesetup.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.base.basesetup.dto.CreatedUpdatedDate;
import com.base.basesetup.dto.Role;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usersgen")
	@SequenceGenerator(name = "usersgen", sequenceName = "usersseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "userid")
	private Long id;

	@Column(name = "username")
	private String userName;
	@Column(name = "password")
	private String password;
	@Column(name = "name")
	private String name;
	@Column(name = "nickname")
	private String nickName;
	@Column(name = "email")
	private String email;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "mobileno")
	private String mobileNo;
	@Column(name = "usertype")
	private String userType;
	@Column(name = "loginstatus")
	private boolean loginStatus;
	@Column(name = "isActive")
	private boolean active;
	@Column(name = "createdby")
	private String createdby;
	@Column(name = "modifiedby")
	private String updatedby;
	@Column(name = "role")
	private Role role;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}
}
