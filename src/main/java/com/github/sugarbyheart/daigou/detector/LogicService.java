package com.github.sugarbyheart.daigou.detector;

import com.github.sugarbyheart.daigou.common.DTO.ItemDiscription;
import com.github.sugarbyheart.daigou.common.Enum.LinkTypeEnum;
import com.github.sugarbyheart.daigou.detector.rules.LetianDetector;
import com.github.sugarbyheart.daigou.detector.rules.XinluoDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogicService {

    @Autowired
    private LetianDetector letianWebService;

    @Autowired
    private XinluoDetector xinluoWebService;

    public boolean canBuy(ItemDiscription itemDiscription) {
        if (itemDiscription.getLinkTypeEnum().equals(LinkTypeEnum.Letian)) {
            return letianWebService.canBuy(itemDiscription);
        } else if (itemDiscription.getLinkTypeEnum().equals(LinkTypeEnum.Xinluo)) {
            return xinluoWebService.canBuy(itemDiscription);
        } else {
            throw new IllegalStateException("Wrong link type:" + itemDiscription.toString());
        }
    }


}
