
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
    "_index",
    "_type",
    "_id",
    "_score",
    "_source"
})
public class Hit {

    @JsonProperty("_index")
    private String index;
    @JsonProperty("_type")
    private String type;
    @JsonProperty("_id")
    private String id;
    @JsonProperty("_score")
    private Integer score;
    @JsonProperty("_source")
    private Source source;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The Index
     */
    @JsonProperty("_index")
    public String getIndex() {
        return index;
    }

    /**
     * 
     * @param Index
     *     The _index
     */
    @JsonProperty("_index")
    public void setIndex(String index) {
        this.index = index;
    }

    /**
     * 
     * @return
     *     The Type
     */
    @JsonProperty("_type")
    public String getType() {
        return type;
    }

    /**
     * 
     * @param Type
     *     The _type
     */
    @JsonProperty("_type")
    public void setType(String Type) {
        this.type = Type;
    }

    /**
     * 
     * @return
     *     The Id
     */
    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    /**
     * 
     * @param Id
     *     The _id
     */
    @JsonProperty("_id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The Score
     */
    @JsonProperty("_score")
    public Integer getScore() {
        return score;
    }

    /**
     * 
     * @param Score
     *     The _score
     */
    @JsonProperty("_score")
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * 
     * @return
     *     The Source
     */
    @JsonProperty("_source")
    public Source getSource() {
        return source;
    }

    /**
     * 
     * @param Source
     *     The _source
     */
    @JsonProperty("_source")
    public void setSource(Source source) {
        this.source = source;
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
