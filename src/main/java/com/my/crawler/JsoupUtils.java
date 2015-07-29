package com.my.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cao on 2015/7/4.
 */
public class JsoupUtils {
	
    public static List<String> getAttrsByClass(String content, String className, String attr) {/*根据类名取多个相同类名的块的某个属性的值*/

        List<String> result = new ArrayList<String>();
        Document doc = Jsoup.parse(content, "UTF-8");//解析html文档
        Elements elements = doc.getElementsByClass(className);
        for (Element link : elements) {
            String linkHref = link.attr(attr);
            result.add(linkHref);
        }

        return result;
    }
    
    public static String getAttrByClass(String content, String className, String attr) {/*根据类名取该块的某个属性的值*/

        
        Document doc = Jsoup.parse(content, "UTF-8");//解析html文档
        Elements elements = doc.getElementsByClass(className);
        
        String result = elements.attr(attr);
                   
        return result;
    }

    public static List<String> getTextsByClass(String content, String className) {/*根据类名取多个相同类名的块的文本值*/

        List<String> result = new ArrayList<String>();
        Document doc = Jsoup.parse(content, "UTF-8");//解析html文档
        Elements elements = doc.getElementsByClass(className);
        for (Element link : elements) {
            String text = link.text();
            result.add(text);
        }

        return result;
    }
    
    public static List<String> selectTexts(String content, String s) {/*根据类名取多个相同类名的块的文本值*/

        List<String> result = new ArrayList<String>();
        Document doc = Jsoup.parse(content, "UTF-8");//解析html文档
        Elements elements = doc.select(s);
        for (Element link : elements) {
            String text = link.text();
            result.add(text);
        }

        return result;
    }
    
    public static String getOneTextByClass(String content, String className) {/*根据类名取内容*/

        Document doc = Jsoup.parse(content, "UTF-8");//解析html文档
        Elements elements = doc.getElementsByClass(className);

        for (Element link : elements) {
            String text = link.text();
            return text;
        }

        return "";
    }
    
    public static String getOneTextByTag(String content, String TagName) {/*根据标签名取内容*/

        Document doc = Jsoup.parse(content, "UTF-8");//解析html文档
        Elements elements = doc.getElementsByTag(TagName);

        for (Element link : elements) {
            String text = link.text();
            return text;
        }

        return "";
    }
    

    public static String selectS(String content, String s) {/*选择器s下的文本信息*/

        Document doc = Jsoup.parse(content, "UTF-8");//解析html文档
        Elements elements = doc.select(s);

        for (Element element : elements) {
            return element.text();
        }

        return null;
    }
    
    public static List<String> selectSAttr(String content, String s, String attr) {/*选择器s下的多个相同块的某属性的值*/

    	List<String> result = new ArrayList<String>();
        Document doc = Jsoup.parse(content, "UTF-8");//解析html文档
        Elements elements = doc.select(s);

        for (Element link : elements) {
            String linkHref = link.attr(attr);
            result.add(linkHref);
        }

        return result;
    }
    
}
