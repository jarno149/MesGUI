/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.mesgui.gui;

import dy.fi.maja.mesgui.models.ChangedOrdersResult;
import dy.fi.maja.mesgui.models.Order;
import dy.fi.maja.mesgui.models.OrderPosition;
import dy.fi.maja.mesgui.models.OrderStep;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
    
    public static ChangedOrdersResult getChangedOrders(Order[] newResult)
    {
        Javers javers = JaversBuilder.javers().withListCompareAlgorithm(ListCompareAlgorithm.SIMPLE).build();
        List<Order> list1 = Arrays.asList(newResult);
        List<Order> list2 = Arrays.asList(storedOrders);
        Diff diff = javers.compareCollections(list2, list1, Order.class);
        
        List<Order> modifiedObjects = diff.getObjectsByChangeType(ValueChange.class);
        List<Order> createdObjects = diff.getObjectsByChangeType(NewObject.class);
        List<Order> removedObjects = diff.getObjectsByChangeType(org.javers.core.diff.changetype.ObjectRemoved.class);
        
        List<Order> modifiedResultObjects = new ArrayList<Order>();
        List<Order> createdResultObjects = new ArrayList<Order>();
        List<Order> removedResultObjects = new ArrayList<Order>();
        
        for(Object o : modifiedObjects)
        {
            if(o.getClass() == Order.class)
            {
                modifiedResultObjects.add((Order)o);
            }
            else
            {
                if(o.getClass() == OrderPosition.class)
                {
                    Optional<Order> mainOrder = list1.stream().filter(x -> x.getoNo() == ((OrderPosition)o).getoNo()).findFirst();
                    if(mainOrder.isPresent() && !modifiedResultObjects.contains(mainOrder.get())
                            && !createdResultObjects.contains(mainOrder.get())
                            && !removedResultObjects.contains(mainOrder.get()))
                    {
                        modifiedResultObjects.add(mainOrder.get());
                    }
                }
                else if(o.getClass() == OrderStep.class)
                {
                    Optional<Order> mainOrder = list1.stream().filter(x -> x.getoNo() == ((OrderStep)o).getoNo()).findFirst();
                    if(mainOrder.isPresent() && !modifiedResultObjects.contains(mainOrder.get())
                            && !createdResultObjects.contains(mainOrder.get())
                            && !removedResultObjects.contains(mainOrder.get()))
                    {
                        modifiedResultObjects.add(mainOrder.get());
                    }
                }
            }
        }
        for(Object o : createdObjects)
        {
            if(o.getClass() == Order.class)
            {
                createdResultObjects.add((Order)o);
            }
            else
            {
                if(o.getClass() == OrderPosition.class)
                {
                    Optional<Order> mainOrder = list1.stream().filter(x -> x.getoNo() == ((OrderPosition)o).getoNo()).findFirst();
                    if(mainOrder.isPresent() && !modifiedResultObjects.contains(mainOrder.get())
                            && !createdResultObjects.contains(mainOrder.get())
                            && !removedResultObjects.contains(mainOrder.get()))
                    {
                        createdResultObjects.add(mainOrder.get());
                    }
                }
                else if(o.getClass() == OrderStep.class)
                {
                    Optional<Order> mainOrder = list1.stream().filter(x -> x.getoNo() == ((OrderStep)o).getoNo()).findFirst();
                    if(mainOrder != null && !modifiedResultObjects.contains(mainOrder.get())
                            && !createdResultObjects.contains(mainOrder.get())
                            && !removedResultObjects.contains(mainOrder.get()))
                    {
                        createdResultObjects.add(mainOrder.get());
                    }
                }
            }
        }
        for(Object o : removedObjects)
        {
            if(o.getClass() == Order.class)
            {
                removedResultObjects.add((Order)o);
            }
            else
            {
                if(o.getClass() == OrderPosition.class)
                {
                    Optional<Order> mainOrder = list2.stream().filter(x -> x.getoNo() == ((OrderPosition)o).getoNo()).findFirst();
                    if(mainOrder != null && !modifiedResultObjects.contains(mainOrder.get())
                            && !createdResultObjects.contains(mainOrder.get())
                            && !removedResultObjects.contains(mainOrder.get()))
                    {
                        removedResultObjects.add(mainOrder.get());
                    }
                }
                else if(o.getClass() == OrderStep.class)
                {
                    Optional<Order> mainOrder = list2.stream().filter(x -> x.getoNo() == ((OrderStep)o).getoNo()).findFirst();
                    if(mainOrder != null && !modifiedResultObjects.contains(mainOrder.get())
                            && !createdResultObjects.contains(mainOrder.get())
                            && !removedResultObjects.contains(mainOrder.get()))
                    {
                        removedResultObjects.add(mainOrder.get());
                    }
                }
            }
        }
        
        // Check if removed objects still exists in ready orders -table.
        for(Order o : removedResultObjects)
        {
            Order finishedOrder = dbhandler.getFinishedOrderByOrderNumber(o.getoNo());
            if(finishedOrder != null)
            {
                removedResultObjects.remove(o);
                modifiedResultObjects.add(finishedOrder);
            }
        }
        
        storedOrders = newResult;
        return new ChangedOrdersResult(modifiedResultObjects, createdResultObjects, removedResultObjects);
    }
}
