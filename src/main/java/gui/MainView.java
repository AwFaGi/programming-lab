package gui;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import stored.City;
import stored.Human;
import transfer.CmdTemplate;
import transfer.Responce;
import utils.ScriptFileProcessor;
import utils.Validator;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainView implements Initializable {

    @FXML
    private Label currentUser;

    @FXML
    private Accordion commandAccordion;

    @FXML
    private Group cityGroup;

    @FXML
    private TableColumn<City, Long> columnAglomeration;

    @FXML
    private TableColumn<City, Integer> columnArea;

    @FXML
    private TableColumn<City, String> columnCLimate;

    @FXML
    private TableColumn<City, Long> columnCoordX;

    @FXML
    private TableColumn<City, Float> columnCoordY;

    @FXML
    private TableColumn<City, Long> columnGovernorAge;

    @FXML
    private TableColumn<City, String> columnGovernorBirthday;

    @FXML
    private TableColumn<City, String> columnGovernorName;

    @FXML
    private TableColumn<City, Float> columnMetersAbove;

    @FXML
    private TableColumn<City, String> columnName;

    @FXML
    private TableColumn<City, Long> columnPopulation;

    @FXML
    private TableColumn<City, Integer> columnTimezone;

    @FXML
    private TableColumn<City, String> columnCreationDate;

    @FXML
    private TableColumn<City, Integer> columnId;

    @FXML
    private TableColumn<City, String> columnAuthor;

    @FXML
    private TableView<City> mainTable;

    @FXML
    private TextArea logArea;
    private ResourceBundle res;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.res = resources;

        columnName.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getName()));
        columnCoordX.setCellValueFactory(x -> new SimpleLongProperty(x.getValue().getCoordinates().getX()).asObject());
        columnCoordY.setCellValueFactory(x -> new SimpleFloatProperty(x.getValue().getCoordinates().getY()).asObject());
        columnArea.setCellValueFactory(x -> new SimpleIntegerProperty(x.getValue().getArea()).asObject());
        columnPopulation.setCellValueFactory(x -> new SimpleLongProperty(x.getValue().getPopulation()).asObject());
        columnMetersAbove.setCellValueFactory(x -> new SimpleFloatProperty(x.getValue().getMetersAboveSeaLevel()).asObject());
        columnTimezone.setCellValueFactory(x -> new SimpleIntegerProperty(x.getValue().getTimezone()).asObject());
        columnAglomeration.setCellValueFactory(x -> new SimpleLongProperty(x.getValue().getAgglomeration()).asObject());
        columnCLimate.setCellValueFactory(x -> new SimpleStringProperty(String.valueOf(x.getValue().getClimate())));
        columnGovernorName.setCellValueFactory(x -> {
            Human human = x.getValue().getGovernor();
            if (human == null){
                return null;
            } else {
                return new SimpleStringProperty(human.getName());
            }
        });
        columnGovernorAge.setCellValueFactory(x -> {
            Human human = x.getValue().getGovernor();
            if (human == null){
                return null;
            } else {
                return new SimpleLongProperty(human.getAge()).asObject();
            }
        });
        columnGovernorBirthday.setCellValueFactory(x -> {
            Human human = x.getValue().getGovernor();
            if(human == null){
                return null;
            } else {
                return new SimpleStringProperty(human.getBirthday().format(Validator.dtFormatter));
            }
        });
        columnAuthor.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getAuthor()));
        columnId.setCellValueFactory(x -> new SimpleIntegerProperty(x.getValue().getId()).asObject());
        columnCreationDate.setCellValueFactory(x -> {

            java.util.Date creationDate = x.getValue().getCreationDate();
            if (creationDate != null){
                return new SimpleStringProperty(Validator.sdFormatter.format(creationDate));
            }else{
                return null;
            }
        });

        mainTable.getItems().addAll(ClientGUI.getInstance().getCollection());
