package com.holidu.interview.assignment.geometry.utils;

import java.util.function.Function;

/**
 * It is a helper class that provides static methods for working with coordinates.
 * @author Vadim Babich
 */
public class CoordinateUtils {

    private CoordinateUtils() {
    }

    /**
     * Transform distance in meters to latitude value in geographic coordinate system (GCS)
     * @param distanceInMeters distance in meters
     * @return latitude value in geographic coordinate system
     */
    public static Double toLatitudeInGCS(Double distanceInMeters){
        //Length in meters of 1° of latitude = always 111.32 km
        return distanceInMeters / 111_320d;
    }

    /**
     * Transform distance in meters to latitude value in geographic coordinate system (GCS)
     * @param distanceInLatitudeGCS distance in geographic coordinate system (GCS)
     * @return latitude value in meters
     */
    public static Double toLatitudeInMeters(Double distanceInLatitudeGCS){
        //Length in meters of 1° of latitude = always 111.32 km
        return distanceInLatitudeGCS * 111_320d;
    }

    /**
     * Transform distance in meters to longitude value in geographic coordinate system
     * @param latitude the latitude value in the geographic coordinate system
     *                 in which the longitude region is located for calculating the distance.
     * @return Function that converts distance in meters to longitude in a geographic coordinate system
     */
    public static Function<Double, Double> toLongitudeInGCSFor(Double latitude){
        double measure = getLongitudeMeasureBy(latitude);
        return  meters -> meters / measure;
    }

    /**
     * Transform distance in geographic coordinate system (GCS) to longitude value in meters
     * @param latitude the latitude value in the geographic coordinate system
     *                 in which the longitude region is located for calculating the distance.
     * @return Function that converts distance in (GCS) to longitude in meters
     */
    public static Function<Double, Double> toLongitudeInMetersFor(Double latitude){
        //Length in meters of 1° of longitude = 40075 km * cos( latitude ) / 360
        double measure = getLongitudeMeasureBy(latitude);
        return  longitudeGCS -> longitudeGCS * measure;
    }

    /**
     * Length in meters of 1° of longitude = 40075 km * cos( latitude ) / 360
     * @param latitude latitude in GCS
     * @return measure value for a particular latitude
     */
    public static double getLongitudeMeasureBy(double latitude){
        return 40_075_000 * Math.cos(Math.toRadians(latitude)) / 360;
    }

    /**
     * Validation that latitude has valid value from -90 to 90. Throwing IllegalArgumentException if value isn't correct.
     * @param latitude latitude value to check
     */
    public static void validateLatitude(Double latitude) {
        if (-90 < latitude && latitude < 90) {
            return;
        }
        throw new IllegalArgumentException(String.format("Latitude %f cannot be less than -90 or more than 90"
                , latitude));
    }

    /**
     * Validation that longitude has valid value from -180 to 180. Throwing IllegalArgumentException if value isn't correct.
     * @param longitude longitude value to check
     */
    public static void validateLongitude(Double longitude) {
        if (-180 < longitude && longitude < 180) {
            return;
        }
        throw new IllegalArgumentException(String.format("Longitude %f cannot be less than -180 or more than 180"
                , longitude));
    }
}
