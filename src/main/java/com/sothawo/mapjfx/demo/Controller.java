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
import com.sothawo.mapjfx.MapView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

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
    /** default zoom value */
    private static final int ZOOM_DEFAULT = 14;

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

    /** section miscellanous options */
    @FXML
    private TitledPane optionsMisc;

    /** for editing the animation duration */
    @FXML
    private TextField animationDuration;

    /** Label to display the current center */
    @FXML
    private Label labelCenter;

    /** Label to display the current zoom */
    @FXML
    private Label labelZoom;

// -------------------------- OTHER METHODS --------------------------

    /**
     * called after the fxml is loaded and all objects are created.
     */
    public void initialize() {
        leftControls.setExpandedPane(optionsMisc);

        // set the controls to disabled, this will be changed when the MapView is intialized
        setControlsDisable(true);

        // wire up the buttons
        buttonKaHarbour.setOnAction(event -> mapView.setCenter(coordKarlsruheHarbour));
        buttonKaCastle.setOnAction(event -> mapView.setCenter(coordKarlsruheCastle));
        buttonKaStation.setOnAction(event -> mapView.setCenter(coordKarlsruheStation));
        buttonZoom.setOnAction(event -> mapView.setZoom(ZOOM_DEFAULT));

        // connect the slider to the map's zoom
        sliderZoom.valueProperty().bindBidirectional(mapView.zoomProperty());

        // add a listener to the animationDuration field and make sure, we only accept int values
        animationDuration.setText("500");
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

        // add an observer for the MapView's center property to adjust the corresponding label
        mapView.centerProperty().addListener(new ChangeListener<Coordinate>() {
            @Override
            public void changed(ObservableValue<? extends Coordinate> observable, Coordinate oldValue,
                                Coordinate newValue) {
                labelCenter.setText(newValue == null ? "" : ("center: " + newValue.toString()));
            }
        });

        // add an observer for the MapView's zoom property to adjust the corresponding label
        mapView.zoomProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                labelZoom.setText(null == newValue ? "" : ("zoom: " + newValue.toString()));
            }
        });

        // watch the MapView's initialized property to finish initialization
        mapView.initializedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    logger.fine("setting center and enabling controls...");
                    // start at the harbour with default zoom
                    mapView.setCenter(coordKarlsruheHarbour);
                    mapView.setZoom(ZOOM_DEFAULT);
                    // now enable the controls
                    setControlsDisable(false);
                }
            }
        });

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
