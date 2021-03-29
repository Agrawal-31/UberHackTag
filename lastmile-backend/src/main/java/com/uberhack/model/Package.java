package com.uberhack.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Package {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long packageId;

    private String packageValue;
    private String hubLocationId;
    private String deliveryLocationId;
    private String hubLocation;
    private String deliveryLocation;
    private String expectedDeliveryDate;
    private String externalPackageId;
    
    

    @Override
    public String toString() {
        return String.format("%s(packageId=%d, hubLocationId=%s, deliveryLocationId=%s, hubLocation=%s, deliveryLocation=%s, packageValue=%s)", 
                this.getClass().getSimpleName(), 
                this.getPackageId(), this.getHubLocationId(), this.getDeliveryLocationId(), this.getHubLocation(), this.getDeliveryLocation(), this.getPackageValue());
    }
    
}
