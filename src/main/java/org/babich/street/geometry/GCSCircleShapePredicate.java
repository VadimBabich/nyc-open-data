package org.babich.street.geometry;

import org.babich.street.geometry.utils.CoordinateUtils;

import java.util.function.Function;

import static org.babich.street.geometry.utils.CoordinateUtils.*;

public class GCSCircleShapePredicate extends GCSCircleShape implements ShapePredicate {

    private final Function<Double, Double> toLongitudeInMeters;
    private final Double radiusRaisedToPowerTwo;

    private GCSCircleShapePredicate(double latitudeCenter
            , double longitudeCentre
            , double latitudeRadius
            , double longitudeRadius) {

        super(latitudeCenter, longitudeCentre, latitudeRadius, longitudeRadius);

        this.toLongitudeInMeters = CoordinateUtils.toLongitudeInMetersFor(latitudeCenter);
        this.radiusRaisedToPowerTwo = latitudeRadiusRaisedToPowerTwoInMeters(latitudeRadius);
    }

    @Override
    public boolean test(Location location) {
        double latitudeInMeters = toLatitudeInMeters(getDistanceInLatitudeToCenter(location));
        double longitudeInMeters = toLongitudeInMeters(getDistanceInLongitudeToCenter(location));

        return isInRing(latitudeInMeters, longitudeInMeters);
    }

    // cˆ2 = aˆ2 * bˆ2;
    private boolean isInRing(double latitudeInMeters, double longitudeInMeters) {
        return radiusRaisedToPowerTwo >= (Math.pow(latitudeInMeters, 2.0) + Math.pow(longitudeInMeters, 2.0));
    }

    private double getDistanceInLongitudeToCenter(Location location) {
        return location.getLongitude() - getLongitudeCentre();
    }

    private double getDistanceInLatitudeToCenter(Location location) {
        return location.getLatitude() - getLatitudeCenter();
    }

    private double toLongitudeInMeters(double distanceInLongitude) {
        return toLongitudeInMeters.apply(distanceInLongitude);
    }

    static private double latitudeRadiusRaisedToPowerTwoInMeters(double latitudeDistance) {
        return Math.pow(toLatitudeInMeters(latitudeDistance), 2.0);
    }

    public static class Builder {

        private Double diameterInMeters;
        private Double latitudeCenter;
        private Double longitudeCentre;

        Builder() {
        }

        public Builder withRadiusInMeters(Integer radiusInMeters) {
            this.diameterInMeters = radiusInMeters.doubleValue() * 2;
            return this;
        }

        public Builder withLatitudeCenter(Double latitudeCenter) {
            this.latitudeCenter = latitudeCenter;
            return this;
        }

        public Builder withLongitudeCentre(Double longitudeCentre) {
            this.longitudeCentre = longitudeCentre;
            return this;
        }

        private double getLatitudeWidthInGCS() {
            return CoordinateUtils.toLatitudeInGCS(diameterInMeters);
        }

        private double getLongitudeHeightInGCS() {
            return CoordinateUtils.toLongitudeInGCSFor(latitudeCenter).apply(diameterInMeters);
        }


        public ShapePredicate buildCircle() {

            validateLatitude(latitudeCenter);
            validateLongitude(longitudeCentre);

            double width = getLatitudeWidthInGCS();
            double height = getLongitudeHeightInGCS();

            double latitudeRadius = width / 2;
            double longitudeRadius = height / 2;

            return new GCSCircleShapePredicate(latitudeCenter, longitudeCentre, latitudeRadius, longitudeRadius);
        }
    }

}
