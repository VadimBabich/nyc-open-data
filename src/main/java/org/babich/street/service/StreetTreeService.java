package org.babich.street.service;

import org.babich.street.exception.ServiceExecutingException;
import org.babich.street.geometry.ShapePredicate;

import java.util.Map;

/**
 * Service for fetching, filtering and aggregating data on street trees.
 * @author Vadim Babich
 */
public interface StreetTreeService {

    /**
     * Makes an aggregated search of trees.
     * @param shapeArea used as a dataset filter that cuts it out as a specific shape.
     * @return Aggregation presented in the form of a map - 'common name': count
     * @throws ServiceExecutingException
     */
    Map<String, Integer> getAggregatedByNameTreesIn(ShapePredicate shapeArea) throws ServiceExecutingException;
}
