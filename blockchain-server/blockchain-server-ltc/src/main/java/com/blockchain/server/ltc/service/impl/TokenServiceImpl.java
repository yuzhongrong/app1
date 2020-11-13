package com.blockchain.server.ltc.service.impl;

import com.blockchain.server.ltc.common.constants.TokenConstans;
import com.blockchain.server.ltc.common.enums.ExceptionEnums;
import com.blockchain.server.ltc.common.exception.ServiceException;
import com.blockchain.server.ltc.dto.TokenDTO;
import com.blockchain.server.ltc.entity.Token;
import com.blockchain.server.ltc.mapper.TokenMapper;
import com.blockchain.server.ltc.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private TokenMapper tokenMapper;

    @Override
    public List<TokenDTO> listToken() {
        return tokenMapper.listToken();
    }

    @Override
    public TokenDTO selectTokenById(Integer tokenId) {
        //校验币种id
        getAndVerifyTokenNameById(tokenId);
        return tokenMapper.selectTokenById(tokenId);
    }

    @Override
    public int getAndVerifyTokenIdByName(String tokenName) {
        if (tokenName.equalsIgnoreCase(TokenConstans.TOKEN_SYMBOL)) {
            return TokenConstans.TOKEN_PROPERTY_ID;
        } else {
            throw new ServiceException(ExceptionEnums.INEXISTENCE_TOKENNAME);
        }
    }

    @Override
    public String getAndVerifyTokenNameById(int tokenId) {
        if (tokenId == TokenConstans.TOKEN_PROPERTY_ID) {
            return TokenConstans.TOKEN_SYMBOL;
        } else {
            throw new ServiceException(ExceptionEnums.INEXISTENCE_TOKENID);
        }
    }

    @Override
    public Token selectByTokenName(String coinName) {
        Token where = new Token();
        where.setTokenSymbol(coinName);
        Token token = tokenMapper.selectOne(where);
        if (token == null) {
            throw new ServiceException(ExceptionEnums.INEXISTENCE_TOKENNAME);
        }
        return token;
    }

}
