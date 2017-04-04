/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.mesgui.models;

import java.util.Calendar;
import java.util.Date;
import org.springframework.data.annotation.Id;


public class Order
{
    @Id
    private long oNo;
    private Date planedStart;
    private Date planedEnd;
    private Date start;
    private Date end;
    private long cNo;
    private long state;
    private boolean enabled;
    private Date release;
    private OrderPosition[] orderPositions;

    public long getoNo()
    {
        return oNo;
    }

    public Date getPlanedStart()
    {
        return planedStart;
    }

    public Date getPlanedEnd()
    {
        return planedEnd;
    }

    public Date getStart()
    {
        return start;
    }

    public void setoNo(long oNo)
    {
        this.oNo = oNo;
    }

    public void setPlanedStart(Date planedStart)
    {
        this.planedStart = planedStart;
    }

    public void setPlanedEnd(Date planedEnd)
    {
        this.planedEnd = planedEnd;
    }

    public void setStart(Date start)
    {
        this.start = start;
    }

    public void setEnd(Date end)
    {
        this.end = end;
    }

    public void setcNo(long cNo)
    {
        this.cNo = cNo;
    }

    public void setState(long state)
    {
        this.state = state;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public void setRelease(Date release)
    {
        this.release = release;
    }

    public void setOrderPositions(OrderPosition[] orderPositions)
    {
        this.orderPositions = orderPositions;
    }

    public Date getEnd()
    {
        return end;
    }

    public long getcNo()
    {
        return cNo;
    }

    public long getState()
    {
        return state;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public Date getRelease()
    {
        return release;
    }

    public OrderPosition[] getOrderPositions()
    {
        return orderPositions;
    }
    
    public String getMostRecentStepDescription()
    {
        return getMostRecentStep().getDescription();
    }
    
    public OrderStep getMostRecentStep()
    {
        OrderStep mostRecent = null;
        for (OrderPosition pos : this.orderPositions)
        {
            for (OrderStep step : pos.getSteps())
            {
                if (step.getStart() != null)
                {
                    if(mostRecent == null)
                    {
                        mostRecent = step;
                    }
                    else
                    {
                        long lastStepTime = mostRecent.getStart().getTime();
                    long currStepTime = step.getStart().getTime();
                    if (lastStepTime < currStepTime)
                    {
                        mostRecent = step;
                    }
                    }
                }
            }
        }
        return mostRecent;
    }
}
