package com.schimer.reportsapp.auth;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.FullAccount;
import com.dropbox.core.v2.users.GetAccountBatchErrorException;
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
    private FullAccount dropboxAccount;

    private UserSession(UserEntity userEntity) {
        this.userEntity = userEntity;
        if (userEntity.getDropboxAccount() != null){
            verifyDropboxAccount();
        }
    }

    private void verifyDropboxAccount() {
        clientDropbox = new DbxClientV2(DropboxConfig.config, userEntity.getDropboxAccount().getToken());
        try{
            dropboxAccount = clientDropbox.users().getCurrentAccount();
        } catch (DbxException e) {
            throw new RuntimeException(e);
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
