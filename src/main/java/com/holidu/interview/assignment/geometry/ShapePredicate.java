package com.holidu.interview.assignment.geometry;

import java.util.function.Predicate;

/**
 * The shape predicates contract.
 * @author Vadim Babich
 */
public interface ShapePredicate extends Predicate<Location>, Shape {


    /**
     * Constructor that lets you customize a circle predicate such as radius and center coordinates.
     *
     * @return CircleShapePredicate.Builder - circle predicate builder.
     */
    static GCSCircleShapePredicate.Builder newCircleShapePredicateBuilder() {
        return new GCSCircleShapePredicate.Builder();
    }
}
