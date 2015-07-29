package com.my.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
//import org.junit.internal.matchers.SubstringMatcher;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;
import java.math.BigDecimal;

public class Crawler_zara {

//    private static final String CRAWLER_URL         = "http://www.hunterboots.com/";
    private static final String PRODUCT_CODE_ZARA = "";
    private static final String PRODUCT_PRICE_ZARA = "";
    private static final int ZARA_BRAND_ID = 15;
    

    public static void main(String[] args) throws Exception {

    		System.out.println("-----start zara-----");
    		
    		String html_zara_women = HTTPUtils.getByURL("http://www.zara.cn/cn/zh/%E6%89%93%E6%8A%98/%E5%A5%B3%E5%A3%AB-c436002.html");
    		List<String> urls_zara_women = JsoupUtils.selectSAttr(html_zara_women,"li#menuItemData_436002 ul>li>a","href");
    		
    		int[] cat_id = {56,51,39,95,41,42,52,32,31,96,83,86,94};
    		
    		for(int i=0;i<urls_zara_women.size();i++){    		   		
//    			Map<String, Integer> mapIds=new HashMap<String, Integer>();
//    			mapIds.put(urls_zara_women.get(i), listId.get(i));
    			String html = HTTPUtils.getByURL(urls_zara_women.get(i));
    			List<String> urls = JsoupUtils.getAttrsByClass(html, "gaProductDetailsLink", "href");
    			MysqlUtils.addBrandCate(ZARA_BRAND_ID, cat_id[i]);
         
    			for (String finalUrl : urls) {          	
    				Thread.sleep(100);
//    				Product product = getAllAttributes(finalUrl); 
    				TGGoods goods = getZaraAttributes(finalUrl, cat_id[i]);
    				//MysqlUtils.addTggoods(goods, goods.getGoodsSn(), goods.getBrandId());
    			}
    		}
            	
            System.out.println("-----end zara-----");
    }

    private static Product getAllAttributes(String finalUrl) throws Exception {
        String content = HTTPUtils.getByURL(finalUrl);

        String title = JsoupUtils.getOneTextByTag(content, "h1");
        String titleN = title.replace("\"", "\\\"");
        

        String was = JsoupUtils.getAttrByClass(content, "line-through", "data-price");
        String now = JsoupUtils.getAttrByClass(content, "sale", "data-price");
        
//        String descp = JsoupUtils.getOneTextByClass(content, "description");
        String descp = finalUrl;
        
        String productCode = JsoupUtils.getOneTextByClass(content, "reference");
        if (productCode.startsWith(PRODUCT_CODE_ZARA)) {
            productCode = productCode.substring(PRODUCT_CODE_ZARA.length());
        }
        

        List<String> pic = JsoupUtils.getAttrsByClass(content, "disabled-anchor", "href");

        Product product = new Product();
        product.setDescp(descp);
        product.setNow(now);
        product.setPic(pic);
        product.setProductCode(productCode);
        product.setTitle(titleN);
        product.setWas(was);
        return product;
    }
    
    
    //zara
    
	private static TGGoods getZaraAttributes(String finalUrl, int catId) throws Exception {
        String content = HTTPUtils.getByURL(finalUrl);

        String goodsName = JsoupUtils.getOneTextByTag(content, "h1");
        goodsName = goodsName.replace("\"", "\\\"");
        

        String OPrice = JsoupUtils.getAttrByClass(content, "line-through", "data-price");
        OPrice = OPrice.substring(PRODUCT_PRICE_ZARA.length());
        double oPrice = Double.parseDouble(OPrice);      
        
        String NPrice = JsoupUtils.getAttrByClass(content, "sale", "data-price");
        NPrice = NPrice.substring(PRODUCT_PRICE_ZARA.length());
        double nPrice = Double.parseDouble(NPrice);
       
        String gUrl = finalUrl;
        
        String goodsDesc = JsoupUtils.getOneTextByClass(content, "description");
               
        String goodsSn = JsoupUtils.getOneTextByClass(content, "reference");
        if (goodsSn.startsWith(PRODUCT_CODE_ZARA)) {
        	goodsSn = goodsSn.substring(PRODUCT_CODE_ZARA.length());
        }
        
        List<String> pic = JsoupUtils.getAttrsByClass(content, "disabled-anchor", "href");
        
        List<String> color = JsoupUtils.selectTexts(content,"div.imgCont span");
        

        TGGoods tggoods = new TGGoods();
        
        tggoods.setGoodsName(goodsName);
        tggoods.setoPrice(oPrice);
        tggoods.setnPrice(nPrice);
        tggoods.setgUrl(gUrl);
        tggoods.setGoodsDesc(goodsDesc);
        tggoods.setGoodsSn(goodsSn);
        tggoods.setPic(pic); 
        tggoods.setColor(color);
        tggoods.setAddTime(new Timestamp(System.currentTimeMillis()));
        tggoods.setBrandId(ZARA_BRAND_ID);
        tggoods.setCatId(catId);
                
        return tggoods;
    }
    
}
