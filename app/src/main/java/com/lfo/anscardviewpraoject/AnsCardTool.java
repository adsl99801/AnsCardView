package com.lfo.anscardviewpraoject;


import com.lfo.anscard.AnsCardView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AnhongNB01 on 2016/2/17.
 */
public class AnsCardTool {
    public static List<AnsCardView.Item> generate() {
        int a = 65;
        List<AnsCardView.Item> ansCard = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            List<AnsCardView.Cell> cells = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                cells.add(new AnsCardView.Cell(String.valueOf(((char) a + j)), false, j,false));
            }
            AnsCardView.Item item = new AnsCardView.Item(cells, i);

            ansCard.add(item);
        }
        return ansCard;
    }
    public static List<String> generateCodeList() {
        int a= 65;
        List<String> codeList = new ArrayList<>();
        for(int i=0;i<5;i++){
            int charint=a+i;
            char chari=(char)charint;
            codeList.add(String.valueOf(chari));
        }
        return codeList;

    }
}
