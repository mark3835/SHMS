package tcb.shms.core.service;

import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

	private final static Logger log = LogManager.getLogger(SmsService.class);
	
	public static void main(String args[]) {
		sendSMS("test1234", "0926354830");
	}

	public static boolean sendSMS(String password, String phone) {
		String url = "http://10.0.6.117:9080/OtpService/SendOTPServlet";
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		String msg = "親愛的客戶您好 您正在進行留存於合作金庫商業銀行之基本資料更新，請輸入驗證碼【" + password + "】以進入資料登打頁面，謝謝。合作金庫商業銀行 啟";
		try {
			msg = URLEncoder.encode(msg, "UTF-8");
		} catch (Exception ex) {
		}
		method.setParameter("SMS_CONTENT", msg);
		method.setParameter("MOBILE_PHONE", phone);
		try {
			client.setTimeout(10000);
			client.executeMethod(method);
			String ret = method.getResponseHeader("MNB_RtnCode").toString();
			if (ret.indexOf("4001") < 0) {
				System.out.println("簡訊發生問題!");
				return false;
			}
			return true;
		} catch (Exception ex) {
			System.out.println("簡訊發生問題!");
			return false;
		}
	}

}
