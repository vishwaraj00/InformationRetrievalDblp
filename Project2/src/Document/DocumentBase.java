package Document;

public abstract class DocumentBase {
	//public String key, mdate, author, title, ee, url, pages, year, crossref, type;
	public String key, mdate, publtype, author, editor, title, ee, url, year, type;
	public DocumentBase(){
		key="";
		mdate="";
		publtype="";
		author="";
		editor="";
		title="";
		ee="";
		url="";
		year="";
		type="basetype";
	}
}
