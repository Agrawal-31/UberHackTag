package com.uberhack.model.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RouteOptionResponse {
    Integer id;
    String from;
    String to;
    Integer reward;
    Integer duration;
    Double packageSourceX;
    Double packageSourceY;
    Double packageDestinationX;
    Double packageDestinationY;
}
