package com.github.sugarbyheart.daigou.detector.rules;

import com.github.sugarbyheart.daigou.common.DTO.ItemDiscription;
import com.github.sugarbyheart.daigou.common.Enum.LinkTypeEnum;

public interface WebDetector {

    boolean validUrl(String url);
    boolean canBuy(ItemDiscription itemDiscription);
    LinkTypeEnum linkType();

}
