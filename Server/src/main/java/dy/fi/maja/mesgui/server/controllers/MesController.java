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
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MesController
{
    @Autowired
    private OrderRepository repository;
    @Autowired
    private WebSocketController wsController;
    
    @RequestMapping(value = "/mesdata", method = RequestMethod.POST, consumes = "application/json")
    public void incomingOrderData(@RequestBody Order[] orders)
    {
        if(orders.length > 0)
        {
            List<Order> orderList = Arrays.asList(orders);
            repository.save(orderList);
            System.out.println("New orderList stored.");
            
            // Send data to websocket clients
            wsController.sendMessage(orderList);
        }
    }
    
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "mesdata/{ono}", method = RequestMethod.GET, produces = "application/json")
    public Order getOrderByONo(@PathVariable long ono)
    {
        Order o = repository.findByoNo(ono);
        return o;
    }
}
