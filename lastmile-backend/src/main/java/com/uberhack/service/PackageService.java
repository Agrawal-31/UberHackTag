package com.uberhack.service;

import java.util.ArrayList;
import java.util.List;

import com.uberhack.Constants;
import com.uberhack.Utils;
import com.uberhack.client.GoogleMapsClient;
import com.uberhack.mapper.PackageToRouteResponse;
import com.uberhack.model.db.DAOPackage;
import com.uberhack.model.requests.RouteRequest;
import com.uberhack.model.responses.RouteOptionResponse;
import com.uberhack.repository.PackageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackageService {
    @Autowired
    private PackageRepository packageRepository;

    public DAOPackage savePackage(DAOPackage DAOPackage1) {
        System.out.println("Inside the service savePackage");
        System.out.println(DAOPackage1.toString());
        return packageRepository.save(DAOPackage1);
    }

    public DAOPackage getPackageById(Long packageId) {
        return packageRepository.findByPackageId(packageId);
    }

    public List<DAOPackage> getAllPackage() {
        return packageRepository.findAll();
    }

    public List<RouteOptionResponse> getPackageRoutesWithProximity(RouteRequest routeRequest){
        List<DAOPackage> daoPackageList = packageRepository.findAll();
        DAOPackage daoPackagetmp = packageRepository.findByPackageId(1l);
        double riderSourceX = routeRequest.getRiderSourceX();
        double riderSourceY = routeRequest.getRiderSourceY();

        List<RouteOptionResponse> routeOptionResponseList = new ArrayList<>();

        for(DAOPackage daoPackage: daoPackageList){
            if(Utils.calculateDistanceInKilometer(riderSourceX, riderSourceY
                    , Double.parseDouble(daoPackage.getHubLattitude())
                    , Double.parseDouble(daoPackage.getHubLongitude())) < Constants.DISTANCE_THRESHOLD){
                routeOptionResponseList.add(PackageToRouteResponse.mapper(daoPackage));
            }
        }

        GoogleMapsClient googleMapsClient = new GoogleMapsClient();

        for(int i = 0; i < routeOptionResponseList.size(); i++){
            routeOptionResponseList.set(i, googleMapsClient.rewardsAndTimeFiller(routeOptionResponseList.get(i)));
        }
        return routeOptionResponseList;
    }

}
