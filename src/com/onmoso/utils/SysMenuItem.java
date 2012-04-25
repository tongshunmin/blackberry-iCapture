package com.onmoso.utils;

import net.rim.blackberry.api.menuitem.ApplicationMenuItem;
import net.rim.device.api.system.ApplicationDescriptor;
import net.rim.device.api.system.ApplicationManager;
import net.rim.device.api.system.ApplicationManagerException;
import net.rim.device.api.system.CodeModuleManager;
/**
 * 应用程序菜单
 * @author xiangguang
 * @version 1.0
 */
public class SysMenuItem extends ApplicationMenuItem{

	public SysMenuItem(){
		super(1);
	} 
	
	/**
	 * 指定菜单项的行为
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
           		//启动程序
				myApp.runApplication(tmp);
			} catch (ApplicationManagerException e) {
				e.printStackTrace();
			}
    	}
        return context;
	}
	
	/**
	 * 菜单项显示文字
	 */
	public String toString() {			
		return "Capture截屏";
	}
}
