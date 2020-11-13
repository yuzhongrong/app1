package com.blockchain.server.eos.eos4j;

import com.alibaba.fastjson.JSONObject;
import com.blockchain.server.eos.common.enums.EosWalletEnums;
import com.blockchain.server.eos.common.exception.EosWalletException;
import com.blockchain.server.eos.eos4j.api.service.RpcService;
import com.blockchain.server.eos.eos4j.api.utils.Generator;
import com.blockchain.server.eos.eos4j.api.vo.*;
import com.blockchain.server.eos.eos4j.api.vo.account.Account;
import com.blockchain.server.eos.eos4j.api.vo.transaction.Transaction;
import com.blockchain.server.eos.eos4j.api.vo.transaction.push.Tx;
import com.blockchain.server.eos.eos4j.api.vo.transaction.push.TxAction;
import com.blockchain.server.eos.eos4j.api.vo.transaction.push.TxRequest;
import com.blockchain.server.eos.eos4j.api.vo.transaction.push.TxSign;
import com.blockchain.server.eos.eos4j.ese.Action;
import com.blockchain.server.eos.eos4j.ese.DataParam;
import com.blockchain.server.eos.eos4j.ese.DataType;
import com.blockchain.server.eos.eos4j.ese.Ese;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import retrofit2.Call;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;


public class Rpc {

