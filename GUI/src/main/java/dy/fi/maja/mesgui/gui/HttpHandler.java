/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.mesgui.gui;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author k1400284
 */
public class HttpHandler
{
    private static HttpURLConnection connection;
    private static URL urlConnection;
    
    public static void initialize(String url)
    {
        try
        {
            urlConnection = new URL(url);
            System.out.println("Http-connection initialized.");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private static void openConnection()
    {
        try
        {
            connection = (HttpURLConnection)urlConnection.openConnection();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void sendData(String data)
    {
        openConnection();
        try
        {
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            DataOutputStream output = new DataOutputStream(connection.getOutputStream());
            output.writeBytes(data);
            output.flush();
            output.close();
            
            int responseCode = connection.getResponseCode();
            System.out.println("RESPONSECODE: " + String.valueOf(responseCode));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
