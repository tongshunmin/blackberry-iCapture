package com.onmoso.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.io.file.FileSystemRegistry;

import net.rim.blackberry.api.invoke.Invoke;
import net.rim.blackberry.api.invoke.MessageArguments;
import net.rim.blackberry.api.mail.Address;
import net.rim.blackberry.api.mail.Message;
import net.rim.blackberry.api.mail.MessagingException;
import net.rim.blackberry.api.mail.Multipart;
import net.rim.blackberry.api.mail.SupportedAttachmentPart;
import net.rim.blackberry.api.mail.Message.RecipientType;
import net.rim.device.api.i18n.SimpleDateFormat;
import net.rim.device.api.system.Application;
import net.rim.device.api.system.ApplicationDescriptor;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.PNGEncodedImage;
/**
 * ������
 * @author xiangguang
 * @version 1.0
 */
public class Tools {
	/**
	 * SD��
	 */
	private static final String SDCARD_STRING = "SDCard";
	
	/**
	 * �����ʼ�
	 * @param bit ��ȡ����Ļ
	 */
	public static  void doSend(final Bitmap bit){
		new Thread(){
			public void run(){
				synchronized (Application.getEventLock()) {
					try {
						//����
					   String name = Config.APP_NAME+Tools.formatDateToString(System.currentTimeMillis())+".jpg"; 
					   Multipart mp = new Multipart();
					   Message msg = new Message();
					   Address[] addresses = {new Address("", "Recipient Name")};
					   PNGEncodedImage img = PNGEncodedImage.encode(bit);
					   //����
					   SupportedAttachmentPart pt = new SupportedAttachmentPart(mp, img.getMIMEType(),name, img.getData());
					   mp.addBodyPart(pt);
					   msg.setContent(mp);
					   msg.addRecipients(RecipientType.TO, addresses);
					   msg.setSubject("");
					   //�����ʼ�
					   Invoke.invokeApplication(Invoke.APP_TYPE_MESSAGES, new MessageArguments(msg));
					} catch (MessagingException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	/**
	 * �Ƿ����SDCard
	 */
	public static boolean isExistSDCard(){
		Enumeration euu = null;
		try {
			euu = FileSystemRegistry.listRoots();
			while(euu.hasMoreElements()){
				String str = (String)euu.nextElement();
				if(str.endsWith("/")){
					str = str.substring(0, str.length()-1);
				}
				if(SDCARD_STRING.equals(str)){
					return true;
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return false;
	}
	
	/**
	 * ���ͼƬ���ڵ�ַ
	 * @param name
	 * @return
	 */
	private static String getURL(String name){
		String url = "file:///store/home/user/pictures/"+name;
		if(Tools.isExistSDCard()){
			url = "file:///SDCard/BlackBerry/pictures/"+name;
		}
		return url;
	}
	
	/**
	 * ����
	 */
	public static void doSave(Bitmap img){
		String name = Config.APP_NAME+Tools.formatDateToString(System.currentTimeMillis())+".jpg"; 
		
		String url = getURL(name);
		FileConnection f = null;
		OutputStream output = null;
		try {
			f = (FileConnection) Connector.open(url);
			
			if(f.exists())
				f.delete();
			
			f.create();
			try{
				output = f.openOutputStream();
				//PNGEncodedImage class is RIM-defined.
				PNGEncodedImage encodedImage = PNGEncodedImage.encode(img); 
				byte[] imageBytes = encodedImage.getData(); 
				
				output.write(imageBytes);
				output.flush();
			}catch(Exception e){
				System.out.println(e.toString());
			}finally{
	        	try {
	        		if(output != null)
	        			output.close();
	        		
	        		output = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
		}catch(Exception ex){
			System.out.println(ex.toString());
		}finally{
			try {
				if(f != null)
					f.close();
				f = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static String formatDateToString(long date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.formatLocal(date);
	}
	
	/**
	 * ��ȡ�ͻ��˰汾��
	 * @return �汾��
	 */
	public static String getVersion() {
		ApplicationDescriptor ap = null;
		String curVersion = "";
		ap = ApplicationDescriptor.currentApplicationDescriptor();
		if (ap != null)
			curVersion = ap.getVersion();

		if (curVersion == null || curVersion.equals(""))
			curVersion = "1.0.0";

		return curVersion;
	}  
}
