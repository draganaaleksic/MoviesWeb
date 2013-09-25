package rs.is.fon.service;

import java.util.ArrayList;
import java.util.Collection;

import rs.is.fon.persistance.DataModelManager;
import rs.is.fon.util.Constants;
import rs.is.fon.domain.Movie;
import rs.is.fon.domain.Review;

public class MovieService implements MovieServiceInterface {


	private QueryExecutor queryExecutor = new QueryExecutor();
	@Override
	public Collection<Movie> getAllMovies(String name, String genre,
			String director, String actor, double minAggregateRating,
			int minDuration, int maxDuration, boolean hasAward,
			int minReleaseYear, int maxReleaseYear, int limit) {

		Collection<Movie> movies = new ArrayList<Movie>();

		String where = " ?movie a schema:Movie. ";
		String filter = "";

		if (!name.isEmpty()) {
			where += "?movie schema:name ?name. ";
			filter += "FILTER regex( ?name, \"" + name + "\", \"i\" ) ";
		}
		if (!director.isEmpty()) {
			where += "?movie schema:director ?director. "
					+ "?director schema:name ?name. ";
			filter += "FILTER regex( ?name, \"" + director + "\", \"i\" ) ";
		}
		if (!actor.isEmpty()) {
			where += "?movie schema:actor ?actor. "
					+ "?actor schema:name ?name. ";
			filter += "FILTER regex( ?name, \"" + actor + "\", \"i\" ) ";
		}

		if (!genre.isEmpty()) {
			String[] categories = genre.split(",");
			for (String string : categories) {
				string = string.trim();
				where += "?movie schema:genre ?" + string + ". ";
				filter += "FILTER regex( ?" + string + ", \"" + string
						+ "\", \"i\" ) ";
			}
		}

		if (minAggregateRating!=0) {
			where += "?movie schema:aggregateRating ?aggregateRating. "
					+ "?aggregateRating schema:ratingValue ?ratingValue. ";
			filter += "FILTER ( ?ratingValue >=" + minAggregateRating + " ) ";
		}

		if (minDuration!=0 || maxDuration!=0) {
			where += "?movie schema:duration ?duration. ";

			if (minDuration!=0)
				filter += "FILTER ( ?duration >=" + minDuration + " ) ";
			if (maxDuration!=0)
				filter += "FILTER ( ?duration <=" + maxDuration + " ) ";
		}

		if (hasAward) {
			where += "?movie schema:awards ?awards. ";
			
		}

		if (minReleaseYear!=0 || maxReleaseYear!=0) {
			where += "?movie schema:datePublished ?datePublished. ";

			if (minReleaseYear!=0)
				filter += "FILTER ( YEAR(?datePublished) >=" + minReleaseYear
						+ " ) ";

			if (maxReleaseYear!=0)
				filter += "FILTER (  YEAR(?datePublished) <=" + maxReleaseYear
						+ " ) ";
		}

		String query =  "PREFIX movies: <" + Constants.NS + "> " + 
						"PREFIX schema: <" + Constants.SCHEMA + "> " + 
						"PREFIX xsd: <" + Constants.XSD + "> " 
						+ "SELECT ?movie " + "WHERE { " + where + filter + " } LIMIT "
						+ limit;

		Collection<String> movieUris = queryExecutor
				.executeOneVariableSelectSparqlQuery(query, "movie",
						DataModelManager.getInstance().getModel());

		for (String string : movieUris) {
			Movie movie = getMovie(string);
			movies.add(movie);
		}
		return movies;
	}

	public Movie getMovie(String uri) {
		Movie movie = queryExecutor.getMovie(uri);
		return movie;
	}

	@Override
	public Collection<Review> getReviewForMovie(String uriMovie, String name,
			String author) {

		Collection<Review> reviews = new ArrayList<Review>();
		String where = "movies:" + uriMovie + "  schema:review ?review ."
				+ " ?review a schema:Review.";
		String filter = "";
		if (!name.isEmpty()) {
			where += "?review schema:name ?name. ";
			filter += "FILTER regex( ?name, \"" + name + "\", \"i\" ) ";
		}
		if (!author.isEmpty()) {
			where += "?review schema:author ?author. ";
			filter += "FILTER regex( ?author, \"" + author + "\", \"i\" ) ";
		}
		String query =  "PREFIX movies: <" + Constants.NS + "> " + 
		   "PREFIX schema: <" + Constants.SCHEMA + "> " + 
		   "PREFIX xsd: <" + Constants.XSD + "> " 
				+ "SELECT ?review " + "FROM <" + uriMovie + ">" + "WHERE { "
				+ where + filter + " } ";

		Collection<String> reviewUris = queryExecutor
				.executeOneVariableSelectSparqlQuery(query, "review",
						DataModelManager.getInstance().getModel());

		for (String string : reviewUris) {
			Review review = getReview(string);
			reviews.add(review);

		}
		return reviews;

	}

	public Review getReview(String uri) {
		Review review = queryExecutor.getReview(uri);
		return review;
	}

}

