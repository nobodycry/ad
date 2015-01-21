package net.oschina.app.common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.MessageDigest;

public class HPTools {
	public static ByteArrayInputStream getContentStream(String strUrl) {
		return new ByteArrayInputStream(getContent(strUrl).getBytes());
	}
	
	public static String getContent(String strUrl) {

		try {

			URL url = new URL(strUrl);

			BufferedReader br = new BufferedReader(new InputStreamReader(url

			.openStream()));

			String s = "";

			StringBuffer sb = new StringBuffer("");

			while ((s = br.readLine()) != null) {

				sb.append(s + "/r/n");

			}

			br.close();

			return sb.toString();

		} catch (Exception e) {

			return "error open url:" + strUrl;

		}

	}
	
	
	public final static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       

        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
