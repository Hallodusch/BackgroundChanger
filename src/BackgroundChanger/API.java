package BackgroundChanger;

import java.io.IOException;

import javax.json.JsonObject;

import org.json.JSONObject;

import com.google.api.client.http.GenericUrl;



abstract public class API {

	protected RequestBehaviour requestBehaviour;
	
	abstract public String requestData(String url) throws Exception;
	
	abstract public JSONObject getToken() throws IOException;
	
	public RequestBehaviour getRequestBehaviour(){ return this.requestBehaviour;}
	public void setRequestBehaviour(RequestBehaviour requestBehaviour){
		this.requestBehaviour = requestBehaviour;
	}
	
	
}
