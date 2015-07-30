package crawler;


import com.my.crawler.HTTPUtils;
import com.my.crawler.JsoupUtils;

import java.util.List;

/**
 * Created by stiles on 15/7/30.
 */
public class Crawler_forever21 {
    private static final int BRAND_ID = 20;
    private static final String FOREVER21_URL = "http://www.forever21.cn";
    public static void main(String[] args) throws Exception {
        System.out.println("-------start-------");
        int[] cat_id = {0,0,0,0};

        String html_forever = HTTPUtils.getByURL(FOREVER21_URL);
        //System.out.println(html_forever);

        List<String> pageUrls = JsoupUtils.selectSAttr(html_forever, "div.clothing>div>div>a,div.acc>div>div>a,div.bag>div>div>a,div.love21>div>div>a", "href");
        List<String> names = JsoupUtils.selectTexts(html_forever, "div.clothing>div>div>a,div.acc>div>div>a,div.bag>div>div>a,div.love21>div>div>a");

        //System.out.println(pageUrls.size());
        //System.out.println(names.size());
        for (int i = 0; i < pageUrls.size(); i++) {
            String pageUrl = FOREVER21_URL+pageUrls.get(i);
            System.out.println(i+": "+names.get(i)+" "+FOREVER21_URL+pageUrls.get(i));
            String page_html = HTTPUtils.getByURL(pageUrl);
            //得到最大的页码
            List<String> tol = JsoupUtils.selectTexts(page_html, "td[align=right]>a");
            int lastPage = Integer.valueOf(tol.get(tol.size()-2));

            System.out.println(tol);
        }

    }
}
