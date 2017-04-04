/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.mesgui.gui;

import dy.fi.maja.mesgui.models.*;
import static java.lang.Class.forName;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author k1400284
 */
public class AccessDBHandler
{
    private String filePath;
    private Statement databaseStatement;

    public AccessDBHandler(String filePath)
    {
        this.filePath = filePath;
    }

    private void createStatement()
    {
        try
        {
            Class:forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://" + this.filePath);
            this.databaseStatement = conn.createStatement();
            System.out.println("Database-connection to: " + this.filePath + " is initialized.");
        }
        catch(Exception e)
        {
            System.err.println("Cannot open database-connection to: " + this.filePath);
            e.printStackTrace();
        }
    }
        
        private ResultSet executeQuery(String query)
    {
        if(this.databaseStatement == null)
            createStatement();
        if(this.databaseStatement == null)
        {
            System.err.println("Cannot open connection to database. Check and fix connections and restart"
                    + " databasehandler");
            System.exit(0);
        }
        
        try
        {
            ResultSet resultSet = this.databaseStatement.executeQuery(query);
            return resultSet;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public OrderStep[] getOrderStepsByOrderNumber(long ono)
    {
        ResultSet result = executeQuery("SELECT * FROM tblStep WHERE tblStep.ONo = " + String.valueOf(ono));
        List<OrderStep> steps = new ArrayList<>();
        try
        {
            while(result.next())
            {
                OrderStep s = new OrderStep();
                s.setwPNo(result.getLong("WPNo"));
                s.setoNo(result.getLong("ONo"));
                s.setStepNo(result.getLong("StepNo"));
                s.setDescription(result.getString("Description"));
                s.setOpNo(result.getLong("OpNo"));
                s.setNextStepNo(result.getLong("NextStepNo"));
                s.setFirstStep(result.getBoolean("FirstStep"));
                s.setErrorStepNo(result.getLong("ErrorStepNo"));
                s.setNewPNo(result.getLong("NewPNo"));
                s.setPlanedStart(result.getTimestamp("PlanedStart"));
                s.setPlanedEnd(result.getTimestamp("PlanedEnd"));
                s.setStart(result.getTimestamp("Start"));
                s.setEnd(result.getTimestamp("End"));
                s.setoPNoType(result.getLong("OPNoType"));
                s.setResourceId(result.getLong("ResourceId"));
                s.setTransPortTime(result.getLong("TransportTime"));
                s.setErrorStep(result.getBoolean("ErrorStep"));
                s.setElectricEnergyCalc(result.getLong("ElectricEnergyCalc"));
                s.setElectricEnergyReal(result.getLong("ElectricEnergyReal"));
                s.setCompressedAirCalc(result.getLong("CompressedAirCalc"));
                s.setCompressedAirReal(result.getLong("CompressedAirReal"));
                steps.add(s);
            }
        }
        catch(Exception e) {e.printStackTrace();}
        
        return steps.toArray(new OrderStep[0]);
    }
    
    public OrderPosition[] getOrderPositionsByOrderNumber(long ono)
    {
        ResultSet result = executeQuery("SELECT * FROM tblOrderPos WHERE tblOrderPos.ONo = " + String.valueOf(ono));
        List<OrderPosition> orderPoss = new ArrayList<>();
        try
        {
            while(result.next())
            {
                OrderPosition pos = new OrderPosition();
                pos.setoPos(result.getLong("OPos"));
                pos.setoPos(result.getLong("ONo"));
                pos.setPlanedStart(result.getTimestamp("PlanedStart"));
                pos.setPlanedEnd(result.getTimestamp("PlanedEnd"));
                pos.setStart(result.getTimestamp("Start"));
                pos.setEnd(result.getTimestamp("End"));
                pos.setwPNo(result.getLong("WPNo"));
                pos.setStepNo(result.getLong("StepNo"));
                pos.setMainOPos(result.getLong("MainOPos"));
                pos.setState(result.getLong("State"));
                pos.setResourceId(result.getLong("ResourceId"));
                pos.setOpNo(result.getLong("OpNo"));
                pos.setwONo(result.getLong("WONo"));
                pos.setpNo(result.getLong("PNo"));
                pos.setSubOrderBlocked(result.getBoolean("SubOrderBlocked"));
                pos.setError(result.getBoolean("Error"));
                pos.setSteps(getOrderStepsByOrderNumber(ono));
                orderPoss.add(pos);
            }
        }
        catch(Exception e){e.printStackTrace();}
        
        return orderPoss.toArray(new OrderPosition[0]);
    }
    
    public Order[] getAllOrders()
    {
        ResultSet result = executeQuery("SELECT * FROM tblOrder");
        List<Order> orders = new ArrayList<Order>();
        try
        {
            while(result.next())
            {
                Order o = new Order();
                o.setoNo(result.getLong("ONo"));
                o.setPlanedStart(result.getTimestamp("PlanedStart"));
                o.setPlanedEnd(result.getTimestamp("PlanedEnd"));
                o.setStart(result.getTimestamp("Start"));
                o.setEnd(result.getTimestamp("End"));
                o.setcNo(result.getLong("CNo"));
                o.setState(result.getLong("State"));
                o.setEnabled(result.getBoolean("Enabled"));
                o.setRelease(result.getTimestamp("Release"));
                o.setOrderPositions(getOrderPositionsByOrderNumber(o.getoNo()));
                orders.add(o);
            }
        }
        catch(Exception e) {e.printStackTrace();}
        
        return orders.toArray(new Order[0]);
    }
    
    public Order getFinishedOrderByOrderNumber(long ono)
    {
        ResultSet result = executeQuery("SELECT * FROM tblFinOrder WHERE ONo = " + String.valueOf(ono));
        try
        {
            while(result.next())
            {
                Order o = new Order();
                o.setoNo(result.getLong("ONo"));
                o.setPlanedStart(result.getTimestamp("PlanedStart"));
                o.setPlanedEnd(result.getTimestamp("PlanedEnd"));
                o.setStart(result.getTimestamp("Start"));
                o.setEnd(result.getTimestamp("End"));
                o.setcNo(result.getLong("CNo"));
                o.setState(result.getLong("State"));
                o.setEnabled(result.getBoolean("Enabled"));
                o.setRelease(result.getTimestamp("Release"));
                o.setOrderPositions(getFinishedOrderPositionsByOrderNumber(o.getoNo()));
                return o;
            }
        }
        catch(Exception e) {e.printStackTrace();}
        return null;
    }
    
    public OrderPosition[] getFinishedOrderPositionsByOrderNumber(long ono)
    {
        ResultSet result = executeQuery("SELECT * FROM tblFinOrderPos WHERE tblFinOrderPos.ONo = " + String.valueOf(ono));
        List<OrderPosition> orderPoss = new ArrayList<>();
        try
        {
            while(result.next())
            {
                OrderPosition pos = new OrderPosition();
                pos.setoPos(result.getLong("OPos"));
                pos.setoNo(result.getLong("ONo"));
                pos.setPlanedStart(result.getTimestamp("PlanedStart"));
                pos.setPlanedEnd(result.getTimestamp("PlanedEnd"));
                pos.setStart(result.getTimestamp("Start"));
                pos.setEnd(result.getTimestamp("End"));
                pos.setwPNo(result.getLong("WPNo"));
                pos.setStepNo(result.getLong("StepNo"));
                pos.setMainOPos(result.getLong("MainOPos"));
                pos.setState(result.getLong("State"));
                pos.setResourceId(result.getLong("ResourceId"));
                pos.setOpNo(result.getLong("OpNo"));
                pos.setwONo(result.getLong("WONo"));
                pos.setpNo(result.getLong("PNo"));
                pos.setSubOrderBlocked(result.getBoolean("SubOrderBlocked"));
                pos.setError(result.getBoolean("Error"));
                pos.setSteps(getOrderStepsByOrderNumber(ono));
                orderPoss.add(pos);
            }
        }
        catch(Exception e){e.printStackTrace();}
        
        return orderPoss.toArray(new OrderPosition[0]);
    }
}
