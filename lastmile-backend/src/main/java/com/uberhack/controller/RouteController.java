package com.uberhack.controller;

import com.uberhack.model.requests.RouteRequest;
import com.uberhack.model.responses.RouteOptionResponse;
import com.uberhack.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class RouteController {

    @Autowired
    private PackageService packageService;

    @PostMapping({ "/route" })
    public List<RouteOptionResponse> firstPage(@RequestBody RouteRequest routeRequest) {
        return packageService.getPackageRoutesWithProximity(routeRequest);
    }

}

