package com.huobi.client;

import com.huobi.client.impl.HuobiApiInternalFactory;
import com.huobi.client.model.enums.BalanceMode;
import com.huobi.client.model.enums.CandlestickInterval;
import com.huobi.client.model.event.AccountEvent;
import com.huobi.client.model.event.CandlestickEvent;
import com.huobi.client.model.event.OrderUpdateEvent;
import com.huobi.client.model.event.PriceDepthEvent;
import com.huobi.client.model.event.TradeEvent;
import com.huobi.client.model.event.TradeStatisticsEvent;

/***
 * The subscription client interface, it is used for subscribing any market data update and
 * account change, it is asynchronous, so you must implement the SubscriptionListener interface.
 * The server will push any update to the client. if client get the update, the onReceive method
 * will be called.
 */
public interface SubscriptionClient {

  /**
   * Subscribe candlestick/kline event. If the candlestick/kline is updated, server will send the
   * data to client and onReceive in callback will be called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like
   * "btcusdt,ethusdt".
   * @param interval The candlestick/kline interval, MIN1, MIN5, DAY1 etc.
   * @param callback The implementation is required. onReceive will be called if receive server's
   * update.
   */
  void subscribeCandlestickEvent(String symbols, CandlestickInterval interval,
                                 SubscriptionListener<CandlestickEvent> callback);

  /**
   * Subscribe candlestick/kline event. If the candlestick/kline is updated, server will send the
   * data to client and onReceive in callback will be called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like
   * "btcusdt,ethusdt".
   * @param interval The candlestick/kline interval, MIN1, MIN5, DAY1 etc.
   * @param callback The implementation is required. onReceive will be called if receive server's
   * update.
   * @param errorHandler The error handler will be called if subscription failed or error happen
   * between client and Huobi server.
   */
  void subscribeCandlestickEvent(String symbols, CandlestickInterval interval,
                                 SubscriptionListener<CandlestickEvent> callback,
                                 SubscriptionErrorHandler errorHandler);

  /**
   * Subscribe price depth event. If the price depth is updated, server will send the data to client
   * and onReceive in callback will be called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like
   * "btcusdt,ethusdt".
   * @param callback The implementation is required. onReceive will be called if receive server's
   * update.
   */
  void subscribePriceDepthEvent(String symbols, SubscriptionListener<PriceDepthEvent> callback);

  /**
   * Subscribe price depth event. If the price depth is updated, server will send the data to client
   * and onReceive in callback will be called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like
   * "btcusdt,ethusdt".
   * @param callback The implementation is required. onReceive will be called if receive server's
   * update.
   * @param errorHandler The error handler will be called if subscription failed or error happen
   * between client and Huobi server.
   */
  void subscribePriceDepthEvent(String symbols,
                                SubscriptionListener<PriceDepthEvent> callback,
                                SubscriptionErrorHandler errorHandler);

  /**
   * Subscribe price depth event. If the price depth is updated server will send the data to client
   * and onReceive in callback will be called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like
   * "btcusdt,ethusdt".
   * @param callback The implementation is required. onReceive will be called if receive server's
   * update.
   */
  void subscribeTradeEvent(String symbols, SubscriptionListener<TradeEvent> callback);

  /**
   * Subscribe price depth event. If the price depth is updated, server will send the data to client
   * and onReceive in callback will be called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like
   * "btcusdt,ethusdt".
   * @param callback The implementation is required. onReceive will be called if receive server's
   * update.
   * @param errorHandler The error handler will be called if subscription failed or error happen
   * between client and Huobi server.
   */
  void subscribeTradeEvent(String symbols,
                           SubscriptionListener<TradeEvent> callback,
                           SubscriptionErrorHandler errorHandler);

  /**
   * Subscribe account changing event. If the balance is updated, server will send the data to
   * client and onReceive in callback will be called. default to subscribe the available balance.
   *
   * @param mode The account balance mode, see {@link BalanceMode}
   * @param callback The implementation is required. onReceive will be called if receive server's
   * update.
   */
  void subscribeAccountEvent(BalanceMode mode, SubscriptionListener<AccountEvent> callback);

