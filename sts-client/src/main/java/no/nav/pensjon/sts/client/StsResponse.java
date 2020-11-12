package no.nav.pensjon.sts.client;

public class StsResponse {

    private String access_token;
    private String token_type;
    private Integer expires_in;

    public String getAccessToken() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }
}
