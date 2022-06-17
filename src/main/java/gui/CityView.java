package gui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import stored.City;
import stored.Climate;
import stored.Coordinates;
import stored.Human;
import utils.Validator;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CityView implements Initializable {

    @FXML
    private TextField cityAgglomeration;

    @FXML
    private TextField cityArea;

    @FXML
    private ChoiceBox<Climate> cityClimate;

    @FXML
    private CheckBox cityGovernor;

    @FXML
    private Button cityInputCancel;

    @FXML
    private Button cityInputEnd;

    @FXML
    private TextField cityMetersAboveSea;

    @FXML
    private TextField cityName;

    @FXML
    private TextField cityPopulation;

    @FXML
    private TextField cityTimezone;

    @FXML
    private TextField cityX;

    @FXML
    private TextField cityY;

    @FXML
    private VBox governorBlock;

    @FXML
    private TextField governorAge;

    @FXML
    private DatePicker governorBirthday;

    @FXML
    private TextField governorName;

    private ResourceBundle res;

    @FXML
    void cancelCity(ActionEvent event) {
        ClientGUI.getInstance().setTransferableObject(null);
        ((Node)event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    void checkCity(ActionEvent event) {
        String textCityName = cityName.getText();
        String textCityX = cityX.getText();
        String textCityY = cityY.getText();
        String textCityArea = cityArea.getText();
        String textCityPopulation = cityPopulation.getText();
        String textCityMetersAboveSea = cityMetersAboveSea.getText();
        String textCityTimezone = cityTimezone.getText();
        String textCityAgglomeration = cityAgglomeration.getText();
        Climate textCityClimate = cityClimate.getValue();
        String textGovernorName = governorName.getText();
        String textGovernorAge = governorAge.getText();
        LocalDate textGovernorBirthday = governorBirthday.getValue();

        String log_text = "";
        boolean valid = true;

        City city = new City();

        if(!Validator.validateName(textCityName)){
            log_text = log_text + res.getString("city.error_in_field") + " " + res.getString("city.city_name") + "\n";
            valid = false;
        } else {
            city.setName(textCityName);
        }

        Coordinates coordinates = new Coordinates();

        try{
            Long textCityXValue = Long.parseLong(textCityX);
            if(!Validator.validateCoordinateX(textCityXValue)){
                log_text = log_text + res.getString("city.error_in_field") + " " + res.getString("city.coord_x") + "\n";
                valid = false;
            } else {
                coordinates.setX(textCityXValue);
            }
        } catch (NumberFormatException e){
            log_text = log_text + res.getString("city.error_in_field") + " " + res.getString("city.coord_x") + "\n";
            valid = false;
        }

        try{
            Float textCityYValue = Float.parseFloat(textCityY);
            if(!Validator.validateCoordinateY(textCityYValue)){
                log_text = log_text + res.getString("city.error_in_field") + " " + res.getString("city.coord_y") + "\n";
                valid = false;
            } else {
                coordinates.setY(textCityYValue);
            }
        } catch (NumberFormatException e){
            log_text = log_text + res.getString("city.error_in_field") + " " + res.getString("city.coord_y") + "\n";
            valid = false;
        }

        city.setCoordinates(coordinates);

        try{
            Integer textAreaValue = Integer.parseInt(textCityArea);
            if(!Validator.validateArea(textAreaValue)){
                log_text = log_text + res.getString("city.error_in_field") + " " + res.getString("city.area") + "\n";
                valid = false;
            } else {
                city.setArea(textAreaValue);
            }
        } catch (NumberFormatException e){
            log_text = log_text + res.getString("city.error_in_field") + " " + res.getString("city.area") + "\n";
            valid = false;
        }

        try{
            Long textPopulationValue = Long.parseLong(textCityPopulation);
            if(!Validator.validatePopulation(textPopulationValue)){
                log_text = log_text + res.getString("city.error_in_field") + " " + res.getString("city.population") + "\n";
                valid = false;
            } else {
                city.setPopulation(textPopulationValue);
            }
        } catch (NumberFormatException e){
            log_text = log_text + res.getString("city.error_in_field") + " " + res.getString("city.population") + "\n";
            valid = false;
        }

        try{
            Float textCityMetersAboveSeaValue = Float.parseFloat(textCityMetersAboveSea);
            city.setMetersAboveSeaLevel(textCityMetersAboveSeaValue);
        } catch (NumberFormatException e){
            log_text = log_text + res.getString("city.error_in_field") + " " + res.getString("city.meters_above") + "\n";
            valid = false;
        }

        try{
            Integer textCityTimezoneValue = Integer.parseInt(textCityTimezone);
            if(!Validator.validateTimezone(textCityTimezoneValue)){
                log_text = log_text + res.getString("city.error_in_field") + " " + res.getString("city.timezone") + "\n";
                valid = false;
            } else {
                city.setTimezone(textCityTimezoneValue);
            }
        } catch (NumberFormatException e){
            log_text = log_text + res.getString("city.error_in_field") + " " + res.getString("city.timezone") + "\n";
            valid = false;
        }

        try{
            Long textCityAgglomerationValue = Long.parseLong(textCityAgglomeration);
            city.setAgglomeration(textCityAgglomerationValue);

        } catch (NumberFormatException e){
            log_text = log_text + res.getString("city.error_in_field") + " " + res.getString("city.agglomeration") + "\n";
            valid = false;
        }

        city.setClimate(textCityClimate);

        if (cityGovernor.isSelected()) {
            Human human = new Human();

            if(!Validator.validateGovernorName(textGovernorName)){
                log_text = log_text + res.getString("city.error_in_field") + " " + res.getString("city.governor_name") + "\n";
                valid = false;
            } else {
                human.setName(textGovernorName);
            }

            try{
                Long textGovernorAgeValue = Long.parseLong(textGovernorAge);
                if(!Validator.validateGovernorAge(textGovernorAgeValue)){
                    log_text = log_text + res.getString("city.error_in_field") + " " + res.getString("city.governor_age") + "\n";
                    valid = false;
                } else {
                    human.setAge(textGovernorAgeValue);
                }
            } catch (NumberFormatException e){
                log_text = log_text + res.getString("city.error_in_field") + " " + res.getString("city.governor_age") + "\n";
                valid = false;
            }
            try{
                LocalDateTime dateTime = LocalDateTime.of(textGovernorBirthday, LocalTime.MIDNIGHT);
                human.setBirthday(dateTime);
            } catch (Exception e){
                e.printStackTrace();
                log_text = log_text + res.getString("city.error_in_field") + " " + res.getString("city.governor_age") + "\n";
                valid = false;
            }
            city.setGovernor(human);

        } else {
            city.setGovernor(null);

        }

        if (valid){
            ClientGUI.getInstance().setTransferableObject(city);
            ((Node)event.getSource()).getScene().getWindow().hide();
        } else {
            ClientGUI.getInstance().setTransferableObject(null);
            Alert alert = new Alert(Alert.AlertType.ERROR, log_text, ButtonType.OK);
            alert.showAndWait();
        }
    }


    @FXML
    void updateGovernorWidgets(MouseEvent event) {
        if (cityGovernor.isSelected()) {
            governorBlock.setDisable(false);
        } else {
            governorBlock.setDisable(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.res = resources;

        cityClimate.setItems(FXCollections.observableArrayList(Climate.values()));
        cityName.setTooltip(new Tooltip("String"));
        cityX.setTooltip(new Tooltip("Long"));
        cityY.setTooltip(new Tooltip("Float, max: 960"));
        cityArea.setTooltip(new Tooltip("Int, min: 1"));
        cityPopulation.setTooltip(new Tooltip("Long, min: 1"));
        cityMetersAboveSea.setTooltip(new Tooltip("Float"));
        cityTimezone.setTooltip(new Tooltip("Int, min: -13, max: 15"));
        cityAgglomeration.setTooltip(new Tooltip("Long"));
        cityClimate.setTooltip(new Tooltip("Climate"));
        governorName.setTooltip(new Tooltip("String"));
        governorAge.setTooltip(new Tooltip("Long, min: 1"));
        governorBirthday.setTooltip(new Tooltip(""));

        Object smth = ClientGUI.getInstance().getTransferableObject();
        if (smth instanceof City){
            City city = (City) smth;
            cityName.setText(city.getName());
            cityX.setText(String.valueOf(city.getCoordinates().getX()));
            cityY.setText(String.valueOf(city.getCoordinates().getY()));
            cityArea.setText(String.valueOf(city.getArea()));
            cityPopulation.setText(String.valueOf(city.getPopulation()));
            cityMetersAboveSea.setText(String.valueOf(city.getMetersAboveSeaLevel()));
            cityTimezone.setText(String.valueOf(city.getTimezone()));
            cityAgglomeration.setText(String.valueOf(city.getAgglomeration()));
            if (city.getClimate() != null){
                cityClimate.setValue(city.getClimate());
            }
            if (city.getGovernor() != null){
                governorBlock.setDisable(false);
                cityGovernor.setSelected(true);
                governorName.setText(city.getGovernor().getName());
                governorAge.setText(String.valueOf(city.getGovernor().getAge()));
                governorBirthday.setValue(city.getGovernor().getBirthday().toLocalDate());
            }

        }
    }
}
