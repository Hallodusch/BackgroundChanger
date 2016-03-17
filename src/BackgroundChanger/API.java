package BackgroundChanger;

import java.net.URL;

public class API {

	private final String api_key;
	private final String api_secret;
	private RequestBehaviour requestBehaviour;
	
	public API(String api_key, String api_secret){
		this.api_key = api_key;
		this.api_secret = api_secret;
	}
	
	public String connectToSite(URL url) {
		////////////////////
		//NOT IMPLEMENTED///
		////////////////////
		return null;
	}
	
	public RequestBehaviour getRequestBehaviour(){ return this.requestBehaviour;}
	public void setRequestBehaviour(RequestBehaviour requestBehaviour){
		this.requestBehaviour = requestBehaviour;
	}
	
	
}
