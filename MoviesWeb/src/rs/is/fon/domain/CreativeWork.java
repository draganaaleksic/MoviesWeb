package rs.is.fon.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import rs.is.fon.util.Constants;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

@Namespace(Constants.SCHEMA)
@RdfType("CreativeWork")
public class CreativeWork extends Thing{
	
	@RdfProperty(Constants.SCHEMA + "datePublished")
	private Date datePublished;
	
	@RdfProperty(Constants.SCHEMA + "keywords")
	private Collection<String> keywords;
	
	@RdfProperty(Constants.SCHEMA + "genre")
	private Collection<String> genre;
	
	@RdfProperty(Constants.SCHEMA + "awards")
	private Collection<String> awards;
	
	@RdfProperty(Constants.SCHEMA + "thumbnailUrl")
	private Collection<String> thumbnailUrl;

	@RdfProperty(Constants.SCHEMA + "contentRating")
	private String contentRating;

	@RdfProperty(Constants.SCHEMA + "headline")
	private String headline; // mozda treba da bude lista
	
	@RdfProperty(Constants.SCHEMA + "audience")
	private Audience audience;
	
	@RdfProperty(Constants.SCHEMA + "review")
	private Review review;
	
	@RdfProperty(Constants.SCHEMA + "aggregateRating")
	private AggregateRating aggregateRating;

	@RdfProperty(Constants.SCHEMA + "creatorOrg")
	private Organization creatorOrganizator;

	@RdfProperty(Constants.SCHEMA + "creator")
	private Collection<Person> creator;
	
	@RdfProperty(Constants.SCHEMA + "provider")
	private String provider; //mozda treba da bude lista
	
	//jos jedan atribut datePublished ali da bude implementiran
	//kao lista koji ce da se odnosi na Related news???
	
	@RdfProperty(Constants.SCHEMA + "author")
	private String author;

	
	public CreativeWork() {
		keywords = new ArrayList<String>();
		awards = new ArrayList<String>();
		genre = new ArrayList<String>();
		thumbnailUrl = new ArrayList<String>();
		creator = new ArrayList<Person>();
	}

	public Date getDatePublished() {
		return datePublished;
	}

	public void setDatePublished(Date datePublished) {
		this.datePublished = datePublished;
	}

	public Collection<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(Collection<String> keywords) {
		this.keywords = keywords;
	}

	public Collection<String> getGenre() {
		return genre;
	}

	public void setGenre(Collection<String> genre) {
		this.genre = genre;
	}

	public Collection<String> getAwards() {
		return awards;
	}

	public void setAwards(Collection<String> awards) {
		this.awards = awards;
	}

	public Collection<String> getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(Collection<String> thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getContentRating() {
		return contentRating;
	}

	public void setContentRating(String contentRating) {
		this.contentRating = contentRating;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public Audience getAudience() {
		return audience;
	}

	public void setAudience(Audience audience) {
		this.audience = audience;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	public AggregateRating getAggregateRating() {
		return aggregateRating;
	}

	public void setAggregateRating(AggregateRating aggregateRating) {
		this.aggregateRating = aggregateRating;
	}

	public Organization getCreatorOrganizator() {
		return creatorOrganizator;
	}

	public void setCreatorOrganizator(Organization creatorOrganizator) {
		this.creatorOrganizator = creatorOrganizator;
	}

	public Collection<Person> getCreator() {
		return creator;
	}

	public void setCreator(Collection<Person> creator) {
		this.creator = creator;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	
	
	
}
