package org.babich.street.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.babich.street.geometry.Location;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.util.Objects;

/**
 * Data transfer object used for interoperability with Street Tree Census service
 * @author Vadim Babich
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class TreeDTO implements Location {

    public static final GenericType<List<TreeDTO>> LIST_TYPE = new GenericType<>() {
    };

    final String  name;
    final Double latitude;
    final Double longitude;

    @JsonCreator
    public TreeDTO(@JsonProperty(value = "spc_common") String name
            , @JsonProperty("latitude") Double latitude
            , @JsonProperty("longitude") Double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public String getName() {
        return name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "TreeDTO{" +
                "name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeDTO treeDTO = (TreeDTO) o;
        return Objects.equals(name, treeDTO.name) && Objects.equals(latitude, treeDTO.latitude) && Objects.equals(longitude, treeDTO.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, latitude, longitude);
    }
}
