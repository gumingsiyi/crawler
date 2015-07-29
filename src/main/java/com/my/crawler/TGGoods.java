package com.my.crawler;

import java.util.Date;
import java.util.List;

public class TGGoods {
	private Integer goodsId;
	private String goodsName;//、、、、
	private String goodsDesc;//、、、、
	private String goodsSn;//、、、、//商品编号、
	private Integer catId;//商品分类
	private Integer brandId;
	private String gUrl;//、、、、
	private double oPrice=0.00;//、、、、//原价
	private double nPrice=0.00;//、、、、//现价
	private Date addTime;//、、、、
	List<String> pic;//、、、、
	List<String> color;//、、、、
	private String imgSmall;
	
	private String goodsBrief;//暂无用处
	private String keyword;//暂无用处
	private Integer clickCount = 0;
	private Integer eavCount=0;
	private Integer dreamCount=0;
	private Integer shareCount=0;		
	private Integer isDelete = 0;
	private Integer isBest = 0;
	private Integer isNew = 0;
	
	
	
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsDesc() {
		return goodsDesc;
	}
	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}
	public String getGoodsSn() {
		return goodsSn;
	}
	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}
	public Integer getCatId() {
		return catId;
	}
	public void setCatId(Integer catId) {
		this.catId = catId;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public String getgUrl() {
		return gUrl;
	}
	public void setgUrl(String gUrl) {
		this.gUrl = gUrl;
	}
	public double getoPrice() {
		return oPrice;
	}
	public void setoPrice(double oPrice) {
		this.oPrice = oPrice;
	}
	public double getnPrice() {
		return nPrice;
	}
	public void setnPrice(double nPrice) {
		this.nPrice = nPrice;
	}
	public String getImgSmall() {
		return imgSmall;
	}
	public void setImgSmall(String imgSmall) {
		this.imgSmall = imgSmall;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public List<String> getPic() {
		return pic;
	}
	public void setPic(List<String> pic) {
		this.pic = pic;
	}
	public List<String> getColor() {
		return color;
	}
	public void setColor(List<String> color) {
		this.color = color;
	}
	
	
	//
	
	
	public String getGoodsBrief() {
		return goodsBrief;
	}
	public void setGoodsBrief(String goodsBrief) {
		this.goodsBrief = goodsBrief;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getClickCount() {
		return clickCount;
	}
	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}
	public Integer getEavCount() {
		return eavCount;
	}
	public void setEavCount(Integer eavCount) {
		this.eavCount = eavCount;
	}
	public Integer getDreamCount() {
		return dreamCount;
	}
	public void setDreamCount(Integer dreamCount) {
		this.dreamCount = dreamCount;
	}
	public Integer getShareCount() {
		return shareCount;
	}
	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public Integer getIsBest() {
		return isBest;
	}
	public void setIsBest(Integer isBest) {
		this.isBest = isBest;
	}
	public Integer getIsNew() {
		return isNew;
	}
	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}

}
