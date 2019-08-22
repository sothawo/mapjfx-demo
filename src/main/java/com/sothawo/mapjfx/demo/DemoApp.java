/*
 Copyright 2015-2019 Peter-Josef Meisch (pj.meisch@sothawo.com)

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

import com.sothawo.mapjfx.Projection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demo application for the mapjfx component.
 *
 * @author P.J. Meisch (pj.meisch@sothawo.com).
 */
public class DemoApp extends Application {

    /** Logger for the class */
    private static final Logger logger = LoggerFactory.getLogger(DemoApp.class);

    public static void main(String[] args) {
        logger.trace("begin main");
        launch(args);
        logger.trace("end main");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.info("starting DemoApp");
        String fxmlFile = "/fxml/DemoApp.fxml";
        logger.debug("loading fxml file {}", fxmlFile);
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        logger.trace("stage loaded");

        final Controller controller = fxmlLoader.getController();
        final Projection projection = getParameters().getUnnamed().contains("wgs84")
            ? Projection.WGS_84 : Projection.WEB_MERCATOR;
        controller.initMapAndControls(projection);

        Scene scene = new Scene(rootNode);
        logger.trace("scene created");

        primaryStage.setTitle("sothawo mapjfx demo application");
        primaryStage.setScene(scene);
        logger.trace("showing scene");
        primaryStage.show();

        logger.debug("application start method finished.");
    }
}
