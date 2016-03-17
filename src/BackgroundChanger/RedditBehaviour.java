package BackgroundChanger;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.IOUtils;
import com.google.appengine.api.urlfetch.HTTPResponse;

public class RedditBehaviour implements RequestBehaviour {

	@Override
	public String requestData(GenericUrl url) {
		String data = "";
		JSONObject object = null;
		URL uri = url.toURL();
		URLConnection con = uri.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
		System.out.println(body);

		
		return data;
	}

}
