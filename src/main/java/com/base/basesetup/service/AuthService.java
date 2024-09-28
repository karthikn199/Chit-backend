package com.base.basesetup.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.base.basesetup.dto.ChangePasswordFormDTO;
import com.base.basesetup.dto.LoginFormDTO;
import com.base.basesetup.dto.RefreshTokenDTO;
import com.base.basesetup.dto.ResetPasswordFormDTO;
import com.base.basesetup.dto.SignUpFormDTO;
import com.base.basesetup.dto.UserResponseDTO;
import com.base.basesetup.entity.UserVO;
import com.base.basesetup.exception.ApplicationException;

@Service
public interface AuthService {
	
	public void signup(SignUpFormDTO signUpRequest) throws ApplicationException;

	public UserResponseDTO login(LoginFormDTO loginRequest) throws ApplicationException;

	public void logout(String userName);

	public void changePassword(ChangePasswordFormDTO changePasswordRequest);

	public void resetPassword(ResetPasswordFormDTO resetPasswordRequest);

	public RefreshTokenDTO getRefreshToken(String userName, String tokenId) throws ApplicationException;

	UserVO getUserByUserName(String userName);

	UserVO getUserById(Long usersId);

	public List<UserVO> getAllUsersByOrgId(Long orgId);

}
