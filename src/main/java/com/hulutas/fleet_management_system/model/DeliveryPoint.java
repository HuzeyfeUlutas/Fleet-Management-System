package com.hulutas.fleet_management_system.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "delivery_points")
public class DeliveryPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "deliveryPoint", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Sack> sacks;

    @OneToMany(mappedBy = "deliveryPoint", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Package> packages;

    public DeliveryPoint() {
    }

    public DeliveryPoint(Long id, String name, List<Sack> sacks, List<Package> packages) {
        this.id = id;
        this.name = name;
        this.sacks = sacks;
        this.packages = packages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Sack> getSacks() {
        return sacks;
    }

    public void setSacks(List<Sack> sacks) {
        this.sacks = sacks;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
