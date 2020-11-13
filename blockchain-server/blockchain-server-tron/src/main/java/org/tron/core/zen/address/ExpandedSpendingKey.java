package org.tron.core.zen.address;

import com.google.protobuf.ByteString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.tron.api.GrpcAPI.BytesMessage;
import org.tron.common.utils.ByteArray;
import org.tron.core.exception.ZksnarkException;
import org.tron.walletserver.WalletApi;

import java.util.Optional;

@Slf4j(topic = "shieldTransaction")
@AllArgsConstructor
public class ExpandedSpendingKey {

  @Setter
  @Getter
  byte[] ask; // the spend authorizing key,256
  @Setter
  @Getter
  byte[] nsk; // the proof authorizing key (ak, nsk),256
  // Let ovk be an outgoing viewing key that is intended to be able to decrypt this payment
  @Setter
  @Getter
  byte[] ovk; // the outgoing viewing key,256

  public ExpandedSpendingKey() {
  }



  public static ExpandedSpendingKey decode(byte[] m_bytes) {
    ExpandedSpendingKey key = new ExpandedSpendingKey();

    byte[] ask = ByteArray.subArray(m_bytes, 0, 32);
    byte[] nsk = ByteArray.subArray(m_bytes, 32, 64);
    byte[] ovk = ByteArray.subArray(m_bytes, 64, 96);
    key.setAsk(ask);
    key.setNsk(nsk);
    key.setOvk(ovk);
    return key;
  }



  public byte[] encode() {
    byte[] m_bytes = new byte[96];
    System.arraycopy(ask, 0, m_bytes, 0, 32);
    System.arraycopy(nsk, 0, m_bytes, 32, 32);
    System.arraycopy(ovk, 0, m_bytes, 64, 32);

    return m_bytes;
  }
}
