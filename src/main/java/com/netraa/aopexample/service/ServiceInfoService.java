package com.netraa.aopexample.service;

import com.netraa.aopexample.entity.ServiceInfo;
import com.netraa.aopexample.repository.ServiceRepository;
import org.springframework.stereotype.Service;


@Service
public class ServiceInfoService {

    private final ServiceRepository serviceRepository;

    public ServiceInfoService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public ServiceInfo saveService(String serviceName){

        if(serviceRepository.existsByName(serviceName)){
            return serviceRepository.findByName(serviceName);
        }

        ServiceInfo service = new ServiceInfo();
        service.setName(serviceName);
        return serviceRepository.save(service);
    }




}
