package com.schimer.reportsapp.controllers.guest.profile;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.schimer.reportsapp.App;
import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.controllers.components.BaseSectionInfo;
import com.schimer.reportsapp.services.AuthService;
import com.schimer.reportsapp.ui.components.WindowsUtils;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;


public class SelectFolderDropboxController {
    @FXML private ListView<String> folderListView;
    @FXML private Button btnUp;
    @FXML private Button btnSelect;
    @FXML private Label lblCurrentPath;
    @FXML
    private Node sectionInfo;
    @FXML
    private BaseSectionInfo sectionInfoController;

    private DbxClientV2 dbxClient;
    private String currentPath = "";
    private final AuthService authService = new AuthService();
    private final UserSession session = UserSession.getInstance();

    public void setDropboxClient() {
        this.dbxClient = session.getClientDropbox();
        this.currentPath = "";
        loadDropboxFolders(currentPath);
    }

    @FXML
    public void initialize() {
        setDropboxClient();
        folderListView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                String selectedFolder = folderListView.getSelectionModel().getSelectedItem();
                if (selectedFolder != null) {
                    currentPath = selectedFolder;
                    loadDropboxFolders(currentPath);
                }
            }
        });
        initializeSectionInfo();
    }

    private void initializeSectionInfo() {
        if (sectionInfoController != null){
            sectionInfoController.getTitleModule().setText("Dropbox");
            sectionInfoController.getDescriptionModule().setText("Esta seccion esta destinada a la administracion\n de la cuenta de dropbox");
        }
    }

    private void loadDropboxFolders(String path) {
        folderListView.setDisable(true);
        btnUp.setDisable(true);

        lblCurrentPath.setText(path.isEmpty() ? "Dropbox Root /" : path);

        Task<ListFolderResult> task = new Task<>() {
            @Override
            protected ListFolderResult call() throws DbxException {
                return dbxClient.files().listFolder(path);
            }
        };

        task.setOnSucceeded(e -> {
            folderListView.getItems().clear();
            var result = task.getValue();

            for (Metadata metadata : result.getEntries()) {
                if (metadata instanceof FolderMetadata) {
                    folderListView.getItems().add(metadata.getPathDisplay());
                }
            }

            folderListView.setDisable(false);
            btnUp.setDisable(path.isEmpty());
        });

        task.setOnFailed(e -> {
            var error = task.getException();
            error.printStackTrace();
            lblCurrentPath.setText("Error loading Dropbox folders.");
            folderListView.setDisable(false);
            btnUp.setDisable(path.isEmpty());
        });

        new Thread(task).start();
    }

    @FXML
    private void handleGoUp() {
        if (currentPath.isEmpty() || currentPath.equals("/")) {
            return;
        }

        int lastSeparator = currentPath.lastIndexOf("/");
        if (lastSeparator <= 0) {
            currentPath = "";
        } else {
            currentPath = currentPath.substring(0, lastSeparator);
        }

        loadDropboxFolders(currentPath);
    }

    @FXML
    private void handleSelectFolder() {
        var finalSelectedFolder = folderListView.getSelectionModel().getSelectedItem();

        if (finalSelectedFolder == null) {
            finalSelectedFolder = currentPath;
        }

        updateDropboxFolder();
    }

    private void updateDropboxFolder() {
        try{
            var dropboxAccount = session.getUser().getDropboxAccount();
            dropboxAccount.setPath(currentPath);
            authService.updateDropboxInfo(
                    session.getUser(),
                    dropboxAccount
            );
            handleCancel();
        }catch (Exception ex){
            WindowsUtils.showAlertErrorSystem();
        }
    }

    @FXML
    private void handleCancel() {
        try{
            App.setRoot("views/guest/products-finished-list");
        }catch (Exception ex){
            WindowsUtils.showAlertErrorSystem();
        }
    }
}
