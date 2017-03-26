/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.mesgui.gui;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author k1400284
 */
public class MQTTHandler
{
    private static String topic = "mes/data";
    private static String password;
    private static String username;
    private static String url;
    private static MqttClient client;
    private static String connectionString;
    
    public static void initialize(String url, String username, String password, int port)
    {
        try
        {
            client = new MqttClient(url, "MesBdService:" + String.valueOf(Math.random()));
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setAutomaticReconnect(true);
            connOpts.setCleanSession(true);
            if((username != null && password != null) && (username.length() > 0 && password.length() > 0))
            {
                connOpts.setUserName(username);
                connOpts.setPassword(password.toCharArray());
            }
            client.connect(connOpts);
            System.out.println("Mqtt-connection initialized.");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void initialize(String url)
    {
        initialize(url, null, null, 1883);
    }
    
    public static void stopService()
    {
        if(client != null && client.isConnected())
        {
            try
            {
                client.close();
            }
            catch(MqttException e)
            {
                e.printStackTrace();
            }
        }
        client = null;
    }
    
    public static void write(String data)
    {
        MqttMessage message = new MqttMessage(data.getBytes());
        try
        {
            client.publish(topic, message);
        }
        catch(MqttException e)
        {
            e.printStackTrace();
        }
    }
}
