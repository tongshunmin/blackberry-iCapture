package com.onmoso.entrypoint;

import net.rim.blackberry.api.menuitem.ApplicationMenuItemRepository;
import net.rim.device.api.ui.UiApplication;

import com.onmoso.main.MyScreen;
import com.onmoso.utils.SysMenuItem;
/**
 * EntryPoint.java
 * @author xiangguang
 * @version 1.0
 */
public class EntryPoint extends UiApplication{
    /**
     * Entry point for application
     * @param args Command line arguments (not used)
     */ 
    public static void main(String[] args)
    {  
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
    	if(args != null && args.length > 0 && args[0].equals("iCaptureEntryPoint")){
        	EntryPoint theApp = new EntryPoint();       
            theApp.enterEventDispatcher();
    	}else{ 
    		SysMenuItem item = new SysMenuItem();
    		//获取应用程序菜单库,调用ApplicationMenuItemRepository.getInstance();
    		ApplicationMenuItemRepository app = ApplicationMenuItemRepository.getInstance();
    		//加入一个菜单项到菜单库
    		app.addMenuItem(ApplicationMenuItemRepository.MENUITEM_SYSTEM, item);
    	} 
    } 
     
    /**
     * Creates a new MyApp object
     */
    public EntryPoint()
    {        
    	pushScreen(new MyScreen());
    } 
}
