package com.base.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.base.basesetup.entity.CustomerVO;

public interface CustomerRepo extends JpaRepository<CustomerVO, Long> {


	boolean existsByOrgIdAndEmailIgnoreCase(Long orgId, String email);

}
