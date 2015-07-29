package com.my.crawler;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.net.URL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;



/**
 * Created by Cao on 2015/7/4.
 */
@SuppressWarnings("restriction")
public class MysqlUtils {

    public static void addProducts(List<Product> products) throws Exception {
        // 1.注册驱动
        Class.forName("com.mysql.jdbc.Driver");

        // 2.建立连接 url格式 - JDBC:子协议:子名称//主机名:端口/数据库名？属性名=属性值&…
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crawler", "root", "");

        // 3.创建语句
        Statement st = conn.createStatement();

        for (Product product : products) {
            List<String> pics = product.getPic();
            if (pics.size() < 3) {
                continue;
            }
            st.executeUpdate("INSERT INTO `crawler`.`product`(`title`,`was`,`now`,`descp`,`product_code`,`pic1`,`pic2`,`pic3`)\n" +
                    "VALUES ('"+ product.getTitle() +"','"+product.getWas()+"','"+product.getNow()+"','"+product.getDescp()+"','"+product.getProductCode()+
                    "','"+pics.get(0)+"','"+pics.get(1)+"','"+pics.get(2)+"');");
        }

        // // 4.执行语句
        // ResultSet rs = st.executeQuery("select * from user");
        //
        // // 5.处理结果
        // while (rs.next()) {
        // System.out.println(rs.getObject(1) + "\t" + rs.getObject(2) + "\t" + rs.getObject(3) + "\t" +
        // rs.getObject(4));
        // }
        //
        // // 6.释放资源
        // rs.close();
        // st.close();
        // conn.close();
    }

    public static void addProduct(Product product) throws Exception {

        // 1.注册驱动
        Class.forName("com.mysql.jdbc.Driver");

        // 2.建立连接 url格式 - JDBC:子协议:子名称//主机名:端口/数据库名？属性名=属性值&…
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crawler", "root", "");

        // 3.创建语句
        Statement st = conn.createStatement();

            List<String> pics = product.getPic();
            if (pics.size() < 3) {
                return;
            }
        String sql = "INSERT INTO `crawler`.`product`(`title`,`was`,`now`,`descp`,`product_code`,`pic1`,`pic2`,`pic3`)\n" +
                "VALUES (\""+ product.getTitle() +"\",\""+product.getWas()+"\",\""+product.getNow()+"\",\""+product.getDescp()+"\",\""+product.getProductCode()+
                "\",\""+pics.get(0)+"\",\""+pics.get(1)+"\",\""+pics.get(2)+"\");";

        System.out.println(sql);

        st.executeUpdate(sql);

    }
    
    @SuppressWarnings("unused")
	public static void addProduct(Product product, String keyWord) throws Exception {

        // 1.注册驱动
        Class.forName("com.mysql.jdbc.Driver");

        // 2.建立连接 url格式 - JDBC:子协议:子名称//主机名:端口/数据库名？属性名=属性值&…
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crawler", "root", "");

        // 3.创建语句
        Statement st = conn.createStatement();
        
        List<String> pics = product.getPic();
        if (pics.size() < 3) {
            return;
        }
        
        String sql_keyWord = "select * from product where product_code ='"+ keyWord +"'";
        ResultSet rs = st.executeQuery(sql_keyWord);
        if(rs.next()){//已有这条数据
        	if(rs.getString("title").equals(product.getTitle())&&rs.getString("was").equals(product.getWas())&&
        			rs.getString("now").equals(product.getNow())&&rs.getString("descp").equals(product.getDescp())&&
        			rs.getString("pic1").equals(pics.get(0))&&rs.getString("pic2").equals(pics.get(1))&&
        			rs.getString("pic3").equals(pics.get(2))){//字段数据都没变化，就不作处理
       	
				System.out.println("数据无变化");
				return;
        	}
        	else{//只要字段数据有变化，就更新
        		
        		String sql_update = "update product set title='"+ product.getTitle() +"',was='"+ product.getWas() +"',now='"+ 
        							product.getNow() +"',descp='"+ product.getDescp() +"',pic1='"+ pics.get(0) +"',pic2='"+ pics.get(1) +
        							"',pic3='"+ pics.get(2) +"' where product_code ='"+ keyWord +"'";
        		
        		System.out.println(sql_update);
        		
        		st.executeUpdate(sql_update);
        	}
        }
        else{//还没有这条数据，就插入数据
        	
            String sql = "INSERT INTO `crawler`.`product`(`title`,`was`,`now`,`descp`,`product_code`,`pic1`,`pic2`,`pic3`)\n" +
                "VALUES (\""+ product.getTitle() +"\",\""+product.getWas()+"\",\""+product.getNow()+"\",\""+product.getDescp()+"\",\""+product.getProductCode()+
                "\",\""+pics.get(0)+"\",\""+pics.get(1)+"\",\""+pics.get(2)+"\");";

            System.out.println(sql);

            st.executeUpdate(sql);
        
        }

    }
    
//    //zara

