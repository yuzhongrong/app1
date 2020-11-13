package com.blockchain.server.eos.eos4j.api.vo.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author espritblock http://eblock.io
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Act {

	@JsonProperty("account")
	private String account;

	@JsonProperty("name")
	private String name;

	@JsonProperty("data")
	private Data data;

	@JsonProperty("hex_data")
	private String hexData;

	@Override
	public String toString() {
		return "Actions{" +
				"account='" + account + '\'' +
				", name='" + name + '\'' +
				", data=" + data +
				", hexData='" + hexData + '\'' +
				'}';
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getHexData() {
		return hexData;
	}

	public void setHexData(String hexData) {
		this.hexData = hexData;
	}
}
