package dy.fi.maja.mesgui.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dy.fi.maja.mesgui.gui.FileListener.FileListenerActions;
import dy.fi.maja.mesgui.gui.Settings.ConnectionType;
import dy.fi.maja.mesgui.models.Order;
import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.shape.Arc;
import javafx.stage.FileChooser;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author k1400284
 */
public class MainPageController implements Initializable
{
    // GUI ELEMENTS
    @FXML
    private Arc loadingIndicator;
    @FXML
    private TextField dbPathTextField;
    @FXML
    private TextField mQTTUrlTextField;
    @FXML
    private PasswordField mQTTPasswordTextField;
    @FXML
    private TextField mQTTUsernameTextField;
    @FXML
    private TextField mQTTPortTextField;
    @FXML
    private TextField serverUrlTextField;
    @FXML
    private Button fileChooserButton;
    @FXML
    private RadioButton mqttRadioButton;
    @FXML
    private RadioButton serverRadioButton;
    @FXML
    private Button serviceStartButton;
    @FXML
    private TableView dataTable;
    
    
    // Class objects
    private Settings appSettings;
    public static MainPageController _instance;
    private AccessDBHandler accessDBHandler;
    private static FileListener fileListener;
    private boolean isRunning = false;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        _instance = this;
        // Start indicator animation and hide indicator
        animateIndicator();
        loadingIndicator.setVisible(false);
        
        // Load appsettings
        this.appSettings = Settings.getSettings();
        this.dbPathTextField.setText(appSettings.getDatabasePath());
        this.mQTTUrlTextField.setText(appSettings.getMqttUrl());
        this.mQTTUsernameTextField.setText(appSettings.getMqttUsername());
        this.mQTTPasswordTextField.setText(appSettings.getMqttPassword());
        this.mQTTPortTextField.setText(String.valueOf(appSettings.getMqttPort()));
        this.serverUrlTextField.setText(appSettings.getServerUrl());
        if(appSettings.getConnectionType().equals(ConnectionType.MQTT))
            this.mqttRadioButton.setSelected(true);
        else
            this.serverRadioButton.setSelected(true);
        
        // Add radiobuttons to group
        ToggleGroup group = new ToggleGroup();
        this.mqttRadioButton.setToggleGroup(group);
        this.serverRadioButton.setToggleGroup(group);
        disableFieldsByConnectionType(appSettings.getConnectionType());
        
