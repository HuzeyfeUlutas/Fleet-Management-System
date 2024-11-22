package com.hulutas.fleet_management_system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hulutas.fleet_management_system.enums.SackStatus;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "sacks")
public class Sack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private SackStatus status;
    @Column(name = "barcode", columnDefinition = "VARCHAR(255)")
    private String barcode;
    @ManyToOne()
    @JoinColumn(name = "vehiche_id")
    @JsonBackReference
    private Vehicle vehicle;
    @ManyToOne()
    @JoinColumn(name = "delivery_point_id")
    @JsonBackReference
    private DeliveryPoint deliveryPoint;
    @OneToMany(mappedBy = "sack", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Package> aPackage;

    public Sack() {
    }

    public Sack(Long id, SackStatus status, String barcode, DeliveryPoint deliveryPoint, List<Package> aPackage) {
        this.id = id;
        this.status = status;
        this.barcode = barcode;
        this.deliveryPoint = deliveryPoint;
        this.aPackage = aPackage;
    }

    public SackStatus getStatus() {
        return status;
    }

    public void setStatus(SackStatus status) {
        this.status = status;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public DeliveryPoint getDeliveryPoint() {
        return deliveryPoint;
    }

    public void setDeliveryPoint(DeliveryPoint deliveryPoint) {
        this.deliveryPoint = deliveryPoint;
    }

    public List<Package> getaPackage() {
        return aPackage;
    }

    public void setaPackage(List<Package> aPackage) {
        this.aPackage = aPackage;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
