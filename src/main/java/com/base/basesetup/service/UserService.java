package com.base.basesetup.service;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
	
	public void createUserAction(String userName, Long userId, String actionType);

	public void removeUser(String userName);

}
