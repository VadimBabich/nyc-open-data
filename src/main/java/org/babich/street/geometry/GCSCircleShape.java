package org.babich.street.geometry;

import java.util.Objects;

/**
 * This is the basic shape of a circle.
 * @author Vadim Babich
 */
public class GCSCircleShape implements Shape {

    private final double minLatitude;
    private final double maxLatitude;

    private final double minLongitude;
    private final double maxLongitude;

    private final double latitudeCenter;
    private final double longitudeCentre;

    private final double width;
    private final double height;

    public GCSCircleShape(double latitudeCenter, double longitudeCentre, double latitudeRadius, double longitudeRadius) {
        this.latitudeCenter = latitudeCenter;
        this.longitudeCentre = longitudeCentre;
        this.minLatitude = latitudeCenter - latitudeRadius;
        this.maxLatitude = latitudeCenter + latitudeRadius;
        this.minLongitude = longitudeCentre - longitudeRadius;
        this.maxLongitude = longitudeCentre + longitudeRadius;
        this.width = maxLatitude - minLatitude;
        this.height = maxLongitude - minLongitude;
    }


    public Double getLatitudeCenter() {
        return latitudeCenter;
    }

    public Double getLongitudeCentre() {
        return longitudeCentre;
    }

    @Override
    public Double getWidth() {
        return width;
    }

    @Override
    public Double getHeight() {
        return height;
    }

    @Override
    public Double getMinLatitude() {
        return minLatitude;
    }

    @Override
    public Double getMaxLatitude() {
        return maxLatitude;
    }

    @Override
    public Double getMinLongitude() {
        return minLongitude;
    }

    @Override
    public Double getMaxLongitude() {
        return maxLongitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GCSCircleShape that = (GCSCircleShape) o;
        return Double.compare(that.minLatitude, minLatitude) == 0 && Double.compare(that.maxLatitude, maxLatitude) == 0 && Double.compare(that.minLongitude, minLongitude) == 0 && Double.compare(that.maxLongitude, maxLongitude) == 0 && Double.compare(that.latitudeCenter, latitudeCenter) == 0 && Double.compare(that.longitudeCentre, longitudeCentre) == 0 && Double.compare(that.width, width) == 0 && Double.compare(that.height, height) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minLatitude, maxLatitude, minLongitude, maxLongitude, latitudeCenter, longitudeCentre, width, height);
    }
}
