package BackgroundChanger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.google.api.client.http.GenericUrl;
import com.google.appengine.api.urlfetch.HTTPResponse;

public class RedditBehaviour implements RequestBehaviour {

	@Override
	public String requestData(GenericUrl url) {
		String data = "";
		JSONObject object = null;
		URL uri = url.toURL();
		URLConnection con = null;
		try {
			con = uri.openConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		InputStream in = null;
		try {
			in = con.getInputStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		try {
			data = IOUtils.toString(in, encoding);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(data);

		
		return data;
	}

}
