package com.lfo.anscardviewpraoject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.gs.collections.impl.list.mutable.FastList;
import com.lfo.anscard.AnsCardTool;
import com.lfo.anscard.AnsCardView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.ansCardView)
    AnsCardView ansCardView;
    @Bind(R.id.but)
    Button but;
    @Bind(R.id.butPick)
    Button butPick;

    private FastList<AnsCardView.Item> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.butPick)
    public void onbutPickClick() {
        list.get(0).getCells().get(1).setPicked(true);
        list.get(1).getCells().get(2).setShowAns(true);
        //ansCardView.setDrawList(list);
        ansCardView.setDrawList(list);
    }

    @OnClick(R.id.but)
    public void onClick() {
        list = AnsCardTool.generate();
        ansCardView.setDrawList(list);
    }
}
