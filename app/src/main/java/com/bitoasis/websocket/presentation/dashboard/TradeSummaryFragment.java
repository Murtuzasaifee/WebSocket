package com.bitoasis.websocket.presentation.dashboard;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitoasis.websocket.R;
import com.bitoasis.websocket.datamodels.TradeModel;
import com.bitoasis.websocket.inits.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class TradeSummaryFragment extends BaseFragment {

    public static final String TAG = TradeSummaryFragment.class.getName();
    private View cr1, cr2, cr3,cr4,cr5,cr6;
    private View parentView;

    public TradeSummaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (parentView != null)
            return parentView;

        parentView = inflater.inflate(R.layout.fragment_trade_summary, container, false);
        initFields();
        return parentView;
    }

    private void initFields() {
        cr1 = parentView.findViewById(R.id.cr1);
        cr2 = parentView.findViewById(R.id.cr2);
        cr3 = parentView.findViewById(R.id.cr3);
        cr4 = parentView.findViewById(R.id.cr4);
        cr5 = parentView.findViewById(R.id.cr5);
        cr6 = parentView.findViewById(R.id.cr6);
    }

    @Override
    public void updateUI(TradeModel tradeModel) {
        super.updateUI(tradeModel);
        if (tradeModel.getCurrencyId() == 201) {
            cr1.setVisibility(View.VISIBLE);
            ((TextView) cr1.findViewById(R.id.currencyTV)).setText("BTC_EOS");
            updateView(cr1, tradeModel);
        }
        else if (tradeModel.getCurrencyId() == 172) {
            cr2.setVisibility(View.VISIBLE);
            ((TextView) cr2.findViewById(R.id.currencyTV)).setText("ETH_ETC");
            updateView(cr2, tradeModel);
        }
        else if (tradeModel.getCurrencyId() == 171) {
            cr3.setVisibility(View.VISIBLE);
            ((TextView) cr3.findViewById(R.id.currencyTV)).setText("BTC_ETC");
            updateView(cr3, tradeModel);
        }
        else if (tradeModel.getCurrencyId() == 114) {
            cr4.setVisibility(View.VISIBLE);
            ((TextView) cr4.findViewById(R.id.currencyTV)).setText("BTC_XMR");
            updateView(cr4, tradeModel);
        }
        else if (tradeModel.getCurrencyId() == 50) {
            cr5.setVisibility(View.VISIBLE);
            ((TextView) cr5.findViewById(R.id.currencyTV)).setText("BTC_LTC");
            updateView(cr5, tradeModel);
        }
        else if (tradeModel.getCurrencyId() == 218) {
            cr6.setVisibility(View.VISIBLE);
            ((TextView) cr6.findViewById(R.id.currencyTV)).setText("USDT_LSK");
            updateView(cr6, tradeModel);
        }

    }

    private void updateView(View v, TradeModel tradeModel) {
        parentView.findViewById(R.id.dataSyncTV).setVisibility(View.GONE);
        ((TextView) v.findViewById(R.id.lastTradePriceTV)).setText(tradeModel.getLastTradePrice());
        ((TextView) v.findViewById(R.id.lowestAskTV)).setText(tradeModel.getLowestAsk());
    }

}
