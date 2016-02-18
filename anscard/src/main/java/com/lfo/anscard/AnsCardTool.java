package com.lfo.anscard;

import com.gs.collections.impl.list.mutable.FastList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AnhongNB01 on 2016/2/17.
 */
public class AnsCardTool {
    public static FastList<AnsCardView.Item> generate() {
        int a = 65;
        List<AnsCardView.Item> ansCard = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            List<AnsCardView.Cell> cells = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                cells.add(new AnsCardView.Cell(String.valueOf(((char) a + j)), false, j,false));
            }
            AnsCardView.Item item = new AnsCardView.Item(FastList.newList(cells), i);

            ansCard.add(item);
        }
        return FastList.newList(ansCard);
    }
}
