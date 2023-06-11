package com.mstrzezon.deviceservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    private String id;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Device> ownedDevices;

    @ManyToMany(mappedBy = "sharedWith", fetch = FetchType.EAGER)
    private Set<Device> sharedDevices;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<Device> getOwnedDevices() {
        return ownedDevices;
    }

    public void setOwnedDevices(Set<Device> ownedDevices) {
        this.ownedDevices = ownedDevices;
    }

    public Set<Device> getSharedDevices() {
        return sharedDevices;
    }

    public void setSharedDevices(Set<Device> sharedDevices) {
        this.sharedDevices = sharedDevices;
    }
}
