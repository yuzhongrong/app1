package com.huobi.client.impl;

import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.UrlParamsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

class ApiSignature {

  static final String op = "op";
  static final String opValue = "auth";
  private static final String accessKeyId = "AccessKeyId";
  private static final String signatureMethod = "SignatureMethod";
  private static final String signatureMethodValue = "HmacSHA256";
  private static final String signatureVersion = "SignatureVersion";
  private static final String signatureVersionValue = "2";
  private static final String timestamp = "Timestamp";
  private static final String signature = "Signature";
  private static final Logger LOG = LoggerFactory.getLogger(ApiSignature.class);

  private static final DateTimeFormatter DT_FORMAT = DateTimeFormatter
      .ofPattern("uuuu-MM-dd'T'HH:mm:ss");
  private static final ZoneId ZONE_GMT = ZoneId.of("Z");


  void createSignature(String accessKey, String secretKey, String method, String host,
      String uri, UrlParamsBuilder builder) {

    LOG.debug("*******createSignature****"+accessKey+" "+secretKey+" "+" "+method+" "+host+" "+uri);
    StringBuilder sb = new StringBuilder(1024);

    if (accessKey == null || "".equals(accessKey) || secretKey == null || "".equals(secretKey)) {
      throw new HuobiApiException(HuobiApiException.KEY_MISSING,
          "API key and secret key are required");
    }

    sb.append(method.toUpperCase()).append('\n')
        .append(host.toLowerCase()).append('\n')
        .append(uri).append('\n');

    builder.putToUrl(accessKeyId, accessKey)
        .putToUrl(signatureVersion, signatureVersionValue)
        .putToUrl(signatureMethod, signatureMethodValue)
        .putToUrl(timestamp, gmtNow());

    sb.append(builder.buildSignature());
    LOG.info("********sb.append*********"+builder.buildUrl());

    Mac hmacSha256;
    try {
      hmacSha256 = Mac.getInstance(signatureMethodValue);
      SecretKeySpec secKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8),
          signatureMethodValue);
      hmacSha256.init(secKey);
    } catch (NoSuchAlgorithmException e) {
      throw new HuobiApiException(HuobiApiException.RUNTIME_ERROR,
          "[Signature] No such algorithm: " + e.getMessage());
    } catch (InvalidKeyException e) {
      throw new HuobiApiException(HuobiApiException.RUNTIME_ERROR,
          "[Signature] Invalid key: " + e.getMessage());
    }
    String payload = sb.toString();
    LOG.info("********payload*********"+payload);
    byte[] hash = hmacSha256.doFinal(payload.getBytes(StandardCharsets.UTF_8));

    String actualSign = Base64.getEncoder().encodeToString(hash);

    builder.putToUrl(signature, actualSign);
    LOG.info("********sb.append-signature*********"+builder.buildUrl());

  }

  private static long epochNow() {
    return Instant.now().getEpochSecond();
  }

  static String gmtNow() {
    String time=Instant.ofEpochSecond(epochNow()).atZone(ZONE_GMT).format(DT_FORMAT);
    LOG.info("********ZONE_GMT*********"+ZONE_GMT);
    LOG.info("********DT_FORMAT*********"+DT_FORMAT);
    LOG.info("********gmtNow*********"+time);
    return time;
  }
}
