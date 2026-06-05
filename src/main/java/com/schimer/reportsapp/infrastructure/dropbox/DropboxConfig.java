package com.schimer.reportsapp.infrastructure.dropbox;

import com.dropbox.core.*;
import lombok.Getter;

@Getter
public class DropboxConfig {
    private static final String APP_KEY    = "";
    private static final String APP_SECRET = "";
    private DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
    private static String keyHost = "http://localhost:8080/OAuth2";
    private DbxWebAuth webAuth;
    public static DbxRequestConfig config = DbxRequestConfig.newBuilder("schimerReportApp").build();

    public String makeUrlLogin(){
        webAuth = new DbxWebAuth(config, appInfo);
        var authRequest = getWebAuth();

        return webAuth.authorize(authRequest);
    }
    
    private DbxWebAuth.Request getWebAuth(){
        return DbxWebAuth.newRequestBuilder()
                .withRedirectUri(
                        keyHost,
                        getSessionStore()
                )
                .build();
    }

    public String getAccessToken(String code) throws DbxException {
        return webAuth.finishFromCode(
                code,
                "http://localhost:8080/OAuth2"
        ).getAccessToken();
    }
    
    private DbxSessionStore  getSessionStore() {
        return new DbxSessionStore() {
            private String stored;

            @Override
            public String get() { return stored; }

            @Override
            public void set(String value) { stored = value; }

            @Override
            public void clear() { stored = null; }
        };
    }
}
