package crawler;

import com.my.crawler.HTTPUtils;
import com.my.crawler.JsoupUtils;
import com.my.crawler.MysqlUtils;
import com.my.crawler.TGGoods;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by stiles on 15/8/8.
 */
public class Crawler_gap {
    private static final int BRAND_ID = 22;
    private static final String WEB_URL = "http://www.gap.cn";
    public static void main(String[] args) throws Exception {
        System.out.println("==start converse==");
        int[] cat_id = {
                31,
                34,
                51,
                32,
                38,
                56,
                41,
                43,
                43,
                40,
                44,
                40,
                66,
                67,
                68,
                38,
                43,
                48,
                91,
                90,
                69,
                86,
                83,
                72
        };
        String[] websites = {
                "http://www.gap.cn/category/22.html",
                "http://www.gap.cn/category/24.html",
                "http://www.gap.cn/category/98.html",
                "http://www.gap.cn/category/26.html",
                "http://www.gap.cn/category/25.html",
                "http://www.gap.cn/category/32.html",
                "http://www.gap.cn/category/18492.html",
                "http://www.gap.cn/category/4890.html",
                "http://www.gap.cn/category/172.html",
                "http://www.gap.cn/category/200.html",
                "http://www.gap.cn/category/570.html",
                "http://www.gap.cn/category/199.html",
                "http://www.gap.cn/category/196.html",
                "http://www.gap.cn/category/197.html",
                "http://www.gap.cn/category/198.html",
                "http://www.gap.cn/category/38.html",
                "http://www.gap.cn/category/469.html",
                "http://www.gap.cn/category/2299.html",
                "http://www.gap.cn/category/375.html",
                "http://www.gap.cn/category/187.html",
                "http://www.gap.cn/category/191.html",
                "http://www.gap.cn/category/189.html",
                "http://www.gap.cn/category/193.html",
                "http://www.gap.cn/category/194.html"
        };
        //System.out.println(cat_id.length);
        //System.out.println(websites.length);
        int count=0;
        for (int i = 0; i < websites.length; i++) {
            String page = HTTPUtils.getByURL(websites[i]);

            List<String> tempPage = JsoupUtils.selectTexts(page, "div.pagenum>a");
            //System.out.println(tempPage);

            int size = 1;
            if (tempPage.size() != 0) {
                size = Integer.valueOf(tempPage.get(tempPage.size() - 2));
            }

            System.out.println(size);
            String cId = StringUtils.substringBetween(websites[i], "http://www.gap.cn/category/", ".html");

            for (int j = 1; j <= size; j++) {
                String content = HTTPUtils.getByURL("http://www.gap.cn/catalog/category/view/id/" + cId + "/?p=" + j);
                List<String> urls = JsoupUtils.selectSAttr(content, "p.product-image>a", "href");
                List<String> pictures = JsoupUtils.selectSAttr(content, "p.product-image>a>img", "src2");
                System.out.println(urls);

                for (int k = 0; k < urls.size(); k++) {
                    String url = urls.get(k);
                    String picture = pictures.get(k);

                    String snPrice = JsoupUtils.selectS(content, "div.categoryProductItem:eq(" + (k + 1) + ")>div.price-box>p>span#product-price-3.price>span");
                    String soPrice = JsoupUtils.selectS(content, "div.categoryProductItem:eq(" + (k + 1) + ")>div.price-box>p>span#old-price-3.price>span");
                    double nPrice = Double.valueOf(snPrice.substring(1));

                    System.out.println(nPrice);
                    System.out.println(soPrice);
                    TGGoods good = getAllAttributes(url, cat_id[i]);
                    good.setnPrice(nPrice);

                    if (soPrice != null) {
                        double oPrice = Double.valueOf(soPrice.substring(1));
                        good.setoPrice(oPrice);
                    } else {
                        good.setoPrice(nPrice);
                    }
                    //MysqlUtils.addBrandCate(BRAND_ID, cat_id[i]);
                    //MysqlUtils.addTggoods(good, good.getGoodsSn(), BRAND_ID, pictures.get(k));

                }
            }
        }
    }

    private static TGGoods getAllAttributes(String finalUrl, int cat_Id) throws Exception {
        String content = HTTPUtils.getByURL(finalUrl);
        TGGoods good = new TGGoods();
        //name
        String name = JsoupUtils.selectS(content, "div.product-name>h1");
        System.out.println(name);
        good.setGoodsName(name);
        //描述
        String des = null;
        good.setGoodsDesc(des);
        //链接
        String gUrl = finalUrl;
        //添加时间
        Date date = new Timestamp(System.currentTimeMillis());
        System.out.println(date);
        //颜色
        List<String> colorList = JsoupUtils.selectTexts(content, "span.choose#showColor");
        System.out.println(colorList);
        //大图
        List<String> picT = JsoupUtils.selectSAttr(content, "div#product_disc>p>img", "src");
        List<String> Bpic = JsoupUtils.selectSAttr(content, "div.containerBigimage>a>img", "src");
        List<String> pic = new ArrayList<String>(Bpic);
        for (int i = 0; i < picT.size()-1; i++) {
            pic.add(WEB_URL+picT.get(i));
        }
        System.out.println(pic);
        //商品编号
        String sn = JsoupUtils.selectS(content, "p.availability");
        sn = sn.substring(4);
        System.out.println(sn);

        good.setAddTime(date);
        good.setBrandId(BRAND_ID);
        good.setGoodsSn(sn);
        good.setgUrl(gUrl);
        good.setPic(pic);
        good.setCatId(cat_Id);

        return good;
    }
}
