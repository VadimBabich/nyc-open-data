package org.babich.street.service;

import com.google.common.base.MoreObjects;
import org.babich.street.dto.TreeDTO;
import org.babich.street.exception.ServiceExecutingException;
import org.babich.street.geometry.Location;
import org.babich.street.geometry.Shape;
import org.babich.street.geometry.ShapePredicate;
import com.socrata.api.Soda2Consumer;
import com.socrata.builders.SoqlQueryBuilder;
import com.socrata.exceptions.SodaError;
import com.socrata.model.soql.SoqlQuery;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This is StreetTreeService implementation based on
 * The Socrata Open Data API allows you to programmatically access a wealth of open data resources.
 * @author Vadim Babich
 */
public class SodaStreetTreeService implements StreetTreeService {

    //The id of the resource to query.
    private String resourceId;

    //The number of results to return from a third party service
    private Integer batchLimit = 50_000;

    private final Soda2Consumer consumer;


    public SodaStreetTreeService(Soda2Consumer consumer) {
        this.consumer = consumer;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getBatchLimit() {
        return batchLimit;
    }

    public void setBatchLimit(Integer batchLimit) {
        this.batchLimit = batchLimit;
    }

    /**
     * Makes an aggregated search of trees in a third party service by the provided shape.
     * @param shapeArea used as a dataset filter that cuts it out as a specific shape.
     * @return Aggregation presented in the form of a map - 'common name': count
     * @throws ServiceExecutingException
     */
    @Override
    public Map<String, Integer> getAggregatedByNameTreesIn(ShapePredicate shapeArea)
            throws ServiceExecutingException {

        SoqlQuery soqlQuery = createSoqlQueryBy(shapeArea);

        List<TreeDTO> resultListOfTree = executeQuery(soqlQuery);

        validateResult(resultListOfTree);

        return aggregateByName(resultListOfTree, shapeArea);
    }

    protected void validateResult(Collection<? extends Location> locationCollection) throws ServiceExecutingException {
        if(locationCollection.size() == batchLimit){
            throw new ServiceExecutingException("Too much data in this area.");
        }
    }

    protected List<TreeDTO> executeQuery(SoqlQuery soqlQuery) throws ServiceExecutingException {
        try {
            return consumer.query(resourceId, soqlQuery, TreeDTO.LIST_TYPE);
        } catch (SodaError sodaError) {
            throw new ServiceExecutingException(sodaError.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        throw new ServiceExecutingException("An error occurred while executing a request to " +
                "the Street Tree Census service.");
    }

    private SoqlQuery createSoqlQueryBy(Shape shape) {
        return new SoqlQueryBuilder()
                .addSelectPhrase("spc_common, latitude, longitude")
                .setWhereClause("latitude >= " + shape.getMinLatitude()
                        + " and latitude <= " + shape.getMaxLatitude()
                        + " and longitude >= " + shape.getMinLongitude()
                        + " and longitude <= " + shape.getMaxLongitude())
                .setLimit(batchLimit)
                .build();
    }

    private Map<String, Integer> aggregateByName(List<TreeDTO> treeList, Predicate<Location> asShape) {
        return treeList.stream()
                .filter(asShape)
                .collect(Collectors.groupingBy(this::toName, Collectors.summingInt(e -> 1)));
    }

    private String toName(TreeDTO tree) {
        return MoreObjects.firstNonNull(tree.getName(), "no name");
    }

}
