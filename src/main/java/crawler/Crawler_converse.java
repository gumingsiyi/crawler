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
 * Created by Administrator on 2015/7/29.
 */
public class Crawler_converse {
    private static final int BRAND_ID = 17;
    private static final String WEB_URL = "http://www.converse.com.cn";
    public static void main(String[] args) throws Exception {
        System.out.println("==start u==");
        int[] cat_id = {};
        String html_converse_women = HTTPUtils.getByURL("http://www.converse.com.cn/index.htm?iid=tplg06012015");
        //System.out.println(html_uniqlo_women);
        List<String> pageUrl = JsoupUtils.selectSAttr(html_converse_women, "div.navigation-expanded>ul>li>a", "href");
        //System.out.println(pageUrl);
       /* int cnt = 0;
        int count = 0;*/
        System.out.println(pageUrl.size());
        for (int i = 9; i <= 11; i++) {
            String page = WEB_URL+pageUrl.get(i);
            //System.out.println(page);
            String html_page = HTTPUtils.getByURL(page);
            
        }
    }

    private static TGGoods getAllAttributes(String finalUrl, int cat_Id) throws Exception {
        String content = HTTPUtils.getByURL(finalUrl);
        //price
        String now = JsoupUtils.selectS(content, "#J_StrPrice");
        double price = Double.valueOf(now);
        System.out.println(now);
        //name
        String name = JsoupUtils.getOneTextByClass(content, "detail-hd");
        System.out.println(name);
        //描述
        String des=null;
        //链接
        String gUrl = finalUrl;
        //添加时间
        Date date = new Timestamp(System.currentTimeMillis());
        System.out.println(date);
        //颜色
        List<String> temp = JsoupUtils.selectTexts(content, "[data-value^=1627207]");
        List<String> colorList = new ArrayList<String>();
        for (String color: temp) {
            color = color.substring(3);
            //System.out.println(color);
            colorList.add(color);
        }
        System.out.println(colorList);
        //大图
        List<String> pic = JsoupUtils.selectSAttr(content, "[href*=taobaocdn]", "href");
        System.out.println(pic);
        //商品编号
        String sn = JsoupUtils.selectS(content, "[title *= UQ]");
        sn = sn.substring(4);
        System.out.println(sn);
        TGGoods good = new TGGoods();
        good.setAddTime(date);
        good.setBrandId(16);
        good.setGoodsSn(sn);
        good.setColor(colorList);
        good.setGoodsName(name);
        good.setnPrice(price);
        good.setoPrice(price);
        good.setgUrl(gUrl);
        good.setPic(pic);
        good.setCatId(cat_Id);

        return good;
    }
}
