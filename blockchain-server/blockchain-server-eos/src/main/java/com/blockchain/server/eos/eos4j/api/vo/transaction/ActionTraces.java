package com.blockchain.server.eos.eos4j.api.vo.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author espritblock http://eblock.io
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionTraces {

	@JsonProperty("act")
	private Act act;

	public Act getAct() {
		return act;
	}

	public void setAct(Act act) {
		this.act = act;
	}

	@Override
	public String toString() {
		return "ActionTraces{" +
				"act=" + act +
				'}';
	}
}
