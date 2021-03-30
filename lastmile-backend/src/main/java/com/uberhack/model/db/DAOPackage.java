package com.uberhack.model.db;

import javax.persistence.*;

import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "package")
public class DAOPackage {
    
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
    private String hubLattitude;
    private String DeliveryLattitude;
    private String hubLongitude;
    private String DeliveryLongitude;
    
    

//    @Override
//    public String toString() {
//        return String.format("%s(packageId=%d, hubLocationId=%s, deliveryLocationId=%s, hubLocation=%s, deliveryLocation=%s, packageValue=%s)",
//                this.getClass().getSimpleName(),
//                this.getPackageId(), this.getHubLocationId(), this.getDeliveryLocationId(), this.getHubLocation(), this.getDeliveryLocation(), this.getPackageValue());
//    }
    
}
