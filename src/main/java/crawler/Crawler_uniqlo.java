package crawler;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.my.crawler.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/7/27.
 */
public class Crawler_uniqlo {
    private static final int BRAND_ID = 16;
    public static void main(String[] args) throws Exception {
        System.out.println("==start u==");
        int[] cat_id = {
                0, 0, 0, 0, 0,
                34,
                51, 53,
                31, 35, 31,
                38,
                32,
                56, 58,
                42, 43, 43, 41,
                68, 66, 67, 68, 69, 66, 68,
                68, 43, 74,
                86, 90,
                0
                };
        String html_uniqlo_women = HTTPUtils.getByURL("http://www.uniqlo.cn/#!women");
        //System.out.println(html_uniqlo_women);
        System.out.println(cat_id.length);
        List<String> pageUrl = JsoupUtils.selectSAttr(html_uniqlo_women, "div#list_women>ul.sub>li>a", "href");
        System.out.println(pageUrl);
        int cnt = 0;
        int count = 0;
        System.out.println(pageUrl.size());
        for (String page:pageUrl) {
            String html_page = HTTPUtils.getByURL(page);
            if (cnt <= 4) {
                cnt++;
                continue;
            }
            if (cnt >= 31) continue;
            System.out.println(cnt+": "+page);
            List<String> urls = JsoupUtils.getAttrsByClass(html_page, "permalink", "href");
            List<String> smallImg = JsoupUtils.selectSAttr(html_page, "div.pic>a>img", "data-ks-lazyload");
            System.out.println(smallImg);
            int i = 0;
            for(String url:urls) {
                System.out.println(url);
                Thread.sleep(500);
                String finalUrl = url;
                TGGoods good = getAllAttributes(finalUrl,cat_id[cnt]);
                if(good.getColor().size()==0) continue;
                //MysqlUtils.addBrandCate(BRAND_ID, cat_id[cnt]);
                //MysqlUtils.addTggoods(good, good.getGoodsSn(), BRAND_ID, smallImg.get(i++));
                System.out.println(count++);
            }
            cnt++;
        }
    }

    private static TGGoods getAllAttributes(String finalUrl, int cat_Id) throws Exception {
        WebClient wc = new WebClient();

        wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
        wc.getOptions().setCssEnabled(false); //禁用css支持
        wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
        wc.getOptions().setThrowExceptionOnFailingStatusCode(false);

        wc.getOptions().setTimeout(10000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待

        HtmlPage page = wc.getPage("http://www.uniqlo.cn/item.htm?spm=0.0.0.0.bfEgZH&id=520313132016");
        String pageXml = page.asXml(); //以xml的形式获取响应文本

        String content = pageXml;


        //price
        String now = JsoupUtils.selectS(content, "#J_StrPrice");
        double price = Double.valueOf(now);
        System.out.println(now);
        //name
        String name = JsoupUtils.getOneTextByClass(content, "detail-hd");
        System.out.println(name);
        //description
        List<String> texts = JsoupUtils.selectTexts(pageXml, "div.content#J_DivItemDesc>table>tbody>tr>td>p:lt(5):gt(2)");
        String des = new String();
        for (String text: texts) {
            des += text;
        }
        System.out.println(des);
        //link
        String gUrl = finalUrl;
        //time
        Date date = new Timestamp(System.currentTimeMillis());
        System.out.println(date);
        //color
        List<String> temp = JsoupUtils.selectTexts(content, "[data-value^=1627207]");
        List<String> colorList = new ArrayList<String>();
        for (String color: temp) {
            color = color.substring(3);
            //System.out.println(color);
            colorList.add(color);
        }
        System.out.println(colorList);
        //��ͼ
        List<String> pic = JsoupUtils.selectSAttr(content, "[href*=taobaocdn]", "href");
        System.out.println(pic);
        //��Ʒ���
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
        good.setGoodsDesc(des);

        return good;
    }
}
