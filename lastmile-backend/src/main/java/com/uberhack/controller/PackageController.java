package com.uberhack.controller;

import java.util.List;

import com.uberhack.model.db.DAOPackage;
import com.uberhack.service.PackageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/packages")
public class PackageController {
    
    @Autowired
    private PackageService packageService;

    @PostMapping("/")
    public DAOPackage savePackage(@RequestBody DAOPackage DAOPackage1) {
        System.out.println("Inside the controller savePackage");
        System.out.println(DAOPackage1.toString());
        return packageService.savePackage(DAOPackage1);
    }

    @GetMapping("/{id}")
    public DAOPackage getPackageById(@PathVariable("id") Long packageId) {
        System.out.println("Inside the controller getPackageById"); 
        return packageService.getPackageById(packageId);
    }

    @GetMapping("/")
    public List<DAOPackage> getAllPackages() {
        System.out.println("Inside the controller getAllPackages"); 
        return packageService.getAllPackage();
    }

}
