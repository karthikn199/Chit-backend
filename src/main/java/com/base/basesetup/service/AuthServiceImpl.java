package com.base.basesetup.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.base.basesetup.common.AuthConstant;
import com.base.basesetup.common.CommonConstant;
import com.base.basesetup.common.UserConstants;
import com.base.basesetup.dto.ChangePasswordFormDTO;
import com.base.basesetup.dto.LoginFormDTO;
import com.base.basesetup.dto.RefreshTokenDTO;
import com.base.basesetup.dto.ResetPasswordFormDTO;
import com.base.basesetup.dto.SignUpFormDTO;
import com.base.basesetup.dto.UserResponseDTO;
import com.base.basesetup.entity.CustomerVO;
import com.base.basesetup.entity.EmployeeVO;
import com.base.basesetup.entity.TokenVO;
import com.base.basesetup.entity.UserVO;
import com.base.basesetup.exception.ApplicationException;
import com.base.basesetup.repo.CustomerRepo;
import com.base.basesetup.repo.EmployeeRepo;
import com.base.basesetup.repo.TokenRepo;
import com.base.basesetup.repo.UserRepo;
import com.base.basesetup.security.TokenProvider;
import com.base.basesetup.util.CryptoUtils;

@Service
public class AuthServiceImpl implements AuthService {

	public static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Autowired
	UserRepo userRepo;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	TokenProvider tokenProvider;

	@Autowired
	TokenRepo tokenRepo;

	@Autowired
	UserService userService;

	@Autowired
	EmployeeRepo employeeRepo;

	@Autowired
	CustomerRepo customerRepo;

