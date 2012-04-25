package com.onmoso.store;

import java.util.Vector;

import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;

/**
 * 存储类
 * http://www.onmoso.com
 * @author xiangguang
 * @version 1.0
 */
public class DataStore {

    //存放地址 iCapture.com.onmoso.store.DataStore._fileName.long.key
    private long mFileName = 0xe8158e7018324a54L;
	private Vector mList;
	private PersistentObject objectList;
	
    /**
     * 返回DataStore类的实例，并初始化数据类型
     * @return DataStore
     */
    public DataStore()
    {
    	objectList = PersistentStore.getPersistentObject(mFileName);
		Object myList = objectList.getContents();
		if(myList == null || !(myList instanceof Vector)){
			objectList.setContents(new Vector());
			objectList.commit();
			mList = (Vector)objectList.getContents();
		}else{
			mList = (Vector)myList;
		}
    }
    
	/**
	 * 获取集合
	 * 
	 */
	public Vector getList(){
		return this.mList;
	}

	/**
     * 返回对应索引的数据
     * @param index 
     * @return 
     */
    public boolean setElementAt(Object data,int index)
    {
        try
        {
            if(this.mList!=null)
            {
                int size = this.mList.size();
                if(index<size)
                    this.mList.setElementAt(data,index);
                else
                    this.mList.addElement(data);
                    
                if (this.mList.size() < this.mList.capacity())
                    this.mList.trimToSize();
                objectList.commit();                 
                return true;
            }
        }
        catch(Exception e)
        {
            System.err.println("DataStore addElement :"+e.toString());
        }
        return false;
    }
}
