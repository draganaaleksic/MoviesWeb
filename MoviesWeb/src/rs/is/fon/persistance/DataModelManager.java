package rs.is.fon.persistance;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import rs.is.fon.persistance.dataprovider.DataProvider;
import rs.is.fon.persistance.dataprovider.TDBDataProvider;
import rs.is.fon.util.Constants;
import thewebsemantic.Bean2RDF;
import thewebsemantic.RDF2Bean;



import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;

public class DataModelManager {
	
	private DataProvider dataProvider;
	private Bean2RDF writer;
	private RDF2Bean reader;
	
	private static DataModelManager INSTANCE;
	
	public DataModelManager (){
		dataProvider = new TDBDataProvider();
		getModel().setNsPrefix("schema", Constants.SCHEMA);
		getModel().setNsPrefix("movies", Constants.NS);
		getModel().setNsPrefix("xsd", Constants.XSD);
		writer = new Bean2RDF(getModel());
		reader = new RDF2Bean(getModel());
		
	}
	
	public static synchronized DataModelManager getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new DataModelManager();
		}
		return INSTANCE;
	}
	
	public synchronized void write(String filename, String syntax) throws FileNotFoundException{
		getModel().write(new FileOutputStream(filename), syntax);
	}
	
	public synchronized void save(Object o) {
		writer.save(o);
	}
	
	public synchronized Object load(String uri) {
		return reader.load(uri);
	}
	
	public synchronized Model getModel() {
		return dataProvider.getDataModel();
	}
	
	public synchronized RDF2Bean getReader() {
		return reader;
	}
	
	public synchronized Bean2RDF getWriter() {
		return writer;
	}

	public synchronized void closeDataModel(){
		dataProvider.close();
	}
	
	public Dataset getDataset(){
		return dataProvider.getDataset();
	}

}
