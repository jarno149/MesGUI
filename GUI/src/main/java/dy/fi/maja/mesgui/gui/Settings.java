/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.mesgui.gui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author k1400284
 */
public class Settings
{
    private String databasePath;
    private String mqttUrl;
    private String mqttUsername;
    private String mqttPassword;
    private int mqttPort;
    private String serverUrl;
    private ConnectionType connectionType;
    
    private static final String filename = "config.properties";

    public static enum ConnectionType
    {
        MQTT,
        HttpServer
    }
    
    public static Settings getSettings()
    {
        InputStream input = null;
        Properties prop = new Properties();
        
        try
        {
            input = new FileInputStream(filename);
            if(input != null)
            {
                prop.load(input);
            
                Settings s = new Settings();
                s.setDatabasePath(prop.getProperty("DatabasePath"));
                s.setMqttUrl(prop.getProperty("MQTTUrl"));
                s.setMqttUsername(prop.getProperty("MQTTUsername"));
                s.setMqttPassword(prop.getProperty("MQTTPassword"));
                s.setMqttPort(Integer.parseInt(prop.getProperty("MQTTPort")));
                s.setServerUrl(prop.getProperty("ServerUrl"));
                s.setConnectionType(Enum.valueOf(ConnectionType.class, prop.getProperty("ConnectionType")));
                return s;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(input != null)
            {
                try
                {
                    input.close();
                }
                catch(IOException e){ e.printStackTrace();}
            } 
        }
        
        Settings s = new Settings();
        s.setDatabasePath("");
        s.setMqttUrl("");
        s.setMqttUsername("");
        s.setMqttPassword("");
        s.setMqttPort(1883);
        s.setServerUrl("");
        s.setConnectionType(ConnectionType.MQTT);
        
        writeSettings(s);
        
        return s;
    }
    
    public static void writeSettings(Settings s)
    {
        Properties prop = new Properties();
        OutputStream output = null;
        
        try
        {
            output = new FileOutputStream(filename);
            
            prop.setProperty("DatabasePath", s.getDatabasePath());
            prop.setProperty("MQTTUrl", s.getMqttUrl());
            prop.setProperty("MQTTUsername", s.getMqttUsername());
            prop.setProperty("MQTTPassword", s.getMqttPassword());
            prop.setProperty("MQTTPort", String.valueOf(s.getMqttPort()));
            prop.setProperty("ServerUrl", s.getServerUrl());
            prop.setProperty("ConnectionType", String.valueOf(s.getConnectionType()));
            prop.store(output, null);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(output != null)
            {
                try
                {
                    output.close();
                }
                catch(IOException e) {e.printStackTrace();}
            }
        }
    }

    public String getDatabaseFileName()
    {
        String tempString = this.databasePath.replace('\\', '/');
        int li = tempString.lastIndexOf("/");
        return tempString.substring(li+1);
    }
    
    public String getDatabaseFolderPath()
    {
        String tempString = this.databasePath.replace('\\', '/');
        int li = tempString.lastIndexOf("/");
        return tempString.substring(0,li+1);
    }

    public String getDatabasePath()
    {
        return databasePath.replace("\\", "/");
    }

    public void setDatabasePath(String databasePath)
    {
        this.databasePath = databasePath;
    }

    public ConnectionType getConnectionType()
    {
        return connectionType;
    }

    public void setConnectionType(ConnectionType connectionType)
    {
        this.connectionType = connectionType;
    }

    public String getMqttUrl()
    {
        return mqttUrl;
    }

    public void setMqttUrl(String mqttUrl)
    {
        this.mqttUrl = mqttUrl;
    }

    public String getMqttUsername()
    {
        return mqttUsername;
    }

    public void setMqttUsername(String mqttUsername)
    {
        this.mqttUsername = mqttUsername;
    }

    public String getMqttPassword()
    {
        return mqttPassword;
    }

    public void setMqttPassword(String mqttPassword)
    {
        this.mqttPassword = mqttPassword;
    }

    public int getMqttPort()
    {
        return mqttPort;
    }

    public void setMqttPort(int mqttPort)
    {
        this.mqttPort = mqttPort;
    }

    public String getServerUrl()
    {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl)
    {
        this.serverUrl = serverUrl;
    }
}
