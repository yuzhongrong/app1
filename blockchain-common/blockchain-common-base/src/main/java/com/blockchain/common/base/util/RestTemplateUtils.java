package com.blockchain.common.base.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.enums.BaseResultEnums;
import com.blockchain.common.base.exception.BaseException;
import com.blockchain.common.base.exception.RPCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author: qinhui
 * @date: 2020/7/2 9:09
 */
@Component
public class RestTemplateUtils {
    private static final String SUCCESS = "200";
    private static final String KEY_CODE = "code";
    private static final String KEY_MSG = "msg";
    private static final String KEY_DATA = "data";
    private static final String KEY_HASH = "hash";
    private static final String KEY_COINNAME = "coinName";
    private static final String KEY_WALLETTYPE = "walletType";
    @Autowired
    RestTemplate restTemplate;

    public JSONObject Rpc(String url, MultiValueMap<String, String> multiValueMap) {

        JSONObject jsonObject = goPost(url, multiValueMap);
        System.out.println(jsonObject.toJSONString());
        if (!SUCCESS.equals(jsonObject.getString(KEY_CODE))) {
            ResultDTO resultDTO= new ResultDTO();
            resultDTO.setCode(Integer.parseInt(jsonObject.getString(KEY_CODE)));
            resultDTO.setMsg(jsonObject.getString(KEY_MSG));
            resultDTO.setData(jsonObject.getString(KEY_DATA));
            throw new RPCException(resultDTO);
        }
        return jsonObject;
    }
    public  JSONObject goPost(String url, MultiValueMap<String, String> data) {
        JSONObject json;
        if(restTemplate==null){
            System.out.println("restTemplate is null");
        }
        try {
            System.out.println("post接口入参地址："+url);
            System.out.println("post接口入参参数："+ JSON.toJSONString(data));
            json = restTemplate.postForObject(url, data, JSONObject.class);
            System.out.println("post接口返回参数信息："+ JSON.toJSONString(json));
            if(json == null) {
                throw new BaseException(BaseResultEnums.BUSY);
            }
        } catch (Exception e) {
            System.out.println("请求得数据："+data.toString());
            e.printStackTrace();
            throw new BaseException(BaseResultEnums.BUSY);
        }
        return json;
    }

    public List<Map>  IncomeInfo(String url, String account, String status, Integer pageNum, Integer pageSize) {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("account", account);
        multiValueMap.add("status", status);
        multiValueMap.add("pageNum", pageNum.toString());
        multiValueMap.add("pageSize", pageSize.toString());
        ResultDTO<List<Map>> list=JSONObject.toJavaObject(Rpc(url, multiValueMap), ResultDTO.class);
        return list.getData();
    }

    public void releaseIncome(String url, String account) {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("account", account);
        ResultDTO resultDTO= JSONObject.toJavaObject(Rpc(url, multiValueMap),ResultDTO.class);
        if(resultDTO.getCode()!=200){
            throw new BaseException(BaseResultEnums.DEFAULT);
        }
    }

    public void releaseIncomeAll(String url) {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        ResultDTO resultDTO= JSONObject.toJavaObject(Rpc(url, multiValueMap),ResultDTO.class);
        if(resultDTO.getCode()!=200){
            throw new BaseException(BaseResultEnums.DEFAULT);
        }
    }
}
