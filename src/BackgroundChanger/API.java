package BackgroundChanger;

import org.json.JSONObject;

import java.io.IOException;



abstract public class API {

	protected RequestBehaviour requestBehaviour;
	
	abstract public String requestLink(String url) throws Exception;
	
	abstract public JSONObject getToken() throws IOException;
	
	public RequestBehaviour getRequestBehaviour(){ return this.requestBehaviour;}
	public void setRequestBehaviour(RequestBehaviour requestBehaviour){
		this.requestBehaviour = requestBehaviour;
	}
	
	
}
