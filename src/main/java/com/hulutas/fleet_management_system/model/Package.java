package com.hulutas.fleet_management_system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hulutas.fleet_management_system.enums.PackageStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "packages")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private Integer statusValue;
    @Transient
    private PackageStatus status;
    private String barcode;
    private int desi;

    @ManyToOne()
    @JoinColumn(name = "delivery_point_id")
    @JsonBackReference
    private DeliveryPoint deliveryPoint;
    @ManyToOne()
    @JoinColumn(name = "vehiche_id")
    @JsonBackReference
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "sack_barcode", referencedColumnName = "barcode")
    @JsonBackReference
    private Sack sack;

    public Package() {
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public PackageStatus getStatus() {
        return PackageStatus.fromValue(this.statusValue);
    }

    public void setStatus(PackageStatus status) {
        this.status = status;
        this.statusValue = status != null ? status.getValue() : null;
    }

    public Package(Long id, PackageStatus status, String barcode, int desi, DeliveryPoint deliveryPoint, Sack sack) {
        this.id = id;
        this.status = status;
        this.barcode = barcode;
        this.desi = desi;
        this.deliveryPoint = deliveryPoint;
        this.sack = sack;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getDesi() {
        return desi;
    }

    public void setDesi(int desi) {
        this.desi = desi;
    }

    public DeliveryPoint getDeliveryPoint() {
        return deliveryPoint;
    }

    public void setDeliveryPoint(DeliveryPoint deliveryPoint) {
        this.deliveryPoint = deliveryPoint;
    }

    public Sack getSack() {
        return sack;
    }

    public void setSack(Sack sack) {
        this.sack = sack;
    }
}
