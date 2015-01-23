package net.oschina.app.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.oschina.app.AppContext;
import net.oschina.app.AppException;
import net.oschina.app.api.ApiClient;
import net.oschina.app.bean.SoftwareList.Software;
import net.oschina.app.bean.SoftwareCatalogList.SoftwareType;
import net.oschina.app.common.HPTools;
import net.oschina.app.common.StringUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

/**
 * 博客列表实体类
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class RssList extends Entity {
	
	public List<Map> rssList = null;

	public static SoftwareList getList(AppContext appContext, String url, boolean isRefresh,String encode) {
		SoftwareList result = new SoftwareList();
		RssList nh = null;
		try {
			
			nh = appContext.getRssList(url, isRefresh,encode);			
			Iterator it = nh.rssList.iterator();
			while (it.hasNext()) {
				HashMap<String, String> temp = (HashMap<String, String>) it
						.next();
				Software software = new Software();
				software.name = temp.get("title");
				software.description = temp.get("description");
				software.url = temp.get("link");
				software.date = temp.get("date");		

				result.getSoftwarelist().add(software);
			}		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.setSoftwarecount(nh.rssList.size());
		result.setPageSize(nh.rssList.size());
		result.setNotice(new Notice());
		result.getNotice().setAtmeCount(0);
		result.getNotice().setMsgCount(0);
		result.getNotice().setReviewCount(0);
		result.getNotice().setNewFansCount(0);
		return result;
	}
	
	public static SoftwareCatalogList getCatList(AppContext appContext, String url, boolean isRefresh) {
		SoftwareCatalogList result = new SoftwareCatalogList();
		RssList nh = null;
		try {
			
			nh = appContext.getRssList(url, isRefresh,null);			
			Iterator it = nh.rssList.iterator();
			while (it.hasNext()) {
				HashMap<String, String> temp = (HashMap<String, String>) it
						.next();
				SoftwareType software = new SoftwareType();
				software.name = temp.get("title");
				software.tag = 1;
				software.url = temp.get("link");
				software.encode = temp.get("description");
				result.getSoftwareTypelist().add(software);
			}		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.setSoftwarecount(nh.rssList.size());		
		result.setNotice(new Notice());
		result.getNotice().setAtmeCount(0);
		result.getNotice().setMsgCount(0);
		result.getNotice().setReviewCount(0);
		result.getNotice().setNewFansCount(0);
		return result;
	}

	public static List<Map> parse(InputStream inputStream,String encode) throws Exception {
		List<Map> list = new ArrayList<Map>();
		HashMap<String, String> i = null;
		// 获得XmlPullParser解析器
		XmlPullParser xmlParser = Xml.newPullParser();
		try {
			xmlParser.setInput(inputStream, encode==null || encode.equalsIgnoreCase("") ? "UTF-8" : encode);
			// 获得解析到的事件类别，这里有开始文档，结束文档，开始标签，结束标签，文本等等事件。
			int eventType = xmlParser.getEventType();
			// 一直循环，直到文档结束
			while (eventType != XmlPullParser.END_DOCUMENT) {
				String name = xmlParser.getName();// 获取解析器当前指向的元素名称
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					if ("item".equalsIgnoreCase(name)) {
						i = new HashMap<String, String>();
					}
					if (i != null) {
						if ("title".equalsIgnoreCase(name)) {
							i.put("title", xmlParser.nextText());
						}
						if ("link".equalsIgnoreCase(name)) {
							i.put("link", xmlParser.nextText());
						}

						if ("pubDate".equalsIgnoreCase(name)) {

							i.put("date", xmlParser.nextText());

						}
						if ("description".equalsIgnoreCase(name)) {
							i.put("description", xmlParser.nextText());
						}
					}
					break;
				case XmlPullParser.END_TAG:
					if ("item".equalsIgnoreCase(name)) {
						list.add(i);
						i = null;
					}
				}
				eventType = xmlParser.next();// 进入下一个元素
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
		}
		return list;
	}
}
