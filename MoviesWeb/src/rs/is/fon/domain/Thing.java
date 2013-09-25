package rs.is.fon.domain;

import java.net.URI;

import rs.is.fon.util.Constants;
import thewebsemantic.Id;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

@Namespace(Constants.SCHEMA)
@RdfType("Thing")
public class Thing {
	
	@Id
	private URI uri;
	
	@RdfProperty(Constants.SCHEMA+ "url")
	private URI url;
	
	@RdfProperty(Constants.SCHEMA + "name")
	private String name;
	
	@RdfProperty(Constants.SCHEMA + "description")
	private String description;
	
	@RdfProperty(Constants.SCHEMA + "image")
	private URI image;

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public URI getUrl() {
		return url;
	}

	public void setUrl(URI url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public URI getImage() {
		return image;
	}

	public void setImage(URI image) {
		this.image = image;
	}
	
	
	
}
