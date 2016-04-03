package Indexer;

import javax.json.Json;
import javax.json.JsonObject;

public class Article extends DocumentBase{
	public String journal, volume, number;

	public Article(){
		super();
		journal="";
		volume="";
		number="";
		type="article";
	}

	public static JsonObject GetJson(Article article){
		JsonObject model = Json.createObjectBuilder()
				.add("type", article.type)
				.add("journal", article.journal)
				.add("volume", article.volume)
				.add("number", article.number)
				.add("key", article.key)
				.add("mdate", article.mdate)
				.add("author", article.author)
				.add("title", article.title)
				.add("ee", article.ee)
				.add("url", article.url)
				.add("pages", article.pages)
				.add("year", article.year)
				.add("crossref", article.crossref)
				.build();
		return model;
	}
}
