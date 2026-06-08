package com.schimer.reportsapp.auth;

import com.dropbox.core.DbxException;
import com.dropbox.core.oauth.DbxCredential;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.FullAccount;
import com.schimer.reportsapp.domain.entities.UserEntity;
import com.schimer.reportsapp.infrastructure.dropbox.DropboxConfig;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSession {

    private static UserSession instance;
    private UserEntity userEntity;
    private DbxClientV2 clientDropbox;
    private FullAccount dropboxSession = null;

    private UserSession(UserEntity userEntity) {
        this.userEntity = userEntity;
        if (userEntity.getDropboxAccount() != null){
            verifyDropboxAccount();
        }
    }

    private void verifyDropboxAccount() {
        try{
            var refreshToken = userEntity.getDropboxAccount().getToken();
            var credential = new DbxCredential(
                    "",
                    -1L,
                    refreshToken,
                    DropboxConfig.APP_KEY,
                    DropboxConfig.APP_SECRET
            );
            clientDropbox = new DbxClientV2(DropboxConfig.config, credential);
            dropboxSession = clientDropbox.users().getCurrentAccount();
        } catch (DbxException e) {
            //TODO
        }
    }

    public static void login(UserEntity userEntity) {
        instance = new UserSession(userEntity);
    }

    public static void logout() {
        instance = null;
    }

    public static UserSession getInstance() {
        if (instance == null) return  null;
        return instance;
    }

    public UserEntity getUser() {
        return userEntity;
    }

    public void setUser(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
