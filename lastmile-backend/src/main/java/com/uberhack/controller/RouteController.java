package com.uberhack.controller;

import com.uberhack.model.RiderRoute;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class RouteController {

    @PostMapping({ "/route" })
    public String firstPage(@RequestBody RiderRoute riderRoute) {
        return "[{\n" +
                "\t\t\"duration\": 500,\n" +
                "\t\t\"from\": \"Fateh Sagar Lake\",\n" +
                "\t\t\"id\": 1,\n" +
                "\t\t\"reward\": 15,\n" +
                "\t\t\"to\": \"Pichola Lake\",\n" +
                "\t\t\"packageSourceX\": 24.572481,\n" +
                "\t\t\"packageSourceY\": 73.724991,\n" +
                "\t\t\"packageDestinationX\": 24.595288,\n" +
                "\t\t\"PackageDestinationY\": 73.689162\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"duration\": 400,\n" +
                "\t\t\"from\": \"Radison BLU\",\n" +
                "\t\t\"id\": 2,\n" +
                "\t\t\"reward\": 20,\n" +
                "\t\t\"to\": \"Sankalp Restaurant\",\n" +
                "\t\t\"packageSourceX\": 24.572481,\n" +
                "\t\t\"packageSourceY\": 73.724991,\n" +
                "\t\t\"packageDestinationX\": 24.595288,\n" +
                "\t\t\"PackageDestinationY\": 73.689162\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"duration\": 3700,\n" +
                "\t\t\"from\": \"NV Sports\",\n" +
                "\t\t\"id\": 3,\n" +
                "\t\t\"reward\": 63,\n" +
                "\t\t\"to\": \"YMCA Pool\",\n" +
                "\t\t\"packageSourceX\": 24.572481,\n" +
                "\t\t\"packageSourceY\": 73.724991,\n" +
                "\t\t\"packageDestinationX\": 24.595288,\n" +
                "\t\t\"PackageDestinationY\": 73.689162\n" +
                "\t}\n" +
                "]";
    }

}

