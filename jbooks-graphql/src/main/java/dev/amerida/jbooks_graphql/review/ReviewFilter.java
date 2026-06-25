package dev.amerida.jbooks_graphql.review;

public record ReviewFilter(Integer rating,
                           Boolean verified,
                           String reviewerName) {
}
