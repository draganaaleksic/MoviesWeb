package rs.is.fon.domain;

import java.util.ArrayList;
import java.util.Collection;

import rs.is.fon.util.Constants;
import thewebsemantic.RdfProperty;

public class AggregateRating extends Rating{

	@RdfProperty(Constants.SCHEMA + "ratingCount")
	private int ratingCount;

	@RdfProperty(Constants.SCHEMA + "reviewCount")
	private Collection<String> reviewCount;
	
	
	public AggregateRating() {
		reviewCount = new ArrayList<String>();
	}

	public int getRatingCount() {
		return ratingCount;
	}

	public void setRatingCount(int ratingCount) {
		this.ratingCount = ratingCount;
	}

	public Collection<String> getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(Collection<String> reviewCount) {
		this.reviewCount = reviewCount;
	}
	
	
	
}
