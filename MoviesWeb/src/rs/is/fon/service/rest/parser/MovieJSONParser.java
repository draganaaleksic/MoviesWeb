package rs.is.fon.service.rest.parser;



import rs.is.fon.util.Constants;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import rs.is.fon.domain.AggregateRating;
import rs.is.fon.domain.Audience;
import rs.is.fon.domain.Movie;
import rs.is.fon.domain.Organization;
import rs.is.fon.domain.Person;
import rs.is.fon.domain.Rating;
import rs.is.fon.domain.Review;

public class MovieJSONParser {
	public static JsonObject serialize(Movie m) {
		JsonObject movieJson = new JsonObject();

		movieJson.addProperty("uri", m.getUri().toString());
		movieJson.addProperty("name", m.getName());
		movieJson.addProperty("description", m.getDescription());
		if (m.getDatePublished()!=null) {
			movieJson.addProperty("datePublished", m.getDatePublished().toString());
		}
		
		if (m.getImage() != null)
			movieJson.addProperty("image", m.getImage().toString());

		JsonArray movieKeyWords = new JsonArray();
		for (String keywords : m.getKeywords()) {
			movieKeyWords.add(new JsonPrimitive(keywords));
		}
		movieJson.add("keywords",movieKeyWords);

		JsonArray movieGenres = new JsonArray();
		for (String genre : m.getGenre()) {
			movieGenres.add(new JsonPrimitive(genre));
		}
		movieJson.add("genre",movieGenres);

		JsonArray movieAwards = new JsonArray();
		for (String award : m.getAwards()) {
			movieAwards.add(new JsonPrimitive(award));
		}
		movieJson.add("award",movieAwards);

		JsonArray movieThumnaiUrl = new JsonArray();
		for (String thum : m.getThumbnailUrl()) {
			movieThumnaiUrl.add(new JsonPrimitive(thum));
		}
		movieJson.add("thumnaiUrl",movieThumnaiUrl);
		
		movieJson.addProperty("duration", m.getDuration());
		movieJson.addProperty("contentRating", m.getContentRating());
		if (m.getAudience()!=null) {
			movieJson.add("audience", serializeAudience(m.getAudience()));
		}
		
		if (m.getCreatorOrganizator()!=null) {
			movieJson.add("creatorOrg",serializeOrganization(m.getCreatorOrganizator()));
		}
		if (m.getAggregateRating()!=null) {
			movieJson.add("aggregateRating", serializeAggregateRating(m.getAggregateRating()));
		}
		
		if(m.getReview()!=null)
			movieJson.add("reviews",serializeReview(m.getReview(), m.getUri().toString()));
		
		
	
		JsonArray movieActor = new JsonArray();
		for (Person person : m.getActor()) {
			movieActor.add(serializePerson(person));
		}
		movieJson.add("actor",movieActor);
		
		JsonArray movieActors = new JsonArray();
		for (Person person : m.getActors()) {
			movieActors.add(serializePerson(person));
		}
		movieJson.add("actors",movieActors);
		
		JsonArray movieCreator = new JsonArray();
		for (Person person : m.getCreator()) {
			movieCreator.add(serializePerson(person));
		}
		movieJson.add("creator",movieCreator);
		movieJson.add("director", serializePerson(m.getDirector()));
		
		
		return movieJson;

	}
	public static JsonObject serializeReview(Review r, String id){
		JsonObject reviewsJson = new JsonObject();
		
		reviewsJson.addProperty("uri", /*Constants.NS +*/ id + "/reviews");
				
		JsonArray reviews = new JsonArray();
		
			JsonObject json = new JsonObject();			
			json.addProperty("name", r.getName());
			json.addProperty("author", r.getAuthor());
			json.addProperty("datePublished", r.getDatePublished().toString());
			json.addProperty("reviewBody", r.getReviewBody());
			if(r.getReviewRating()!=null)
				json.add("reviewRating", serializeReviewRating(r.getReviewRating()));
			reviews.add(json);
		
		reviewsJson.add("reviews", reviews);
				
		return reviewsJson;
	}
	
	public static JsonObject serializeReviewRating(Rating r){
		JsonObject json = new JsonObject();
		
		json.addProperty("bestRating", r.getBestRating());
		json.addProperty("ratingValue", r.getRatingValue());
		json.addProperty("worstRating", r.getWorstRating());
				
		return json;
	}

	

	private static JsonObject serializePerson(Person person) {
		JsonObject personJSON = new JsonObject() ;
		personJSON.addProperty("name", person.getName());
		personJSON.addProperty("url", person.getUrl().toString());
		
		return personJSON;
	}



	private static JsonElement serializeAggregateRating(
			AggregateRating aggregateRating) {
		JsonObject aggregateRatingJSON = new JsonObject();
		
		aggregateRatingJSON.addProperty("ratingValue", aggregateRating.getRatingValue());
		aggregateRatingJSON.addProperty("reviewCount", aggregateRating.getReviewCount().toString());
		aggregateRatingJSON.addProperty("bestRating", aggregateRating.getBestRating());		
		aggregateRatingJSON.addProperty("ratingCount", aggregateRating.getRatingCount());	
		return aggregateRatingJSON;
	}

	private static JsonElement serializeOrganization(
			Organization creatorOrganizator) {
		JsonObject organizationJSON = new JsonObject();
		organizationJSON.addProperty("url", creatorOrganizator.getUrl()
				.toString());
		organizationJSON.addProperty("name", creatorOrganizator.getName());
		return organizationJSON;
	}

	private static JsonObject serializeAudience(Audience audience) {
		JsonObject audienceJSON = new JsonObject();
		if (audience.getUrl()!=null) {
			audienceJSON.addProperty("url", audience.getUrl().toString());
		}
		
		return audienceJSON;
	}
}
