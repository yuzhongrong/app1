package com.blockchain.server.eos.eos4j.api.vo.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author espritblock http://eblock.io
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequiredAuth {

	private List<Map> accounts;

	private List<Key> keys;

	private Long threshold;

	public List<Map> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Map> accounts) {
		this.accounts = accounts;
	}

	public List<Key> getKeys() {
		return keys;
	}

	public void setKeys(List<Key> keys) {
		this.keys = keys;
	}

	public Long getThreshold() {
		return threshold;
	}

	public void setThreshold(Long threshold) {
		this.threshold = threshold;
	}

	@Override
	public String toString() {
		return "RequiredAuth{" +
				"accounts=" + accounts +
				", keys=" + keys +
				", threshold=" + threshold +
				'}';
	}
}
