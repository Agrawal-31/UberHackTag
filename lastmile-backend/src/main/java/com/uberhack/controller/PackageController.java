package com.uberhack.controller;

import java.util.List;

import com.uberhack.model.Package;
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
    public Package savePackage(@RequestBody Package package1) {
        System.out.println("Inside the controller savePackage");
        System.out.println(package1.toString());
        return packageService.savePackage(package1);
    }

    @GetMapping("/{id}")
    public Package getPackageById(@PathVariable("id") Long packageId) {
        System.out.println("Inside the controller getPackageById"); 
        return packageService.getPackageById(packageId);
    }

    @GetMapping("/")
    public List<Package> getAllPackages() {
        System.out.println("Inside the controller getAllPackages"); 
        return packageService.getAllPackage();
    }

}
