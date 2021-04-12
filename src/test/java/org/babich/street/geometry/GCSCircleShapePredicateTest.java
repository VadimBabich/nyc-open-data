package org.babich.street.geometry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.babich.street.dto.TreeDTO;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Unit test for geometry
 * @author Vadim Babich
 */
public class GCSCircleShapePredicateTest {

    private ObjectReader objectReader;

    @Before
    public void setUp() {
        TypeReference<List<TreeDTO>> listTypeReference = new TypeReference<>() {
        };
        objectReader = new ObjectMapper().readerFor(listTypeReference);
    }

    @Test
    public void givenLocationListOfRectThenFilteringAsCircleAndResultSetShouldMatchToCircle() throws IOException {

        ShapePredicate asCircleFigure = ShapePredicate.newCircleShapePredicateBuilder()
                .withRadiusInMeters(50)
                .withLatitudeCenter(47.59815d)
                .withLongitudeCentre(-122.334540)
                .buildCircle();


        List<TreeDTO> givenRectLocationList = objectReader
                .readValue(Paths.get("src/test/resources/within_rect.json").toFile());

        List<TreeDTO> filteredAsCircleLocationList = givenRectLocationList.stream()
                .filter(asCircleFigure)
                .collect(Collectors.toList());


        List<TreeDTO> expectedCircleList = objectReader
                .readValue(Paths.get("src/test/resources/within_circle.json").toFile());

        Assert.assertThat(filteredAsCircleLocationList, Matchers.containsInAnyOrder(expectedCircleList.toArray()));
    }

}