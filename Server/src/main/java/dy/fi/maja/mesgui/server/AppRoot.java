/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.mesgui.server;

import dy.fi.maja.mesgui.configurations.WebsocketConfig;
import dy.fi.maja.mesgui.models.Order;
import dy.fi.maja.mesgui.server.controllers.WebSocketController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@Import(WebsocketConfig.class)
@ComponentScan({"dy.fi.maja.mesgui.server.controllers"})
public class AppRoot implements CommandLineRunner
{
    public static void main(String[] args)
    {
        System.out.println("///////////////////////////////////////////////////////////////////////////");
        System.out.println("//                                                                       //");
        System.out.println("//                     STARTING APPLICATION...                           //");
        System.out.println("//                                                                       //");
        System.out.println("//                     Jarno Rajala 13.03.2017                           //");
        System.out.println("///////////////////////////////////////////////////////////////////////////");
        
        int port = 28862;
        
        if(args.length > 0)
        {
            try
            {
                port = Integer.parseInt(args[0]);
            }
            catch(Exception e)
            {
                System.err.println("Invalid port-number... Starting server with port 28862...");
            }
        }
        System.getProperties().put("spring.thymeleaf.cache", false);
        System.getProperties().put("server.port", port);
        System.getProperties().put("spring.data.mongodb.uri", "mongodb://13.69.75.14:27017/mes");
        
        SpringApplication app = new SpringApplication(AppRoot.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run();
        
        while(true){}
    }

    @Override
    public void run(String... strings) throws Exception
    {
        
    }
}
