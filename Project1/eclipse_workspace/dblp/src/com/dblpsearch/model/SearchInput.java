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
@JsonPropertyOrder({ "from", "size", "query" })
public class SearchInput {

	@JsonProperty("from")
	private int from;
	@JsonProperty("size")
	private int size;
	@JsonProperty("query")
	private Query query;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("from")
	public int getFrom() {
		return from;
	}

	@JsonProperty("from")
	public void setFrom(int from) {
		this.from = from;
	}

	@JsonProperty("size")
	public int getSize() {
		return size;
	}

	@JsonProperty("size")
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * 
	 * @return The query
	 */
	@JsonProperty("query")
	public Query getQuery() {
		return query;
	}

	/**
	 * 
	 * @param query
	 *            The query
	 */
	@JsonProperty("query")
	public void setQuery(Query query) {
		this.query = query;
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