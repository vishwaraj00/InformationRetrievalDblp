package Index;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import Document.*;

public class Parser {
	public static final String SP_ENTITY_EXPANSION_LIMIT = "jdk.xml.entityExpansionLimit"; 
	public static final String SP_TOTAL_ENTITY_SIZE_LIMIT = "jdk.xml.totalEntitySizeLimit";     
	public static final String SP_PARAMETER_ENTITY_SIZE_LIMIT = "jdk.xml.maxParameterEntitySizeLimit";
	private Client esClient;
	private TextClassifier tc;
	BulkRequestBuilder bulkRequest;
	private static int MaxBulkSize = 10000;
	private int bulkSize;

	private enum XmlElementType{none, article, inproceedings, proceedings, incollection, 
		web, book, phdthesis, masterthesis};
	public int numArticlesSeen = 0, numInproceedingsSeen = 0, count = 0, conferenceCount = 0;
	//public List<Article> ListArticles;
	//public List<Inproceedings> ListInproceedings;

	private class ConfigHandler extends DefaultHandler {
		private String Value;
		private XmlElementType currentElement = XmlElementType.none;
		private Article local_article;
		private Inproceedings local_inproceedings;
		private HashMap<String,Year> yearList = new HashMap<>();
		private HashMap<String,Conference> conferenceList = new HashMap<>();

		public void setDocumentLocator(Locator locator) {
		}

		public void startElement(String namespaceURI, String localName, String rawName, Attributes atts) throws SAXException {
			
			if (rawName.equals("article")){
				local_article = new Article();
				if ((atts.getLength()>0)) {
					if ((atts.getValue("key"))!=null){local_article.key += atts.getValue("key");}
					if (((atts.getValue("mdate"))!=null)){local_article.mdate += atts.getValue("mdate");}
					if (((atts.getValue("publtype"))!=null)){local_article.publtype += atts.getValue("publtype");}
				}
				currentElement = XmlElementType.article;
				
			}
			else if(rawName.equals("inproceedings")){
				local_inproceedings = new Inproceedings();
				if ((atts.getLength()>0)) {
					if ((atts.getValue("key"))!=null){local_inproceedings.key += atts.getValue("key");}
					if (((atts.getValue("mdate"))!=null)){local_inproceedings.mdate += atts.getValue("mdate");}
					if (((atts.getValue("publtype"))!=null)){local_inproceedings.publtype += atts.getValue("publtype");}
				}
				currentElement = XmlElementType.inproceedings;
			}
			Value="";
		}

