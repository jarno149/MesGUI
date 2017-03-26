/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.mesgui.gui;

import dy.fi.maja.mesgui.models.Order;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.ListCompareAlgorithm;
import org.javers.core.diff.changetype.NewObject;
import org.javers.core.diff.changetype.ValueChange;

/**
 *
 * @author k1400284
 */
public class OrderComparer
{
    private static Order[] storedOrders;
    private static AccessDBHandler dbhandler;
    
    public static void initialize(AccessDBHandler dbhandler)
    {
        dbhandler = dbhandler;
        storedOrders = dbhandler.getAllOrders();
    }
    
    public static Order[] getChangedOrders(Order[] newResult)
    {
        ArrayList<Order> changedOrders = new ArrayList<>();
        Javers javers = JaversBuilder.javers().withListCompareAlgorithm(ListCompareAlgorithm.SIMPLE).build();
        List<Order> list1 = Arrays.asList(newResult);
        List<Order> list2 = Arrays.asList(storedOrders);
        Diff diff = javers.compareCollections(list2, list1, Order.class);
        
        List<Order> modifiedObjects = diff.getObjectsByChangeType(ValueChange.class);
        List<Order> createdObjects = diff.getObjectsByChangeType(NewObject.class);
        
        for(Object o : modifiedObjects)
        {
            if(o.getClass() == Order.class)
                changedOrders.add((Order)o);
        }
        for(Object o : createdObjects)
        {
            if(o.getClass() == Order.class)
                changedOrders.add((Order)o);
        }
        
        storedOrders = newResult;
        return changedOrders.toArray(new Order[0]);
    }
}
