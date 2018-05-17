package com.github.sugarbyheart.daigou.detector.rules;

import com.github.sugarbyheart.daigou.common.DTO.ItemDiscription;
import com.github.sugarbyheart.daigou.common.Enum.LinkTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class XinluoWebService implements WebService {

    private final String prefix = "m.shilladfs.com";
    private final String selector =
            "#container > div.detail_view > div.pr_wrap.clear_both > div.txt > ul > li > a.btn_soldout";

    @Override
    public LinkTypeEnum linkType() {
        return LinkTypeEnum.Xinluo;
    }

    @Override
    public boolean validUrl(String url) {
        if (!url.contains(prefix)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean canBuy(ItemDiscription itemDiscription) {
        try {
            Document doc = Jsoup.connect(itemDiscription.getLink()).get();
            Elements elements = doc.select(selector);
            if (elements != null && elements.size() != 0) {
                return false;
            }
            return true;
        } catch (Exception e) {

            log.error("Exception: {}", e);
            return false;
        }
    }

}
