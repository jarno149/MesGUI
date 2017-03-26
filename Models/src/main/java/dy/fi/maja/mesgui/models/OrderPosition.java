/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.mesgui.models;

import java.util.Date;

/**
 *
 * @author k1400284
 */
public class OrderPosition
{
    private long oPos;
    private Date planedStart;
    private Date planedEnd;
    private Date start;
    private Date end;
    private long wPNo;
    private long stepNo;
    private long mainOPos;
    private long state;
    private long resourceId;
    private long opNo;
    private long wONo;
    private long pNo;
    private boolean subOrderBlocked;
    private boolean error;
    private OrderStep[] steps;

    public long getoPos()
    {
        return oPos;
    }

    public void setoPos(long oPos)
    {
        this.oPos = oPos;
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

    public void setwPNo(long wPNo)
    {
        this.wPNo = wPNo;
    }

    public void setStepNo(long stepNo)
    {
        this.stepNo = stepNo;
    }

    public void setMainOPos(long mainOPos)
    {
        this.mainOPos = mainOPos;
    }

    public void setState(long state)
    {
        this.state = state;
    }

    public void setResourceId(long resourceId)
    {
        this.resourceId = resourceId;
    }

    public void setOpNo(long opNo)
    {
        this.opNo = opNo;
    }

    public void setwONo(long wONo)
    {
        this.wONo = wONo;
    }

    public void setpNo(long pNo)
    {
        this.pNo = pNo;
    }

    public void setSubOrderBlocked(boolean subOrderBlocked)
    {
        this.subOrderBlocked = subOrderBlocked;
    }

    public void setError(boolean error)
    {
        this.error = error;
    }

    public void setSteps(OrderStep[] steps)
    {
        this.steps = steps;
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

    public Date getEnd()
    {
        return end;
    }

    public long getwPNo()
    {
        return wPNo;
    }

    public long getStepNo()
    {
        return stepNo;
    }

    public long getMainOPos()
    {
        return mainOPos;
    }

    public long getState()
    {
        return state;
    }

    public long getResourceId()
    {
        return resourceId;
    }

    public long getOpNo()
    {
        return opNo;
    }

    public long getwONo()
    {
        return wONo;
    }

    public long getpNo()
    {
        return pNo;
    }

    public boolean isSubOrderBlocked()
    {
        return subOrderBlocked;
    }

    public boolean isError()
    {
        return error;
    }

    public OrderStep[] getSteps()
    {
        return steps;
    }
}
