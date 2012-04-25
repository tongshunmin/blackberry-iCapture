package com.onmoso.fields;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.LabelField;

import com.onmoso.utils.Config;
/**
 * �Զ���BannerField�ؼ�
 * @author xiangguang
 * @version 1.0
 * http://www.onmoso.com
 */
public class BannerField extends LabelField{
	//��ʾ����
	private String mText;
	
	/**
	 * ���췽��
	 * @param text ��ʾ����
	 * @param l ��ʾ��ʽ
	 */
	public BannerField(String text,long l){
		super(text,l);
		this.mText = text;
		this.setFont(Config.F_20);
	}
	
	/**
	 * �Զ�����ʾ����
	 * @see net.rim.device.api.ui.component.LabelField#paint(net.rim.device.api.ui.Graphics)
	 */
	protected void paint(Graphics g) {
		int w = this.getWidth();
		//������ɫΪ��ɫ
		g.setColor(Color.BLACK);
		//������
		g.fillRect(0, 0, w, this.getHeight());
		//������ɫΪ��ɫ
		g.setColor(Color.WHITE);
		//����Banner��ʾ����
		g.drawText(mText, (w - Config.F_20.getAdvance(mText))>>1, 0);
	}
	
	/**
	 * (non-Javadoc)
	 * @see net.rim.device.api.ui.component.LabelField#layout(int, int)
	 */
	protected void layout(int width, int height) {
		this.setExtent(Display.getWidth(), 25);
	}
}
