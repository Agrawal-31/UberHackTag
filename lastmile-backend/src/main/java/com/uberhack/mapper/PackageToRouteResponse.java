package com.uberhack.mapper;

import com.uberhack.model.db.DAOPackage;
import com.uberhack.model.responses.RouteOptionResponse;

import java.util.Random;

public class PackageToRouteResponse {
    public static RouteOptionResponse mapper(DAOPackage daoPackage){
        return RouteOptionResponse
                .builder()
                .id(daoPackage.getPackageId().intValue())
                .from(daoPackage.getHubLocation())
                .to(daoPackage.getDeliveryLocation())
                .packageSourceX(Double.parseDouble(daoPackage.getHubLattitude()))
                .packageSourceY(Double.parseDouble(daoPackage.getHubLongitude()))
                .packageDestinationX(Double.parseDouble(daoPackage.getDeliveryLattitude()))
                .packageDestinationY(Double.parseDouble(daoPackage.getDeliveryLongitude()))
                .reward(new Random().nextInt(100) + 10)
                .duration(new Random().nextInt(4000) + 500)
                .build();

    }
}
