package com.projects.investmentaggregator.repository;

import com.projects.investmentaggregator.entity.BillingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingAddressRepository extends JpaRepository<BillingAddress, Long> {
}
