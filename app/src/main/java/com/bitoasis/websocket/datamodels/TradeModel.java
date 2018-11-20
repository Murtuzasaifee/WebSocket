package com.bitoasis.websocket.datamodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TradeModel {

    private int channelId;
    private int currencyId;
    private String lastTradePrice;
    private String lowestAsk;
    private String highestBid;
    private String pertChange;
    private String baseCurrency;
    private String qouteCurrency;
    private String highestTradePrice;
    private String lowestTradePrice;
    private int isFrozen;

}
