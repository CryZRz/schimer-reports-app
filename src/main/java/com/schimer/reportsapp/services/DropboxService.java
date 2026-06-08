package com.schimer.reportsapp.services;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;
import com.schimer.reportsapp.App;
import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.domain.entities.DropboxAccountEntity;
import com.schimer.reportsapp.infrastructure.dropbox.DropboxConfig;
import com.schimer.reportsapp.utils.DropboxServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DropboxService {
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
                new DropboxAccountEntity(null, refreshToken, "/")
        );

        var client = new DbxClientV2(DropboxConfig.config, accessToken);
        session.setUser(user);
        session.setClientDropbox(client);
        session.setDropboxSession(client.users().getCurrentAccount());

        return client;
    }

    public FileMetadata uploadFileToDropbox(File localFile, String dropboxPath, DbxClientV2 dbxClient) throws DbxException, IOException {
        var targetPath = dropboxPath.equals("/") ? "/" + localFile.getName() : dropboxPath + "/" + localFile.getName();

        try (InputStream in = new FileInputStream(localFile)) {
            return dbxClient.files().uploadBuilder(targetPath)
                    .withMode(WriteMode.OVERWRITE)
                    .uploadAndFinish(in);
        }
    }
}