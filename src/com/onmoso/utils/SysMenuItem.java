package com.onmoso.utils;

import net.rim.blackberry.api.menuitem.ApplicationMenuItem;
import net.rim.device.api.system.ApplicationDescriptor;
import net.rim.device.api.system.ApplicationManager;
import net.rim.device.api.system.ApplicationManagerException;
import net.rim.device.api.system.CodeModuleManager;
/**
 * Ӧ�ó���˵�
 * @author xiangguang
 * @version 1.0
 */
public class SysMenuItem extends ApplicationMenuItem{

	public SysMenuItem(){
		super(1);
	} 
	
	/**
	 * ָ���˵������Ϊ
	 * @see net.rim.blackberry.api.menuitem.ApplicationMenuItem#run(java.lang.Object)
	 */
	public Object run(Object context){
		ApplicationManager myApp = ApplicationManager.getApplicationManager();
		int modHandle = CodeModuleManager.getModuleHandle("iCapture");
    	if(modHandle != 0){
    		String[] si={"iCaptureEntryPoint"};
           	ApplicationDescriptor[] apDes = CodeModuleManager.getApplicationDescriptors(modHandle);
           	ApplicationDescriptor tmp=new ApplicationDescriptor(apDes[0],si);
           	try {
           		//��������
				myApp.runApplication(tmp);
			} catch (ApplicationManagerException e) {
				e.printStackTrace();
			}
    	}
        return context;
	}
	
	/**
	 * �˵�����ʾ����
	 */
	public String toString() {			
		return "Capture����";
	}
}
