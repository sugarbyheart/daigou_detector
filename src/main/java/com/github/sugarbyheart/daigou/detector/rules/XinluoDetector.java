package com.github.sugarbyheart.daigou.detector.rules;

import com.github.sugarbyheart.daigou.common.DTO.ItemDiscription;
import com.github.sugarbyheart.daigou.common.Enum.LinkTypeEnum;
import com.github.sugarbyheart.daigou.common.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class XinluoDetector implements WebDetector {

    private String selector = "a[onclick*=productDetailInfo.cart()]";

    @Override
    public LinkTypeEnum linkType() {
        return LinkTypeEnum.Xinluo;
    }

    @Override
    public boolean validUrl(String url) {
        return Utils.getLinkTypeEnum(url) == LinkTypeEnum.Xinluo;
    }

    @Override
    public boolean canBuy(ItemDiscription itemDiscription) {
        try {
            Document doc = Jsoup.connect(itemDiscription.getLink()).get();
            Elements elements = doc.select(selector);
            if (elements != null && elements.size() != 0) {
                return true;
            }
            return false;
        } catch (Exception e) {

            log.error("Exception: {}", e);
            return false;
        }
    }

}
