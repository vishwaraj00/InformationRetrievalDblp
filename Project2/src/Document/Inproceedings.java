package Document;

import javax.json.Json;
import javax.json.JsonObject;


public class Inproceedings extends DocumentBase{
	public String booktitle,pages,crossref;

	public Inproceedings(){
		super();
		booktitle="";
		pages="";
		crossref="";
		type="inproceedings";
	}

	public static JsonObject GetJson(Inproceedings inproceedings){
		JsonObject model = Json.createObjectBuilder()
				.add("type", inproceedings.type)
				.add("key", inproceedings.key)
				.add("mdate", inproceedings.mdate)
				.add("publtype",inproceedings.publtype)
				.add("author", inproceedings.author)
				.add("editor", inproceedings.editor)
				.add("title", inproceedings.title)
				.add("ee", inproceedings.ee)
				.add("url", inproceedings.url)
				.add("year", inproceedings.year)
				.add("booktitle", inproceedings.booktitle)
				.add("pages", inproceedings.pages)
				.add("crossref", inproceedings.crossref)
				.build();
		return model;
	}
	public static String getJsonString(Inproceedings inproceedings){
		String json = "{" +
		        "\"type\":\""+inproceedings.type+"\"," +
		        "\"key\":\""+inproceedings.key+"\"," +
		        "\"mdate\":\""+inproceedings.mdate+"\"," +
		        "\"publtype\":\""+inproceedings.publtype+"\"," +
		        "\"author\":\""+inproceedings.author+"\"," +
		        "\"editor\":\""+inproceedings.editor+"\"," +
		        "\"title\":\""+inproceedings.title+"\"," +
		        "\"ee\":\""+inproceedings.ee+"\"," +
		        "\"url\":\""+inproceedings.url+"\"," +
		        "\"year\":\""+inproceedings.year+"\"," +
		        "\"pages\":\""+inproceedings.pages+"\"," +
		        "\"booktitle\":\""+inproceedings.booktitle+"\","+
		        "\"crossref\":\""+inproceedings.crossref+"\"" +
		    "}";
		return json;
	}
}

