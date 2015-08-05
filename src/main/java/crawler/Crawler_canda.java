package crawler;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.my.crawler.HTTPUtils;
import com.my.crawler.JsoupUtils;
import com.my.crawler.MysqlUtils;
import com.my.crawler.TGGoods;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by stiles on 15/8/3.
 */
public class Crawler_canda {
    private static final int BRAND_ID = 21;
    private static final String CANDA_URL = "http://http://www.canda.cn/women.html";
    public static void main(String[] args) throws Exception {
        System.out.println("==start C&A==");
        int[] cat_id = {
                31,
                34,
                32,
                38,
                57,
                51,
                52,
                42,
                41,
                43,
                89
        };
        String[] websites = {
                "http://www.canda.cn/women/women-shangzhuang/women-ckn.html",
                "http://www.canda.cn/women/women-shangzhuang/women-bls.html",
                "http://www.canda.cn/women/women-shangzhuang/women-fkn.html",
                "http://www.canda.cn/women/women-shangzhuang/women-swt.html",
                "http://www.canda.cn/women/women-shangzhuang/women-out.html",
                "http://www.canda.cn/women/women-xiazhuang/women-drs.html",
                "http://www.canda.cn/women/women-xiazhuang/women-skt.html",
                "http://www.canda.cn/women/women-xiazhuang/women-jeans.html",
                "http://www.canda.cn/women/women-xiazhuang/women-shorts.html",
                "http://www.canda.cn/women/women-xiazhuang/women-trs.html",
                "http://www.canda.cn/women/women-accessories.html"
        };
        //System.out.println(cat_id.length);
        //System.out.println(websites.length);
        int count=0;
        for (int i = 0; i < websites.length; i++) {
            int p = 0;
            String pre;
            String cur = "";
            while (true) {
                pre = cur;
                cur = HTTPUtils.getByURL(websites[i]+ "?toolbar=1&p=" + (++p) + "&dir=asc&order=position&ajaxtoolbar=1");
                if (cur.equals(pre)) break;

                JSONObject jb = new JSONObject(cur);
                String page = jb.getString("toolbarlistproduct");

                List<String> pictures = JsoupUtils.selectSAttr(page, "div.hover-box>img", "src");
                List<String> finalUrls = JsoupUtils.selectSAttr(page, "h2.product-name>a", "href");
                List<String> names = JsoupUtils.selectTexts(page, "h2.product-name>a");
                int cnt = 0;
                for (String finalUrl: finalUrls) {
                    System.out.println(finalUrl);
                    Thread.sleep(500);

                    TGGoods good = getAllAttributes(finalUrl, cat_id[i], names.get(cnt));
                    if(good.getColor().size()==0) continue;
                    //MysqlUtils.addBrandCate(BRAND_ID, cat_id[i]);
                    //MysqlUtils.addTggoods(good, good.getGoodsSn(), BRAND_ID, pictures.get(cnt));
                    cnt++;
                    System.out.println(count++);
                }
            }
        }
    }

    private static TGGoods getAllAttributes(String finalUrl, int cat_Id, String name) throws Exception {
        WebClient wc = new WebClient();

        wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
        wc.getOptions().setCssEnabled(false); //禁用css支持
        wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
        wc.getOptions().setThrowExceptionOnFailingStatusCode(false);

        wc.getOptions().setTimeout(10000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待

        HtmlPage page = wc.getPage(finalUrl);
        String pageXml = page.asXml(); //以xml的形式获取响应文本

        String content = pageXml;
        //System.out.println(content);
        TGGoods good = new TGGoods();
        //price
        double nprice, oprice;

        String old = JsoupUtils.selectS(content, "div.product-box>div.price-box>span");
        old = old.substring(1);
        oprice = Double.valueOf(old);
        System.out.println(oprice);
        good.setoPrice(oprice);
        //System.out.println(old);
        //name
        System.out.println(name);
        //description
        String des=JsoupUtils.selectS(content, "div.std>p");
        System.out.println(des);
        //link
        String gUrl = finalUrl;
        //time
        Date date = new Timestamp(System.currentTimeMillis());
        System.out.println(date);
        //color
        List<String> colorList = JsoupUtils.selectSAttr(content, "div.color>dd>a", "title");
        System.out.println(colorList);
        //pictures
        List<String> pic = JsoupUtils.selectSAttr(content, "div.product-image>div>a>img.image", "src");
        System.out.println(pic);
        //no.
        String sn = JsoupUtils.selectS(content, "p.availability");
        sn = sn.substring(3);
        System.out.println(sn);
        good.setAddTime(date);
        good.setBrandId(BRAND_ID);
        good.setGoodsSn(sn);
        good.setColor(colorList);
        good.setGoodsName(name);
        good.setGoodsDesc(des);
        good.setgUrl(gUrl);
        good.setPic(pic);
        good.setCatId(cat_Id);

        return good;
    }
}
