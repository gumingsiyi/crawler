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
    private static final int BRAND_ID = 19;
    private static final String WEB_URL = "http://www.converse.com.cn";
    public static void main(String[] args) throws Exception {
        System.out.println("==start converse==");
        int[] cat_id = {
                71,
                51,
                34,
                31,
                57,
                40,
                38,
                38,
                78,
                88,
                89,
                69,
                89
        };
        String[] websites = {
                "http://www.converse.com.cn/women-sneakers/category.htm?iid=tpnvfc06012015&attributeParams=&propertyCode=&sort=gender_asc&rowsNum=1000&isPaging=true",
                "http://www.converse.com.cn/women-clothing/category.htm?iid=tpnvfw06012015&attributeParams=&propertyCode=skirt&sort=gender_asc&rowsNum=1000&isPaging=true",
                "http://www.converse.com.cn/women-clothing/category.htm?iid=tpnvfw06012015&attributeParams=&propertyCode=shirt&isPaging=true&rowsNum=1000",
                "http://www.converse.com.cn/women-clothing/category.htm?iid=tpnvfw06012015&attributeParams=&propertyCode=tee&isPaging=true&rowsNum=1000",
                "http://www.converse.com.cn/women-clothing/category.htm?iid=tpnvfw06012015&attributeParams=&propertyCode=jacket&isPaging=true&rowsNum=1000",
                "http://www.converse.com.cn/women-clothing/category.htm?iid=tpnvfw06012015&attributeParams=&propertyCode=vest&isPaging=true&rowsNum=1000",
                "http://www.converse.com.cn/women-clothing/category.htm?iid=tpnvfw06012015&attributeParams=&propertyCode=thick_knitwear&isPaging=true&rowsNum=1000",
                "http://www.converse.com.cn/women-clothing/category.htm?iid=tpnvfw06012015&attributeParams=&propertyCode=light_knitwear&isPaging=true&rowsNum=1000",
                "http://www.converse.com.cn/women-accessories/category.htm?iid=tpnvfa06012015&attributeParams=&propertyCode=bag&isPaging=true&rowsNum=1000",
                "http://www.converse.com.cn/women-accessories/category.htm?iid=tpnvfa06012015&attributeParams=&propertyCode=cap&isPaging=true&rowsNum=1000",
                "http://www.converse.com.cn/women-accessories/category.htm?iid=tpnvfa06012015&attributeParams=&propertyCode=keychain&isPaging=true&rowsNum=1000",
                "http://www.converse.com.cn/women-accessories/category.htm?iid=tpnvfa06012015&attributeParams=&propertyCode=sock&isPaging=true&rowsNum=1000",
                "http://www.converse.com.cn/women-accessories/category.htm?iid=tpnvfa06012015&attributeParams=&propertyCode=lace&isPaging=true&rowsNum=1000",
        };
        //System.out.println(cat_id.length);
        //System.out.println(websites.length);
        int count=0;
        for (int i = 0; i < websites.length; i++) {
            String website = HTTPUtils.getByURL(websites[i]);
            List<String> finalUrls = JsoupUtils.selectSAttr(website, "dl[skuStyle]>dt>a", "href");
            List<String> pictures = JsoupUtils.selectSAttr(website, "dl[skuStyle]>dt>a>img", "lazy_src");
            System.out.println(pictures);
            System.out.println(finalUrls.size());
            System.out.println(pictures.size());

            for (int j = 0; j < finalUrls.size(); j++) {
                String finalUrl = WEB_URL+finalUrls.get(j);
                System.out.println(finalUrl);
                System.out.println(++count);
                //MysqlUtils.addBrandCate(BRAND_ID, cat_id[i]);
                TGGoods good = getAllAttributes(finalUrl, cat_id[i]);
                //MysqlUtils.addTggoods(good, good.getGoodsSn(), BRAND_ID, pictures.get(j));
            }
        }
    }

    private static TGGoods getAllAttributes(String finalUrl, int cat_Id) throws Exception {
        String content = HTTPUtils.getByURL(finalUrl);
        TGGoods good = new TGGoods();
        //price
        List<String> old = JsoupUtils.selectSAttr(content, "#skuPrice", "value");
        double price = Double.valueOf(old.get(0));
        good.setoPrice(price);
        System.out.println(price);
        //name
        List<String> name = JsoupUtils.selectSAttr(content, "a.jqzoom[alt]", "alt");
        System.out.println(name.get(0));
        good.setGoodsName(name.get(0));
        //描述
        String des = JsoupUtils.getOneTextByClass(content, "product-description");
        System.out.println(des);
        good.setGoodsDesc(des);
        //链接
        String gUrl = finalUrl;
        //添加时间
        Date date = new Timestamp(System.currentTimeMillis());
        System.out.println(date);
        //颜色
        List<String> colorList = JsoupUtils.selectSAttr(content, "div.other-product-list>a>img", "title");
        System.out.println(colorList);
        //大图
        List<String> picS = JsoupUtils.selectSAttr(content, "div.product-thumb-list>a", "data-img");
        System.out.println(picS);
        List<String> picH = new ArrayList<String>();
        for(String pic: picS) {
            String[] tpic = pic.split("_");
            //System.out.println(tpic.length);
            pic = tpic[0]+"_"+tpic[1].charAt(0)+"H_"+tpic[2];
            picH.add(pic);
            System.out.println(pic);
        }
        //商品编号
        List<String> sn = JsoupUtils.selectSAttr(content, "#skuCode", "value");

        System.out.println(sn.get(0));
        good.setAddTime(date);
        good.setBrandId(BRAND_ID);
        good.setGoodsSn(sn.get(0));
        good.setgUrl(gUrl);
        good.setPic(picH);
        good.setCatId(cat_Id);

        return good;
    }
}
