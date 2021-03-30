package com.uberhack.repository;

import com.uberhack.model.db.DAOPackage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends JpaRepository<DAOPackage, Long> {

    DAOPackage findByPackageId(Long packageId);
    
}
