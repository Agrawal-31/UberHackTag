package com.uberhack.repository;

import com.uberhack.model.Package;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {

    Package findByPackageId(Long packageId);
    
}
