package BackgroundChanger;

import java.io.IOException;

import org.json.JSONObject;

import com.google.api.client.http.GenericUrl;



abstract public class API {

	private final String api_key;
	protected RequestBehaviour requestBehaviour;
	
	public API(String api_key){
		
		this.api_key = api_key;
	}
	
	abstract public String requestData(String url) throws Exception;
	
	abstract public JSONObject getToken() throws IOException;
	
	public RequestBehaviour getRequestBehaviour(){ return this.requestBehaviour;}
	public void setRequestBehaviour(RequestBehaviour requestBehaviour){
		this.requestBehaviour = requestBehaviour;
	}
	
	
}
