package com.projects.investmentaggregator.repository;

import com.projects.investmentaggregator.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
