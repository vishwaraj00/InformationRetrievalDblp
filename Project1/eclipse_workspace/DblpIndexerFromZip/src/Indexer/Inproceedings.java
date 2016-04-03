package Indexer;

import javax.json.Json;
import javax.json.JsonObject;


public class Inproceedings extends DocumentBase{
	public String booktitle;

	public Inproceedings(){
		super();
		booktitle="";
		type="inproceedings";
	}

	public static JsonObject GetJson(Inproceedings inproceedings){
		JsonObject model = Json.createObjectBuilder()
				.add("type", inproceedings.type)
				.add("booktitle", inproceedings.booktitle)
				.add("key", inproceedings.key)
				.add("mdate", inproceedings.mdate)
				.add("author", inproceedings.author)
				.add("title", inproceedings.title)
				.add("ee", inproceedings.ee)
				.add("url", inproceedings.url)
				.add("pages", inproceedings.pages)
				.add("year", inproceedings.year)
				.add("crossref", inproceedings.crossref)
				.build();
		return model;
	}
}

