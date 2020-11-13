package com.blockchain.server.eos.eos4j.api.vo.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 
 * @author espritblock http://eblock.io
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Processed {

	@JsonProperty("id")
	private String id;

	@JsonProperty("receipt")
	private Receipt receipt;

	@JsonProperty("elapsed")
	private Long elapsed;

	@JsonProperty("net_usage")
	private Long netUsage;

	@JsonProperty("scheduled")
	private Boolean scheduled;


	@JsonProperty("block_time")
	private String blockTime;

	@JsonProperty("block_num")
	private String blockNum;

	 @JsonProperty("action_traces")
	 private List<ActionTraces> actionTraces;

	 public List<ActionTraces> getActionTraces() {
	 	return actionTraces;
	 }

	 public void setActionTraces(List<ActionTraces> actionTraces) {
	 	this.actionTraces = actionTraces;
	 }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Receipt getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}

	public Long getElapsed() {
		return elapsed;
	}

	public void setElapsed(Long elapsed) {
		this.elapsed = elapsed;
	}

	public Long getNetUsage() {
		return netUsage;
	}

	public void setNetUsage(Long netUsage) {
		this.netUsage = netUsage;
	}

	public Boolean getScheduled() {
		return scheduled;
	}

	public void setScheduled(Boolean scheduled) {
		this.scheduled = scheduled;
	}

	public String getBlockTime() {
		return blockTime;
	}

	public void setBlockTime(String blockTime) {
		this.blockTime = blockTime;
	}

	public String getBlockNum() {
		return blockNum;
	}

	public void setBlockNum(String blockNum) {
		this.blockNum = blockNum;
	}

	@Override
	public String toString() {
		return "Processed{" +
				"id='" + id + '\'' +
				", receipt=" + receipt +
				", elapsed=" + elapsed +
				", netUsage=" + netUsage +
				", scheduled=" + scheduled +
				", blockTime='" + blockTime + '\'' +
				", blockNum='" + blockNum + '\'' +
				", actionTraces=" + actionTraces +
				'}';
	}
}
