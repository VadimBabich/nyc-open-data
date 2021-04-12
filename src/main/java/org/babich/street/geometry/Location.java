package org.babich.street.geometry;

/**
 * The object of this interface is its own location.
 * @author Vadim Babich
 */
public interface Location {

    /**
     * Retrieves the value of the Latitude-coordinate
     */
    Double getLatitude();

    /**
     * Retrieves the value of the Longitude-coordinate
     */
    Double getLongitude();
}
