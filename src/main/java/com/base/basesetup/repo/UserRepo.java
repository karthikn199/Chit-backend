package com.base.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.base.basesetup.entity.UserVO;

public interface UserRepo extends JpaRepository<UserVO, Long> {

	boolean existsByUserNameOrEmail(String userName, String email);

	UserVO findByUserName(String userName);

	@Query(value = "select u from UserVO u where u.id =?1")
	UserVO getUserById(Long userId);

	UserVO findByUserNameAndId(String userName, Long userId);

	boolean existsByOrgIdAndUserNameIgnoreCase(Long orgId,String email);

	UserVO findByUserNameOrMobileNo(String userName, String userName2);

	@Query(value = "select u from UserVO u where u.orgId =?1")
	List<UserVO> findAllByOrgId(Long orgId);

}