package rs.is.fon.domain;

import rs.is.fon.util.Constants;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

@Namespace(Constants.SCHEMA)
@RdfType("Rating")
public class Rating extends Thing{

	@RdfProperty(Constants.SCHEMA + "bestRating")
	private int bestRating;
	
	@RdfProperty(Constants.SCHEMA + "worstRating")
	private int worstRating;
	
	@RdfProperty(Constants.SCHEMA + "ratingValue")
	private double ratingValue;

	public int getBestRating() {
		return bestRating;
	}

	public void setBestRating(int bestRating) {
		this.bestRating = bestRating;
	}

	public int getWorstRating() {
		return worstRating;
	}

	public void setWorstRating(int worstRating) {
		this.worstRating = worstRating;
	}

	public double getRatingValue() {
		return ratingValue;
	}

	public void setRatingValue(double ratingValue) {
		this.ratingValue = ratingValue;
	}
	
	
}
