package com.blockchain.server.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


/**
 * @author  Xia rong tao
 * @tible   MyToken 返回信息实体类
 * @Date    2020年4月28日
 */
@Data //生成getter,setter等函数
public class MyTokenDTO {

   private String  symbol ;//    ETH      #币种，交易币
   private String anchor  ;// BTC       #交易区，法币
   private float last_price ;// 0.07   #网站上显示的最新价格，浮点数
   private float  volume_24h  ;//100    #symbol的24小时交易量，浮点数
   private float amount_24h ;// 7      #anchor的24h交易量，浮点数
   private long price_updated_at;//  1531799742  #秒级时间戳

   public MyTokenDTO(){}

   /**
    *
    * @param symbol  ETH      #币种，交易币
    * @param anchor   BTC       #交易区，法币
    * @param last_price  网站上显示的最新价格，浮点数
    * @param volume_24h  symbol的24小时交易量，浮点数
    * @param amount_24h  anchor的24h交易量，浮点数
    * @param price_updated_at 秒级时间戳
    */
   public MyTokenDTO(String symbol, String anchor, float last_price, float volume_24h, float amount_24h, long price_updated_at) {
      this.symbol = symbol;
      this.anchor = anchor;
      this.last_price = last_price;
      this.volume_24h = volume_24h;
      this.amount_24h = amount_24h;
      this.price_updated_at = price_updated_at;
   }


}
