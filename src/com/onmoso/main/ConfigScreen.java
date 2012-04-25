package com.onmoso.main;

import java.util.Vector;

import net.rim.device.api.ui.component.ObjectChoiceField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;

import com.onmoso.store.DataStore;
import com.onmoso.store.OptionData;
/**
 * �û����ý���
 * @author xiangguang
 * @version 1.0
 */
public class ConfigScreen extends MainScreen{
	//����ѡ��
	private String[] array = {"��","��"};
	private ObjectChoiceField choice = new ObjectChoiceField("��ʾ��", array);
	
	/**
	 * ���췽��
	 */
	public ConfigScreen(){
		//��ô洢������
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
		//���ñ���
		this.setTitle("����");
		//��ӵ���Ļ
		this.add(choice);
		//��ӷָ���
		this.add(new SeparatorField());
	}
	
	/**
	 * ����
	 * @see net.rim.device.api.ui.Screen#onSave()
	 */
	protected boolean onSave() {
		// TODO Auto-generated method stub
		doSave();
		return super.onSave();
	}
	
	/**
	 * ��������
	 */
	private void doSave(){
		int index = choice.getSelectedIndex();
		DataStore store = new DataStore();
		OptionData data = new OptionData();
		data.setType(index);
		store.setElementAt(data, 0);
	}
}
