package com.onmoso.store;

import net.rim.device.api.util.Persistable;
/**
 * 自定义设置对象
 * @author xiangguang
 * @version 1.0
 */
public class OptionData implements Persistable{
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
