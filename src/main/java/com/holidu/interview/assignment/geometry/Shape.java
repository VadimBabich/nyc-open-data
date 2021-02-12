package com.holidu.interview.assignment.geometry;

/**
 * The contract that represent base geometry of a shape.
 * @author Vadim Babich
 */
public interface Shape {

    /**
     * Returns the difference between the maximum and minimum Latitude values.
     */
    Double getWidth();

    /**
     * Returns the difference between the maximum and minimum Longitude values.
     */
    Double getHeight();

    /**
     * the minimum Latitude-coordinate
     */
    Double getMinLatitude();

    /**
     * the maximum Latitude-coordinate
     */
    Double getMaxLatitude();

    /**
     * the minimum Longitude-coordinate
     */
    Double getMinLongitude();

    /**
     * the maximum Longitude-coordinate
     */
    Double getMaxLongitude();

    /**
     * the centre Latitude-coordinate of this shape
     */
    Double getLatitudeCenter();

    /**
     * the centre Longitude-coordinate of this shape
     */
    Double getLongitudeCentre();
}
