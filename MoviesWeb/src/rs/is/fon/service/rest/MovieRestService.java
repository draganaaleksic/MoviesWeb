package rs.is.fon.service.rest;

import java.util.Collection;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hp.hpl.jena.tdb.TDB;

import rs.is.fon.domain.Movie;
import rs.is.fon.domain.Review;
import rs.is.fon.persistance.DataModelManager;
import rs.is.fon.service.MovieService;
import rs.is.fon.service.rest.parser.MovieJSONParser;

@Path("/movies")
public class MovieRestService {

	private MovieService movieService;

	public MovieRestService() {
		movieService = new MovieService();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllMoviesgetAllMovies(
			@DefaultValue("") @QueryParam("name") String name,
			@DefaultValue("") @QueryParam("genre") String genre,
			@DefaultValue("") @QueryParam("director") String director,
			@DefaultValue("") @QueryParam("actor") String actor,
			@DefaultValue("") @QueryParam("minAggregateRating") String minAggregateRating,
			@DefaultValue("") @QueryParam("minDuration") String minDuration,
			@DefaultValue("") @QueryParam("maxDuration") String maxDuration,
			@DefaultValue("true") @QueryParam("hasAward") String hasAward,
			@DefaultValue("") @QueryParam("minReleaseYear") String minReleaseYear,
			@DefaultValue("") @QueryParam("maxReleaseYear") String maxReleaseYear,
			@DefaultValue("5") @QueryParam("limit") String limit) {
		double minAR = 0;
		try {
			minAR = Double.parseDouble(minAggregateRating);
		} catch (Exception e) {
		}
		int minDur = 0;
		try {
			minDur = Integer.parseInt(minDuration);
		} catch (Exception e) {
		}
		int maxDur = 0;
		try {
			maxDur = Integer.parseInt(maxDuration);
		} catch (Exception e) {
		}
		boolean award = true;
		try {
			award = Boolean.parseBoolean(hasAward);
		} catch (Exception e) {
		}
		int maxRY = 0;
		try {
			maxRY = Integer.parseInt(maxReleaseYear);
		} catch (Exception e) {
		}
		int minRY = 0;
		try {
			minRY = Integer.parseInt(minReleaseYear);
		} catch (Exception e) {
		}
		int l = 0;
		try {
			l = Integer.parseInt(limit);
		} catch (Exception e) {
		}
		Collection<Movie> movies = movieService.getAllMovies(name, genre,
				director, actor, minAR, minDur, maxDur, award, minRY, maxRY, l);

		if (movies != null && !movies.isEmpty()) {
			JsonArray movieArray = new JsonArray();

			for (Movie m : movies) {
				JsonObject movieJson = MovieJSONParser.serialize(m);
				movieArray.add(movieJson);
			}
			TDB.sync(DataModelManager.getInstance().getModel());

			return movieArray.toString();
		}
		return null;

	}

	@GET
	@Path("{id}/reviews")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllReviews(@PathParam("id") String id,
			@DefaultValue("") @QueryParam("name") String name,
			@DefaultValue("") @QueryParam("author") String author) {
		Collection<Review> reviews = movieService.getReviewForMovie(id, name,
				author);
		System.out.println("lista " + reviews);
		if (reviews != null && !reviews.isEmpty()) {
			JsonArray reviewsArray = new JsonArray();

			for (Review r : reviews) {
				JsonObject movieJson = MovieJSONParser.serializeReview(r, id);
				reviewsArray.add(movieJson);
			}
			TDB.sync(DataModelManager.getInstance().getModel());

			return reviewsArray.toString();
		}
		System.out.println("nije vratio json");
		return null;
	}

}
