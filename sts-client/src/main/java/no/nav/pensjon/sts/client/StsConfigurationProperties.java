package no.nav.pensjon.sts.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "security-token-service.client")
public class StsConfigurationProperties {
	
    
    String url;
   
    String  username;
    
    String password;
    
    
    String apikey;


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getApikey() {
		return apikey;
	}


	public void setApikey(String apikey) {
		this.apikey = apikey;
	}


    
    

}
