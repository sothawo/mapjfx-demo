/*
 Copyright 2015 Peter-Josef Meisch (pj.meisch@sothawo.com)

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

import com.sothawo.mapjfx.*;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private static final Coordinate coordKarlsruheSoccer = new Coordinate(49.020035, 8.412975);

    private static final Extent extentAllLocations = Extent.forCoordinates(coordKarlsruheCastle,
            coordKarlsruheHarbour, coordKarlsruheStation, coordKarlsruheSoccer);

    /** four markers */
    private final Marker markerKaHarbour;
    private final Marker markerKaCastle;
    private final Marker markerKaStation;
    private final Marker markerKaSoccer;
    private final Marker markerClick;

    public Controller() {
        // a couple of markers using the provided ones
        markerKaHarbour = Marker.createProvided(Marker.Provided.BLUE).setPosition(coordKarlsruheHarbour).setVisible(
                false);
        markerKaCastle = Marker.createProvided(Marker.Provided.GREEN).setPosition(coordKarlsruheCastle).setVisible(
                false);
        markerKaStation =
                Marker.createProvided(Marker.Provided.RED).setPosition(coordKarlsruheStation).setVisible(false);
        // no position for click marker yet
        markerClick = Marker.createProvided(Marker.Provided.ORANGE).setVisible(false);

        // a marker with a custom icon
        markerKaSoccer = new Marker(getClass().getResource("/ksc.png"), -20, -20).setPosition(coordKarlsruheSoccer)
                .setVisible(false);
    }

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
    /** button to set the map's center */
    private Button buttonKaSoccer;

    @FXML
    /** button to set the map's extent */
    private Button buttonAllLocations;

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

    /** Check button for harbour marker */
    @FXML
    private CheckBox checkKaHarbourMarker;

    /** Check button for castle marker */
    @FXML
    private CheckBox checkKaCastleMarker;

    /** Check button for harbour marker */
    @FXML
    private CheckBox checkKaStationMarker;

    /** Check button for soccer marker */
    @FXML
    private CheckBox checkKaSoccerMarker;

    /** Check button for harbour marker */
    @FXML
    private CheckBox checkClickMarker;

// -------------------------- OTHER METHODS --------------------------

    /**
     * called after the fxml is loaded and all objects are created.
     */
    public void initialize() {
        leftControls.setExpandedPane(optionsLocations);

        // set the controls to disabled, this will be changed when the MapView is intialized
        setControlsDisable(true);

        // wire up the location buttons
        buttonKaHarbour.setOnAction(event -> mapView.setCenter(coordKarlsruheHarbour));
        buttonKaCastle.setOnAction(event -> mapView.setCenter(coordKarlsruheCastle));
        buttonKaStation.setOnAction(event -> mapView.setCenter(coordKarlsruheStation));
        buttonKaSoccer.setOnAction(event -> mapView.setCenter(coordKarlsruheSoccer));

        buttonAllLocations.setOnAction(event -> mapView.setExtent(extentAllLocations));


        // wire the zoom button and connect the slider to the map's zoom
        buttonZoom.setOnAction(event -> mapView.setZoom(ZOOM_DEFAULT));
        sliderZoom.valueProperty().bindBidirectional(mapView.zoomProperty());

        // add a listener to the animationDuration field and make sure we only accept int values
        animationDuration.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                mapView.setAnimationDuration(0);
            } else {
                try {
                    mapView.setAnimationDuration(Integer.parseInt(newValue));
                } catch (NumberFormatException e) {
                    animationDuration.setText(oldValue);
                }
            }
        });
        animationDuration.setText("500");

        // bind the map's center and zoom properties to the corrsponding labels and format them
        labelCenter.textProperty().bind(Bindings.format("center: %s", mapView.centerProperty()));
        labelZoom.textProperty().bind(Bindings.format("zoom: %.0f", mapView.zoomProperty()));

        // watch the MapView's initialized property to finish initialization
        mapView.initializedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                logger.fine("setting center and enabling controls...");
                // start at the harbour with default zoom
                mapView.setZoom(ZOOM_DEFAULT);
                mapView.setCenter(coordKarlsruheHarbour);
                // now enable the controls
                setControlsDisable(false);
            }
        });

        // observe the map type radiobuttons
        mapTypeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine(() -> MessageFormat.format("map type toggled to {0}", newValue.toString()));
            MapType mapType = MapType.OSM;
            if (newValue == radioMsOSM) {
                mapType = MapType.OSM;
            } else if (newValue == radioMsMQ) {
                mapType = MapType.MAPQUEST_OSM;
            }
            mapView.setMapType(mapType);
        });
        mapTypeGroup.selectToggle(radioMsOSM);

        // add an event handler for singleclicks, set the click marker to the new position
        mapView.addEventHandler(CoordinateEvent.MAP_CLICKED, event -> {
            event.consume();
            if (markerClick.getVisible()) {
                markerClick.setPosition(event.getCoordinate());
            }
        });

        // add the markers to the map - they are still invisible
        mapView.addMarker(markerKaHarbour);
        mapView.addMarker(markerKaCastle);
        mapView.addMarker(markerKaStation);
        mapView.addMarker(markerKaSoccer);
        mapView.addMarker(markerClick);

        // add the graphics to the checkboxes
        checkKaHarbourMarker.setGraphic(
                new ImageView(new Image(markerKaHarbour.getImageURL().toExternalForm(), 16.0, 16.0, true, true)));
        checkKaCastleMarker.setGraphic(
                new ImageView(new Image(markerKaCastle.getImageURL().toExternalForm(), 16.0, 16.0, true, true)));
        checkKaStationMarker.setGraphic(
                new ImageView(new Image(markerKaStation.getImageURL().toExternalForm(), 16.0, 16.0, true, true)));
        checkKaSoccerMarker.setGraphic(
                new ImageView(new Image(markerKaSoccer.getImageURL().toExternalForm(), 16.0, 16.0, true, true)));
        checkClickMarker.setGraphic(
                new ImageView(new Image(markerClick.getImageURL().toExternalForm(), 16.0, 16.0, true, true)));

        // bind the checkboxes to the markers visibility
        checkKaHarbourMarker.selectedProperty().bindBidirectional(markerKaHarbour.visibleProperty());
        checkKaCastleMarker.selectedProperty().bindBidirectional(markerKaCastle.visibleProperty());
        checkKaStationMarker.selectedProperty().bindBidirectional(markerKaStation.visibleProperty());
        checkKaSoccerMarker.selectedProperty().bindBidirectional(markerKaSoccer.visibleProperty());
        checkClickMarker.selectedProperty().bindBidirectional(markerClick.visibleProperty());

        // finally initialize the map view
        mapView.initialize();
        logger.fine("initialization finished");
    }

    /**
     * enables / disables the different controls
     *
     * @param flag if true the controls are disabled
     */
    private void setControlsDisable(boolean flag) {
        topControls.setDisable(flag);
        leftControls.setDisable(flag);
    }
}
