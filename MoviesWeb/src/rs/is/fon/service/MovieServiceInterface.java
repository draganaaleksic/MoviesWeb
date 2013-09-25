package rs.is.fon.service;

import java.util.Collection;


import rs.is.fon.domain.Movie;
import rs.is.fon.domain.Review;

public interface MovieServiceInterface {
	public Collection<Movie> getAllMovies(String name, String genre,
			String director, String actor, double minAggregateRating,
			int minDuration, int maxDuration, boolean hasAward,
			int minReleaseYear, int maxReleaseYear, int limit);
	
	Collection<Review> getReviewForMovie(String id, String name, String author);

}
