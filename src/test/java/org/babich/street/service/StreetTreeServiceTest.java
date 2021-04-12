package org.babich.street.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.babich.street.dto.TreeDTO;
import org.babich.street.exception.ServiceExecutingException;
import org.babich.street.geometry.ShapePredicate;
import com.socrata.api.Soda2Consumer;
import com.socrata.model.soql.SoqlQuery;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Unit test of StreetTreeService
 * @author Vadim Babich
 */
public class StreetTreeServiceTest {

    private SodaStreetTreeService underTest;

    @Before
    public void setUp() {
        TypeReference<List<TreeDTO>> listTypeReference = new TypeReference<>() {
        };
        ObjectReader objectReader = new ObjectMapper().readerFor(listTypeReference);

        underTest = new SodaStreetTreeService(Mockito.mock(Soda2Consumer.class)) {
            @Override
            protected List<TreeDTO> executeQuery(SoqlQuery soqlQuery) throws ServiceExecutingException {
                Path path = Paths.get("src/test/resources/within_rect.json");
                try {
                    return objectReader.readValue(path.toFile());
                } catch (IOException e) {
                    throw new ServiceExecutingException("Cannot read data from file " + path);
                }
            }
        };
    }

    @Test
    public void shouldResultTreesInCircleAreaToBeAggregatedByNameWhenGivenRectangleArea()
            throws ServiceExecutingException {

        ShapePredicate circleShape = ShapePredicate.newCircleShapePredicateBuilder()
                .withRadiusInMeters(50)
                .withLatitudeCenter(47.59815d)
                .withLongitudeCentre(-122.334540)
                .buildCircle();


        Map<String, Integer> value = underTest.getAggregatedByNameTreesIn(circleShape);

        Assert.assertThat(value, Matchers.hasEntry("red maple", 4));
        Assert.assertThat(value, Matchers.hasEntry("American linden", 5));
        Assert.assertThat(value, Matchers.hasEntry("London planetree", 3));
    }

}