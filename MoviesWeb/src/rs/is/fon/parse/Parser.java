package rs.is.fon.parse;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import rs.is.fon.domain.AggregateRating;
import rs.is.fon.domain.Audience;
import rs.is.fon.domain.Movie;
import rs.is.fon.domain.Organization;
import rs.is.fon.domain.Person;
import rs.is.fon.domain.Rating;
import rs.is.fon.domain.Review;
import rs.is.fon.persistance.DataModelManager;
import rs.is.fon.util.URIGenerator;

public class Parser {

	public static Movie parseMovie(String url) throws Exception {

		Document doc = Jsoup.connect(url).get();

		Element movieDiv = doc.select(
				"div[itemscope][itemtype=http://schema.org/Movie]").first();
		Movie movie = getBasicInfoAboutMovie(movieDiv);

		Element audienceElem = doc.select(
				"span[itemscope][itemtype=http://schema.org/Audience]").first();
		Audience audience = parseAudience(audienceElem);
		movie.setAudience(audience);

		Element organizationElem = doc.select(
				"span[itemscope][itemtype=http://schema.org/Organization]")
				.first();
		Organization organization = parseOrganization(organizationElem);
		movie.setCreatorOrganizator(organization);

		Date today = new Date();
		System.out.println(today);
		if (movie.getDatePublished()!=null) {

			if (today.after(movie.getDatePublished())) {

				Element aggregateRatingElem = doc
						.select("div[itemscope][itemtype=http://schema.org/AggregateRating]")
						.first();
				AggregateRating aggregateRating = parseAggregateRating(aggregateRatingElem);
				if (aggregateRating == null) {
					movie.setUri(URIGenerator.generate(movie));
					DataModelManager.getInstance().save(movie);
					return movie;
				}
				movie.setAggregateRating(aggregateRating);

				Element reviewElem = doc.select(
						"span[itemscope][itemtype=http://schema.org/Review]")
						.first();
				Review review = parseReview(reviewElem);
				movie.setReview(review);
			}
		}
		movie.setUri(URIGenerator.generate(movie));
		DataModelManager.getInstance().save(movie);
		return movie;

	}

	private static Review parseReview(Element reviewElem) throws Exception {
		Review review = new Review();
		if (reviewElem == null) {
			return null;
		}

		String name = reviewElem.select("strong[itemprop=name]").text();
		System.out.println("IME REVIEW: " + name);
		review.setName(name);

		String author = reviewElem.select("span[itemprop=author]").text();
		System.out.println("AUTOR REVIEW-A: " + author);
		review.setAuthor(author);

		String date = reviewElem.select("meta[itemprop=datePublished]").attr(
				"content");
		review.setDatePublished(new SimpleDateFormat("yyyy-MM-dd").parse(date));
		System.out.println("DATUM OBJAVLJIVANJA: " + review.getDatePublished());// srediti
																				// format

		String rb = reviewElem.select("p[itemprop=reviewBody]").text();
		review.setReviewBody(rb);
		System.out.println("REVIEW BODY: " + rb);

		Element rating = reviewElem.select(
				"span[itemscope][itemtype=http://schema.org/Rating]").first();
		Rating ratingReview = parseRating(rating);
		review.setReviewRating(ratingReview);

		review.setUri(URIGenerator.generate(review));
		DataModelManager.getInstance().save(review);
		return review;
	}

	private static Rating parseRating(Element rating) throws Exception {
		Rating r = new Rating();

		String wr = rating.select("meta[itemprop=worstRating]").attr("content");
		int worstRating = Integer.parseInt(wr);
		r.setWorstRating(worstRating);
		System.out.println("WORST RATING: " + worstRating);

		String rv = rating.select("meta[itemprop=ratingValue]").attr("content");
		double ratingValue = Double.parseDouble(rv);
		r.setRatingValue(ratingValue);
		System.out.println("RATING VALUE: " + ratingValue);

		String br = rating.select("meta[itemprop=bestRating]").attr("content");
		int bestRating = Integer.parseInt(br);
		r.setBestRating(bestRating);
		System.out.println("BEST RATING: " + bestRating);

		r.setUri(URIGenerator.generate(r));
		DataModelManager.getInstance().save(r);
		return r;
	}

