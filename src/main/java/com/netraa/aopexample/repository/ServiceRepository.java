package com.netraa.aopexample.repository;

import com.netraa.aopexample.entity.ServiceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<ServiceInfo, Long> {

    boolean existsByName(String serviceName);

    ServiceInfo findByName(String serviceName);

}
