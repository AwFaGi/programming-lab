package gui;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import transfer.CmdTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    @FXML
    private Button regButton;

    @FXML
    private TextField regLogin;

    @FXML
    private PasswordField regPassword;

    @FXML
    private Button signinButton;

    @FXML
    private TextField signinLogin;

    @FXML
    private PasswordField signinPassword;

    @FXML
    private Hyperlink welcomeLangChange;
    private ResourceBundle res;

    @FXML
    public void initialize(){
        Object smth = ClientGUI.getInstance().getTransferableObject();
    }

    @FXML
    void askLang(ActionEvent event) {
        ClientGUI.getInstance().setScene(ClientGUI.Scenes.LANGUAGE);
    }

    @FXML
    void doRegister(ActionEvent event) {
        if (regLogin.getText().isEmpty() || regPassword.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, res.getString("registration_failed"), ButtonType.OK);
            alert.showAndWait();
            return;
        }
        CmdTemplate cmdTemplate = new CmdTemplate("register", regLogin.getText(), regPassword.getText());
        boolean loggedIn = ClientGUI.getInstance().sendAuthRequest(cmdTemplate);
        if (loggedIn){
            ClientGUI.getInstance().setScene(ClientGUI.Scenes.MAIN);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, res.getString("registration_failed"), ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    void doSignin(ActionEvent event) {
        if (signinLogin.getText().isEmpty() || signinPassword.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, res.getString("authorisation_failed"), ButtonType.OK);
            alert.showAndWait();
            return;
        }
        CmdTemplate cmdTemplate = new CmdTemplate("login", signinLogin.getText(), signinPassword.getText());
        boolean loggedIn = ClientGUI.getInstance().sendAuthRequest(cmdTemplate);
        if (loggedIn){
            ClientGUI.getInstance().setScene(ClientGUI.Scenes.MAIN);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, res.getString("authorisation_failed"), ButtonType.OK);
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.res = resources;
    }
}
