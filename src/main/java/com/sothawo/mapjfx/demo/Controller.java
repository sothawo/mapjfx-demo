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

import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.CoordinateEvent;
import com.sothawo.mapjfx.CoordinateLine;
import com.sothawo.mapjfx.Extent;
import com.sothawo.mapjfx.MapLabel;
import com.sothawo.mapjfx.MapType;
import com.sothawo.mapjfx.MapView;
import com.sothawo.mapjfx.Marker;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Controller for the FXML defined code.
 *
 * @author P.J. Meisch (pj.meisch@sothawo.com).
 */
public class Controller {
// ------------------------------ FIELDS ------------------------------

    /** logger for the class. */
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    /** some coordinates from around town. */
    private static final Coordinate coordKarlsruheCastle = new Coordinate(49.013517, 8.404435);
    private static final Coordinate coordKarlsruheHarbour = new Coordinate(49.015511, 8.323497);
    private static final Coordinate coordKarlsruheStation = new Coordinate(48.993284, 8.402186);
    private static final Coordinate coordKarlsruheSoccer = new Coordinate(49.020035, 8.412975);
    private static final Coordinate coordKarlsruheUniversity = new Coordinate(49.011809, 8.413639);
    private static final Extent extentAllLocations = Extent.forCoordinates(coordKarlsruheCastle,
            coordKarlsruheHarbour, coordKarlsruheStation, coordKarlsruheSoccer);

    /** default zoom value. */
    private static final int ZOOM_DEFAULT = 14;

    /** the markers. */
    private final Marker markerKaHarbour;
    private final Marker markerKaCastle;
    private final Marker markerKaStation;
    private final Marker markerKaSoccer;
    private final Marker markerClick;

    /** the labels. */
    private final MapLabel labelKaUniversity;
    private final MapLabel labelKaCastle;
    private final MapLabel labelKaStation;
    private final MapLabel labelClick;

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

    /** the BIng Maps API Key. */
    @FXML
    private TextField bingMapsApiKey;

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

    /** RadioButton for MapStyle Bing Road */
    @FXML
    private RadioButton radioMsBR;

    /** RadioButton for MapStyle Bing Aerial */
    @FXML
    private RadioButton radioMsBA;

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

    /** Check button for click marker */
    @FXML
    private CheckBox checkClickMarker;

    /** the first CoordinateLine */
    private CoordinateLine trackMagenta;
    /** Check button for first track */
    @FXML
    private CheckBox checkTrackMagenta;

    /** the second CoordinateLine */
    private CoordinateLine trackCyan;
    /** Check button for first track */
    @FXML
    private CheckBox checkTrackCyan;

// --------------------------- CONSTRUCTORS ---------------------------

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

        // the fix label, default style
        labelKaUniversity = new MapLabel("university").setPosition(coordKarlsruheUniversity).setVisible(true);
        // the attached labels, custom style
        labelKaCastle = new MapLabel("castle", 10, -10).setVisible(false).setCssClass("green-label");
        labelKaStation = new MapLabel("station", 10, -10).setVisible(false).setCssClass("red-label");
        labelClick = new MapLabel("click!", 10, -10).setVisible(false).setCssClass("orange-label");

