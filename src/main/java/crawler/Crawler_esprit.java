package crawler;

import com.my.crawler.HTTPUtils;
import com.my.crawler.JsoupUtils;
import com.my.crawler.MysqlUtils;
import com.my.crawler.TGGoods;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by stiles on 15/8/1.
 */
public class Crawler_esprit {
    private static final int BRAND_ID = 18;
    private static final String ESPRIT_URL = "http://www.esprit.cn";
    public static void main(String[] args) throws Exception {
        System.out.println("==start e==");
        int[] cat_id = {
            51, 31, 34, 52, 42, 43, 41, 57, 38, 32, 66, 0, 73, 83, 90, 82, 86, 87, 89
        };
        String html_esprit = HTTPUtils.getByURL("http://www.esprit.cn/products/2---------.htm");

        List<String> temp_pageUrls = JsoupUtils.selectSAttr(html_esprit, "div.nav_left>dl.hover:gt(2)>dd>ul>li>a", "onclick");
        //System.out.println(temp_pageUrls.size());
        List<String> names = JsoupUtils.selectTexts(html_esprit, "div.nav_left>dl.hover:gt(2)>dd>ul>li>a");
        //System.out.println(names.size());
        int count = 0;
        for (int i = 0; i < temp_pageUrls.size(); i++) {
            if (i == 11) continue;
            String[] pageUrl = temp_pageUrls.get(i).split("'");
            String temp = pageUrl[1];
            temp = temp.substring(0, pageUrl[1].length()-4);
            System.out.println(i+": "+names.get(i)+": "+temp);

            for (int j = 1;; j++) {
                //当这页没有商品链接时，说明这个商品已经全部完成了
                String page = HTTPUtils.getByURL(ESPRIT_URL + temp + "/scroll.htm?page=" + j + "&c7=&c8=&c10=");
                List<String> thingUrls = JsoupUtils.selectSAttr(page, "div.sku_title>a", "href");
                List<String> pictures = JsoupUtils.selectSAttr(page, "div.sku_pic>a>img", "src");
                System.out.println(pictures);
                if (thingUrls.size() == 0) break;
                int cnt = 0;
                for (String thingUrl: thingUrls) {
                    System.out.println(++count);
                    thingUrl = ESPRIT_URL+thingUrl;
                    //System.out.println(thingUrl);
                    //MysqlUtils.addBrandCate(BRAND_ID, cat_id[i]);
                    TGGoods good = getAllAttributes(thingUrl, cat_id[i]);
                    //MysqlUtils.addTggoods(good, good.getGoodsSn(), BRAND_ID, pictures.get(cnt++));
                }
            }

        }
    }

    private static TGGoods getAllAttributes(String finalUrl, int cat_Id) throws Exception {
        String content = HTTPUtils.getByURL(finalUrl);
        //System.out.println(content);
        TGGoods good = new TGGoods();
        //price
        double nprice, oprice;
        String now = JsoupUtils.selectS(content, "div.sku_price3>h4>span:gt(0)");
        if (now != null) {
            now = now.substring(1);
            nprice = Double.valueOf(now);
            System.out.println(nprice);
            good.setnPrice(nprice);
        }

        System.out.println(now);
        String old = JsoupUtils.selectS(content, "div.sku_price3>h4>span:lt(1)");
        old = old.substring(1);
        oprice = Double.valueOf(old);
        System.out.println(oprice);
        good.setoPrice(oprice);
        //System.out.println(old);
        //name
        String name = JsoupUtils.selectS(content, "title");
        System.out.println(name);
        //description
        String des=JsoupUtils.selectS(content, "dl.clear>dd");
        System.out.println(des);
        //link
        String gUrl = finalUrl;
        //time
        Date date = new Timestamp(System.currentTimeMillis());
        System.out.println(date);
        //color
        List<String> colorList = JsoupUtils.selectSAttr(content, "div.sku_color2>a", "title");
        System.out.println(colorList);
        //pictures
        List<String> pic = JsoupUtils.selectSAttr(content, "ul[style=left:0px;]>li>img", "src");
        System.out.println(pic);
        //no.
        List<String> sn = JsoupUtils.selectSAttr(content, "input#sku_code", "value");
        System.out.println(sn.get(0));

        good.setAddTime(date);
        good.setBrandId(BRAND_ID);
        good.setGoodsSn(sn.get(0));
        good.setColor(colorList);
        good.setGoodsName(name);
        good.setGoodsDesc(des);
        good.setgUrl(gUrl);
        good.setPic(pic);
        good.setCatId(cat_Id);

        return good;
    }
}

