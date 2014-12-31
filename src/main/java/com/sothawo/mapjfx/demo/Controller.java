/*
 Copyright 2014 Peter-Josef Meisch (pj.meisch@sothawo.com)

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.sothawo.mapjfx.demo;

import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.Extent;
import com.sothawo.mapjfx.MapType;
import com.sothawo.mapjfx.MapView;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.text.MessageFormat;
import java.util.logging.Logger;

/**
 * Controller for the FXML defined code.
 *
 * @author P.J. Meisch (pj.meisch@sothawo.com).
 */
public class Controller {
// ------------------------------ FIELDS ------------------------------

    /** logger for the class */
    private static final Logger logger = Logger.getLogger(Controller.class.getCanonicalName());

    /** some coordinates from around town */
    private static final Coordinate coordKarlsruheCastle = new Coordinate(49.013517, 8.404435);
    private static final Coordinate coordKarlsruheHarbour = new Coordinate(49.015511, 8.323497);
    private static final Coordinate coordKarlsruheStation = new Coordinate(48.993284, 8.402186);
    private static final Extent extentAllLocations = Extent.forCoordinates(coordKarlsruheCastle,
            coordKarlsruheHarbour, coordKarlsruheStation);
    /** default zoom value */
    private static final int ZOOM_DEFAULT = 14;

    @FXML
    /** button to set the map's zoom to 15*/
    private Button buttonZoom;

    /** the MapView containing the map */
    @FXML
    private MapView mapView;

    /** the box containing the top controls, must be enabled when mapView is initialized */
    @FXML
    private HBox topControls;

    /** Slider to change the zoom value */
    @FXML
    private Slider sliderZoom;

    /** Accordion for all the different options */
    @FXML
    private Accordion leftControls;

    /** section containing the location button */
    @FXML
    private TitledPane optionsLocations;

    @FXML
    /** button to set the map's center */
    private Button buttonKaHarbour;

    @FXML
    /** button to set the map's center */
    private Button buttonKaCastle;

    @FXML
    /** button to set the map's center */
    private Button buttonKaStation;

    @FXML
    /** button to set the map's extent */
    private Button buttonAllLocations;

    /** section miscellanous options */
    @FXML
    private TitledPane optionsMisc;

    /** section map type */
    @FXML
    private TitledPane optionsMapType;

    /** for editing the animation duration */
    @FXML
    private TextField animationDuration;

    /** Label to display the current center */
    @FXML
    private Label labelCenter;

    /** Label to display the current zoom */
    @FXML
    private Label labelZoom;

    /** RadioButton for MapStyle OSM */
    @FXML
    private RadioButton radioMsOSM;

    /** RadioButton for MapStyle MapQuest */
    @FXML
    private RadioButton radioMsMQ;

    /** ToggleGroup for the MapStyle radios */
    @FXML
    private ToggleGroup mapTypeGroup;

// -------------------------- OTHER METHODS --------------------------

    /**
     * called after the fxml is loaded and all objects are created.
     */
    public void initialize() {
        leftControls.setExpandedPane(optionsLocations);

        // set the controls to disabled, this will be changed when the MapView is intialized
        setControlsDisable(true);

        // wire up the buttons
        buttonKaHarbour.setOnAction(event -> mapView.setCenter(coordKarlsruheHarbour));
        buttonKaCastle.setOnAction(event -> mapView.setCenter(coordKarlsruheCastle));
        buttonKaStation.setOnAction(event -> mapView.setCenter(coordKarlsruheStation));

        buttonAllLocations.setOnAction(event -> mapView.setExtent(extentAllLocations));

        buttonZoom.setOnAction(event -> mapView.setZoom(ZOOM_DEFAULT));

        // connect the slider to the map's zoom
        sliderZoom.valueProperty().bindBidirectional(mapView.zoomProperty());

        // add a listener to the animationDuration field and make sure, we only accept int values
        animationDuration.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.isEmpty()) {
                    mapView.setAnimationDuration(0);
                } else {
                    try {
                        mapView.setAnimationDuration(Integer.parseInt(newValue));
                    } catch (NumberFormatException e) {
                        animationDuration.setText(oldValue);
                    }
                }
            }
        });
        animationDuration.setText("500");

        // bind the map's center and zoom properties to the label and format them
        labelCenter.textProperty().bind(Bindings.format("center: %s", mapView.centerProperty()));
        labelZoom.textProperty().bind(Bindings.format("zoom: %.0f", mapView.zoomProperty()));

        // watch the MapView's initialized property to finish initialization
        mapView.initializedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    logger.fine("setting center and enabling controls...");
                    // start at the harbour with default zoom
                    mapView.setZoom(ZOOM_DEFAULT);
                    mapView.setMapType(MapType.OSM);
                    mapView.setCenter(coordKarlsruheHarbour);
                    // now enable the controls
                    setControlsDisable(false);
                }
            }
        });

        // the different RadioButtons
        mapTypeGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                logger.fine(() -> MessageFormat.format("map type toggled to {0}", newValue.toString()));
                MapType mapType = MapType.OSM;
                if (newValue == radioMsOSM) {
                    mapType = MapType.OSM;
                } else if (newValue == radioMsMQ) {
                    mapType = MapType.MAPQUEST_OSM;
                }
                mapView.setMapType(mapType);
            }
        });
        mapTypeGroup.selectToggle(radioMsOSM);

        mapView.initialize();

        logger.fine("initialization finished");
    }

    /**
     * enables / disables the different controls
     *
     * @param flag
     */
    private void setControlsDisable(boolean flag) {
        topControls.setDisable(flag);
        leftControls.setDisable(flag);
    }
}
