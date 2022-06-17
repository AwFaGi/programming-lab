package gui;

import client.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import stored.City;
import transfer.CmdTemplate;
import transfer.Responce;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

public class ClientGUI extends Application {

    private static ClientGUI INSTANCE;
    public static ClientGUI getInstance() {
        return INSTANCE;
    }

    private Stage primaryStage;
    private InnerClient innerClient;
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("localization.lang", Locale.getDefault());
    private Object transferableObject;

    private static String login;
    private static String password;
    private static boolean loggedIn = false;

    public static void main(String[] args) {
        launch(args);
    }

    public void setLocale(Locale locale) {
        this.resourceBundle = ResourceBundle.getBundle("localization.lang", locale);
    }

    public enum Scenes {
        WELCOME("/gui/welcome-view.fxml"),
        LANGUAGE("/gui/language-view.fxml"),
        CITY("/gui/city-view.fxml",500, 750),
        MAIN("/gui/main-view.fxml", 1280, 720),
        ;

        private final String fileName;
        private final int width;
        private final int height;

        Scenes(String fileName){
            this.fileName = fileName;
            this.width = 800;
            this.height = 600;
        }
        Scenes(String fileName, int width, int height){
            this.fileName = fileName;
            this.width = width;
            this.height = height;
        }

        public String getFileName(){
            return fileName;
        };
        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

    @Override
    public void init() throws Exception {
        INSTANCE = this;
        innerClient = new InnerClient();

    }

    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        try {
            innerClient.requestCommands();
        } catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
            Platform.exit();
        }
        this.primaryStage = primaryStage;
        primaryStage.setTitle("City Manager");
        primaryStage.setResizable(false);
        setScene(Scenes.WELCOME);
        primaryStage.show();

    }

    public void createModal(Scenes scenes) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader(ClientGUI.class.getResource(scenes.getFileName()));
        fxmlLoader.setResources(resourceBundle);
        try {
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            System.err.println("Cannot change scene!");
        }
    }

    public void setScene(Scenes scenes) {

        FXMLLoader fxmlLoader = new FXMLLoader(ClientGUI.class.getResource(scenes.getFileName()));
        fxmlLoader.setResources(resourceBundle);
        try {
            Scene scene = new Scene(fxmlLoader.load(), scenes.getWidth(), scenes.getHeight());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            System.err.println("Cannot change scene!");
            e.printStackTrace();
        }
    }

    public void setScene(Scenes scenes, Object object){
        setTransferableObject(object);
        setScene(scenes);
    }

    public File chooseFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Execute script");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        return fileChooser.showOpenDialog(primaryStage);
    }

    public Object getTransferableObject() {
        return transferableObject;
    }

    public void setTransferableObject(Object transferableObject) {
        this.transferableObject = transferableObject;
    }

    public boolean sendAuthRequest(CmdTemplate cmdTemplate){
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 8);
        try{
            boolean logged = innerClient.sendCommandAndCheckAnswer(cmdTemplate, buffer);
            if (logged){
                login = cmdTemplate.getUsername();
                password = cmdTemplate.getPassword();
                loggedIn = true;
                innerClient.requestObjects();
            }
            return logged;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Responce sendRequests(CmdTemplate cmdTemplate){
        cmdTemplate.updateAuth(login, password);
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 8);
        try{
            Responce responce = innerClient.sendCommandAndGetAnswer(cmdTemplate, buffer);
            innerClient.requestObjects();
            return responce;
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
            return null;
        }
    }

    public TreeSet<City> getCollection(){
        TreeSet<City> city = new TreeSet<>(Comparator.comparing(City::getId));
        city.addAll(innerClient.collection);
        return city;
    }

    public ArrayList<CmdTemplate> getCommands(){
        return new ArrayList<>(innerClient.hehCommands);
    }

    public String HKEY_Current_user(){
        return login;
    }

    public String HKEY_Local_machine(){
        return password;
    }

}
