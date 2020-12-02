package com.lib.showfield.http.respones.mqtt;

public class NotifySecurityBean {


    /**
     * username : youyoukele
     * password : 123456
     * serverUri : tcp://localhost:1883
     */

    private String username;
    private String password;
    private String serverUri;
    private String clientId;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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

    public String getServerUri() {
        return serverUri;
    }

    public void setServerUri(String serverUri) {
        this.serverUri = serverUri;
    }
}
