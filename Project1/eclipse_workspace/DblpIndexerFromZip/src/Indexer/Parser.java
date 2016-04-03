package Indexer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class Parser {
	public static final String SP_ENTITY_EXPANSION_LIMIT = "jdk.xml.entityExpansionLimit"; 
	public static final String SP_TOTAL_ENTITY_SIZE_LIMIT = "jdk.xml.totalEntitySizeLimit";     
	public static final String SP_PARAMETER_ENTITY_SIZE_LIMIT = "jdk.xml.maxParameterEntitySizeLimit";
	private EsClient esClient;

	private enum XmlElementType{none, article, inproceedings};
	public int numArticlesSeen = 0, numInproceedingsSeen = 0;
	//public List<Article> ListArticles;
	//public List<Inproceedings> ListInproceedings;

	private class ConfigHandler extends DefaultHandler {
		private Locator locator;

		private String Value;
		private XmlElementType currentElement = XmlElementType.none;
		private Article local_article;
		private Inproceedings local_inproceedings;

		public void setDocumentLocator(Locator locator) {
			this.locator = locator;
		}

		public void startElement(String namespaceURI, String localName, String rawName, Attributes atts) throws SAXException {
			if (rawName.equals("article")||rawName.equals("inproceedings")){
				local_article = new Article();
				local_inproceedings = new Inproceedings();
				if ((atts.getLength()>0) && ((atts.getValue("key"))!=null)&& ((atts.getValue("mdate"))!=null)) {
					local_article.key += ":" + atts.getValue("key");
					local_inproceedings.key += ":" + atts.getValue("key");
					local_article.mdate += ":" + atts.getValue("mdate");
					local_inproceedings.mdate += ":" + atts.getValue("mdate");
				}
				if(rawName.equals("inproceedings")){
					currentElement = XmlElementType.inproceedings;
				}
				else{
					currentElement = XmlElementType.article;
				}
			}
			Value="";
		}

		public void endElement(String namespaceURI, String localName, String rawName) throws SAXException {
			if((currentElement == XmlElementType.article)||(currentElement == XmlElementType.inproceedings)){
				if (rawName.equals("author")) {
					local_article.author = AddMessageToString(local_article.author, Value);
					local_inproceedings.author = AddMessageToString(local_inproceedings.author, Value);
				}
				else if (rawName.equals("title")) {
					local_article.title =  AddMessageToString(local_article.title, Value);
					local_inproceedings.title = AddMessageToString(local_inproceedings.title, Value);
				}
				else if (rawName.equals("pages")) {
					local_article.pages =  AddMessageToString(local_article.pages, Value);
					local_inproceedings.pages = AddMessageToString(local_inproceedings.pages, Value);
				}
				else if (rawName.equals("year")) {
					local_article.year = AddMessageToString(local_article.year, Value);
					local_inproceedings.year = AddMessageToString(local_inproceedings.year, Value);
				}
				else if (rawName.equals("ee")) {
					local_article.ee = AddMessageToString(local_article.ee, Value);
					local_inproceedings.ee = AddMessageToString(local_inproceedings.ee, Value);
				}
				else if (rawName.equals("crossref")) {
					local_article.crossref = AddMessageToString(local_article.crossref, Value);
					local_inproceedings.crossref = AddMessageToString(local_inproceedings.crossref, Value);
				}
				else if (rawName.equals("url")) {
					local_article.url = AddMessageToString(local_article.url, Value);
					local_inproceedings.url = AddMessageToString(local_inproceedings.url, Value);
				}
				else if (rawName.equals("volume")) {
					local_article.volume = AddMessageToString(local_article.volume, Value);
					//local_inproceedings.volume = AddMessageToString(local_inproceedings.volume, Value);
				}
				else if (rawName.equals("journal")) {
					local_article.journal = AddMessageToString(local_article.journal, Value);
					//local_inproceedings.journal = AddMessageToString(local_inproceedings.journal, Value);
				}
				else if (rawName.equals("number")) {
					local_article.number = AddMessageToString(local_article.number, Value);
					//local_inproceedings.number = AddMessageToString(local_inproceedings.number, Value);
				}
				else if (rawName.equals("booktitle")) {
					//local_article.booktitle = AddMessageToString(local_article.booktitle, Value);
					local_inproceedings.booktitle = AddMessageToString(local_inproceedings.booktitle, Value);
				}
				else if (rawName.equals("article")){
					numArticlesSeen++;
					esClient.BulkPOST(Article.GetJson(local_article));
					//ListArticles.add(local_article);
					currentElement = XmlElementType.none;
				}
				else if(rawName.equals("inproceedings")){
					numInproceedingsSeen++;
					esClient.BulkPOST(Inproceedings.GetJson(local_inproceedings));
					//ListInproceedings.add(local_inproceedings);
					currentElement = XmlElementType.none;
				}
			}
		}

		public void characters(char[] ch, int start, int length) throws SAXException {
			if (currentElement != XmlElementType.none){
				Value += new String(ch, start, length);
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

	public Parser(EsClient EsClient){
		esClient = EsClient;
		//ListArticles = new ArrayList<Article>();
		//ListInproceedings = new ArrayList<Inproceedings>();
	}
	

	
	private static String AddMessageToString(String main_string, String message){
		if((null == main_string)||(main_string.isEmpty())){
			return message;
		}
		else{
			return main_string + " ; " + message;
		}
	}
}