        markerKaCastle.attachLabel(labelKaCastle);
        markerKaStation.attachLabel(labelKaStation);
        markerClick.attachLabel(labelClick);
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * called after the fxml is loaded and all objects are created.
     */
    public void initialize() {
        logger.trace("begin initialize");

        // set the custom css file for the MapView
        mapView.setCustomMapviewCssURL(getClass().getResource("/custom_mapview.css"));

        leftControls.setExpandedPane(optionsLocations);

        // set the controls to disabled, this will be changed when the MapView is intialized
        setControlsDisable(true);

        // wire up the location buttons
        buttonKaHarbour.setOnAction(event -> mapView.setCenter(coordKarlsruheHarbour));
        buttonKaCastle.setOnAction(event -> mapView.setCenter(coordKarlsruheCastle));
        buttonKaStation.setOnAction(event -> mapView.setCenter(coordKarlsruheStation));
        buttonKaSoccer.setOnAction(event -> mapView.setCenter(coordKarlsruheSoccer));

        buttonAllLocations.setOnAction(event -> mapView.setExtent(extentAllLocations));
        logger.trace("location buttons done");

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

        // bind the map's center and zoom properties to the corresponding labels and format them
        labelCenter.textProperty().bind(Bindings.format("center: %s", mapView.centerProperty()));
        labelZoom.textProperty().bind(Bindings.format("zoom: %.0f", mapView.zoomProperty()));
        logger.trace("options and labels done");

        // watch the MapView's initialized property to finish initialization
        mapView.initializedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                afterMapIsInitialized();
            }
        });

        // observe the map type radiobuttons
        mapTypeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            logger.debug("map type toggled to {}", newValue.toString());
            MapType mapType = MapType.OSM;
            if (newValue == radioMsOSM) {
                mapType = MapType.OSM;
            } else if (newValue == radioMsMQ) {
                mapType = MapType.MAPQUEST_OSM;
            } else if (newValue == radioMsBR) {
                mapType = MapType.BINGMAPS_ROAD;
            } else if (newValue == radioMsBA) {
                mapType = MapType.BINGMAPS_AERIAL;
            }
            mapView.setBingMapsApiKey(bingMapsApiKey.getText());
            mapView.setMapType(mapType);
        });
        mapTypeGroup.selectToggle(radioMsOSM);

        // add an event handler for singleclicks, set the click marker to the new position when it's visible
        mapView.addEventHandler(CoordinateEvent.MAP_CLICKED, event -> {
            event.consume();
            if (markerClick.getVisible()) {
                boolean needToAddMarker = (null == markerClick.getPosition());
                markerClick.setPosition(event.getCoordinate());
                if (needToAddMarker) {
                    // adding can only be done after coordinate is set
                    mapView.addMarker(markerClick);
                }
            }
        });
        logger.trace("map handlers initialized");

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
        logger.trace("marker checks done");

        // load two coordinate lines
        trackMagenta = loadCoordinateLine(getClass().getResource("/M1.csv")).orElse(new CoordinateLine
                ()).setColor(Color.MAGENTA);
        trackCyan = loadCoordinateLine(getClass().getResource("/M2.csv")).orElse(new CoordinateLine
                ()).setColor(Color.CYAN).setWidth(7);
        logger.trace("tracks loaded");
        checkTrackMagenta.selectedProperty().bindBidirectional(trackMagenta.visibleProperty());
        checkTrackCyan.selectedProperty().bindBidirectional(trackCyan.visibleProperty());
        logger.trace("tracks checks done");
        // get the extent of both tracks
        Extent tracksExtent = Extent.forCoordinates(
                Stream.concat(trackMagenta.getCoordinateStream(), trackCyan.getCoordinateStream())
                        .collect(Collectors.toList()));
        ChangeListener<Boolean> trackVisibleListener =
                (observable, oldValue, newValue) -> mapView.setExtent(tracksExtent);
        trackMagenta.visibleProperty().addListener(trackVisibleListener);
        trackCyan.visibleProperty().addListener(trackVisibleListener);

        // finally initialize the map view
        logger.trace("start map initialization");
        mapView.initialize();
        logger.debug("initialization finished");
    }

    /**
     * enables / disables the different controls
     *
     * @param flag
     *         if true the controls are disabled
     */
    private void setControlsDisable(boolean flag) {
        topControls.setDisable(flag);
        leftControls.setDisable(flag);
    }

    /**
     * finishes setup after the mpa is initialzed
     */
    private void afterMapIsInitialized() {
        logger.trace("map intialized");
        logger.debug("setting center and enabling controls...");
        // start at the harbour with default zoom
        mapView.setZoom(ZOOM_DEFAULT);
        mapView.setCenter(coordKarlsruheHarbour);
        // add the markers to the map - they are still invisible
        mapView.addMarker(markerKaHarbour);
        mapView.addMarker(markerKaCastle);
        mapView.addMarker(markerKaStation);
        mapView.addMarker(markerKaSoccer);
        // can't add the markerClick at this moment, it has no position, so it would not be added to the map

        // add the fix label, the other's are attached to markers.
        mapView.addLabel(labelKaUniversity);

        // add the tracks
        mapView.addCoordinateLine(trackMagenta);
        mapView.addCoordinateLine(trackCyan);
        // now enable the controls
        setControlsDisable(false);
    }

    /**
     * load a coordinateLine from the given uri in lat;lon csv format
     *
     * @param url
     *         url where to load from
     * @return optional CoordinateLine object
     * @throws java.lang.NullPointerException
     *         if uri is null
     */
    private Optional<CoordinateLine> loadCoordinateLine(URL url) {
        try (
                Stream<String> lines = new BufferedReader(
                        new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)).lines()
        ) {
            return Optional.of(new CoordinateLine(
                    lines.map(line -> line.split(";")).filter(array -> array.length == 2)
                            .map(values -> new Coordinate(Double.valueOf(values[0]), Double.valueOf(values[1])))
                            .collect(Collectors.toList())));
        } catch (IOException | NumberFormatException e) {
            logger.error("load {}", url, e);
        }
        return Optional.empty();
    }
}
