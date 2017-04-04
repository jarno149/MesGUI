/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.mesgui.server.controllers;

import dy.fi.maja.mesgui.models.Order;
import dy.fi.maja.mesgui.models.OrderPosition;
import dy.fi.maja.mesgui.models.OrderStep;
import dy.fi.maja.mesgui.server.OrderRepository;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@Controller
public class GuiController
{
    @Autowired
    private WebSocketController con;
    @Autowired
    private OrderRepository orderRepo;
    
    @RequestMapping("/")
    public String index(Model model)
    {
        List<Order> ordersFromDb = orderRepo.findAll();
        List<Order> readyOrders = ordersFromDb.stream().filter(x -> x.getEnd() != null).collect(Collectors.toList());
        List<Order> pendingOrders = ordersFromDb.stream().filter(x -> x.getEnd() == null).collect(Collectors.toList());
        model.addAttribute("orders", pendingOrders);
        model.addAttribute("readyOrders", readyOrders);
        return "index";
    }
    
    public static Order[] generateOrders()
    {
        List<Order> orders = new ArrayList<Order>();
        for(int i = 0; i < 10; i++)
        {
            Order o = new Order();
            o.setoNo(i + 1000);
            
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            
            try
            {
                o.setStart(formatter.parse("2017-03-18T13:42:02"));
                o.setEnd(formatter.parse("2017-03-18T13:51:00"));
            }
            catch(Exception e)
            {
                
            }
            
            
            
            
            OrderPosition oPos = new OrderPosition();
            oPos.setwONo(i + 1000);
            OrderStep[] oStep = new OrderStep[2];
            oStep[0] = new OrderStep();
            oStep[1] = new OrderStep();
            try
            {
                oStep[0].setStart(formatter.parse("2017-03-18T13:43:12"));
                oStep[0].setEnd(formatter.parse("2017-03-18T13:44:44"));
                oStep[1].setStart(formatter.parse("2017-03-18T13:46:12"));
                oStep[1].setEnd(formatter.parse("2017-03-18T13:50:44"));
                
            } catch (ParseException ex)
            {
                Logger.getLogger(GuiController.class.getName()).log(Level.SEVERE, null, ex);
            }
            oPos.setSteps(oStep);
            o.setOrderPositions(new OrderPosition[]{ oPos });
            
            int isd = (int)Math.round(Math.random() * 3);
            o.setState(isd);
            orders.add(o);
        }
        return orders.toArray(new Order[0]);
    }
}
