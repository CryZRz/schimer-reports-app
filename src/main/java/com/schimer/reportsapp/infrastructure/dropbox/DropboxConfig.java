package com.schimer.reportsapp.infrastructure.dropbox;

import com.dropbox.core.*;
import lombok.Getter;

@Getter
public class DropboxConfig {
    public static final String APP_KEY    = "745gkasj6igwfls";
    public static final String APP_SECRET = "2t62034yexyn187";
    private DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
    private static String keyHost = "http://localhost:8080/OAuth2";
    private DbxWebAuth webAuth;
    public static DbxRequestConfig config = DbxRequestConfig.newBuilder("schimerReportApp").build();

    public String makeUrlLogin(){
        webAuth = new DbxWebAuth(config, appInfo);
        var authRequest = getWebAuthRequest();

        return webAuth.authorize(authRequest);
    }
    
    private DbxWebAuth.Request getWebAuthRequest(){
        return DbxWebAuth.newRequestBuilder()
                .withRedirectUri(
                        keyHost,
                        getSessionStore()
                )
                .withTokenAccessType(TokenAccessType.OFFLINE)
                .build();
    }

    public DbxAuthFinish finishAuthenticate(String code) throws DbxException {
        return webAuth.finishFromCode(
                code,
                "http://localhost:8080/OAuth2"
        );
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
