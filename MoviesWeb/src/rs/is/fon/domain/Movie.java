package rs.is.fon.domain;

import java.util.ArrayList;
import java.util.Collection;

import rs.is.fon.util.Constants;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

@Namespace(Constants.SCHEMA)
@RdfType("Movie")
public class Movie extends CreativeWork{

	@RdfProperty(Constants.SCHEMA + "duration")
	private int duration;
	
	@RdfProperty(Constants.SCHEMA + "director")
	private Person director;
	
	@RdfProperty(Constants.SCHEMA + "actor")
	private Collection<Person> actor;
	
	@RdfProperty(Constants.SCHEMA + "actors")
	private Collection<Person> actors;
	

	public Movie() {
		actors = new ArrayList<Person>();
		actor = new ArrayList<Person>();
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Person getDirector() {
		return director;
	}

	public void setDirector(Person director) {
		this.director = director;
	}

	public Collection<Person> getActor() {
		return actor;
	}

	public void setActor(Collection<Person> actor) {
		this.actor = actor;
	}

	public Collection<Person> getActors() {
		return actors;
	}

	public void setActors(Collection<Person> actors) {
		this.actors = actors;
	}
	
	
}
