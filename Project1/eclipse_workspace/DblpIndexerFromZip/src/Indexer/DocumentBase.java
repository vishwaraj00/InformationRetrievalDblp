package Indexer;

public abstract class DocumentBase {
	public String key, mdate, author, title, ee, url, pages, year, crossref, type;
	
	public DocumentBase(){
		key="";
		mdate="";
		author="";
		title="";
		ee="";
		url="";
		pages="";
		year="";
		crossref="";
		type="basetype";
	}
}
