package com.blockchain.common.base.util;

import com.blockchain.common.base.dto.TokenDTO;
import com.blockchain.common.base.enums.BaseResultEnums;
import com.blockchain.common.base.exception.BaseException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * \* <p>Desciption:</p>
 * \* CreateTime: 2018/12/1 11:18
 * \* User: XianChaoWei
 * \* Version: V1.0
 * \
 */
public class RSACoderUtils {
    //非对称密钥算法
    private static final String KEY_ALGORITHM = "RSA";

    /**
     * 密钥长度，DH算法的默认密钥长度是1024
     * 密钥长度必须是64的倍数，在512到65536位之间
     */
    private static final int KEY_SIZE = 1024;

    //公钥
    public static final byte[] publicKey = {48, -127, -97, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 1, 5, 0,
            3, -127, -115, 0, 48, -127, -119, 2, -127, -127, 0, -128, 48, -25, 69, -64, -57, -59, -64, -116, -5, -4,
            -26, -5, 109, 17, 59, 68, -69, -16, -110, 19, -21, -78, 25, 77, -40, 18, 77, 2, 70, -45, 15, 112, 57, 111
            , 14, 78, -69, 18, -115, 21, 9, -38, 126, -107, -116, -124, -95, -116, 6, 18, 57, -73, -25, -9, 46, 69, 1
            , 127, -27, -92, 2, -119, 106, -33, -78, -35, -26, 72, -65, 116, 61, -53, 85, 94, 68, -84, -121, 55, -68,
            77, -128, 6, -99, 81, -5, 80, 52, 68, 53, -27, 79, -14, 37, 41, -18, 70, 96, -65, -32, -82, 75, -23, -77,
            -39, -36, -46, 120, 7, 67, 101, 107, 58, 97, 66, 72, -52, -128, -62, 39, -121, -47, -73, 73, -100, 49, 9,
            113, 2, 3, 1, 0, 1};
    //私钥
    public static final byte[] privateKey = {48, -126, 2, 118, 2, 1, 0, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1,
            1, 1, 5, 0, 4, -126, 2, 96, 48, -126, 2, 92, 2, 1, 0, 2, -127, -127, 0, -128, 48, -25, 69, -64, -57, -59,
            -64, -116, -5, -4, -26, -5, 109, 17, 59, 68, -69, -16, -110, 19, -21, -78, 25, 77, -40, 18, 77, 2, 70,
            -45, 15, 112, 57, 111, 14, 78, -69, 18, -115, 21, 9, -38, 126, -107, -116, -124, -95, -116, 6, 18, 57,
            -73, -25, -9, 46, 69, 1, 127, -27, -92, 2, -119, 106, -33, -78, -35, -26, 72, -65, 116, 61, -53, 85, 94,
            68, -84, -121, 55, -68, 77, -128, 6, -99, 81, -5, 80, 52, 68, 53, -27, 79, -14, 37, 41, -18, 70, 96, -65,
            -32, -82, 75, -23, -77, -39, -36, -46, 120, 7, 67, 101, 107, 58, 97, 66, 72, -52, -128, -62, 39, -121,
            -47, -73, 73, -100, 49, 9, 113, 2, 3, 1, 0, 1, 2, -127, -128, 103, -35, 55, -59, -64, -119, 28, -91, 2,
            -106, 57, 55, 61, -120, 5, 106, 44, 42, -54, -92, -47, 23, 43, 90, 109, 68, 32, -81, -36, -92, 93, -26,
            40, 91, -96, -85, -53, 6, -81, -27, 55, -94, -96, 49, -24, 33, -50, 100, -59, -5, 53, 81, 38, -68, -1, -3
            , -79, 83, -95, -71, 2, -58, 59, 103, -13, -78, -15, 56, 113, 53, -107, -98, 6, 54, -95, -110, -115, 88,
            2, -122, -111, 9, 29, 22, -78, -21, 105, -95, -74, -62, 109, -31, -48, -75, 101, 112, 103, 26, 11, -126,
            97, 48, 115, -74, 47, 122, -14, 97, -55, 77, 49, 7, -105, -87, 58, 21, -117, 98, -28, 27, -106, 113, -30,
            -28, 45, -101, -109, 1, 2, 65, 0, -7, -37, 63, 25, -106, 28, 88, 14, -22, -47, -26, -117, 4, 55, -10, 37,
            -73, 111, 75, 24, -123, 104, 42, -20, -46, -95, -119, -41, -82, -85, -11, -27, 41, -8, 38, 22, 112, -51,
            35, 112, 23, -66, 4, -55, -6, 33, -121, -98, 35, 2, -64, -79, 118, -85, -83, -33, -103, -107, -33, 127,
            -52, -115, 93, 25, 2, 65, 0, -125, 87, -47, -128, 76, -6, -66, 113, -107, 27, -58, -46, -15, -54, 59, -74
            , -98, -95, 16, -43, 109, 81, -79, 26, -57, 120, -82, 95, 66, 47, 105, 49, -92, -28, 61, 81, -60, 115,
            -18, -84, 8, 99, 25, -123, -109, 46, -48, 115, -37, -13, 56, 6, -9, 36, -36, 112, -42, -8, 62, -87, 21,
            70, -62, 25, 2, 65, 0, -16, -26, -89, 76, 48, 35, 91, -13, -26, 12, 67, 80, 61, -35, 7, 3, 14, 125, -53,
            -43, -12, -86, -98, -40, 127, -83, 40, -114, 63, -25, -92, -54, 51, 81, 2, -56, 24, 50, 113, -68, -99,
            -25, -92, 14, 105, -112, -14, -123, 82, 20, 81, 93, -55, -95, 117, -97, 101, 33, -49, -64, 20, -91, 39,
            -31, 2, 64, 7, 23, 44, -106, 50, -111, -82, -54, 78, -12, 106, -19, 100, 100, 56, -119, 9, 83, 68, -89,
            96, -7, 114, 8, 50, 16, -113, -55, 80, -73, 98, -124, 109, -108, 108, -61, 7, 74, 2, -18, -126, -99, 102,
            -7, 81, 18, -53, -22, 21, 75, -78, 16, -98, 50, -3, 59, -110, 63, 96, -110, -100, 53, 111, -79, 2, 64, 80
            , -113, -19, 28, -46, -110, -75, 123, -29, -94, -19, -28, -64, -124, 87, 5, -4, 125, 35, -68, 68, -4, -60
            , -38, 123, -28, -34, -1, 1, 52, -11, 19, -32, -24, 83, 119, -4, 66, -93, -43, -3, 105, -106, 22, -39,
            -55, 116, 91, -124, 39, -106, 37, -83, -39, 26, 118, 78, -87, 22, 76, 12, -110, 112, 77};

