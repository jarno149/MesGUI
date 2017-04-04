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
public class OrderStep
{
    private long oNo;
    private long wPNo;
    private long stepNo;
    private String description;
    private long opNo;
    private long nextStepNo;
    private boolean firstStep;
    private long errorStepNo;
    private long newPNo;
    private Date planedStart;
    private Date planedEnd;
    private Date start;
    private Date end;
    private long oPNoType;
    private long resourceId;
    private long transPortTime;
    private boolean errorStep;
    private long electricEnergyCalc;
    private long electricEnergyReal;
    private long compressedAirCalc;
    private long compressedAirReal;

    public long getwPNo()
    {
        return wPNo;
    }
    
    public long getoNo()
    {
        return oNo;
    }

    public void setoNo(long oNo)
    {
        this.oNo = oNo;
    }

    public void setwPNo(long wPNo)
    {
        this.wPNo = wPNo;
    }

    public long getStepNo()
    {
        return stepNo;
    }

    public void setStepNo(long stepNo)
    {
        this.stepNo = stepNo;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public long getOpNo()
    {
        return opNo;
    }

    public void setOpNo(long opNo)
    {
        this.opNo = opNo;
    }

    public long getNextStepNo()
    {
        return nextStepNo;
    }

    public void setNextStepNo(long nextStepNo)
    {
        this.nextStepNo = nextStepNo;
    }

    public boolean isFirstStep()
    {
        return firstStep;
    }

    public void setFirstStep(boolean firstStep)
    {
        this.firstStep = firstStep;
    }

    public long getErrorStepNo()
    {
        return errorStepNo;
    }

    public void setErrorStepNo(long errorStepNo)
    {
        this.errorStepNo = errorStepNo;
    }

    public long getNewPNo()
    {
        return newPNo;
    }

    public void setNewPNo(long newPNo)
    {
        this.newPNo = newPNo;
    }

    public Date getPlanedStart()
    {
        return planedStart;
    }

    public void setPlanedStart(Date planedStart)
    {
        this.planedStart = planedStart;
    }

    public Date getPlanedEnd()
    {
        return planedEnd;
    }

    public void setPlanedEnd(Date planedEnd)
    {
        this.planedEnd = planedEnd;
    }

    public Date getStart()
    {
        return start;
    }

    public void setStart(Date start)
    {
        this.start = start;
    }

    public Date getEnd()
    {
        return end;
    }

    public void setEnd(Date end)
    {
        this.end = end;
    }

    public long getoPNoType()
    {
        return oPNoType;
    }

    public void setoPNoType(long oPNoType)
    {
        this.oPNoType = oPNoType;
    }

    public long getResourceId()
    {
        return resourceId;
    }

    public void setResourceId(long resourceId)
    {
        this.resourceId = resourceId;
    }

    public long getTransPortTime()
    {
        return transPortTime;
    }

    public void setTransPortTime(long transPortTime)
    {
        this.transPortTime = transPortTime;
    }

    public boolean isErrorStep()
    {
        return errorStep;
    }

    public void setErrorStep(boolean errorStep)
    {
        this.errorStep = errorStep;
    }

    public long getElectricEnergyCalc()
    {
        return electricEnergyCalc;
    }

    public void setElectricEnergyCalc(long electricEnergyCalc)
    {
        this.electricEnergyCalc = electricEnergyCalc;
    }

    public long getElectricEnergyReal()
    {
        return electricEnergyReal;
    }

    public void setElectricEnergyReal(long electricEnergyReal)
    {
        this.electricEnergyReal = electricEnergyReal;
    }

    public long getCompressedAirCalc()
    {
        return compressedAirCalc;
    }

    public void setCompressedAirCalc(long compressedAirCalc)
    {
        this.compressedAirCalc = compressedAirCalc;
    }

    public long getCompressedAirReal()
    {
        return compressedAirReal;
    }

    public void setCompressedAirReal(long compressedAirReal)
    {
        this.compressedAirReal = compressedAirReal;
    }
}