	private static AggregateRating parseAggregateRating(
			Element aggregateRatingElem) throws Exception {
		AggregateRating aggregateRating = new AggregateRating();

		String rv = aggregateRatingElem.select("span[itemprop=ratingValue]")
				.text();
		if (rv.isEmpty()) {
			return null;
		}
		double ratingValue = Double.parseDouble(rv);
		aggregateRating.setRatingValue(ratingValue);
		System.out.println("RATING VALUE: " + ratingValue);

		String br = aggregateRatingElem.select("span[itemprop=bestRating]")
				.text();
		int bestRating = Integer.parseInt(br);
		aggregateRating.setBestRating(bestRating);
		System.out.println("BEST RATING: " + bestRating);

		String rc = aggregateRatingElem.select("span[itemprop=ratingCount]")
				.text();
		System.out.println("RATING COUNT: " + rc);
		String[] niz = rc.split(",");
		String bb = "";
		for (int i = 0; i < niz.length; i++) {
			bb += niz[i];
		}
		int ratingCount = Integer.parseInt(bb);
		aggregateRating.setRatingCount(ratingCount);

		Collection<String> rwc = new LinkedList<String>();
		Elements rvc = aggregateRatingElem.select("span[itemprop=reviewCount]");
		for (Element element : rvc) {
			rwc.add(element.text());
		}
		System.out.println("REVIEW COUNT: " + rwc);
		aggregateRating.setReviewCount(rwc);

		aggregateRating.setUri(URIGenerator.generate(aggregateRating));
		DataModelManager.getInstance().save(aggregateRating);
		return aggregateRating;
	}

	private static Organization parseOrganization(Element organizationElem)
			throws Exception {

		if (organizationElem == null) {
			return null;
		}
		Organization org = new Organization();

		String name = organizationElem.select("span[itemprop=name]").text();
		org.setName(name);
		System.out.println("IME ORGANIZACIJE: " + name);

		String url = organizationElem.select("a[itemprop=url]").attr("href");
		System.out.println("URL ORGANIZACIJE: " + url);
		if (!url.isEmpty()) {
			org.setUrl(new URI(url));
		}
		org.setUri(URIGenerator.generate(org));
		DataModelManager.getInstance().save(org);
		return org;
	}

	private static Audience parseAudience(Element audience) throws Exception {
		Audience a = new Audience();

		String url = audience.select("a[itemprop=url]").attr("href");
		System.out.println("URL AUDIENCE: " + url);
		if (!url.isEmpty()) {
			a.setUrl(new URI(url));
		}
		a.setUri(URIGenerator.generate(a));
		DataModelManager.getInstance().save(a);
		return a;
	}

