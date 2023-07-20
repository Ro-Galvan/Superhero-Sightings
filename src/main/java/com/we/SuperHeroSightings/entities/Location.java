
package com.we.SuperHeroSightings.entities;

import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author jtriolo
 */
public class Location {
    
    private int id;
    @NotBlank(message = "Name must not be blank")
    @Size(max = 50, message = "Name must be fewer than 50 characters")
    private String name;
    @NotBlank(message = "Description must not be blank")
    @Size(max = 255, message = "Description must be fewer than 255 characters")
    private String description;
    @NotBlank(message = "Address must not be blank")
    @Size(max = 150, message = "Address must be fewer than 150 characters")
    private String address;
    @NotBlank(message = "Latitude must not be blank")
    @Size(max = 10, message = "Latitude must be fewer than 10 characters")
    private String longitude;
    @NotBlank(message = "Longitude must not be blank")
    @Size(max = 10, message = "Longitude must be fewer than 10 characters")
    private String latitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.id;
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + Objects.hashCode(this.description);
        hash = 71 * hash + Objects.hashCode(this.address);
        hash = 71 * hash + Objects.hashCode(this.longitude);
        hash = 71 * hash + Objects.hashCode(this.latitude);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude)) {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Location{" + "LocationPK=" + id + ", LocationName=" + name + ", Description=" + description + ", LocationAddress=" + address + ", Latitude=" + latitude + ", Longitude=" + longitude + '}';
    }

}
