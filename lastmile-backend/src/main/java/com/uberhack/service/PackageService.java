package com.uberhack.service;

import java.util.List;

import com.uberhack.model.Package;
import com.uberhack.repository.PackageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackageService {
    @Autowired
    private PackageRepository packageRepository;

    public Package savePackage(Package package1) {
        System.out.println("Inside the service savePackage");
        System.out.println(package1.toString());
        return packageRepository.save(package1);
    }

    public Package getPackageById(Long packageId) {
        return packageRepository.findByPackageId(packageId);
    }

    public List<Package> getAllPackage() {
        return packageRepository.findAll();
    }

}