	private static Movie getBasicInfoAboutMovie(Element movieDiv)
			throws Exception {
		Movie m = new Movie();

		System.out.println("PODACI O FILMU");

		String image = movieDiv.select("img[itemprop=image]").attr("src");
		System.out.println("URL slike: " + image);
		if (!image.isEmpty()) {
			m.setImage(new URI(image));
		}

		String name = movieDiv.select("h1 span[itemprop=name]").text();
		m.setName(name);
		System.out.println("NAZIV: " + name);

		String cr = movieDiv.select("span[itemprop=contentRating]").attr(
				"content");
		m.setContentRating(cr);
		System.out.println("CONTENT RATING: " + cr);

		String duration = movieDiv.select("time[itemprop=duration]").attr(
				"datetime");
		System.out.println("TRAJANJE: " + duration);
		if (!duration.isEmpty()) {
			String dd = duration.replaceAll("[PTM]", "");
			int dur = Integer.parseInt(dd);
			m.setDuration(dur);
		}

		Elements genresElem = movieDiv.select("a span[itemprop=genre]");
		Collection<String> genres = new LinkedList<String>();
		for (Element el : genresElem) {
			String g = el.text();
			genres.add(g);
		}
		m.setGenre(genres);
		System.out.println("ZANR: " + genres);

		String date = movieDiv.select("a meta[itemprop=datePublished]").attr(
				"content");
		System.out.println("neki datum hvata " + date);
		// m.setDatePublished(new SimpleDateFormat("yyyy-MM-dd").parse(date));
		if (!date.isEmpty()) {
			m.setDatePublished(new SimpleDateFormat("yyyy").parse(date));
			System.out.println("DATUM OBJAVLJIVANJA: " + m.getDatePublished());// srediti
																				// format
		}

		String description = movieDiv.select("div[itemprop=description] p")
				.text();
		m.setDescription(description); // izbaciti tekst za link
		System.out.println("OPIS: " + description);
		// 2.description???
		// nadovezati na ovaj String?

		Elements keyWElem = movieDiv.select("span[itemprop=keywords]");
		Collection<String> keywords = new LinkedList<String>();
		for (Element kw : keyWElem) {
			String k = kw.text();
				keywords.add(k);
			
		}
		m.setKeywords(keywords);
		System.out.println("KLJUCNE RECI: " + keywords);

		Elements awardsWElem = movieDiv.select("span[itemprop=awards]");
		Collection<String> awards = new LinkedList<String>();
		for (Element aw : awardsWElem) {
			String k = aw.text();
			if (k.length()>0) {
				awards.add(k);
			}
			
		}
		m.setAwards(awards);
		System.out.println("NAGRADE: " + awards);

		Elements thumbnailUrlElem = movieDiv.select("a[itemprop=thumbnailUrl]");
		Collection<String> thumbnailUrl = new LinkedList<String>();
		for (Element thum : thumbnailUrlElem) {
			String t = thum.attr("href");
			thumbnailUrl.add(t);
		}
		m.setThumbnailUrl(thumbnailUrl);
		System.out.println("THUMBNAIURL: " + thumbnailUrl);

		System.out.println("PODACI O REZISERU");
		Element d = movieDiv.select("div[itemprop=director").first();
		Person director = parsePerson(d);
		m.setDirector(director);

		System.out.println("PODACI O SVIM GLUMCIMA");
		Elements a = movieDiv.select("td[itemprop=actor");
		Collection<Person> actor = new LinkedList<Person>();

		for (Element e : a) {
			Person p = parsePerson(e);
			actor.add(p);
		}
		m.setActor(actor);
		
		System.out.println("PODACI O KREATORU");
		Element c = movieDiv.select("div[itemprop=creator]").first();
		Collection<Person> per = parsePersons(c);
		m.setCreator(per);

		System.out.println("PODACI O 3 GLUMCA");
		Element ac = movieDiv.select("div[itemprop=actors]").first();
		Collection<Person> actors = parsePersons(ac);
		m.setActors(actors);

		return m;
	}

	private static Collection<Person> parsePersons(Element c) throws Exception {
		Collection<Person> persons = new LinkedList<Person>();

		if (c==null) {
			return null;
		}
		Elements elem = c.select("a");
		
		for (Element e : elem) {
			Person p = new Person();
			String name = e.select("span[itemprop=name]").text();
			if (name.isEmpty()) {
				return persons; // izbegavanje linka a kod property-ja actors
				// koji ne predstavlja Person
			}
			p.setName(name);

			System.out.println("IME OSOBE: " + p.getName());
			String url = e.attr("href");
			if (!url.isEmpty()) {
				p.setUrl(new URI(url));
				System.out.println("URL OSOBE: " + url);
			}
			p.setUri(URIGenerator.generate(p));
			DataModelManager.getInstance().save(p);
			persons.add(p);
		}

		return persons;
	}

	private static Person parsePerson(Element element) throws Exception {
		Person p = new Person();

		String url = element.select("a[itemprop=url]").attr("href");
		if (!url.isEmpty()) {
			p.setUrl(new URI(url));
		}
		String name = element.select("span[itemprop=name]").text();
		System.out.println("IME OSOBE: " + name);
		System.out.println("URL OSOBE: " + url);
		p.setName(name);

		p.setUri(URIGenerator.generate(p));
		DataModelManager.getInstance().save(p);
		return p;
	}

	public static void parseIMDB(String allMovies) throws Exception {
		Collection<Movie> movies = new LinkedList<Movie>();
		Document doc = Jsoup.connect(allMovies).userAgent("Mozilla").get();
		Element table = doc.select("table.results").first();

		Elements rows = table.select("tr td.image");

		//ThreadPool tp = new ThreadPool();

		for (Element element : rows) {
			String url = element.select("a").attr("href");
			String i = "http://www.imdb.com" + url;
			// System.out.println( "URL FILMAAAAAAAAAA " +i);
			// ParserWorker parser = new ParserWorker(i);
			// tp.runTask(parser);
			Movie m = parseMovie(i);
			movies.add(m);
		}
		// tp.shutDown();
		// return movies;
	}
}