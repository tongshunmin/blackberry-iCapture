package com.onmoso.fields;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.LabelField;

import com.onmoso.utils.Config;
/**
 * 自定义BannerField控件
 * @author xiangguang
 * @version 1.0
 */
public class BannerField extends LabelField{
	//显示文字
	private String text;
	
	/**
	 * 构造方法
	 * @param text 显示文字
	 * @param l 显示样式
	 */
	public BannerField(String text,long l){
		super(text,l);
		this.text = text;
		this.setFont(Config.F_20);
	}
	
	/**
	 * 自定义显示内容
	 * @see net.rim.device.api.ui.component.LabelField#paint(net.rim.device.api.ui.Graphics)
	 */
	protected void paint(Graphics g) {
		int w = this.getWidth();
		//设置颜色为黑色
		g.setColor(Color.BLACK);
		//填充矩形
		g.fillRect(0, 0, w, this.getHeight());
		//设置颜色为白色
		g.setColor(Color.WHITE);
		//绘制Banner显示文字
		g.drawText(text, (w - Config.F_20.getAdvance(text))>>1, 0);
	}
	
	/**
	 * (non-Javadoc)
	 * @see net.rim.device.api.ui.component.LabelField#layout(int, int)
	 */
	protected void layout(int width, int height) {
		this.setExtent(Display.getWidth(), 25);
	}
}
