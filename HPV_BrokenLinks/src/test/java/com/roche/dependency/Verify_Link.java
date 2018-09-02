package com.roche.dependency;

import java.net.HttpURLConnection;
import java.net.URL;

public class Verify_Link {

	public int VerifyLinkStatus(String Link) {
		try {
			URL url = new URL(Link);
			HttpURLConnection HttpURLConnect = (HttpURLConnection) url.openConnection();
			HttpURLConnect.setConnectTimeout(6000);
			HttpURLConnect.setRequestMethod("GET");
			HttpURLConnect.connect();
			System.out.println(Link + "  -  " + HttpURLConnect.getResponseCode());
			return HttpURLConnect.getResponseCode();

		} catch (Exception e) {
			return -1;
		}

	}

}
