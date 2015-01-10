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

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * Demo application for the mapjfx component.
 *
 * @author P.J. Meisch (pj.meisch@sothawo.com).
 */
public class DemoApp extends Application {
// ------------------------------ FIELDS ------------------------------

    /** Logger for the class */
    private static final Logger logger;

    // -------------------------- STATIC METHODS --------------------------
    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        logger = LoggerFactory.getLogger(DemoApp.class);
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.info("starting DemoApp");
        String fxmlFile = "/fxml/DemoApp.fxml";
        logger.debug("loading fxml file {}", fxmlFile);
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));

        Scene scene = new Scene(rootNode);

        primaryStage.setTitle("sothawo mapjfx demo application");
        primaryStage.setScene(scene);
        primaryStage.show();

        logger.debug("application start method finished.");
    }

// --------------------------- main() method ---------------------------

    public static void main(String[] args) {
        launch(args);
    }
}
