package com.blockchain.server.eos.eos4j.api.vo.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author espritblock http://eblock.io
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Key {

	private String key;

	private Long weight;

	public Key() {

	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Long getWeight() {
		return weight;
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "Key{" +
				"key='" + key + '\'' +
				", weight=" + weight +
				'}';
	}
}