  /**
   * Subscribe account changing event. If the balance is updated, server will send the data to
   * client and onReceive in callback will be called.
   *
   * @param mode when mode is AVAILABLE, balance refers to available balance; when mode is TOTAL,
   * balance refers to TOTAL balance for trade sub account (available+frozen).
   * @param callback The implementation is required. onReceive will be called if receive server's
   * update.
   * @param errorHandler The error handler will be called if subscription failed or error happen
   * between client and Huobi server.
   */
  void subscribeAccountEvent(BalanceMode mode, SubscriptionListener<AccountEvent> callback,
                             SubscriptionErrorHandler errorHandler);

  /**
   * Subscribe order changing event. If a order is created, canceled etc, server will send the data
   * to client and onReceive in callback will be called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like
   * "btcusdt,ethusdt".
   * @param callback The implementation is required. onReceive will be called if receive server's
   * update.
   */
  void subscribeOrderUpdateEvent(String symbols, SubscriptionListener<OrderUpdateEvent> callback);

  /**
   * Subscribe order changing event. If a order is created, canceled etc, server will send the data
   * to client and onReceive in callback will be called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like
   * "btcusdt,ethusdt".
   * @param callback The implementation is required. onReceive will be called if receive server's
   * update.
   * @param errorHandler The error handler will be called if subscription failed or error happen
   * between client and Huobi server.
   * 订阅订单更改事件。如果一个订单被创建，取消等，服务器将发送data到客户端和onReceive在回调将被调用
   * 如果订阅失败或客户端与火比服务器之间发生错误*，将调用错误处理程序。
   */
  void subscribeOrderUpdateEvent(String symbols, SubscriptionListener<OrderUpdateEvent> callback,
                                 SubscriptionErrorHandler errorHandler);

  /**
   * Subscribe 24 hours trade statistics event. If statistics is generated, server will send the
   * data to client and onReceive in callback will be called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like
   * "btcusdt,ethusdt".
   * @param callback The implementation is required. onReceive will be called if receive server's
   * update.
   */
  void subscribe24HTradeStatisticsEvent(String symbols,
                                        SubscriptionListener<TradeStatisticsEvent> callback);

  /**
   * Subscribe 24 hours trade statistics event. If statistics is generated, server will send the
   * data to client and onReceive in callback will be called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like
   * "btcusdt,ethusdt".
   * @param callback The implementation is required. onReceive will be called if receive server's
   * update.
   * @param errorHandler The error handler will be called if subscription failed or error happen
   * between client and Huobi server.
   */
  void subscribe24HTradeStatisticsEvent(String symbols,
                                        SubscriptionListener<TradeStatisticsEvent> callback, SubscriptionErrorHandler errorHandler);

  /**
   * Unsubscribe all subscription.
   */
  void unsubscribeAll();

  void unSubscribeSymbol(String symbol);

  /**
   * Create the subscription client to subscribe the update from server.
   *
   * @return The instance of synchronous client.
   */
  static SubscriptionClient create() {
    return create("", "", new SubscriptionOptions());
  }

  /**
   * Create the subscription client to subscribe the update from server.
   *
   * @param apiKey The public key applied from Huobi.
   * @param secretKey The private key applied from Huobi.
   * @return The instance of synchronous client.
   */
  static SubscriptionClient create(
          String apiKey, String secretKey) {
    return HuobiApiInternalFactory.getInstance().createSubscriptionClient(
        apiKey, secretKey, new SubscriptionOptions());
  }

  /**
   * Create the subscription client to subscribe the update from server.
   *
   * @param apiKey The public key applied from Huobi.
   * @param secretKey The private key applied from Huobi.
   * @param subscriptionOptions The option of subscription connection, see {@link
   * SubscriptionOptions}
   * @return The instance of synchronous client.
   */
  static SubscriptionClient create(
          String apiKey, String secretKey, SubscriptionOptions subscriptionOptions) {
    return HuobiApiInternalFactory.getInstance().createSubscriptionClient(
        apiKey, secretKey, subscriptionOptions);
  }

}
