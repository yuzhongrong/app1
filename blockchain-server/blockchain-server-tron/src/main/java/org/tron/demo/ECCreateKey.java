package org.tron.demo;

import com.blockchain.server.tron.common.constant.TronConstant;
import org.spongycastle.math.ec.ECPoint;
import org.tron.common.crypto.ECKey;
import org.tron.common.crypto.Hash;
import org.tron.common.crypto.Sha256Sm3Hash;
import org.tron.common.utils.Base58;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.Utils;
import org.tron.core.exception.CipherException;
import org.tron.walletserver.WalletApi;

import java.math.BigInteger;

import static java.util.Arrays.copyOfRange;

public class ECCreateKey {

  private static byte[] private2PublicDemo(byte[] privateKey) {
    BigInteger privKey = new BigInteger(1, privateKey);
    ECPoint point = ECKey.CURVE.getG().multiply(privKey);
    return point.getEncoded(false);
  }


  private static byte[] public2AddressDemo(byte[] publicKey) {
    byte[] hash = Hash.sha3(copyOfRange(publicKey, 1, publicKey.length));
    //System.out.println("sha3 = " + ByteArray.toHexString(hash));
    byte[] address = copyOfRange(hash, 11, hash.length);
    address[0] = WalletApi.getAddressPreFixByte();
    return address;
  }

  public static String address2Encode58CheckDemo(byte[] input) {
    byte[] hash0 = Sha256Sm3Hash.hash(input);

    byte[] hash1 = Sha256Sm3Hash.hash(hash0);

    byte[] inputCheck = new byte[input.length + 4];

    System.arraycopy(input, 0, inputCheck, 0, input.length);
    System.arraycopy(hash1, 0, inputCheck, input.length, 4);

    return Base58.encode(inputCheck);
  }

  public static String private2Address() throws CipherException {
    ECKey eCkey = new ECKey(Utils.getRandom()); //

    String privateKey=ByteArray.toHexString(eCkey.getPrivKeyBytes());
    System.out.println("Private Key: " + privateKey);

    byte[] publicKey0 = eCkey.getPubKey();
    byte[] publicKey1 = private2PublicDemo(eCkey.getPrivKeyBytes());
    if (!ECCreateKey.equals(publicKey0, publicKey1)) {
      throw new CipherException("publickey error");
    }
    String publicKey=ByteArray.toHexString(publicKey0);
    System.out.println("Public Key: " + publicKey);

    byte[] address0 = eCkey.getAddress();
    byte[] address1 = public2AddressDemo(publicKey0);
    if (!ECCreateKey.equals(address0, address1)) {
      throw new CipherException("address error");
    }
    String address=ByteArray.toHexString(address0);
    System.out.println("Address: " +address);

    String base58checkAddress0 = WalletApi.encode58Check(address0);
    String base58checkAddress1 = address2Encode58CheckDemo(address0);
    if (!base58checkAddress0.equals(base58checkAddress1)) {
      throw new CipherException("base58checkAddress error");
    }
    String dataList=privateKey+ TronConstant.DELIMIT+base58checkAddress1+TronConstant.DELIMIT+address;
    return dataList;
  }

  public static boolean equals(byte[] a, byte[] a2) {
    if (a==a2)
      return true;
    if (a==null || a2==null){
      System.out.println("都为null");
      return false;
    }


    int length = a.length;
    if (a2.length != length){
      System.out.println("长度不等");
      return false;
    }


    for (int i=0; i<length; i++)
      if (a[i] != a2[i]){
        System.out.println("值不等");
        return false;
      }


    return true;
  }

  public static void main(String[] args) throws CipherException {

//    String privateKey = "F43EBCC94E6C257EDBE559183D1A8778B2D5A08040902C0F0A77A3343A1D0EA5";
//
//    String address = private2Address(ByteArray.fromHexString(privateKey));
//    System.out.println("base58Address: " + address);

    System.out.println("================================================================\r\n");
    String dataList = private2Address();
    System.out.println("base58Address: " + dataList);
    String[] split = dataList.split(TronConstant.DELIMIT);
    String privateKey = split[0];
    String address = split[1];
    String hexAddress =split[2];
    System.out.println("privateKey:"+privateKey+" address:"+address+" hexAddress:"+hexAddress);
  }
}
