
package com.dblpsearch.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "type",
    "journal",
    "volume",
    "number",
    "key",
    "mdate",
    "author",
    "title",
    "ee",
    "url",
    "pages",
    "year",
    "crossref"
})
public class Source {

    @JsonProperty("type")
    private String type;
    @JsonProperty("journal")
    private String journal;
    @JsonProperty("volume")
    private String volume;
    @JsonProperty("number")
    private String number;
    @JsonProperty("key")
    private String key;
    @JsonProperty("mdate")
    private String mdate;
    @JsonProperty("author")
    private String author;
    @JsonProperty("title")
    private String title;
    @JsonProperty("ee")
    private String ee;
    @JsonProperty("url")
    private String url;
    @JsonProperty("pages")
    private String pages;
    @JsonProperty("year")
    private String year;
    @JsonProperty("crossref")
    private String crossref;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The type
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The journal
     */
    @JsonProperty("journal")
    public String getJournal() {
        return journal;
    }

    /**
     * 
     * @param journal
     *     The journal
     */
    @JsonProperty("journal")
    public void setJournal(String journal) {
        this.journal = journal;
    }

    /**
     * 
     * @return
     *     The volume
     */
    @JsonProperty("volume")
    public String getVolume() {
        return volume;
    }

    /**
     * 
     * @param volume
     *     The volume
     */
    @JsonProperty("volume")
    public void setVolume(String volume) {
        this.volume = volume;
    }

    /**
     * 
     * @return
     *     The number
     */
    @JsonProperty("number")
    public String getNumber() {
        return number;
    }

    /**
     * 
     * @param number
     *     The number
     */
    @JsonProperty("number")
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * 
     * @return
     *     The key
     */
    @JsonProperty("key")
    public String getKey() {
        return key;
    }

    /**
     * 
     * @param key
     *     The key
     */
    @JsonProperty("key")
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 
     * @return
     *     The mdate
     */
    @JsonProperty("mdate")
    public String getMdate() {
        return mdate;
    }

    /**
     * 
     * @param mdate
     *     The mdate
     */
    @JsonProperty("mdate")
    public void setMdate(String mdate) {
        this.mdate = mdate;
    }

    /**
     * 
     * @return
     *     The author
     */
    @JsonProperty("author")
    public String getAuthor() {
        return author;
    }

    /**
     * 
     * @param author
     *     The author
     */
    @JsonProperty("author")
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 
     * @return
     *     The title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The ee
     */
    @JsonProperty("ee")
    public String getEe() {
        return ee;
    }

    /**
     * 
     * @param ee
     *     The ee
     */
    @JsonProperty("ee")
    public void setEe(String ee) {
        this.ee = ee;
    }

    /**
     * 
     * @return
     *     The url
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 
     * @return
     *     The pages
     */
    @JsonProperty("pages")
    public String getPages() {
        return pages;
    }

    /**
     * 
     * @param pages
     *     The pages
     */
    @JsonProperty("pages")
    public void setPages(String pages) {
        this.pages = pages;
    }

    /**
     * 
     * @return
     *     The year
     */
    @JsonProperty("year")
    public String getYear() {
        return year;
    }

    /**
     * 
     * @param year
     *     The year
     */
    @JsonProperty("year")
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * 
     * @return
     *     The crossref
     */
    @JsonProperty("crossref")
    public String getCrossref() {
        return crossref;
    }

    /**
     * 
     * @param crossref
     *     The crossref
     */
    @JsonProperty("crossref")
    public void setCrossref(String crossref) {
        this.crossref = crossref;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