	@Override
	public void signup(SignUpFormDTO signUpRequest) throws ApplicationException {
		String methodName = "signup()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		if (ObjectUtils.isEmpty(signUpRequest) || StringUtils.isBlank(signUpRequest.getEmail())
				|| StringUtils.isBlank(signUpRequest.getEmail())) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_USER_REGISTER_INFORMATION);
		}

		if (signUpRequest.getUserType().equals("CUSTOMER")) {
			getCustomerVOFromSignupFormDTO(signUpRequest);
		} else {
			getEmployeeVOFromSignupFormDTO(signUpRequest);
		}
		if (ObjectUtils.isEmpty(signUpRequest.getId())) {
			UserVO userVO = getUserVOFromSignUpFormDTO(signUpRequest);
			userRepo.save(userVO);
			userService.createUserAction(userVO.getUserName(), userVO.getId(), UserConstants.USER_ACTION_ADD_ACCOUNT);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	}

	private void getEmployeeVOFromSignupFormDTO(SignUpFormDTO signUpRequest) throws ApplicationException {

		EmployeeVO employeeVO = new EmployeeVO();

		if (ObjectUtils.isEmpty(signUpRequest.getId())) {
			if (employeeRepo.existsByOrgIdAndEmailIgnoreCase( signUpRequest.getOrgId(),signUpRequest.getEmail())) {
				throw new ApplicationException("Email Already Exist");
			}
			employeeVO.setOrgId(signUpRequest.getOrgId());
			employeeVO.setFullName(signUpRequest.getFullName());
			employeeVO.setNickName(signUpRequest.getNickName());
			employeeVO.setGender(signUpRequest.getGender());
			employeeVO.setDob(signUpRequest.getDob());
			employeeVO.setMobile(signUpRequest.getMobile());
			employeeVO.setEmail(signUpRequest.getEmail());
			employeeVO.setPan(signUpRequest.getPan());
			employeeVO.setAadhar(signUpRequest.getAadhar());
			employeeVO.setBranch(signUpRequest.getBranch());
			employeeVO.setAddress(signUpRequest.getAddress());
			employeeVO.setCountry(signUpRequest.getCountry());
			employeeVO.setState(signUpRequest.getState());
			employeeVO.setCity(signUpRequest.getCity());
			employeeVO.setPinCode(signUpRequest.getPinCode());
			employeeVO.setEmployeeCode(signUpRequest.getEmployeeCode());
			employeeVO.setDoj(signUpRequest.getDoj());
			employeeVO.setDepartment(signUpRequest.getDepartment());
			employeeVO.setDesignation(signUpRequest.getDesignation());
			employeeVO.setActive(signUpRequest.isActive());
			employeeVO.setCreatedBy(signUpRequest.getCreatedBy());
			employeeVO.setModifiedBy(signUpRequest.getCreatedBy());
			employeeRepo.save(employeeVO);
		} else {
			employeeVO = employeeRepo.findById(signUpRequest.getId()).get();

			if (!employeeVO.getEmail().equals(signUpRequest.getEmail())) {
				if (employeeRepo.existsByOrgIdAndEmailIgnoreCase( signUpRequest.getOrgId(),signUpRequest.getEmail())) {
					throw new ApplicationException("Email Already Exist");
				}
				employeeVO.setEmail(signUpRequest.getEmail());
			}
			employeeVO.setOrgId(signUpRequest.getOrgId());
			employeeVO.setFullName(signUpRequest.getFullName());
			employeeVO.setNickName(signUpRequest.getNickName());
			employeeVO.setGender(signUpRequest.getGender());
			employeeVO.setDob(signUpRequest.getDob());
			employeeVO.setMobile(signUpRequest.getMobile());
			employeeVO.setPan(signUpRequest.getPan());
			employeeVO.setAadhar(signUpRequest.getAadhar());
			employeeVO.setBranch(signUpRequest.getBranch());
			employeeVO.setAddress(signUpRequest.getAddress());
			employeeVO.setCountry(signUpRequest.getCountry());
			employeeVO.setState(signUpRequest.getState());
			employeeVO.setCity(signUpRequest.getCity());
			employeeVO.setPinCode(signUpRequest.getPinCode());
			employeeVO.setEmployeeCode(signUpRequest.getEmployeeCode());
			employeeVO.setDoj(signUpRequest.getDoj());
			employeeVO.setDepartment(signUpRequest.getDepartment());
			employeeVO.setDesignation(signUpRequest.getDesignation());
			employeeVO.setActive(signUpRequest.isActive());
			employeeVO.setModifiedBy(signUpRequest.getCreatedBy());
			employeeRepo.save(employeeVO);
		}

	}

	private void getCustomerVOFromSignupFormDTO(SignUpFormDTO signUpRequest) throws ApplicationException {
		CustomerVO customerVO = new CustomerVO();

		if (ObjectUtils.isEmpty(signUpRequest.getId())) {
			if (customerRepo.existsByOrgIdAndEmailIgnoreCase(signUpRequest.getOrgId(),signUpRequest.getEmail())) {
				throw new ApplicationException("Email Already Exist");
			}
			customerVO.setOrgId(signUpRequest.getOrgId());
		    customerVO.setFullName(signUpRequest.getFullName());
		    customerVO.setNickName(signUpRequest.getNickName());
		    customerVO.setGender(signUpRequest.getGender());
		    customerVO.setDob(signUpRequest.getDob());
		    customerVO.setMobile(signUpRequest.getMobile());
		    customerVO.setEmail(signUpRequest.getEmail());
		    customerVO.setPan(signUpRequest.getPan());
		    customerVO.setAadhar(signUpRequest.getAadhar());
		    customerVO.setBranch(signUpRequest.getBranch());
		    customerVO.setAddress(signUpRequest.getAddress());
		    customerVO.setCountry(signUpRequest.getCountry());
		    customerVO.setState(signUpRequest.getState());
		    customerVO.setCity(signUpRequest.getCity());
		    customerVO.setPinCode(signUpRequest.getPinCode());
		    customerVO.setJobNature(signUpRequest.getJobNature());
		    customerVO.setSelfCompany(signUpRequest.getSelfCompany());
		    customerVO.setSelfCompanyLocation(signUpRequest.getSelfCompanyLocation());
		    customerVO.setMonthlyIncome(signUpRequest.getMonthlyIncome());
		    customerVO.setActive(signUpRequest.isActive());
			customerRepo.save(customerVO);
		} else {
			customerVO = customerRepo.findById(signUpRequest.getId()).get();

			if (!customerVO.getEmail().equals(signUpRequest.getEmail())) {
				if (customerRepo.existsByOrgIdAndEmailIgnoreCase(signUpRequest.getOrgId(),signUpRequest.getEmail())) {
					throw new ApplicationException("Email Already Exist");
				}
				customerVO.setEmail(signUpRequest.getEmail());
			}
			customerVO.setOrgId(signUpRequest.getOrgId());
		    customerVO.setFullName(signUpRequest.getFullName());
		    customerVO.setNickName(signUpRequest.getNickName());
		    customerVO.setGender(signUpRequest.getGender());
		    customerVO.setDob(signUpRequest.getDob());
		    customerVO.setMobile(signUpRequest.getMobile());
		    customerVO.setPan(signUpRequest.getPan());
		    customerVO.setAadhar(signUpRequest.getAadhar());
		    customerVO.setBranch(signUpRequest.getBranch());
		    customerVO.setAddress(signUpRequest.getAddress());
		    customerVO.setCountry(signUpRequest.getCountry());
		    customerVO.setState(signUpRequest.getState());
		    customerVO.setCity(signUpRequest.getCity());
		    customerVO.setPinCode(signUpRequest.getPinCode());
		    customerVO.setJobNature(signUpRequest.getJobNature());
		    customerVO.setSelfCompany(signUpRequest.getSelfCompany());
		    customerVO.setSelfCompanyLocation(signUpRequest.getSelfCompanyLocation());
		    customerVO.setMonthlyIncome(signUpRequest.getMonthlyIncome());
		    customerVO.setActive(signUpRequest.isActive());
			customerRepo.save(customerVO);
		}

	}

	private UserVO getUserVOFromSignUpFormDTO(SignUpFormDTO signUpFormDTO) throws ApplicationException {

		UserVO userVO = new UserVO();
		if(userRepo.existsByOrgIdAndUserNameIgnoreCase(signUpFormDTO.getOrgId(),signUpFormDTO.getEmail())) {
			throw new ApplicationException("Email Already Exist");
		}
		userVO.setUserName(signUpFormDTO.getEmail());
		try {
			if (StringUtils.isNotEmpty(signUpFormDTO.getPassword())) {
				userVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(signUpFormDTO.getPassword())));
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_ENCODE_USER_PASSWORD);
		}
		userVO.setName(signUpFormDTO.getFullName());
		userVO.setEmail(signUpFormDTO.getEmail());
		userVO.setMobileNo(signUpFormDTO.getMobile());
		userVO.setUserType(signUpFormDTO.getUserType().toLowerCase());
		userVO.setActive(signUpFormDTO.isActive());
		userVO.setOrgId(signUpFormDTO.getOrgId());
		return userVO;
	}

	@Override
	public UserResponseDTO login(LoginFormDTO loginRequest) throws ApplicationException {
		String methodName = "login()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		if (ObjectUtils.isEmpty(loginRequest) || StringUtils.isBlank(loginRequest.getUserName())
				|| StringUtils.isBlank(loginRequest.getPassword())) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_USER_LOGIN_INFORMATION);
		}
		UserVO userVO = userRepo.findByUserNameOrMobileNo(loginRequest.getUserName(),
				loginRequest.getUserName());

		if (ObjectUtils.isNotEmpty(userVO)) {
			if(!userVO.isActive()) {
				throw new ApplicationException("Your Account is In-Active, Please Conatct Administrator");
				}
			if (compareEncodedPasswordWithEncryptedPassword(loginRequest.getPassword(), userVO.getPassword())) {
				updateUserLoginInformation(userVO);
			} else {
				throw new ApplicationContextException(UserConstants.ERRROR_MSG_PASSWORD_MISMATCH);
			}
		} else {
			throw new ApplicationContextException(
					UserConstants.ERRROR_MSG_USER_INFORMATION_NOT_FOUND_AND_ASKING_SIGNUP);
		}
		UserResponseDTO userResponseDTO = mapUserVOToDTO(userVO);

		TokenVO tokenVO = tokenProvider.createToken(userVO.getId(), loginRequest.getUserName());
		userResponseDTO.setToken(tokenVO.getToken());
		userResponseDTO.setTokenId(tokenVO.getId());
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return userResponseDTO;
	}

	/**
	 * @param encryptedPassword -> Data from user;
	 * @param encodedPassword   ->Data from DB;
	 * @return
	 */
	private boolean compareEncodedPasswordWithEncryptedPassword(String encryptedPassword, String encodedPassword) {
		boolean userStatus = false;
		try {
			userStatus = encoder.matches(CryptoUtils.getDecrypt(encryptedPassword), encodedPassword);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_ENCODE_USER_PASSWORD);
		}
		return userStatus;
	}

	@Override
	public void logout(String userName) {
		String methodName = "logout()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		if (StringUtils.isBlank(userName)) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_USER_LOGOUT_INFORMATION);
		}
		UserVO userVO = userRepo.findByUserName(userName);
		if (ObjectUtils.isNotEmpty(userVO)) {
			updateUserLogOutInformation(userVO);
		} else {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_USER_INFORMATION_NOT_FOUND);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	}

	@Override
	public void changePassword(ChangePasswordFormDTO changePasswordRequest) {
		String methodName = "changePassword()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		if (ObjectUtils.isEmpty(changePasswordRequest) || StringUtils.isBlank(changePasswordRequest.getUserName())
				|| StringUtils.isBlank(changePasswordRequest.getOldPassword())
				|| StringUtils.isBlank(changePasswordRequest.getNewPassword())) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_CHANGE_PASSWORD_INFORMATION);
		}
		UserVO userVO = userRepo.findByUserName(changePasswordRequest.getUserName());
		if (ObjectUtils.isNotEmpty(userVO)) {
			if (compareEncodedPasswordWithEncryptedPassword(changePasswordRequest.getOldPassword(),
					userVO.getPassword())) {
				try {
					userVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(changePasswordRequest.getNewPassword())));
				} catch (Exception e) {
					throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_ENCODE_USER_PASSWORD);
				}
				userRepo.save(userVO);
				userService.createUserAction(userVO.getUserName(), userVO.getId(),
						UserConstants.USER_ACTION_TYPE_CHANGE_PASSWORD);
			} else {
				throw new ApplicationContextException(UserConstants.ERRROR_MSG_OLD_PASSWORD_MISMATCH);
			}
		} else {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_USER_INFORMATION_NOT_FOUND);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	}

	@Override
	public void resetPassword(ResetPasswordFormDTO resetPasswordRequest) {
		String methodName = "resetPassword()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		if (ObjectUtils.isEmpty(resetPasswordRequest) || StringUtils.isBlank(resetPasswordRequest.getUserName())
				|| StringUtils.isBlank(resetPasswordRequest.getNewPassword())) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_RESET_PASSWORD_INFORMATION);
		}
		UserVO userVO = userRepo.findByUserName(resetPasswordRequest.getUserName());
		if (ObjectUtils.isNotEmpty(userVO)) {
			try {
				userVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(resetPasswordRequest.getNewPassword())));
			} catch (Exception e) {
				throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_ENCODE_USER_PASSWORD);
			}
			userRepo.save(userVO);
			userService.createUserAction(userVO.getUserName(), userVO.getId(),
					UserConstants.USER_ACTION_TYPE_RESET_PASSWORD);
		} else {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_USER_INFORMATION_NOT_FOUND);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	}

	@Override
	public RefreshTokenDTO getRefreshToken(String userName, String tokenId) throws ApplicationException {
		UserVO userVO = userRepo.findByUserName(userName);
		RefreshTokenDTO refreshTokenDTO = null;
		if (ObjectUtils.isEmpty(userVO)) {
			throw new ApplicationException(UserConstants.ERRROR_MSG_USER_INFORMATION_NOT_FOUND);
		}
		TokenVO tokenVO = tokenRepo.findById(tokenId).orElseThrow(() -> new ApplicationException("Invalid Token Id."));
		if (tokenVO.getExpDate().compareTo(new Date()) > 0) {
			tokenVO = tokenProvider.createRefreshToken(tokenVO, userVO);
			refreshTokenDTO = RefreshTokenDTO.builder().token(tokenVO.getToken()).tokenId(tokenVO.getId()).build();
		} else {
			tokenRepo.delete(tokenVO);
			throw new ApplicationException(AuthConstant.REFRESH_TOKEN_EXPIRED_MESSAGE);
		}
		return refreshTokenDTO;
	}

	/**
	 * @param userVO
	 */
	private void updateUserLoginInformation(UserVO userVO) {
		try {
			userVO.setLoginStatus(true);
			userRepo.save(userVO);
			userService.createUserAction(userVO.getUserName(), userVO.getId(), UserConstants.USER_ACTION_TYPE_LOGIN);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_UPDATE_USER_INFORMATION);
		}
	}

	private void updateUserLogOutInformation(UserVO userVO) {
		try {
			userVO.setLoginStatus(false);
			userRepo.save(userVO);
			userService.createUserAction(userVO.getUserName(), userVO.getId(), UserConstants.USER_ACTION_TYPE_LOGOUT);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_UPDATE_USER_INFORMATION);
		}
	}

	public static UserResponseDTO mapUserVOToDTO(UserVO userVO) {
		UserResponseDTO userDTO = new UserResponseDTO();
		userDTO.setUsersId(userVO.getId());
		userDTO.setName(userVO.getName());
		userDTO.setOrgId(userVO.getOrgId());
		userDTO.setActive(userVO.isActive());
		userDTO.setUserType(userVO.getUserType());
		userDTO.setEmail(userVO.getEmail());
		userDTO.setUserName(userVO.getUserName());
		userDTO.setLoginStatus(userVO.isLoginStatus());
		// userDTO.setIsActive(userVO.getIsActive());
		userDTO.setCommonDate(userVO.getCommonDate());
		return userDTO;
	}

	

	@Override
	public UserVO getUserByUserName(String userName) {
		String methodName = "getUserByUserName()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		if (StringUtils.isNotEmpty(userName)) {
			UserVO userVO = userRepo.findByUserName(userName);
			if (ObjectUtils.isEmpty(userVO)) {
				throw new ApplicationContextException(UserConstants.ERRROR_MSG_USER_INFORMATION_NOT_FOUND);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return userVO;
		} else {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_USER_NAME);
		}
	}

	@Override
	public List<UserVO> getAllUsersByOrgId(Long orgId) {
		// TODO Auto-generated method stub
		return userRepo.findAllByOrgId(orgId);
	}

	@Override
	public UserVO getUserById(Long usersId) {
		String methodName = "getUserById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		if (ObjectUtils.isEmpty(usersId)) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_USER_ID);
		}
		UserVO userVO = userRepo.getUserById(usersId);
		if (ObjectUtils.isEmpty(userVO)) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_USER_INFORMATION_NOT_FOUND);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return userVO;
	}

}
