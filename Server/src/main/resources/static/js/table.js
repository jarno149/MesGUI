function Table(containerId, readyContainerId)
{
    this.container = document.getElementById(containerId);
    this.readycontainer = document.getElementById(readyContainerId)
}

Table.prototype.update = function(data)
{
    for(var i = 0; i < data.createdObjects.length; i++)
    {
        this.addRow(data.createdObjects[i]);
    }
    for(var i = 0; i < data.removedObjects.length; i++)
    {
        this.removeRowByOno(data.removedObjects[i].oNo);
    }
    for(var i = 0; i < data.modifiedObjects.length; i++)
    {
        this.addRow(data.modifiedObjects[i]);
    }
}

Table.prototype.getCurrentStep = function(orderData)
{
    var currentStep;
    for(var i = 0; i < orderData.orderPositions.length; i++)
    {
        for(var j = 0; j < orderData.orderPositions[i].steps.length; j++)
        {
            var step = orderData.orderPositions[i].steps[j];
            if(currentStep == undefined)
            {
                currentStep = step;
            }
            else if(currentStep.start != undefined && step.start != undefined && currentStep.start < step.start)
            {
                currentStep = step;
            }
        }
    }
    return currentStep;
}

Table.prototype.addRow = function(orderData)
{
    if(this.containsRow(orderData.oNo))
    {
        var row = document.getElementById(orderData.oNo);
        if(orderData.end != undefined)
        {
            var row = document.getElementById(ono);
            if(row != undefined)
            {
                this.container.removeChild(row);
                if(this.readycontainer.childNodes != null && this.readycontainer.childNodes.length > 0)
                    this.readycontainer.insertBefore(row, this.readycontainer.childNodes[0]);
                else this.readycontainer.appendChild(row);
            }
        }
        else
        {
            var children = row.getElementsByTagName("td")
            children[1].innerHTML = formatDate(orderData.start);
            children[2].innerHTML = this.getCurrentStep(orderData).description;
            this.setRowStatus(row, orderData);
            if(orderData.enabled == false)
            {
                row.setAttribute("class", "info");
                children[2].innerHTML = "Disabled";
            }
        }   
    }
    else
    {
        var row = document.createElement("tr");
        var col1 = document.createElement("td");
        var col2 = document.createElement("td");
        var col3 = document.createElement("td");
        var col4 = document.createElement("td");
        row.appendChild(col1);
        row.appendChild(col2);
        row.appendChild(col3);
        row.appendChild(col4);

        var glyph = document.createElement("span");
        glyph.classList.add("glyphicon");
        glyph.classList.add("glyphicon-info-sign");
        glyph.classList.add("pull-right");
        glyph.setAttribute("onclick", "infoClicked('" + orderData.oNo + "')");
        col4.appendChild(glyph);

        row.setAttribute("id", orderData.oNo);
        this.setRowStatus(row, orderData);
        
        col1.innerHTML = orderData.oNo;
        col2.innerHTML = formatDate(orderData.start);
        col3.innerHTML = this.getCurrentStep(orderData).description;

        if(orderData.end != undefined)
            this.container.insertBefore(row, this.container.childNodes[0]);
        else
            if(this.readycontainer.childNodes != null && this.readycontainer.childNodes.length > 0)
                this.readycontainer.insertBefore(row, this.readycontainer.childNodes[0]);
            else this.readycontainer.appendChild(row);
    }
}

function formatDate(milliseconds)
{
    if(milliseconds == null)
        return " - ";
	var d = new Date(milliseconds);
    var dateNumber = d.getDate();
    var monthNumber = d.getMonth() + 1;
    var yearNumber = d.getFullYear();
    var hourNumber = d.getHours();
    var minuteNumber = d.getMinutes();
    var secondNumber = d.getSeconds();
    if(secondNumber < 10)
    	secondNumber = "0" + secondNumber;
    if(minuteNumber < 10)
    	minuteNumber = "0" + minuteNumber;
    if(dateNumber < 10)
        dateNumber = "0" + dateNumber;
    if(monthNumber < 10)
        monthNumber = "0" + monthNumber;
    
    var dateString = dateNumber + "." + monthNumber + "." + yearNumber + " " + hourNumber + ":" + minuteNumber + ":" + secondNumber;
    return dateString;
}

Table.prototype.setRowStatus = function(row, rowData)
{
    switch(rowData.state)
        {
            case 10:
                row.setAttribute("class", "success");
                break;
            case 0:
                row.setAttribute("class", "warning");
                break;
            case 1:
                row.setAttribute("class", "success");
                break;
            case 99:
                row.setAttribute("class", "danger");
                break;
            case undefined:
                row.setAttribute("class", "warning");
                break;
            default:
                row.removeAttribute("class");
        }
}

Table.prototype.removeRowByOno = function(ono)
{
    var row = document.getElementById(ono);
    if(row != undefined)
    {
        this.container.removeChild(row);
        this.readycontainer.removeChild(row);
    }
}

Table.prototype.containsRow = function(ono)
{
    var row = document.getElementById(ono);
    return row != undefined;
}