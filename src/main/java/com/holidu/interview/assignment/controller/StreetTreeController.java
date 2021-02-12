package com.holidu.interview.assignment.controller;

import com.holidu.interview.assignment.exception.BadArgumentException;
import com.holidu.interview.assignment.exception.ServiceExecutingException;
import com.holidu.interview.assignment.geometry.ShapePredicate;
import com.holidu.interview.assignment.service.StreetTreeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 *
 * A REST endpoint that provides information about trees in streets.
 * @author Vadim Babich
 */
@RestController
@RequestMapping("/street-tree")
public class StreetTreeController {

    private final StreetTreeService streetTreeService;

    public StreetTreeController(StreetTreeService streetTreeService) {
        this.streetTreeService = streetTreeService;
    }

    /**
     * Make an aggregated search of trees in the circle with {@code radius},
     * latitude ({@code Y}) and longitude ({@code X}) coordinates of the center point.
     * @param radius a search radius in meters
     * @param X longitude of the center point from -180 to 180.
     * @param Y latitude of the center point from -90 to 90.
     * @return Aggregation presented in the form of a map - 'common name': count
     * @throws ServiceExecutingException when an error occurred in a third-party service.
     * @throws BadArgumentException when invalid arguments are passed to the REST endpoint.
     */
    @GetMapping("/in-circle-area")
    public Map<String, Integer> getAggregatedByNameTreesInCircleArea(
            @RequestParam(value = "radius") Integer radius,
            @RequestParam(value = "x") Double X,
            @RequestParam(value = "y") Double Y) throws ServiceExecutingException, BadArgumentException {

        ShapePredicate circleShape = getCircleShape(radius, X, Y);

        return streetTreeService.getAggregatedByNameTreesIn(circleShape);
    }


    private static ShapePredicate getCircleShape(Integer radius, Double X, Double Y) throws BadArgumentException{
        try {
            return ShapePredicate.newCircleShapePredicateBuilder()
                    .withRadiusInMeters(radius)
                    .withLatitudeCenter(Y)
                    .withLongitudeCentre(X)
                    .buildCircle();
        } catch (IllegalArgumentException e) {
            throw new BadArgumentException(e.getMessage());
        }
    }

}
