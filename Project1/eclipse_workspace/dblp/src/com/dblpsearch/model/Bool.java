package com.dblpsearch.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
@JsonPropertyOrder({ "should", "minimum_should_match", "boost" })
public class Bool {

	@JsonProperty("should")
	private List<Should> should = new ArrayList<Should>();
	@JsonProperty("minimum_should_match")
	private Integer minimumShouldMatch;
	@JsonProperty("boost")
	private Integer boost;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The should
	 */
	@JsonProperty("should")
	public List<Should> getShould() {
		return should;
	}

	/**
	 * 
	 * @param should
	 *            The should
	 */
	@JsonProperty("should")
	public void setShould(List<Should> should) {
		this.should = should;
	}

	/**
	 * 
	 * @return The minimumShouldMatch
	 */
	@JsonProperty("minimum_should_match")
	public Integer getMinimumShouldMatch() {
		return minimumShouldMatch;
	}

	/**
	 * 
	 * @param minimumShouldMatch
	 *            The minimum_should_match
	 */
	@JsonProperty("minimum_should_match")
	public void setMinimumShouldMatch(Integer minimumShouldMatch) {
		this.minimumShouldMatch = minimumShouldMatch;
	}

	/**
	 * 
	 * @return The boost
	 */
	@JsonProperty("boost")
	public Integer getBoost() {
		return boost;
	}

	/**
	 * 
	 * @param boost
	 *            The boost
	 */
	@JsonProperty("boost")
	public void setBoost(Integer boost) {
		this.boost = boost;
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