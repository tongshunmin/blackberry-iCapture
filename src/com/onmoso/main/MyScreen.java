package com.onmoso.main;

import java.util.Vector;

import net.rim.device.api.system.Alert;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Characters;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.Status;
import net.rim.device.api.ui.container.FlowFieldManager;
import net.rim.device.api.ui.container.FullScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;

import com.onmoso.fields.BannerField;
import com.onmoso.store.DataStore;
import com.onmoso.store.OptionData;
import com.onmoso.utils.Config;
import com.onmoso.utils.Tools;
/**
 * MyScreen.java
 * http://www.onmoso.com
 * @author xiangguang
 * @version 1.0
 */
public class MyScreen extends FullScreen{
	
	private final static int MARGIN = 45;
	//ʵ����һ��������
	private FlowFieldManager _v = new FlowFieldManager(FlowFieldManager.VERTICAL_SCROLL|VERTICAL_SCROLLBAR|FIELD_HCENTER);
	
	private int dw = Display.getWidth();
	private int dh = Display.getHeight();
	
	private Bitmap newbit = new Bitmap(dw - MARGIN,dh-MARGIN);
	private Bitmap bit = new Bitmap(dw,dh);
	
	/**
	 * ���췽��
	 */
	public MyScreen(){
		super(new VerticalFieldManager(),DEFAULT_MENU|DEFAULT_CLOSE);
		this.init();
	}
	
	/**
	 * ��ʼ����Ļ��ʾ
	 */
	private void init(){
		//���ӵ���Ļ
		this.add(new BannerField(Config.APP_NAME,USE_ALL_WIDTH));
		//������߾�
        _v.setMargin(10,0,0,0);
        this.add(_v);
        //���ñ�����ɫ
        this.setBackground(BackgroundFactory.createSolidBackground(0x272727));
	}
	
	/**
	 * (non-Javadoc)
	 * @see net.rim.device.api.ui.Screen#onUiEngineAttached(boolean)
	 */
	protected void onUiEngineAttached(boolean attached) {
		super.onUiEngineAttached(attached);
		
		if(attached){
			DataStore store = new DataStore();
			Vector v = store.getList();
			int len = v.size();
			//�ж����������Ƿ�Ϊ��
			if(len > 0){
				Object obj = v.elementAt(0);
				if(obj instanceof OptionData){
					OptionData data = (OptionData)obj;
					if(data.getType() == 1){
						//��300����
						Alert.startVibrate(300);
					}
				}
			}
			//��ʼ����
			doShot();
			//����ѡ���
			UiApplication.getUiApplication().invokeLater(new Runnable() {
				
				public void run() {
					String[] str = {"�ʼ�(E)","����(S)","ȡ��(C)"};
					int[] num = {0,1,2};
					Bitmap questionImg = Bitmap.getPredefinedBitmap(Bitmap.QUESTION);
					final Dialog d = new Dialog("����?", str, num,0, questionImg){
						protected boolean keyChar(char key, int status, int time) {
							switch(key){
							case Characters.ESCAPE:
								this.cancel();
								break;
							case Characters.LATIN_SMALL_LETTER_E:
								Tools.doSend(bit);
								this.cancel();
								break;
							case Characters.LATIN_SMALL_LETTER_S:
								Tools.doSave(bit);
								this.cancel();
								break;
							case Characters.LATIN_SMALL_LETTER_C:
								break;
							default:
								return super.keyChar(key, status, time);
							}
							return true;
						}
					};
					
					int answer = d.doModal();
					switch(answer){
					case 0:
						Tools.doSend(bit);
						break;
					case 1: 
						Tools.doSave(bit);
						break;
					case 2:
						break;
					}
				}
			});
		}
	}
	
	/**
	 * ����
	 */
	private void doShot(){
		net.rim.device.api.system.Display.screenshot(bit);
		doAdd(bit);
	}
	
	/**
	 * ���ӵ���Ļ��ʾ
	 * @param bit ͼƬ
	 */
	private void doAdd(Bitmap bit){
		_v.deleteAll();
		bit.scaleInto(newbit, Bitmap.FILTER_BOX);
		_v.add(new BitmapField(newbit,FOCUSABLE|FIELD_HCENTER));
	}
	
	protected MenuItem send = new MenuItem("�����ʼ�(E)",10,10){
		public void run(){
			Tools.doSend(bit);
		}
	}; 
	
	protected MenuItem save = new MenuItem("�����ͼ(S)",20,10){
		public void run(){
			Tools.doSave(bit);
			Dialog.inform("����ɹ�");
		}
	};
	
	protected MenuItem option = new MenuItem("����(O)",30,10){
		public void run(){
			doConfig();
		}
	};
	
	protected MenuItem about = new MenuItem("����(A)",50,10){
		public void run(){
			doAbout();
		}
	};
	
	protected MenuItem close = new MenuItem("�ر�(Q)",80,10){
		public void run(){
			doClose();
		}
	};
	
	protected void makeMenu(Menu menu, int instance) {
		menu.add(send);
		menu.add(save);
		menu.addSeparator();
		menu.add(option);
		menu.add(about);
		menu.addSeparator();
		menu.add(close);
	}
	
	/**
	 * ����
	 */
	private void doAbout(){
		Status.show(Config.ABOUT_TEXT, Config.LOGO, 4000);
	}
	
	/**
	 * ������Ӧ
	 * @see net.rim.device.api.ui.Screen#keyChar(char, int, int)
	 */
	protected boolean keyChar(char c, int status, int time) {
		// TODO Auto-generated method stub
		switch(c){
		case Characters.LATIN_SMALL_LETTER_O:
			doConfig();
			break;
		case Characters.LATIN_SMALL_LETTER_Q:
			doClose();
			break;
		case Characters.LATIN_SMALL_LETTER_A:
			doAbout();
			break;
		case Characters.LATIN_SMALL_LETTER_S:
			Tools.doSave(bit);
			Dialog.inform("����ɹ�");
			break;
		case Characters.LATIN_SMALL_LETTER_E:
			Tools.doSend(bit);
			break;
		default:
			return super.keyChar(c, status, time);
		}
		return true;
	}
	
	/**
	 * �����ý���
	 */
	private void doConfig(){
		UiApplication.getUiApplication().pushScreen(new ConfigScreen());
	}
	
	/**
	 * �ر�
	 */
	private void doClose(){
		this.close();
	}
}