package com.schimer.reportsapp.services;

import com.dropbox.core.v2.DbxClientV2;
import com.schimer.reportsapp.App;
import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.infrastructure.dropbox.DropboxConfig;
import com.schimer.reportsapp.utils.DropboxServer;

public class DropboxAuthService {
    private final DropboxConfig dropboxConfig = new DropboxConfig();
    private final AuthService authService = new AuthService();
    private final UserSession session = UserSession.getInstance();

    public DbxClientV2 handleDropboxLogin() throws Exception {
        var server = new DropboxServer(8080);
        server.start();

        var authUrl = dropboxConfig.makeUrlLogin();

        if (App.getHost() != null) {
            App.getHost().showDocument(authUrl);
        }

        var code = server.waitForCode();
        var authFinish = dropboxConfig.finishAuthenticate(code);
        server.stop();

        var accessToken = authFinish.getAccessToken();
        var refreshToken = authFinish.getRefreshToken();

        var user = authService.updateDropboxInfo(
                session.getUserEntity(),
                refreshToken
        );

        var client = new DbxClientV2(DropboxConfig.config, accessToken);
        session.setUser(user);
        session.setClientDropbox(client);

        return client;
    }
}