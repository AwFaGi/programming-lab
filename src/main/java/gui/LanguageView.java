package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Locale;

public class LanguageView {

    @FXML
    private ImageView imgHU;

    @FXML
    private ImageView imgPO;

    @FXML
    private ImageView imgRU;

    @FXML
    private ImageView imgSP;

    @FXML
    private Button toStartLink;

    @FXML
    void returnStartFromLang(ActionEvent event) {
        ClientGUI.getInstance().setScene(ClientGUI.Scenes.WELCOME);
    }

    @FXML
    public void initialize() {
        imgRU.setImage(new Image("gui/images/Flag_of_Russia.jpg"));
        imgHU.setImage(new Image("gui/images/Flag_of_Hungary.jpg"));
        imgPO.setImage(new Image("gui/images/Flag_of_Portugal.jpg"));
        imgSP.setImage(new Image("gui/images/Flag_of_Spain.jpg"));

    }

    @FXML
    void setHungary(MouseEvent event) {
        ClientGUI.getInstance().setLocale(new Locale("HU"));
        ClientGUI.getInstance().setScene(ClientGUI.Scenes.WELCOME);
    }

    @FXML
    void setPortugal(MouseEvent event) {
        ClientGUI.getInstance().setLocale(new Locale("PT"));
        ClientGUI.getInstance().setScene(ClientGUI.Scenes.WELCOME);
    }

    @FXML
    void setRussian(MouseEvent event) {
        ClientGUI.getInstance().setLocale(new Locale("RU"));
        ClientGUI.getInstance().setScene(ClientGUI.Scenes.WELCOME);
    }

    @FXML
    void setSpain(MouseEvent event) {
        ClientGUI.getInstance().setLocale(new Locale("ES"));
        ClientGUI.getInstance().setScene(ClientGUI.Scenes.WELCOME);
    }



}