	private final RpcService rpcService;
	private static final RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());


	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	public Rpc(String baseUrl) {
		dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		rpcService = Generator.createService(RpcService.class, baseUrl);
	}

	/**
	 * 获得链信息
	 * 
	 * @return
	 */
	public ChainInfo getChainInfo() {
		return Generator.executeSync(rpcService.getChainInfo());
	}

	/**
	 * 获得区块信息
	 * 
	 * @param blockNumberOrId
	 *            区块ID或者高度
	 * @return
	 */
	public Block getBlock(String blockNumberOrId) {
		return Generator.executeSync(rpcService.getBlock(Collections.singletonMap("block_num_or_id", blockNumberOrId)));
	}

	/**
	 * 获得账户信息
	 * 
	 * @param account
	 *            账户名称
	 * @return
	 */
	public Account getAccount(String account) {
		return Generator.executeSync(rpcService.getAccount(Collections.singletonMap("account_name", account)));
	}


	/*public Transaction getTransaction(String id, String blockNum){
		LinkedHashMap<String, String> requestParameters = new LinkedHashMap<>(1);

		requestParameters.put("id", id);
		requestParameters.put("block_num_hint", blockNum);

		return Generator.executeSync(rpcService.getTransaction(requestParameters));
	}*/

	/**
	 * 获得table数据
	 * 
	 * @param req
	 * @return
	 */
	public TableRows getTableRows(TableRowsReq req) {
		return Generator.executeSync(rpcService.getTableRows(req));
	}

	/**
	 * 发送请求
	 * 
	 * @param compression
	 *            压缩
	 * @param pushTransaction
	 *            交易
	 * @param signatures
	 *            签名
	 * @return
	 * @throws Exception
	 */
	public Transaction pushTransaction(String compression, Tx pushTransaction, String[] signatures) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String mapJakcson = mapper.writeValueAsString(new TxRequest(compression, pushTransaction, signatures));
		System.out.println(mapJakcson);
		return Generator
				.executeSync(rpcService.pushTransaction(new TxRequest(compression, pushTransaction, signatures)));
	}

	/**
	 * 发送交易
	 * 
	 * @param tx
	 * @return
	 * @throws Exception
	 */
	public Transaction pushTransaction(String tx) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		TxRequest txObj = mapper.readValue(tx, TxRequest.class);
		// TODO: 2019/2/14
		Call<Transaction> transactionCall = rpcService.pushTransaction(txObj);
		System.out.println("transactionCall: " + transactionCall);
		return Generator.executeSync(transactionCall);
	}

	/**
	 * 获取离线签名参数
	 * 
	 * @param exp
	 *            过期时间秒
	 * @return
	 */
	public SignParam getOfflineSignParams(Long exp) {
		SignParam params = new SignParam();
		ChainInfo info = getChainInfo();
		Block block = getBlock(info.getLastIrreversibleBlockNum().toString());
		params.setChainId(info.getChainId());
		params.setHeadBlockTime(info.getHeadBlockTime());
		params.setLastIrreversibleBlockNum(info.getLastIrreversibleBlockNum());
		params.setRefBlockPrefix(block.getRefBlockPrefix());
		params.setExp(exp);
		return params;
	}

	/**
	 * 转账
	 * 
	 * @param pk
	 *            私钥
	 * @param contractAccount
	 *            合约账户
	 * @param from
	 *            从
	 * @param to
	 *            到
	 * @param quantity
	 *            币种金额
	 * @param memo
	 *            留言
	 * @return
	 * @throws Exception
	 */
	public Transaction transfer(String pk, String contractAccount, String from, String to, String quantity, String memo)
			throws Exception {
		// get chain info
		ChainInfo info = getChainInfo();
//		info.setChainId("cf057bbfb72640471fd910bcb67639c22df9f92470936cddc1ade0e2f2e7dc4f");
//		info.setLastIrreversibleBlockNum(826366l);
//		info.setHeadBlockTime(dateFormatter.parse("2018-08-22T09:19:01.000"));
		// get block info
		Block block = getBlock(info.getLastIrreversibleBlockNum().toString());
//		block.setRefBlockPrefix(2919590658l);
		// tx
		Tx tx = new Tx();
		tx.setExpiration(info.getHeadBlockTime().getTime() / 1000 + 60);
		tx.setRef_block_num(info.getLastIrreversibleBlockNum());
		tx.setRef_block_prefix(block.getRefBlockPrefix());
		tx.setNet_usage_words(0l);
		tx.setMax_cpu_usage_ms(0l);
		tx.setDelay_sec(0l);
		// actions
		List<TxAction> actions = new ArrayList<>();
		// data
		Map<String, Object> dataMap = new LinkedHashMap<>();
		dataMap.put("from", from);
		dataMap.put("to", to);
		dataMap.put("quantity", new DataParam(quantity, DataType.asset, Action.transfer).getValue());
		dataMap.put("memo", memo);
		// action
		TxAction action = new TxAction(from, contractAccount, "transfer", dataMap);
		actions.add(action);
		tx.setActions(actions);
		// sgin
		String sign = Ecc.signTransaction(pk, new TxSign(info.getChainId(), tx));
		// data parse
		String data = Ecc.parseTransferData(from, to, quantity, memo);
		// reset data
		action.setData(data);
		// reset expiration
		tx.setExpiration(dateFormatter.format(new Date(1000 * Long.parseLong(tx.getExpiration().toString()))));
		return pushTransaction("none", tx, new String[] { sign });
	}

	/**
	 * 创建账户
	 * 
	 * @param pk
	 *            私钥
	 * @param creator
	 *            创建者
	 * @param newAccount
	 *            新账户
	 * @param owner
	 *            公钥
	 * @param active
	 *            公钥
	 * @param buyRam
	 *            ram
	 * @return
	 * @throws Exception
	 */
	public Transaction createAccount(String pk, String creator, String newAccount, String owner, String active,
			Long buyRam) throws Exception {
		// get chain info
		ChainInfo info = getChainInfo();
		// get block info
		Block block = getBlock(info.getLastIrreversibleBlockNum().toString());
		// tx
		Tx tx = new Tx();
		tx.setExpiration(info.getHeadBlockTime().getTime() / 1000 + 60);
		tx.setRef_block_num(info.getLastIrreversibleBlockNum());
		tx.setRef_block_prefix(block.getRefBlockPrefix());
		tx.setNet_usage_words(0l);
		tx.setMax_cpu_usage_ms(0l);
		tx.setDelay_sec(0l);
		// actions
		List<TxAction> actions = new ArrayList<>();
		tx.setActions(actions);
		// create
		Map<String, Object> createMap = new LinkedHashMap<>();
		createMap.put("creator", creator);
		createMap.put("name", newAccount);
		createMap.put("owner", owner);
		createMap.put("active", active);
		TxAction createAction = new TxAction(creator, "eosio", "newaccount", createMap);
		actions.add(createAction);
		// buyrap
		Map<String, Object> buyMap = new LinkedHashMap<>();
		buyMap.put("payer", creator);
		buyMap.put("receiver", newAccount);
		buyMap.put("bytes", buyRam);
		TxAction buyAction = new TxAction(creator, "eosio", "buyrambytes", buyMap);
		actions.add(buyAction);
		// sgin
		String sign = Ecc.signTransaction(pk, new TxSign(info.getChainId(), tx));
		// data parse
		String accountData = Ese.parseAccountData(creator, newAccount, owner, active);
		createAction.setData(accountData);
		// data parse
		String ramData = Ese.parseBuyRamData(creator, newAccount, buyRam);
		buyAction.setData(ramData);
		// reset expiration
		tx.setExpiration(dateFormatter.format(new Date(1000 * Long.parseLong(tx.getExpiration().toString()))));
		return pushTransaction("none", tx, new String[] { sign });
	}

	/**
	 * 创建账户
	 * 
	 * @param pk
	 *            私钥
	 * @param creator
	 *            创建者
	 * @param newAccount
	 *            新账户
	 * @param owner
	 *            公钥
	 * @param active
	 *            公钥
	 * @param buyRam
	 *            购买空间数量
	 * @param stakeNetQuantity
	 *            网络抵押
	 * @param stakeCpuQuantity
	 *            cpu抵押
	 * @param transfer
	 *            抵押资产是否转送给对方，0自己所有，1对方所有
	 * @return
	 * @throws Exception
	 */
	public Transaction createAccount(String pk, String creator, String newAccount, String owner, String active,
			Long buyRam, String stakeNetQuantity, String stakeCpuQuantity, Long transfer) throws Exception {
		// get chain info
		ChainInfo info = getChainInfo();
		// info.setChainId("cf057bbfb72640471fd910bcb67639c22df9f92470936cddc1ade0e2f2e7dc4f");
		// info.setLastIrreversibleBlockNum(22117l);
		// get block info
		Block block = getBlock(info.getLastIrreversibleBlockNum().toString());
		// block.setRefBlockPrefix(3920078619l);
		// tx
		Tx tx = new Tx();
		tx.setExpiration(info.getHeadBlockTime().getTime() / 1000 + 60);
		// tx.setExpiration(1528436078);
		tx.setRef_block_num(info.getLastIrreversibleBlockNum());
		tx.setRef_block_prefix(block.getRefBlockPrefix());
		tx.setNet_usage_words(0l);
		tx.setMax_cpu_usage_ms(0l);
		tx.setDelay_sec(0l);
		// actions
		List<TxAction> actions = new ArrayList<>();
		tx.setActions(actions);
		// create
		Map<String, Object> createMap = new LinkedHashMap<>();
		createMap.put("creator", creator);
		createMap.put("name", newAccount);
		createMap.put("owner", owner);
		createMap.put("active", active);
		TxAction createAction = new TxAction(creator, "eosio", "newaccount", createMap);
		actions.add(createAction);
		// buyrap
		Map<String, Object> buyMap = new LinkedHashMap<>();
		buyMap.put("payer", creator);
		buyMap.put("receiver", newAccount);
		buyMap.put("bytes", buyRam);
		TxAction buyAction = new TxAction(creator, "eosio", "buyrambytes", buyMap);
		actions.add(buyAction);
		// buyrap
		Map<String, Object> delMap = new LinkedHashMap<>();
		delMap.put("from", creator);
		delMap.put("receiver", newAccount);
		delMap.put("stake_net_quantity", new DataParam(stakeNetQuantity, DataType.asset, Action.delegate).getValue());
		delMap.put("stake_cpu_quantity", new DataParam(stakeCpuQuantity, DataType.asset, Action.delegate).getValue());
		delMap.put("transfer", transfer);
		TxAction delAction = new TxAction(creator, "eosio", "delegatebw", delMap);
		actions.add(delAction);
		// // sgin
		String sign = Ecc.signTransaction(pk, new TxSign(info.getChainId(), tx));
		// data parse
		String accountData = Ese.parseAccountData(creator, newAccount, owner, active);
		createAction.setData(accountData);
		// data parse
		String ramData = Ese.parseBuyRamData(creator, newAccount, buyRam);
		buyAction.setData(ramData);
		// data parse
		String delData = Ese.parseDelegateData(creator, newAccount, stakeNetQuantity, stakeCpuQuantity,
				transfer.intValue());
		delAction.setData(delData);
		// reset expiration
		tx.setExpiration(dateFormatter.format(new Date(1000 * Long.parseLong(tx.getExpiration().toString()))));
		return pushTransaction("none", tx, new String[] { sign });
	}
	
	/**
	 * 
	 * @param pk
	 * @param voter
	 * @param proxy
	 * @param producers
	 * @return
	 * @throws Exception
	 */
	public Transaction voteproducer(String pk,String voter,String proxy,List<String> producers) throws Exception {
		Comparator<String> comparator = (h1, h2) -> h2.compareTo(h1);
		producers.sort(comparator.reversed());
		// get chain info
		ChainInfo info = getChainInfo();
		// get block info
		Block block = getBlock(info.getLastIrreversibleBlockNum().toString());
		// tx
		Tx tx = new Tx();
		tx.setExpiration(info.getHeadBlockTime().getTime() / 1000 + 60);
		tx.setRef_block_num(info.getLastIrreversibleBlockNum());
		tx.setRef_block_prefix(block.getRefBlockPrefix());
		tx.setNet_usage_words(0l);
		tx.setMax_cpu_usage_ms(0l);
		tx.setDelay_sec(0l);
		// actions
		List<TxAction> actions = new ArrayList<>();
		// data
		Map<String, Object> dataMap = new LinkedHashMap<>();
		dataMap.put("voter", voter);
		dataMap.put("proxy", proxy);
		dataMap.put("producers",producers);
		// action
		TxAction action = new TxAction(voter, "eosio", "voteproducer", dataMap);
		actions.add(action);
		tx.setActions(actions);
		// sgin
		String sign = Ecc.signTransaction(pk, new TxSign(info.getChainId(), tx));
		// data parse
		String data = Ecc.parseVoteProducerData(voter, proxy, producers);
		// reset data
		action.setData(data);
		// reset expiration
		tx.setExpiration(dateFormatter.format(new Date(1000 * Long.parseLong(tx.getExpiration().toString()))));
		return pushTransaction("none", tx, new String[] { sign });
	}

	/**
	 * 根据账户获取用户历史交易信息
	 *
	 * @param url
	 * @param accountName
	 * @return
	 */
	public ResponseEntity<JSONObject> getActions(String url, String accountName, String offset) {
		Map<String, String> map = new HashMap<>();
		map.put("pos", "-1");
		map.put("offset", offset);
		map.put("account_name", accountName);
		String para = JsonUtils.objectToJson(map);
		return postVisitEos(url, para);
	}

	/**
	 * 根据账户获取用户历史交易信息
	 *
	 * @param url
	 * @param accountName
	 * @return
	 */
	public ResponseEntity<String> getActionsTest(String url, String accountName, String offset) {
		Map<String, String> map = new HashMap<>();
		map.put("pos", "-1");
		map.put("offset", offset);
		map.put("account_name", accountName);
		String para = JsonUtils.objectToJson(map);
		return postVisitEosString(url, para);
	}
	
	/**
	 * token close
	 * @param owner
	 * @param symbol
	 * @return
	 * @throws Exception
	 */
	public Transaction close(String pk,String contract,String owner, String symbol)throws Exception {
		ChainInfo info = getChainInfo();			
		Block block = getBlock(info.getLastIrreversibleBlockNum().toString());
		Tx tx = new Tx();
		tx.setExpiration(info.getHeadBlockTime().getTime() / 1000 + 60);
		tx.setRef_block_num(info.getLastIrreversibleBlockNum());
		tx.setRef_block_prefix(block.getRefBlockPrefix());
		tx.setNet_usage_words(0l);
		tx.setMax_cpu_usage_ms(0l);
		tx.setDelay_sec(0l);
		// actions
		List<TxAction> actions = new ArrayList<>();
		// data
		Map<String, Object> dataMap = new LinkedHashMap<>();
		dataMap.put("close-owner", owner);
		dataMap.put("close-symbol",  new DataParam(symbol, DataType.symbol, Action.close).getValue());
		// action
		TxAction action = new TxAction(owner,contract,"close",dataMap);
		actions.add(action);
		tx.setActions(actions);
		// sgin
		String sign = Ecc.signTransaction(pk, new TxSign(info.getChainId(), tx));
		// data parse
		String data = Ecc.parseCloseData(owner, symbol);
		// reset data
		action.setData(data);
		// reset expiration
		tx.setExpiration(dateFormatter.format(new Date(1000 * Long.parseLong(tx.getExpiration().toString()))));
		return pushTransaction("none", tx, new String[] { sign });
	}


	// 获取区块信息
	public ResponseEntity<JSONObject> get_block(String url, BigInteger blockNumber) {
		Map<String, BigInteger> map = new HashMap<>();
		map.put("block_num_or_id", blockNumber);

		String para = JsonUtils.objectToJson(map);
		ResponseEntity<JSONObject> body = postVisitEos(url, para);
		// System.out.println(body);
		return body;
	}

	public String getTransaction(String url, String blockNum, String hashId) {
	    Map<String, String> map = new HashMap<>();
	    map.put("id", hashId);
	    map.put("block_num_hint", blockNum);
        String para = JsonUtils.objectToJson(map);
        return postVisitEosString(url, para).getBody();
        // System.out.println(body);
        // return body;
    }

	/**
	 * eos post请求
	 *
	 * @param url
	 * @param parameterJson
	 * @return
	 */
	public ResponseEntity<JSONObject> postVisitEos(String url, String parameterJson) {
		ResponseEntity<JSONObject> responseEntity = null;
		try {
			responseEntity = restTemplate.postForEntity(url, parameterJson, JSONObject.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EosWalletException(EosWalletEnums.EOS_RPC_ERROR);
		}
		return responseEntity;
	}

	/**
	 * eos post请求
	 *
	 * @param url
	 * @param parameterJson
	 * @return
	 */
	public ResponseEntity<String> postVisitEosString(String url, String parameterJson) {
		ResponseEntity<String> responseEntity = null;
		try {
			responseEntity = restTemplate.postForEntity(url, parameterJson, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EosWalletException(EosWalletEnums.EOS_RPC_ERROR);
		}
		return responseEntity;
	}


	/**
	 * 配置HttpClient超时时间
	 *
	 * @return
	 */
	private static ClientHttpRequestFactory getClientHttpRequestFactory() {
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000)
				.setConnectTimeout(10000).build();
		CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
		return new HttpComponentsClientHttpRequestFactory(client);
	}
}
