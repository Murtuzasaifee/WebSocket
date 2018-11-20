package com.bitoasis.websocket.presentation.dashboard;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bitoasis.websocket.R;
import com.bitoasis.websocket.datamodels.TradeModel;
import com.bitoasis.websocket.inits.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class TradeHighlightFragment extends BaseFragment {

    public static final String TAG = TradeHighlightFragment.class.getName();
    private View btc, eth, xrp;
    private View parentView;

    public TradeHighlightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (parentView != null)
            return parentView;

        parentView = inflater.inflate(R.layout.fragment_trade_highlight, container, false);
        initFields();
        return parentView;
    }

    private void initFields() {
        btc = parentView.findViewById(R.id.btc);
        eth = parentView.findViewById(R.id.eth);
        xrp = parentView.findViewById(R.id.xrp);
    }

    @Override
    public void updateUI(TradeModel tradeModel) {
        super.updateUI(tradeModel);
        if (tradeModel.getCurrencyId() == 201) {
            ((TextView) btc.findViewById(R.id.currencyTV)).setText("BTC_EOS");
            updateView(btc, tradeModel);
        } else if (tradeModel.getCurrencyId() == 172) {
            ((TextView) eth.findViewById(R.id.currencyTV)).setText("ETH_ETC");
            updateView(eth, tradeModel);
        } else if (tradeModel.getCurrencyId() == 137) {
            ((TextView) xrp.findViewById(R.id.currencyTV)).setText("XMR_LTC");
            updateView(xrp, tradeModel);
        }

    }

    private void updateView(View v, TradeModel tradeModel) {
        ((TextView) v.findViewById(R.id.lastTradePriceTV)).setText(tradeModel.getLastTradePrice());
        ((TextView) v.findViewById(R.id.lowestAskTV)).setText(tradeModel.getLowestAsk());
        ((TextView) v.findViewById(R.id.highestBidTV)).setText(tradeModel.getHighestBid());
        if (tradeModel.getIsFrozen() == 0)
            ((TextView) v.findViewById(R.id.frozenTV)).setText("No");
        else
            ((TextView) v.findViewById(R.id.frozenTV)).setText("YES");
    }
}
