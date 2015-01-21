package net.oschina.app.bean;

import net.sf.json.JSONObject;
 


public class JsonData extends Entity {
	
	public JSONObject jsonData = null;

	public static JSONObject parse(String jsonStr,String encode) throws Exception {
		JSONObject jsonData = null; 
		try {
			jsonData = JSONObject.fromObject(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonData;
	}
}
