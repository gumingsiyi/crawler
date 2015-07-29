package com.my.crawler;

import java.util.List;

/**
 * Created by Cao on 2015/7/4.
 */
public class Product {
    String title;
    String was;
    String now;
    String descp;
    String productCode;
    List<String> pic;

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWas() {
        return was;
    }

    public void setWas(String was) {
        this.was = was;
    }

    public String getDescp() {
        return descp;
    }

    public void setDescp(String descp) {
        this.descp = descp;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public List<String> getPic() {
        return pic;
    }

    public void setPic(List<String> pic) {
        this.pic = pic;
    }
}
