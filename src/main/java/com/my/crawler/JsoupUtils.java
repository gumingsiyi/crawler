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
	
    public static List<String> getAttrsByClass(String content, String className, String attr) {/*��������ȡ�����ͬ�����Ŀ��ĳ�����Ե�ֵ*/

        List<String> result = new ArrayList<String>();
        Document doc = Jsoup.parse(content, "UTF-8");//����html�ĵ�
        Elements elements = doc.getElementsByClass(className);
        for (Element link : elements) {
            String linkHref = link.attr(attr);
            result.add(linkHref);
        }

        return result;
    }
    
    public static String getAttrByClass(String content, String className, String attr) {/*��������ȡ�ÿ��ĳ�����Ե�ֵ*/

        
        Document doc = Jsoup.parse(content, "UTF-8");//����html�ĵ�
        Elements elements = doc.getElementsByClass(className);
        
        String result = elements.attr(attr);
                   
        return result;
    }

    public static List<String> getTextsByClass(String content, String className) {/*��������ȡ�����ͬ�����Ŀ���ı�ֵ*/

        List<String> result = new ArrayList<String>();
        Document doc = Jsoup.parse(content, "UTF-8");//����html�ĵ�
        Elements elements = doc.getElementsByClass(className);
        for (Element link : elements) {
            String text = link.text();
            result.add(text);
        }

        return result;
    }
    
    public static List<String> selectTexts(String content, String s) {/*��������ȡ�����ͬ�����Ŀ���ı�ֵ*/

        List<String> result = new ArrayList<String>();
        Document doc = Jsoup.parse(content, "UTF-8");//����html�ĵ�
        Elements elements = doc.select(s);
        for (Element link : elements) {
            String text = link.text();
            result.add(text);
        }

        return result;
    }
    
    public static String getOneTextByClass(String content, String className) {/*��������ȡ����*/

        Document doc = Jsoup.parse(content, "UTF-8");//����html�ĵ�
        Elements elements = doc.getElementsByClass(className);

        for (Element link : elements) {
            String text = link.text();
            return text;
        }

        return "";
    }
    
    public static String getOneTextByTag(String content, String TagName) {/*���ݱ�ǩ��ȡ����*/

        Document doc = Jsoup.parse(content, "UTF-8");//����html�ĵ�
        Elements elements = doc.getElementsByTag(TagName);

        for (Element link : elements) {
            String text = link.text();
            return text;
        }

        return "";
    }
    

    public static String selectS(String content, String s) {/*ѡ����s�µ��ı���Ϣ*/

        Document doc = Jsoup.parse(content, "UTF-8");//����html�ĵ�
        Elements elements = doc.select(s);

        for (Element element : elements) {
            return element.text();
        }

        return null;
    }
    
    public static List<String> selectSAttr(String content, String s, String attr) {/*ѡ����s�µĶ����ͬ���ĳ���Ե�ֵ*/

    	List<String> result = new ArrayList<String>();
        Document doc = Jsoup.parse(content, "UTF-8");//����html�ĵ�
        Elements elements = doc.select(s);

        for (Element link : elements) {
            String linkHref = link.attr(attr);
            result.add(linkHref);
        }

        return result;
    }
    
}
