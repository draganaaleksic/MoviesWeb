package rs.is.fon.domain;

import rs.is.fon.util.Constants;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

@Namespace(Constants.SCHEMA)
@RdfType("Review")
public class Review extends CreativeWork{

	@RdfProperty(Constants.SCHEMA + "reviewRating")
	private Rating reviewRating;
	
	@RdfProperty(Constants.SCHEMA + "reviewBody")
	private String reviewBody;
	
	

	public Rating getReviewRating() {
		return reviewRating;
	}

	public void setReviewRating(Rating reviewRating) {
		this.reviewRating = reviewRating;
	}

	public String getReviewBody() {
		return reviewBody;
	}

	public void setReviewBody(String reviewBody) {
		this.reviewBody = reviewBody;
	}

	
	
	
}
