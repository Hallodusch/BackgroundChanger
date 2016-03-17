package BackgroundChanger;

import java.io.IOException;

import org.json.JSONObject;



abstract public class API {

	private final String api_key;
	private RequestBehaviour requestBehaviour;
	
	public API(String api_key){
		
		this.api_key = api_key;
	}
	
	abstract public JSONObject getToken() throws IOException;
	
	public RequestBehaviour getRequestBehaviour(){ return this.requestBehaviour;}
	public void setRequestBehaviour(RequestBehaviour requestBehaviour){
		this.requestBehaviour = requestBehaviour;
	}
	
	
}
