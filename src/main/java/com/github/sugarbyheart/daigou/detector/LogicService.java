package com.github.sugarbyheart.daigou.detector;

import com.github.sugarbyheart.daigou.common.DTO.ItemDiscription;
import com.github.sugarbyheart.daigou.common.Enum.LinkTypeEnum;
import com.github.sugarbyheart.daigou.detector.rules.LetianWebService;
import com.github.sugarbyheart.daigou.detector.rules.XinluoWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogicService {

    @Autowired
    private LetianWebService letianWebService;

    @Autowired
    private XinluoWebService xinluoWebService;

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
