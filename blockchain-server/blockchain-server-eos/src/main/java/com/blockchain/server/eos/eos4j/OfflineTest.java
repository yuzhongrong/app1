package com.blockchain.server.eos.eos4j;


import com.blockchain.server.eos.eos4j.api.exception.ApiException;
import com.blockchain.server.eos.eos4j.api.vo.SignParam;
import com.blockchain.server.eos.eos4j.api.vo.transaction.Transaction;

/**
 * 
 * @author espritblock http://eblock.io
 *
 */
public class OfflineTest {

	/*public static void main(String[] args) {
		 testOfflineCreate();
//		testOfflineTransfer();
	}*/

	public static void testOfflineCreate() {
//		Rpc rpc = new Rpc("http://47.106.221.171:8888");
		Rpc rpc = null; // new Rpc("https://api-kylin.eosasia.one");
		// 获取离线签名参数
		SignParam params = rpc.getOfflineSignParams(60l);
		// 离线签名
		OfflineSign sign = new OfflineSign();
		// 交易信息
		String content = "";
		try {
			/*content = sign.createAccount(params, "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3", "eeeeeeeeeeee",
					"555555555551", "EOS6MRyAjQq8ud7hVNYcfnVPJqcVpscN5So8BhtHuGYqET5GDW5CV",
					"EOS6MRyAjQq8ud7hVNYcfnVPJqcVpscN5So8BhtHuGYqET5GDW5CV", 8000l);*/
			content = sign.createAccount(params, "5KBR1zJT1VYxcHDAB1MgDqmXufvFYJDmWtwZGVMUDGottR5k5gJ", "aaasssxxxccc",
					"5555dcvfg551", "EOS8FDspV2gWzQgMJNWj8ZbMG9fTcLVJbNPTNFEtNxCAvJH4kfKKd",
					"EOS8FDspV2gWzQgMJNWj8ZbMG9fTcLVJbNPTNFEtNxCAvJH4kfKKd", 8000l);
			System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 广播交易
		try {
			Transaction tx = rpc.pushTransaction(content);
			System.out.println(tx.toString());
			System.out.println(tx.getTransactionId());
		} catch (ApiException ex) {
			System.out.println(ex.getError().getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testOfflineTransfer() {
//		Rpc rpc = new Rpc("http://47.106.221.171:8888");
		Rpc rpc = null; // new Rpc("https://api-kylin.eosasia.one");
		// 获取离线签名参数
		SignParam params = rpc.getOfflineSignParams(60l);
		// 离线签名
		OfflineSign sign = new OfflineSign();
		// 交易信息
		String content = "";
		try {
			// 转账需要active的私钥
			/*content = sign.transfer(params, "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3", "eosio.token",
					"eeeeeeeeeeee", "555555555551", "372.0993 EOS", "test");*/
			content = sign.transfer(params, "5KBR1zJT1VYxcHDAB1MgDqmXufvFYJDmWtwZGVMUDGottR5k5gJ", "eosio.token",
					"aaasssxxxccc", "scfpolkiolkm", "10.0000 EOS", "test123");
			System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 广播交易
		try {
			Transaction tx = rpc.pushTransaction(content);
			System.out.println(tx.getTransactionId());
			System.out.println(tx.toString());
		} catch (ApiException ex) {
			 System.out.println(ex.getError().getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
