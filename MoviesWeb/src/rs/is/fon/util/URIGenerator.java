package rs.is.fon.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import rs.is.fon.domain.Thing;

public class URIGenerator {
	
	public synchronized static <T extends Thing> URI generate(T resource) throws URISyntaxException{
		String uri = Constants.NS +  UUID.randomUUID();
		return new URI(uri);
	}

}