        // Initialize table
        initializeTableColumns();
        // Add field listeners
        addChangedListenerToFields();
    }    
    
    private void animateIndicator()
    {
        KeyValue kv = new KeyValue(loadingIndicator.startAngleProperty(), 360);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        Timeline timeLine = new Timeline();
        timeLine.setAutoReverse(false);
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.getKeyFrames().add(kf);
        timeLine.play();
    }
    
    private void disableFieldsByConnectionType(ConnectionType type)
    {
        if(type.equals(ConnectionType.MQTT))
        {
            this.mQTTPasswordTextField.setDisable(false);
            this.mQTTPortTextField.setDisable(false);
            this.mQTTUrlTextField.setDisable(false);
            this.mQTTUsernameTextField.setDisable(false);
            this.serverUrlTextField.setDisable(true);
        }
        else
        {
            this.mQTTPasswordTextField.setDisable(true);
            this.mQTTPortTextField.setDisable(true);
            this.mQTTUrlTextField.setDisable(true);
            this.mQTTUsernameTextField.setDisable(true);
            this.serverUrlTextField.setDisable(false);
        }
    }
    
    private void initializeTableColumns()
    {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        List<TableColumn> columns = new ArrayList<>();
        
        TableColumn<Order, String> onoColumn = new TableColumn("ONo");
        onoColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(data.getValue().getoNo())));
        onoColumn.prefWidthProperty().bind(this.dataTable.widthProperty().multiply(0.1));
        onoColumn.minWidthProperty().set(60);
        columns.add(onoColumn);
        
        TableColumn<Order, String> startColumn = new TableColumn("Start");
        startColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getStart() != null ? formatter.format(data.getValue().getStart()) : ""));
        startColumn.prefWidthProperty().bind(this.dataTable.widthProperty().multiply(0.2));
        startColumn.minWidthProperty().set(150);
        columns.add(startColumn);
        
        TableColumn<Order, String> stateColumn = new TableColumn("State");
        stateColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getState() == 1 ? "Under construction" : "Waiting"));
        stateColumn.prefWidthProperty().bind(this.dataTable.widthProperty().multiply(0.2));
        stateColumn.minWidthProperty().set(150);
        columns.add(stateColumn);
        
        TableColumn<Order, String> enabledColumn = new TableColumn("Enabled");
        enabledColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().isEnabled() == true ? "Enabled" : "Disabled"));
        enabledColumn.prefWidthProperty().bind(this.dataTable.widthProperty().multiply(0.2));
        enabledColumn.minWidthProperty().set(150);
        columns.add(enabledColumn);
        
        TableColumn<Order, String> currentStepColumn = new TableColumn("Current step");
        currentStepColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(
                data.getValue().getMostRecentStep() == null
                        || data.getValue().getMostRecentStep().getDescription() == null
                        ? "" : data.getValue().getMostRecentStep().getDescription()));
        currentStepColumn.prefWidthProperty().bind(this.dataTable.widthProperty().multiply(0.3));
        currentStepColumn.minWidthProperty().set(180);
        columns.add(currentStepColumn);
        
        this.dataTable.getColumns().addAll(columns);
    }
    
    private void startBackgroundServices()
    {
        Runnable r = new Runnable() {
            @Override
            public void run()
            {
                // Show loading spinner and disable start-button
                MainPageController._instance.loadingIndicator.setVisible(true);
                MainPageController._instance.serviceStartButton.setDisable(true);
                
                // create db-handler
                accessDBHandler = new AccessDBHandler(appSettings.getDatabasePath());
                // Initialize orderComparer
                OrderComparer.initialize(accessDBHandler);
                // Update table
                Order[] existingOrders = accessDBHandler.getAllOrders();
                updateList(existingOrders);
                // Initialize networkConnection
                if(appSettings.getConnectionType().equals(ConnectionType.MQTT))
                {
                    MQTTHandler.initialize(appSettings.getMqttUrl(), appSettings.getMqttUsername(), appSettings.getMqttPassword(), appSettings.getMqttPort());
                }
                else
                {
                    HttpHandler.initialize(appSettings.getServerUrl());
                }
                
                // Create filelistener
                fileListener = new FileListener(
                        appSettings.getDatabaseFolderPath(),
                        appSettings.getDatabaseFileName(),
                        new FileListenerActions() {
                    @Override
                    public void onEvent(String filename)
                    {
                        MainPageController._instance.loadingIndicator.setVisible(true);
                        
                        // Reload database contents
                        // Small delay to wait database changes
                        try
                        {
                            Thread.sleep(100);
                        }
                        catch(InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        Order[] newOrderResult = accessDBHandler.getAllOrders();
                       // Order[] changedOrders = OrderComparer.getChangedOrders(newOrderResult);
                        
                       // System.out.println("Changed orders: " + changedOrders.length);
                        
                        if(true /*changedOrders.length > 0*/)
                        {
                            Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
                          //  String dataString = g.toJson(changedOrders);
                          String dataString = g.toJson(newOrderResult);
                            
                            if(appSettings.getConnectionType().equals(ConnectionType.MQTT))
                            {
                                MQTTHandler.write(dataString);
                            }
                            else
                            {
                                HttpHandler.sendData(dataString);
                            }
                        }
                        MainPageController._instance.updateList(newOrderResult);
                        MainPageController._instance.loadingIndicator.setVisible(false);
                    }
                });
                fileListener.startListener();
                
                // hide loading spinner
                MainPageController._instance.loadingIndicator.setVisible(false);
                MainPageController._instance.serviceStartButton.setDisable(false);
            }
        };
        Thread t = new Thread(r);
        t.start();
    }
    
    private void updateList(Order[] orders)
    {
        ObservableList<Order> o = FXCollections.observableArrayList(orders);
        this.dataTable.setItems(o);
    }
    
    private void addChangedListenerToFields()
    {
        ChangeListener listener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
            {
                // FocusChanged
                if(!newValue)
                {
                    appSettings.setMqttUrl(_instance.mQTTUrlTextField.getText());
                    appSettings.setMqttPassword(_instance.mQTTPasswordTextField.getText());
                    appSettings.setMqttUsername(_instance.mQTTUsernameTextField.getText());
                    appSettings.setServerUrl(_instance.serverUrlTextField.getText());
                    appSettings.setMqttPort(Integer.parseInt(_instance.mQTTPortTextField.getText()));
                    Settings.writeSettings(appSettings);
                }
            }
        };
        this.mQTTUrlTextField.focusedProperty().addListener(listener);
        this.mQTTUsernameTextField.focusedProperty().addListener(listener);
        this.mQTTPasswordTextField.focusedProperty().addListener(listener);
        this.mQTTPortTextField.focusedProperty().addListener(listener);
        this.serverUrlTextField.focusedProperty().addListener(listener);
    }
    
    @FXML
    private void startService()
    {
        if(!isRunning)
        {
            startBackgroundServices();
            this.serviceStartButton.setText("Stop");
            this.isRunning = true;
        }
        else
        {
            // STOP SERVICES!
            fileListener.stopListener();
            accessDBHandler = null;
            this.serviceStartButton.setText("Start");
            this.isRunning = false;
            this.dataTable.setItems(null);
        }
    }
    
    @FXML
    private void fileChooserButtonClicked()
    {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select database-file...");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Access Database", "*.accdb"));
        File selectedFile = chooser.showOpenDialog(null);
        if(selectedFile != null)
        {
            this.dbPathTextField.setText(selectedFile.getAbsolutePath());
            appSettings.setDatabasePath(selectedFile.getAbsolutePath());
            Settings.writeSettings(appSettings);
        }
    }
    
    @FXML
    private void connectionTypeChanged()
    {
        if(this.serverRadioButton.isSelected())
        {
            appSettings.setConnectionType(ConnectionType.HttpServer);
        }
        else
        {
            appSettings.setConnectionType(ConnectionType.MQTT);
        }
        Settings.writeSettings(appSettings);
        disableFieldsByConnectionType(appSettings.getConnectionType());
    }
}