    public static void addTggoods(TGGoods goods, String goodsSn, int brandId, String smallPic) throws Exception {

        // 1.注册驱动
        Class.forName("com.mysql.jdbc.Driver");

        // 2.建立连接 url格式 - JDBC:子协议:子名称//主机名:端口/数据库名？属性名=属性值&…
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/muyin_db", "root", "root");
//        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/muyin_db", "root", "");//本地数据库

        // 3.创建语句
        Statement st = conn.createStatement();

        List<String> colors = goods.getColor();

        List<String> Pics = goods.getPic();
        List<String> pics = new ArrayList<String>();
		for(int j=0;j<Pics.size();j++){
			pics.add(Pics.get(j).replace("w/1920/","w/1024/"));
		}


        String sql_attrColorId = "select * from t_g_attribute where cat_id ='"+ goods.getCatId() +"' and attr_name='颜色'";
        ResultSet rs_attrColorId = st.executeQuery(sql_attrColorId);
        int attr_id=0;
        if(rs_attrColorId.next()){
        	attr_id = rs_attrColorId.getInt("attr_id");
        }
//        System.out.println(attr_id);//测试
        String sql_rangeValue = "select * from t_g_attr_range where attr_id='"+ attr_id +"'";
        ResultSet rs_rangeValue = st.executeQuery(sql_rangeValue);
        List<String> rangeValue = new ArrayList<String>();
        while(rs_rangeValue.next()){
        	rangeValue.add(rs_rangeValue.getString("rangeValue"));
        }
        if(rangeValue.size()>0){//已有属性值，做判断
        	for(int i=0;i<colors.size();i++){
        		Boolean flag = true;
        		for(int j=0;j<rangeValue.size();j++){
//        			System.out.println(rs_rangeValue.getString("rangeValue")+"lallalalala");//测试
        			if(rangeValue.get(j).equals(colors.get(i))){
        				flag = false;
        			}
        		}//
        		if(flag){//判断某个属性值还没存入，就插入
        			String sql_tgattrrange = "INSERT INTO `muyin_db`.`t_g_attr_range`(`attr_id`,`rangeValue`)\n" +
        	                "VALUES (\""+ attr_id +"\",\""+colors.get(i)+"\");";
        			System.out.println(sql_tgattrrange);
        			st.executeUpdate(sql_tgattrrange);
        		}
        		else{//某个属性值已经存入，不作处理
        			System.out.println("扩展属性值已存在");//测试
        		}

        	}
        }
        else{//还没有属性值，就直接插入
        	for(int j=0;j<colors.size();j++){
        		String sql_tgattrrange = "INSERT INTO `muyin_db`.`t_g_attr_range`(`attr_id`,`rangeValue`)\n" +
    	                "VALUES (\""+ attr_id +"\",\""+colors.get(j)+"\");";
        		System.out.println(sql_tgattrrange);
//        		System.out.println("66666666");//测试
        		st.executeUpdate(sql_tgattrrange);
        	}
        }



        String sql_keyWord = "select * from t_g_goods where goods_sn ='"+ goodsSn +"' and brand_id ='"+ brandId +"'";
        ResultSet rs = st.executeQuery(sql_keyWord);
        if(rs.next()){//已有这条数据
        	if(rs.getString("goods_name").equals(goods.getGoodsName())&&rs.getDouble("o_price")==goods.getoPrice()&&
        			rs.getDouble("n_price")==goods.getnPrice()&&rs.getInt("cat_id")==goods.getCatId()){//字段数据都没变化，就不作处理

				System.out.println("商品数据无变化");
//				return;
        	}
        	else{//只要字段数据有变化，就更新

        		String sql_update = "update t_g_goods set goods_name='"+ goods.getGoodsName() +"',o_price='"+ goods.getoPrice() +"',n_price='"+
        				goods.getnPrice() +"',cat_id='"+ goods.getCatId() +"' where goods_sn ='"+ goodsSn +"' and brand_id ='"+ brandId +"'";

        		System.out.println(sql_update);

        		st.executeUpdate(sql_update);
        	}
        }
        else{//还没有这条数据，就插入数据

            String sql = "INSERT INTO `muyin_db`.`t_g_goods`(`goods_name`,`o_price`,`n_price`,`goods_desc`,`goods_sn`,`g_url`,`brand_id`,`add_time`,`cat_id`)\n" +
                "VALUES (\""+ goods.getGoodsName() +"\",\""+goods.getoPrice()+"\",\""+goods.getnPrice()+"\",\""+goods.getGoodsDesc()+"\",\""+goods.getGoodsSn()+
                "\",\""+goods.getgUrl()+"\",\""+goods.getBrandId()+"\",\""+goods.getAddTime()+"\",\""+goods.getCatId()+"\");";

            System.out.println(sql);

            st.executeUpdate(sql);

        }



        String sql_goodsID = "select * from t_g_goods where goods_sn ='"+ goodsSn +"' and brand_id ='"+ brandId +"'";
        ResultSet rs_goodsID = st.executeQuery(sql_goodsID);
        if(rs_goodsID.next()){
        	int goodsId = rs_goodsID.getInt("goods_id");
        	//商品扩展属性存入
        	String sql_CatRangeId = "select * from t_g_attr_range where attr_id='"+ attr_id +"' and rangeValue='"+ colors.get(0) +"'";
            ResultSet rs_CatRangeId = st.executeQuery(sql_CatRangeId);
        	if(rs_CatRangeId.next()){
            	int cat_range_id = rs_CatRangeId.getInt("cat_range_id");
            	String sql_goodsId = "select * from t_g_goods_attr where goods_id='"+ goodsId +"'";
                ResultSet rs_goodsId = st.executeQuery(sql_goodsId);
            	if(rs_goodsId.next()){
            		//已存在该商品就不插入数据
            	}
            	else{
            		String sql_goodsAttr = "INSERT INTO `muyin_db`.`t_g_goods_attr`(`goods_id`,`attr_range_id`)\n" +
            				"VALUES (\""+ goodsId +"\",\""+ cat_range_id +"\");";
            		System.out.println(sql_goodsAttr);
            		st.executeUpdate(sql_goodsAttr);
            	}
            }
        	//图片存入
        	String sql_goods_img = "select * from t_g_goods_img where goods_id='"+ goodsId +"'";
        	ResultSet rs_goods_img = st.executeQuery(sql_goods_img);
        	if(rs_goods_img.next()){
        		//该商品图片已存在，不插入
        		System.out.println("图片已存在");
        		//-----------------------------------------------------------//
//        		System.out.println(pics.get(0));//测试
        		List<String> imgUrl = downloadPic(pics, 1);//大图
        		String smallImgUrl = downloadSmallPic(smallPic, 1);//小图一张
        		//删除原来该商品所有的图片路径
        		String sql_del_goodsimg = "delete from t_g_goods_img where goods_id='"+ goodsId +"'";
        		System.out.println(sql_del_goodsimg);
        		st.executeUpdate(sql_del_goodsimg);
        		//图片存入
        		for(int i=0;i<imgUrl.size();i++ ){
        			String sql_goodsImg = "INSERT INTO `muyin_db`.`t_g_goods_img`(`goods_id`,`value`)\n" +
            				"VALUES (\""+ goodsId +"\",\""+ "/hia/goodsimg/" + imgUrl.get(i) +"\");";
        			System.out.println(sql_goodsImg);
            		st.executeUpdate(sql_goodsImg);
        		}
        		String sql_updateGoodImg = "update t_g_goods set img_small='"+ "/hia/goodsimgSmall/" + smallImgUrl +"' where goods_id ='"+ goodsId +"'";
        		System.out.println(sql_updateGoodImg);
        		st.executeUpdate(sql_updateGoodImg);
        		//----------------------------------------------------------//
        	}else{
        		//图片下载
        		List<String> imgUrl = downloadPic(pics, 1);//大图
        		String smallImgUrl = downloadSmallPic(smallPic, 1);//小图一张
        		//图片存入
        		for(int i=0;i<imgUrl.size();i++ ){
        			String sql_goodsImg = "INSERT INTO `muyin_db`.`t_g_goods_img`(`goods_id`,`value`)\n" +
            				"VALUES (\""+ goodsId +"\",\""+ "/hia/goodsimg/" + imgUrl.get(i) +"\");";
        			System.out.println(sql_goodsImg);
            		st.executeUpdate(sql_goodsImg);
        		}
        		String sql_updateGoodImg = "update t_g_goods set img_small='"+ "/hia/goodsimgSmall/" + smallImgUrl +"' where goods_id ='"+ goodsId +"'";
        		System.out.println(sql_updateGoodImg);
        		st.executeUpdate(sql_updateGoodImg);
        	}
        }


        System.out.println("---------------------------------------------------------------------------");

    }
    
    
	public static void addBrandCate(int brandId, int catId) throws Exception {
		// 1.注册驱动
        Class.forName("com.mysql.jdbc.Driver");

        // 2.建立连接 url格式 - JDBC:子协议:子名称//主机名:端口/数据库名？属性名=属性值&…
//        Connection conn = DriverManager.getConnection("jdbc:mysql://121.40.63.10:3306/muyin_db", "root", "root");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/muyin_db", "root", "root");//本地数据库

        // 3.创建语句
        Statement st = conn.createStatement();
        
        List<Integer> cat_id = new ArrayList<Integer>(); 
        Boolean flag = true; 
        while(flag){
        	cat_id.add(catId);
        	String sql_pId = "select * from t_g_category where cat_id='"+ catId +"'";
    		ResultSet rs_pId = st.executeQuery(sql_pId);
    		if(rs_pId.next()){
    			catId = rs_pId.getInt("p_id");
    			if(catId==0){
    				flag = false;
    			}
    		}
    		else{
    			flag = false;
    		}
        }
        for(int i=0;i<cat_id.size();i++){
        	String sql_brandcateId = "select * from t_g_brand_cate where brand_id ='"+ brandId +"' and cate_id ='"+ cat_id.get(i) +"'";
        	ResultSet rs_brandcateId = st.executeQuery(sql_brandcateId);
        	if(rs_brandcateId.next()){
        		//已存在数据，不插入
        	}else{
        		String sql_brandCate = "INSERT INTO `muyin_db`.`t_g_brand_cate`(`brand_id`,`cate_id`)\n" +
        				"VALUES (\""+ brandId +"\",\""+ cat_id.get(i) +"\");";
            	System.out.println(sql_brandCate);       
        		st.executeUpdate(sql_brandCate);
        	}
        	
        }
	}
	
