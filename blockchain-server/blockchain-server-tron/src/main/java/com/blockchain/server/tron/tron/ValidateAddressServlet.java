package com.blockchain.server.tron.tron;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServlet;
import java.util.Base64;


@Component
public class ValidateAddressServlet extends HttpServlet {

    public String validAddress(String input) {
        byte[] address = null;
        boolean result = true;
        String msg;
        try {
            if (input.length() == Constant.ADDRESS_SIZE) {
                //hex
                address = ByteArray.fromHexString(input);
                msg = "Hex string format";
            } else if (input.length() == 34) {
                //base58check
                address = Wallet.decodeFromBase58Check(input);
                msg = "Base58check format";
            } else if (input.length() == 28) {
                //base64
                address = Base64.getDecoder().decode(input);
                msg = "Base64 format";
            } else {
                result = false;
                msg = "Length error";
            }
            if (result) {
                result = Wallet.addressValid(address);
                if (!result) {
                    msg = "Invalid address";
                }
            }
        } catch (Exception e) {
            result = false;
            msg = e.getMessage();
        }

        JSONObject jsonAddress = new JSONObject();
        jsonAddress.put("result", result);
        jsonAddress.put("message", msg);

        return jsonAddress.toJSONString();
    }
}