//        mainTable.setOnKeyReleased(event ->  {
//            if (event.getText().equals("DELETE")){
//                City city = mainTable.getSelectionModel().getSelectedItem();
//                CmdTemplate cmdTemplate = new CmdTemplate("remove_by_id", "", "", new String[]{"int"});
//                cmdTemplate.sendArg(String.valueOf(city.getId()));
//                ClientGUI.getInstance().sendRequests(cmdTemplate);
//                updateTable();
//            }
//            else if (event.getText().equals("BACKSPACE")){
//                City city = mainTable.getSelectionModel().getSelectedItem();
//                ClientGUI.getInstance().setTransferableObject(city);
//                ClientGUI.getInstance().createModal(ClientGUI.Scenes.CITY);
//                if (ClientGUI.getInstance().getTransferableObject() != null && ClientGUI.getInstance().getTransferableObject() instanceof City){
//                    City newCity = (City) ClientGUI.getInstance().getTransferableObject();
//                    CmdTemplate cmdTemplate = new CmdTemplate("update", "", "", new String[]{"int", "stored.City"});
//                    cmdTemplate.sendArg(String.valueOf(city.getId()));
//                    cmdTemplate.sendArg(newCity);
//                    ClientGUI.getInstance().sendRequests(cmdTemplate);
//                    updateTable();
//                }
//            }
//
//        });
        mainTable.setOnMouseClicked(event -> {
            if (event.isAltDown() && event.isShiftDown() && event.getClickCount() == 2){
                City city = mainTable.getSelectionModel().getSelectedItem();
                CmdTemplate cmdTemplate = new CmdTemplate("remove_by_id", "", "", new String[]{"int"});
                cmdTemplate.sendArg(String.valueOf(city.getId()));
                ClientGUI.getInstance().sendRequests(cmdTemplate);
                updateTable();
            }
            else if (event.isControlDown() && event.getClickCount() == 2){
                City city = mainTable.getSelectionModel().getSelectedItem();
                ClientGUI.getInstance().setTransferableObject(city);
                ClientGUI.getInstance().createModal(ClientGUI.Scenes.CITY);
                if (ClientGUI.getInstance().getTransferableObject() != null && ClientGUI.getInstance().getTransferableObject() instanceof City){
                    City newCity = (City) ClientGUI.getInstance().getTransferableObject();
                    CmdTemplate cmdTemplate = new CmdTemplate("update", "", "", new String[]{"int", "stored.City"});
                    cmdTemplate.sendArg(String.valueOf(city.getId()));
                    cmdTemplate.sendArg(newCity);
                    ClientGUI.getInstance().sendRequests(cmdTemplate);
                    updateTable();
                }
            }


        });

        updateGraph();

        currentUser.setText(currentUser.getText() + " " + ClientGUI.getInstance().HKEY_Current_user());

        ArrayList<CmdTemplate> list = ClientGUI.getInstance().getCommands();
        for (CmdTemplate i: list
             ) {

            CmdTemplate req = i.createCopy();
            Button button = new Button(i.name);
            button.setMnemonicParsing(false);
            button.setFont(Font.font("Courier new", 14));

            HBox hBox = new HBox();
            hBox.setSpacing(15);
            hBox.getChildren().add(button);

            if (i.requirements != null && i.requirements.length > 0){
                for (String j: i.requirements
                     ) {
                    if (j.equals("stored.City")){
                        Button city = new Button("Enter city");
                        city.setFont(Font.font("Courier new", 14));
                        city.setOnAction(event -> {
                            City newCity;
                            ClientGUI.getInstance().createModal(ClientGUI.Scenes.CITY);
                            newCity = (City) ClientGUI.getInstance().getTransferableObject();
                            if (newCity != null){
                                city.setStyle("-fx-background-color: lightgreen");
                            } else {
                                city.setStyle("-fx-background-color: red");
                            }
                            button.setUserData(newCity);
                        });
                        hBox.getChildren().add(city);
                    } else {
                        TextField textField = new TextField();
                        hBox.getChildren().add(textField);
                    }

                }
            }

            button.setOnAction(event -> {
                req.args.clear();
                for (Node node: hBox.getChildren()
                     ) {
                    if (node instanceof TextField){
                        req.sendArg(((TextField) node).getText());
                    }
                }
                if (req.countNeedArgType("stored.City") > 0){
                    if (button.getUserData() == null){
                        Alert alert = new Alert(Alert.AlertType.ERROR, res.getString("main.need_enter")+" City", ButtonType.OK);
                        alert.showAndWait();
                        return;
                    }
                    req.sendArg(button.getUserData());
                }
                Responce responce = ClientGUI.getInstance().sendRequests(req);

                logArea.setText(responce.message + "\n\n" + logArea.getText());
                if (responce.isError) {
                    logArea.setText("[ERROR] " + logArea.getText());
                }

                updateTable();

            });

            VBox vBox = new VBox();
            vBox.setSpacing(15);
            Label label = new Label(i.usage);
            label.setFont(Font.font("Courier new", 14));
            vBox.getChildren().add(label);
            vBox.getChildren().add(hBox);

            TitledPane titledPane = new TitledPane(i.name, vBox);
            commandAccordion.getPanes().add(titledPane);
        }

        Button button = new Button("execute_script");
        button.setMnemonicParsing(false);
        button.setFont(Font.font("Courier new", 14));

        HBox hBox = new HBox();
        hBox.setSpacing(15);
        hBox.getChildren().add(button);

        VBox vBox = new VBox();
        vBox.setSpacing(15);
        Label label = new Label("execute_script filename");
        label.setFont(Font.font("Courier new", 14));
        vBox.getChildren().add(label);
        vBox.getChildren().add(hBox);

        TitledPane titledPane = new TitledPane("execute_script", vBox);
        commandAccordion.getPanes().add(titledPane);

        button.setOnAction(event -> {
            File file = ClientGUI.getInstance().chooseFile();
            if (file != null) {
                try (FileReader fr = new FileReader(file)) {
                    ScriptFileProcessor sfp = new ScriptFileProcessor(fr, true);
                    sfp.execute_gui();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, res.getString("main.script_ready"), ButtonType.OK);
                    alert.showAndWait();
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                    alert.showAndWait();
                } finally {
                    updateTable();
                }
            }
        });
    }

    public void updateTable(){
        mainTable.getItems().clear();
        mainTable.getItems().addAll(ClientGUI.getInstance().getCollection());

        updateGraph();
    }

    public void updateGraph() {

        Line lineX = new Line(400, 0, 400, 600);
        Line lineY = new Line(0, 300, 800, 300);

        Pane pane = new Pane();
        pane.getChildren().addAll(lineX, lineY);

        for (City elem: mainTable.getItems()
        ) {
            double radius = elem.getArea()/3.14;
            Circle circle = new Circle(radius);
            if (elem.getAuthor().equals(ClientGUI.getInstance().HKEY_Current_user())){
                circle.setFill(Color.CYAN);
            } else {
                circle.setFill(Color.BLACK);
            }
            circle.relocate(400+elem.getCoordinates().getX(), 300+elem.getCoordinates().getY());
            circle.setOnMouseClicked(event -> {
                Paint old = circle.getFill();
                circle.setFill(Color.RED);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, elem.toString(), ButtonType.OK);
                alert.showAndWait();
                circle.setFill(old);
            });
            pane.getChildren().add(circle);
        }

        cityGroup.getChildren().clear();
        cityGroup.getChildren().add(pane);
    }

}