    /**
     * 初始化密钥对
     *
     * @return
     */
    public static KeyPair initKey() throws Exception {
        //实例化密钥生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        //初始化密钥生成器
        keyPairGenerator.initialize(KEY_SIZE);
        //生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }


    /**
     * 私钥加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[] 加密数据
     */
    public static byte[] encryptByPrivateKey(byte[] data, byte[] key) throws Exception {

        //取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        //数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 公钥加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[] 加密数据
     */
    public static byte[] encryptByPublicKey(byte[] data, byte[] key) throws Exception {

        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //初始化公钥
        //密钥材料转换
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
        //产生公钥
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

        //数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return cipher.doFinal(data);
    }

    /**
     * 私钥解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[] 解密数据
     */
    public static byte[] decryptByPrivateKey(byte[] data, byte[] key) throws Exception {
        //取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        //数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 公钥解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[] 解密数据
     */
    public static byte[] decryptByPublicKey(byte[] data, byte[] key) throws Exception {

        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //初始化公钥
        //密钥材料转换
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
        //产生公钥
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        //数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        return cipher.doFinal(data);
    }

    /**
     * 加密成token
     *
     * @param dto
     * @return
     */
    public static String encryptToken(TokenDTO dto) {
        try {
            //转成json数据
//            String json = JsonUtils.objectToJson(dto);
            String json = dto.getTel() + "-" + dto.getTimestamp() + "-" + dto.getTokenType();
            //用私钥加密
            byte[] encrypt = RSACoderUtils.encryptByPrivateKey(json.getBytes(), privateKey);
            //转成字符串
            return Base64.encodeBase64String(encrypt);
        } catch (Exception e) {
            throw new BaseException(BaseResultEnums.RSA_ERROR);
        }
    }

    /**
     * 解密获取用户数据
     *
     * @param token
     * @return
     */
    public static TokenDTO decryptToken(String token) {
        try {
            //转成数组
            byte[] decrypt = Base64.decodeBase64(StringUtils.getBytesUtf8(token));
            //解密
            byte[] bytes = RSACoderUtils.decryptByPublicKey(decrypt, publicKey);
            //转成json数据
            String[] json = new String(bytes).split("-");
            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setTel(json[0]);
            tokenDTO.setTimestamp(new Long(json[1]));
            tokenDTO.setTokenType(json[2]);
            //返回token对象
            return tokenDTO;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 加密私钥密码
     *
     * @param data 私钥/密码
     * @return
     */
    public static String encryptPassword(String data) {
        try {
            //用私钥加密
            byte[] encrypt = RSACoderUtils.encryptByPrivateKey(data.getBytes(), privateKey);
            //转成字符串
            return Base64.encodeBase64String(encrypt);
        } catch (Exception e) {
            throw new BaseException(BaseResultEnums.RSA_ERROR);
        }
    }
    /**
     * 解密获取密码
     *
     * @param data 待解密数据
     * @return 密码
     */
    public static String decryptPassword(String data) {
        try {
            byte[] dataByte = Base64.decodeBase64(data);
            byte[] password = RSACoderUtils.decryptByPublicKey(dataByte, publicKey);
            return new String(password);
        } catch (Exception e) {
            throw new BaseException(BaseResultEnums.PASSWORD_ERROR);
        }
    }

    /**
     * 根据私钥解密获取密码
     *
     * @param data 待解密数据
     * @param key  私钥
     * @return 密码
     */
    public static String decryptPassword(String data, String key) {
        try {
            byte[] dataByte = Base64.decodeBase64(data);
            byte[] keyByte = Base64.decodeBase64(key);
            byte[] password = RSACoderUtils.decryptByPrivateKey(dataByte, keyByte);
            return new String(password);
        } catch (Exception e) {
            throw new BaseException(BaseResultEnums.PASSWORD_ERROR);
        }
    }

}
