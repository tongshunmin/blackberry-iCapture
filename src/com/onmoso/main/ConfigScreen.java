package com.onmoso.main;

import java.util.Vector;

import net.rim.device.api.ui.component.ObjectChoiceField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;

import com.onmoso.store.DataStore;
import com.onmoso.store.OptionData;
/**
 * 用户设置界面
 * @author xiangguang
 * @version 1.0
 */
public class ConfigScreen extends MainScreen{
	//设置选项
	private String[] array = {"无","震动"};
	private ObjectChoiceField choice = new ObjectChoiceField("提示：", array);
	
	/**
	 * 构造方法
	 */
	public ConfigScreen(){
		//获得存储的设置
		DataStore store = new DataStore();
		Vector v = store.getList();
		int len = v.size();
		if(len > 0){
			Object obj = v.elementAt(0);
			if(obj instanceof OptionData){
				OptionData data = (OptionData)obj;
				choice.setSelectedIndex(data.getType());
			}
		}
		//设置标题
		this.setTitle("设置");
		//添加到屏幕
		this.add(choice);
		//添加分割线
		this.add(new SeparatorField());
	}
	
	/**
	 * 保存
	 * @see net.rim.device.api.ui.Screen#onSave()
	 */
	protected boolean onSave() {
		// TODO Auto-generated method stub
		doSave();
		return super.onSave();
	}
	
	/**
	 * 保存设置
	 */
	private void doSave(){
		int index = choice.getSelectedIndex();
		DataStore store = new DataStore();
		OptionData data = new OptionData();
		data.setType(index);
		store.setElementAt(data, 0);
	}
}
