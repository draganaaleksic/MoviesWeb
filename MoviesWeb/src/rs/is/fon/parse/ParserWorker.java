package rs.is.fon.parse;

public class ParserWorker extends Thread {

	private String url;

	public ParserWorker(String url) {
		this.url = url;
	}

	
	public void run() {
		try {
			Parser.parseMovie(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
