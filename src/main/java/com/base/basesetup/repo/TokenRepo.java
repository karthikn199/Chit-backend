package com.base.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.base.basesetup.entity.TokenVO;

public interface TokenRepo extends JpaRepository<TokenVO, String> {

}
