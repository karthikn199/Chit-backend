package com.base.basesetup.dto;

import java.util.Date;

import javax.persistence.Embedded;

import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
	
	private Long usersId;
	private String userName;
	private String name; 
	private String email; 
	private String userType;
	private Long orgId;
	private boolean loginStatus;
	private boolean active;
	
    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
    private Date accountRemovedDate;
    private String token;
    private String tokenId;
	

    @JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

}
