package com.dblpsearch.model;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "term" })
public class Should {

	@JsonIgnore
	private Map<String, Object> term = new HashMap<String, Object>();

	@JsonAnyGetter
	public Map<String, Object> getTerm() {
		return term;
	}

	@JsonAnySetter
	public void setTerm(Map<String, Object> term) {
		this.term = term;
	}

}