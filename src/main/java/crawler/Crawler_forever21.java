package crawler;


import com.my.crawler.HTTPUtils;
import com.my.crawler.JsoupUtils;
import com.my.crawler.TGGoods;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
            if (i >= 11 && i <= 16) continue;
            String pageUrl = FOREVER21_URL+pageUrls.get(i);
            //System.out.println(i+": "+names.get(i)+" "+FOREVER21_URL+pageUrls.get(i));
            String page_html = HTTPUtils.getByURL(pageUrl);
            //得到最大的页码
            List<String> tol = JsoupUtils.selectTexts(page_html, "td[align=right]>a");
            int lastPage = Integer.valueOf(tol.get(tol.size() - 2));
            //抓取每个商品
            for (int j = 1; j <= lastPage; j++) {
                String context = HTTPUtils.getByURL(pageUrl+"&CurrentPage="+j);

                List<String> texts = JsoupUtils.selectSAttr(context, "div.item>div.images>a", "href");
                //System.out.println(texts);

                for (String text: texts) {
                    String[] item = text.split("'");
                    String id = item[1];
                    String type = item[3];
                    System.out.println(id+type);

                    String finalUrl = "http://www.forever21.cn/Forever21/ProductView.aspx?Category="+type+"&ProductId="+id;
                    TGGoods good = getAllAttributes(finalUrl, 0);

                }
            }

        }
    }
    private static TGGoods getAllAttributes(String finalUrl, int cat_Id) throws Exception {
        String content = HTTPUtils.getByURL(finalUrl);
        //price
        String now = JsoupUtils.getOneTextByClass(content, "t_now");
        System.out.println(now);
        double price = Double.valueOf(now);
        //name
        String name = JsoupUtils.selectS(content, "em.title");
        System.out.println(name);
        //desp
        String des = JsoupUtils.getOneTextByClass(content, "description");
        System.out.println(des);
        //link
        String gUrl = finalUrl;
        //time
        Date date = new Timestamp(System.currentTimeMillis());
        //color
        List<String> temp = JsoupUtils.selectTexts(content, "dd.option>div>select>option");
        //System.out.println(temp);
        List<String> colors = new ArrayList<String>();
        for (String color: temp) {
            color.replaceAll("[a-zA-Z]", "");
            colors.add(color);
        }
        System.out.println(colors);
        return null;
    }
}
