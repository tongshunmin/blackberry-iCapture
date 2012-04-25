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
	//实例化一个管理器
	private FlowFieldManager mFlowManager = new FlowFieldManager(FlowFieldManager.VERTICAL_SCROLL|VERTICAL_SCROLLBAR|FIELD_HCENTER);
	
	private int mDisWidth = Display.getWidth();
	private int mDisHeight = Display.getHeight();
	
	private Bitmap mNewBitmap = new Bitmap(mDisWidth - Config.MARGIN,mDisHeight - Config.MARGIN);
	private Bitmap mBitmap = new Bitmap(mDisWidth,mDisHeight);
	
	/**
	 * 构造方法
	 */
	public MyScreen(){
		super(new VerticalFieldManager(),DEFAULT_MENU|DEFAULT_CLOSE);
		this.init();
	}
	
	/**
	 * 初始化屏幕显示
	 */
	private void init(){
		//添加到屏幕
		this.add(new BannerField(Config.APP_NAME,USE_ALL_WIDTH));
		//设置外边距
		mFlowManager.setMargin(10,0,0,0);
        this.add(mFlowManager);
        //设置背景颜色
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
			//判断设置内容是否为震动
			if(len > 0){
				Object obj = v.elementAt(0);
				if(obj instanceof OptionData){
					OptionData data = (OptionData)obj;
					if(data.getType() == 1){
						//震动300毫秒
						Alert.startVibrate(300);
					}
				}
			}
			//开始截屏
			doShot();
			//弹出选择框
			UiApplication.getUiApplication().invokeLater(new Runnable() {
				
				public void run() {
					String[] str = {"邮件(E)","保存(S)","取消(C)"};
					int[] num = {0,1,2};
					Bitmap questionImg = Bitmap.getPredefinedBitmap(Bitmap.QUESTION);
					final Dialog d = new Dialog("你想?", str, num,0, questionImg){
						protected boolean keyChar(char key, int status, int time) {
							switch(key){
							case Characters.ESCAPE:
								this.cancel();
								break;
							case Characters.LATIN_SMALL_LETTER_E:
								Tools.doSend(mBitmap);
								this.cancel();
								break;
							case Characters.LATIN_SMALL_LETTER_S:
								Tools.doSave(mBitmap);
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
						Tools.doSend(mBitmap);
						break;
					case 1: 
						Tools.doSave(mBitmap);
						break;
					case 2:
						break;
					}
				}
			});
		}
	}
	
	/**
	 * 截屏
	 */
	private void doShot(){
		net.rim.device.api.system.Display.screenshot(mBitmap);
		doAdd(mBitmap);
	}
	
	/**
	 * 添加到屏幕显示
	 * @param bit 图片
	 */
	private void doAdd(Bitmap bit){
		mFlowManager.deleteAll();
		bit.scaleInto(mNewBitmap, Bitmap.FILTER_BOX);
		mFlowManager.add(new BitmapField(mNewBitmap,FOCUSABLE|FIELD_HCENTER));
	}
	
	protected MenuItem send = new MenuItem("发送邮件(E)",10,10){
		public void run(){
			Tools.doSend(mBitmap);
		}
	}; 
	
	protected MenuItem save = new MenuItem("保存截图(S)",20,10){
		public void run(){
			Tools.doSave(mBitmap);
			Dialog.inform(Config.SAVE_SUCCESS);
		}
	};
	
	protected MenuItem option = new MenuItem("设置(O)",30,10){
		public void run(){
			doConfig();
		}
	};
	
	protected MenuItem about = new MenuItem("关于(A)",50,10){
		public void run(){
			doAbout();
		}
	};
	
	protected MenuItem close = new MenuItem("关闭(Q)",80,10){
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
	 * 关于
	 */
	private void doAbout(){
		Status.show(Config.ABOUT_TEXT, Config.LOGO, 4000);
	}
	
	/**
	 * 按键响应
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
			Tools.doSave(mBitmap);
			Dialog.inform(Config.SAVE_SUCCESS);
			break;
		case Characters.LATIN_SMALL_LETTER_E:
			Tools.doSend(mBitmap);
			break;
		default:
			return super.keyChar(c, status, time);
		}
		return true;
	}
	
	/**
	 * 打开设置界面
	 */
	private void doConfig(){
		UiApplication.getUiApplication().pushScreen(new ConfigScreen());
	}
	
	/**
	 * 关闭
	 */
	private void doClose(){
		this.close();
	}
}
