package com.blockchain.server.tron.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * WalletTokentDTO 数据传输类（用户托管钱包的基本信息）
 *
 * @version 1.0
 * @date 2018-11-05 15:10:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWalletInfoDTO {
    private String openId;  // 用户ID
    private String tel; // 手机号
    private String nickname; // 昵称
    private String balance; // usdt 可用余额
    private List<WalletPayTokentDTO> tokens; // 代币基本信息
}