		public void endElement(String namespaceURI, String localName, String rawName) throws SAXException {
			if(currentElement == XmlElementType.article){

				if (rawName.equals("author")) {
					local_article.author = Value+", ";
				}
				else if (rawName.equals("title")) {
					local_article.title = Value;
				}
				else if (rawName.equals("pages")) {
					local_article.pages = Value;
				}
				else if (rawName.equals("year")) {
					local_article.year = Value;
				}
				else if (rawName.equals("ee")) {
					local_article.ee = Value;
				}
				else if (rawName.equals("crossref")) {
					local_article.crossref = Value;
				}
				else if (rawName.equals("url")) {
					local_article.url = Value;
				}
				else if (rawName.equals("volume")) {
					local_article.volume = Value;
				}
				else if (rawName.equals("journal")) {
					local_article.journal = Value;
				}
				else if (rawName.equals("number")) {
					local_article.number = Value;
				}
				else if (rawName.equals("article")){
					if(!local_article.year.equals("")&&Integer.parseInt(local_article.year)>=2000){							
						numArticlesSeen++;
						System.out.println(local_article.title);
						ArrayList<String> topics = new ArrayList<String>();
						try {
							topics = tc.classify(local_article.title);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (!yearList.containsKey(local_article.year)){
							Year y = new Year(local_article.year);
							y.update(topics);
							yearList.put(local_article.year, y);
						}
						else{
							Year y = yearList.get(local_article.year);
							y.update(topics);
							yearList.put(local_article.year, y);
						}
						String conferenceKey = "";
						if (!local_article.year.equals("")&&!local_article.journal.equals("")){
							
							conferenceKey = local_article.journal;
							if (!conferenceList.containsKey(conferenceKey)){
								Conference c = new Conference(local_article.journal,local_article.year);
								c.update(topics);
								conferenceList.put(conferenceKey, c);
							}
							else{
								Conference c = conferenceList.get(conferenceKey);
								c.update(topics);
								conferenceList.put(conferenceKey, c);
							}
						}
						if (bulkSize < MaxBulkSize){
							bulkRequest.add(esClient.prepareIndex("dblp", "article")
									.setSource(Article.getJsonString(local_article)));
							bulkSize++;
						}
						else{
							BulkResponse bulkResponse = bulkRequest.execute().actionGet();
							if (bulkResponse.hasFailures()) {
								// process failures by iterating through each bulk response item
							}
							bulkRequest = esClient.prepareBulk();
							System.out.println("Uploaded");
							bulkSize = 0;
						}
						currentElement = XmlElementType.none;
						count++;

						System.out.println(count);
					}
				}
			}
			else if(currentElement == XmlElementType.inproceedings){
				if (rawName.equals("author")) {
					local_inproceedings.author = Value+", ";
				}
				else if (rawName.equals("title")) {
					local_inproceedings.title = Value;
				}
				else if (rawName.equals("pages")) {
					local_inproceedings.pages = Value;
				}
				else if (rawName.equals("year")) {
					local_inproceedings.year = Value;
				}
				else if (rawName.equals("ee")) {
					local_inproceedings.ee = Value;
				}
				else if (rawName.equals("crossref")) {
					local_inproceedings.crossref = Value;
				}
				else if (rawName.equals("url")) {
					local_inproceedings.url = Value;
				}
				else if (rawName.equals("booktitle")) {
					local_inproceedings.booktitle = Value;
				}
				else if(rawName.equals("inproceedings")){
					if(!local_inproceedings.year.equals("")&&Integer.parseInt(local_inproceedings.year)>=2000){
						numInproceedingsSeen++;
						System.out.println(local_inproceedings.title);
						ArrayList<String> topics = new ArrayList<String>();
						try {
							topics = tc.classify(local_inproceedings.title);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (!yearList.containsKey(local_inproceedings.year)){
							Year y = new Year(local_inproceedings.year);
							y.update(topics);
							yearList.put(local_inproceedings.year, y);
						}
						else{
							Year y = yearList.get(local_inproceedings.year);
							y.update(topics);
							yearList.put(local_inproceedings.year, y);
						}
						String conferenceKey = "";
						if (!local_inproceedings.year.equals("")&&!local_inproceedings.booktitle.equals("")){
							
							conferenceKey = local_inproceedings.booktitle;
							if (!conferenceList.containsKey(conferenceKey)){
								Conference c = new Conference(local_inproceedings.booktitle,local_inproceedings.year);
								c.update(topics);
								conferenceList.put(conferenceKey, c);
								conferenceCount ++;
							}
							else{
								Conference c = conferenceList.get(conferenceKey);
								c.update(topics);
								conferenceList.put(conferenceKey, c);
							}
						}
						if (bulkSize < MaxBulkSize){
							bulkRequest.add(esClient.prepareIndex("dblp", "inproceedings")
									.setSource(Inproceedings.getJsonString(local_inproceedings)));
							bulkSize++;
						}
						else{
							BulkResponse bulkResponse = bulkRequest.execute().actionGet();
							if (bulkResponse.hasFailures()) {
								// process failures by iterating through each bulk response item
							}
							bulkRequest = esClient.prepareBulk();
							System.out.println("Uploaded");
							bulkSize = 0;
						}
						currentElement = XmlElementType.none;
						count++;
						System.out.println(count);
					}
				}
			}
		}

		public void endDocument(){
			bulkSize = 0;
			System.out.println("Conference: "+conferenceCount);

			for(Entry<String, Year> entry : yearList.entrySet()) {
				Year y = entry.getValue();
				esClient.prepareIndex("dblp", "year")
				.setSource(Year.getJsonString(y)).execute().actionGet();

			}
			int count = 0;
			for(Entry<String, Conference> entry : conferenceList.entrySet()) {
				System.out.println("Count "+count++);
				Conference c = entry.getValue();
				esClient.prepareIndex("dblp", "conference")
				.setSource(Conference.getJsonString(c)).execute().actionGet();

			}
			System.out.println("Article: "+numArticlesSeen);
			System.out.println("InProceedings: "+numInproceedingsSeen);
		}

		public void characters(char[] ch, int start, int length) throws SAXException {
			if (currentElement != XmlElementType.none){
				Value += (new String(ch, start, length)).replace("\\", "\\\\").replace("\"", "\\\"");
			}
		}

		private void Message(String mode, SAXParseException exception) {
			System.out.println(mode + " Line: " + exception.getLineNumber()
					+ " URI: " + exception.getSystemId() + "\n" + " Message: "
					+ exception.getMessage());
		}

		public void warning(SAXParseException exception) throws SAXException {

			Message("**Parsing Warning**\n", exception);
			throw new SAXException("Warning encountered");
		}

		public void error(SAXParseException exception) throws SAXException {

			Message("**Parsing Error**\n", exception);
			throw new SAXException("Error encountered");
		}

		public void fatalError(SAXParseException exception) throws SAXException {

			Message("**Parsing Fatal Error**\n", exception);
			throw new SAXException("Fatal Error encountered");
		}
	}

	public boolean parse(String uri) {
		System.setProperty(SP_ENTITY_EXPANSION_LIMIT, "20000000");
		System.setProperty(SP_TOTAL_ENTITY_SIZE_LIMIT, "20000000");
		System.setProperty(SP_PARAMETER_ENTITY_SIZE_LIMIT, "20000000");

		try {
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			SAXParser parser = parserFactory.newSAXParser();
			ConfigHandler handler = new ConfigHandler();
			parser.getXMLReader().setFeature(
					"http://xml.org/sax/features/validation", true);
			parser.parse(new File(uri), handler);
		} catch (IOException e) {
			System.out.println("Error reading URI: " + e.getMessage());
			return false;
		} catch (SAXException e) {
			System.out.println("Error in parsing: " + e.getMessage());
			return false;
		} catch (ParserConfigurationException e) {
			System.out.println("Error in XML parser configuration: " + e.getMessage());
			return false;
		}

		System.out.println("Number of Articles parsed : "+ numArticlesSeen);
		System.out.println("Number of Inproceedings parsed : "+ numInproceedingsSeen);
		return true;
	}

	public Parser(Client EsClient, TextClassifier classifier){
		esClient = EsClient;
		bulkRequest = esClient.prepareBulk();
        bulkSize = 0;
		tc = classifier;
		//ListArticles = new ArrayList<Article>();
		//ListInproceedings = new ArrayList<Inproceedings>();
	}
}