	//下载图片函数
	private static List<String> downloadPic(List<String> pics, int times) throws Exception{//大图
		System.out.println(pics.get(0));
		List<String> imgUrl = new ArrayList<String>();
        String dateStr = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        for(int i=0;i<pics.size();i++){
        	String dateStrUrl = dateStr + "_" + i + ".jpg";
        	imgUrl.add(dateStrUrl);
			URL url;
			if(pics.get(i).substring(0, 4).equals("http")) {
				url = new URL(pics.get(i));
			} else {
				url = new URL("http:" + pics.get(i));
			}
        	Image src = javax.imageio.ImageIO.read(url);
        	int wideth = src.getWidth(null);
        	int height = src.getHeight(null);
        	BufferedImage tag = new BufferedImage(wideth/times, height/times, BufferedImage.TYPE_INT_RGB);
        	tag.getGraphics().drawImage(src, 0, 0, wideth/times, height/times, null);
        	FileOutputStream out = new FileOutputStream("F:/goodsimg/"+ dateStrUrl);
        	JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        	encoder.encode(tag);
        	out.close();
        }
		return imgUrl;
	}
	
	private static String downloadSmallPic(String pics, int times) throws Exception{//小图		
    	String dateStr = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());       
    	String dateStrUrl = dateStr + "_0.jpg";
		URL url;
		if(pics.substring(0, 4).equals("http")) {
			url = new URL(pics);
		} else {
			url = new URL("http:" + pics);
		}
    	Image src = javax.imageio.ImageIO.read(url);
    	int wideth = src.getWidth(null);
    	int height = src.getHeight(null);
    	BufferedImage tag = new BufferedImage(wideth/times, height/times, BufferedImage.TYPE_INT_RGB);
    	tag.getGraphics().drawImage(src, 0, 0, wideth/times, height/times, null);
    	FileOutputStream out = new FileOutputStream("F:/goodsimgSmall/"+ dateStrUrl);
    	JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
    	encoder.encode(tag);
    	out.close();
		return dateStrUrl;
	}
	
	//zara
    /*public static void addTggoods(TGGoods goods, String goodsSn, int brandId, String smallPic) throws Exception {

        // 1.注册驱动
        Class.forName("com.mysql.jdbc.Driver");

        // 2.建立连接 url格式 - JDBC:子协议:子名称//主机名:端口/数据库名？属性名=属性值&…
//        Connection conn = DriverManager.getConnection("jdbc:mysql://121.40.63.10:3306/muyin_db", "root", "root");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/muyin_db", "root", "");//本地数据库

        // 3.创建语句
        Statement st = conn.createStatement();
        
        List<String> colors = goods.getColor();
        
        List<String> Pics = goods.getPic();
        List<String> pics = new ArrayList<String>();
		for(int j=0;j<Pics.size();j++){
			pics.add(Pics.get(j).replace("w/1920/","w/1024/"));
		}
        
        
        String sql_attrColorId = "select * from t_g_attribute where cat_id ='"+ goods.getCatId() +"' and attr_name='颜色'";
        ResultSet rs_attrColorId = st.executeQuery(sql_attrColorId);
        int attr_id=0;
        if(rs_attrColorId.next()){
        	attr_id = rs_attrColorId.getInt("attr_id");
        }
//        System.out.println(attr_id);//测试
        String sql_rangeValue = "select * from t_g_attr_range where attr_id='"+ attr_id +"'";
        ResultSet rs_rangeValue = st.executeQuery(sql_rangeValue);
        List<String> rangeValue = new ArrayList<String>();
        while(rs_rangeValue.next()){
        	rangeValue.add(rs_rangeValue.getString("rangeValue"));
        }        
        if(rangeValue.size()>0){//已有属性值，做判断
        	for(int i=0;i<colors.size();i++){
        		Boolean flag = true;
        		for(int j=0;j<rangeValue.size();j++){
//        			System.out.println(rs_rangeValue.getString("rangeValue")+"lallalalala");//测试
        			if(rangeValue.get(j).equals(colors.get(i))){
        				flag = false;
        			}
        		}//
        		if(flag){//判断某个属性值还没存入，就插入
        			String sql_tgattrrange = "INSERT INTO `muyin_db`.`t_g_attr_range`(`attr_id`,`rangeValue`)\n" +
        	                "VALUES (\""+ attr_id +"\",\""+colors.get(i)+"\");";
        			System.out.println(sql_tgattrrange);
        			st.executeUpdate(sql_tgattrrange);
        		}
        		else{//某个属性值已经存入，不作处理
        			System.out.println("扩展属性值已存在");//测试
        		} 
        		
        	}
        }
        else{//还没有属性值，就直接插入
        	for(int j=0;j<colors.size();j++){
        		String sql_tgattrrange = "INSERT INTO `muyin_db`.`t_g_attr_range`(`attr_id`,`rangeValue`)\n" +
    	                "VALUES (\""+ attr_id +"\",\""+colors.get(j)+"\");";
        		System.out.println(sql_tgattrrange);
//        		System.out.println("66666666");//测试
        		st.executeUpdate(sql_tgattrrange);
        	}
        }
        
        
        
        String sql_keyWord = "select * from t_g_goods where goods_sn ='"+ goodsSn +"' and brand_id ='"+ brandId +"'";
        ResultSet rs = st.executeQuery(sql_keyWord);
      
        List<Integer> goods_Ids = new ArrayList<Integer>();
        while(rs.next()){
        	goods_Ids.add(rs.getInt("goods_id"));
        }
        List<Integer> attrRangeId = new ArrayList<Integer>();//存放编号相同的已存入数据库的商品的属性值ID
        for(int i=0;i<goods_Ids.size();i++){
        	String sql_attrRangeId = "select * from t_g_goods_attr where goods_id ='"+ goods_Ids.get(i) +"'";
        	ResultSet rs_attrRangeId = st.executeQuery(sql_attrRangeId);
        	while(rs_attrRangeId.next()){
        		attrRangeId.add(rs_attrRangeId.getInt("attr_range_id"));
        	}
        }
        String sql_Cat_Range_Id = "select * from t_g_attr_range where attr_id='"+ attr_id +"' and rangeValue='"+ colors.get(0) +"'";
        ResultSet rs_Cat_Range_Id = st.executeQuery(sql_Cat_Range_Id);
        int cat_Range_Id=0;//存放本商品的属性值ID
        while(rs_Cat_Range_Id.next()){
        	cat_Range_Id = rs_Cat_Range_Id.getInt("cat_range_id");
        }
        Boolean goods_flag = false;
        for(int i=0;i<attrRangeId.size();i++){
        	if(attrRangeId.get(i)==cat_Range_Id){
        		goods_flag = true;
        	}
        }
        
        
        if(goods_flag){//已有这条数据        	
        	String sql_goodsId = "select * from t_g_goods_attr where attr_range_id ='"+ cat_Range_Id +"'";
        	ResultSet rs_goodsId = st.executeQuery(sql_goodsId);
        	int goodsId=0;
        	while(rs_goodsId.next()){
        		goodsId = rs_goodsId.getInt("goods_id");
        	}
        	String sql_tggoods = "select * from t_g_goods where goods_id ='"+ goodsId +"'";
        	ResultSet rs_tggoods = st.executeQuery(sql_tggoods);
        	if(rs_tggoods.next()){
        		if(rs_tggoods.getString("goods_name").equals(goods.getGoodsName())&&rs_tggoods.getDouble("o_price")==goods.getoPrice()&&
            			rs_tggoods.getDouble("n_price")==goods.getnPrice()&&rs_tggoods.getInt("cat_id")==goods.getCatId()){//字段数据都没变化，就不作处理
            		
    				System.out.println("商品数据无变化");
            	}
            	else{//只要字段数据有变化，就更新       		
            		String sql_update = "update t_g_goods set goods_name='"+ goods.getGoodsName() +"',o_price='"+ goods.getoPrice() +"',n_price='"+ 
            				goods.getnPrice() +"',cat_id='"+ goods.getCatId() +"' where goods_id ='"+ goodsId +"'";
            		
            		System.out.println(sql_update);
            		
            		st.executeUpdate(sql_update);
            	}
        	}
        	        	
        }
        else{//还没有这条数据，就插入数据
        	
            String sql = "INSERT INTO `muyin_db`.`t_g_goods`(`goods_name`,`o_price`,`n_price`,`goods_desc`,`goods_sn`,`g_url`,`brand_id`,`add_time`,`cat_id`)\n" +
                "VALUES (\""+ goods.getGoodsName() +"\",\""+goods.getoPrice()+"\",\""+goods.getnPrice()+"\",\""+goods.getGoodsDesc()+"\",\""+goods.getGoodsSn()+
                "\",\""+goods.getgUrl()+"\",\""+goods.getBrandId()+"\",\""+goods.getAddTime()+"\",\""+goods.getCatId()+"\");";

            System.out.println(sql);

            st.executeUpdate(sql);
        
        }  
        
        
        
      String sql_goodsID = "select * from t_g_goods where goods_sn ='"+ goodsSn +"' and brand_id ='"+ brandId +"' and img_small ='null'";
      ResultSet rs_goodsID = st.executeQuery(sql_goodsID);
      if(rs_goodsID.next()){
      	int goodsId = rs_goodsID.getInt("goods_id"); 
      	//商品扩展属性存入
      	String sql_CatRangeId = "select * from t_g_attr_range where attr_id='"+ attr_id +"' and rangeValue='"+ colors.get(0) +"'";
          ResultSet rs_CatRangeId = st.executeQuery(sql_CatRangeId);
      	if(rs_CatRangeId.next()){
          	int cat_range_id = rs_CatRangeId.getInt("cat_range_id");
          	String sql_goodsId = "select * from t_g_goods_attr where goods_id='"+ goodsId +"'";
              ResultSet rs_goodsId = st.executeQuery(sql_goodsId);
          	if(rs_goodsId.next()){
          		//已存在该商品就不插入数据
          	}
          	else{
          		String sql_goodsAttr = "INSERT INTO `muyin_db`.`t_g_goods_attr`(`goods_id`,`attr_range_id`)\n" +
          				"VALUES (\""+ goodsId +"\",\""+ cat_range_id +"\");";
          		System.out.println(sql_goodsAttr);       
          		st.executeUpdate(sql_goodsAttr);
          	}
          }
      	//图片存入
      	String sql_goods_img = "select * from t_g_goods_img where goods_id='"+ goodsId +"'";
      	ResultSet rs_goods_img = st.executeQuery(sql_goods_img);
      	if(rs_goods_img.next()){
      		//该商品图片已存在，不插入
      		System.out.println("图片已存在");
      		//-----------------------------------------------------------//
//      		System.out.println(pics.get(0));//测试
      		List<String> imgUrl = downloadPic(pics, 1);//大图
      		String smallImgUrl = downloadSmallPic(smallPic, 1);//小图一张
      		//删除原来该商品所有的图片路径
      		String sql_del_goodsimg = "delete from t_g_goods_img where goods_id='"+ goodsId +"'";
      		System.out.println(sql_del_goodsimg); 
      		st.executeUpdate(sql_del_goodsimg);
      		//图片存入
      		for(int i=0;i<imgUrl.size();i++ ){
      			String sql_goodsImg = "INSERT INTO `muyin_db`.`t_g_goods_img`(`goods_id`,`value`)\n" +
          				"VALUES (\""+ goodsId +"\",\""+ "/hia/goodsimg/" + imgUrl.get(i) +"\");";
      			System.out.println(sql_goodsImg);       
          		st.executeUpdate(sql_goodsImg);
      		}
      		String sql_updateGoodImg = "update t_g_goods set img_small='"+ "/hia/goodsimgSmall/" + smallImgUrl +"' where goods_id ='"+ goodsId +"'";
      		System.out.println(sql_updateGoodImg);       
      		st.executeUpdate(sql_updateGoodImg);
      		//----------------------------------------------------------//
      	}else{
      		//图片下载
      		List<String> imgUrl = downloadPic(pics, 1);//大图
      		String smallImgUrl = downloadSmallPic(smallPic, 1);//小图一张
      		//图片存入
      		for(int i=0;i<imgUrl.size();i++ ){
      			String sql_goodsImg = "INSERT INTO `muyin_db`.`t_g_goods_img`(`goods_id`,`value`)\n" +
          				"VALUES (\""+ goodsId +"\",\""+ "/hia/goodsimg/" + imgUrl.get(i) +"\");";
      			System.out.println(sql_goodsImg);       
          		st.executeUpdate(sql_goodsImg);
      		}
      		String sql_updateGoodImg = "update t_g_goods set img_small='"+ "/hia/goodsimgSmall/" + smallImgUrl +"' where goods_id ='"+ goodsId +"'";
      		System.out.println(sql_updateGoodImg);       
      		st.executeUpdate(sql_updateGoodImg);
      	}
      }  
                
        System.out.println("---------------------------------------------------------------------------");

    
    }*/
}
