package com.onmoso.utils;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Font;
/**
 * 配置类
 * @author xiangguang
 * 
 */
public class Config {
	
	public static final String APP_NAME = "iCapture";
	
	public static final Font F_25 = Font.getDefault().derive(Font.PLAIN, 25);
	public static final Font F_22 = Font.getDefault().derive(Font.PLAIN, 22);
	public static final Font F_20 = Font.getDefault().derive(Font.PLAIN, 20);
	public static final Font F_15 = Font.getDefault().derive(Font.PLAIN, 15);
	
	public static final Bitmap LOGO = Bitmap.getBitmapResource("logo.png");
	
	public final static String SAVE_SUCCESS = "保存成功";
	public final static int MARGIN = 45;
	
	private static final String EMAIL = "zhaoxiangguang@gmail.com";
	private static final String WEB = "www.onmoso.com";
	public static final String ABOUT_TEXT = Config.APP_NAME + " "+Tools.getVersion()+"\n"+Config.WEB+"\n"+Config.EMAIL;
} 
