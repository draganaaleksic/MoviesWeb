package rs.is.fon.main;

import java.util.Collection;

import com.hp.hpl.jena.tdb.TDB;

import rs.is.fon.domain.Movie;
import rs.is.fon.domain.Review;
import rs.is.fon.parse.Parser;
import rs.is.fon.persistance.DataModelManager;
import rs.is.fon.service.MovieService;
import rs.is.fon.service.rest.MovieRestService;
import rs.is.fon.service.rest.parser.MovieJSONParser;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// Parser.parseMovie("http://www.imdb.com/title/tt1921064/");
			// Parser.parseIMDB("http://www.imdb.com/search/title?at=0&genres=drama&sort=user_rating,desc&start=1&title_type=feature");
		//Parser.parseIMDB("http://www.imdb.com/search/title?genres=thriller&title_type=feature");

			 //DataModelManager.getInstance().write("Movies.rdf", "TURTLE");
		//TDB.sync(DataModelManager.getInstance().getDataset());

			//QueryService qs = new QueryService();
			/*
			  Collection<Movie> m = qs.getAllMovies("Now You See Me", "", "", "", "", "", "",
			  "", "", "", "5"); 
			  
			 for (Movie movie : m) {
				System.out.println(movie.getName());
			}*/
			
			
			/*  Review r = qs.getAllReviews(
			  "http://is.fon.rs/movies/c3e33c52-ad15-4eef-9f0b-3a8ef2532ae1",
			  "", "");
			  
			  System.out.println(r.getAuthor()); */
			 

			/*MovieService ms = new MovieService();
			Review r = ms
					.getReviewForMovie(
							"http://is.fon.rs/movies/498673a6-c145-4a98-b800-296ab11a9672",
							"", "Julian");
			System.out.println(r.getAuthor() + " autor");
			System.out.println(r.getName() + " name");*/
			  
			MovieRestService mjp = new MovieRestService();
			String json =  mjp.getAllMoviesgetAllMovies("", "", "", "", "", "", "", "", "", "", "50");
			System.out.println(json);
			
			String jsn = mjp.getAllReviews("8b1224c3-8eb9-4de3-8ce5-c6718009daf0", "", "");
			System.out.println("JSON "+jsn);
			DataModelManager.getInstance().closeDataModel();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
