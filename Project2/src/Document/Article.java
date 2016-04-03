package Document;

import javax.json.Json;
import javax.json.JsonObject;

public class Article extends DocumentBase{
	public String journal, volume, number,crossref,pages;

	public Article(){
		super();
		journal="";
		volume="";
		number="";
		crossref="";
		pages="";
		type="article";
	}

	public static JsonObject GetJson(Article article){
		JsonObject model = Json.createObjectBuilder()
				.add("type", article.type)
				.add("key", article.key)
				.add("mdate", article.mdate)
				.add("publtype",article.publtype)
				.add("author", article.author)
				.add("editor", article.editor)
				.add("title", article.title)
				.add("ee", article.ee)
				.add("url", article.url)
				.add("year", article.year)
				.add("pages", article.pages)
				.add("journal", article.journal)
				.add("volume", article.volume)
				.add("number", article.number)
				.add("crossref", article.crossref)
				.build();
		System.out.print(model);
		return model;
	}
	public static String getJsonString(Article article){
		String json = "{" +
		        "\"type\":\""+article.type+"\"," +
		        "\"key\":\""+article.key+"\"," +
		        "\"mdate\":\""+article.mdate+"\"," +
		        "\"publtype\":\""+article.publtype+"\"," +
		        "\"author\":\""+article.author+"\"," +
		        "\"editor\":\""+article.editor+"\"," +
		        "\"title\":\""+article.title+"\"," +
		        "\"ee\":\""+article.ee+"\"," +
		        "\"url\":\""+article.url+"\"," +
		        "\"year\":\""+article.year+"\"," +
		        "\"pages\":\""+article.pages+"\"," +
		        "\"journal\":\""+article.journal+"\"," +
		        "\"volume\":\""+article.volume+"\"," +
		        "\"number\":\""+article.number+"\"," +
		        "\"crossref\":\""+article.crossref+"\"" +
		    "}";
		return json;
	}
}
