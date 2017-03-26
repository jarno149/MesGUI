function Table(containerId)
{
    this.container = document.getElementById(containerId);
}

Table.prototype.addRow = function(orderData)
{
    if(this.containsRow(orderData.oNo))
    {
        var row = document.getElementById(orderData.oNo);
        var children = row.getElementsByTagName("td")
        children[1].innerHTML = formatDate(orderData.start);
        children[2].innerHTML = "TEST CurrentStep";
        this.setRowStatus(orderData);
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
        this.setRowStatus(orderData);
        
        col1.innerHTML = orderData.oNo;
        col2.innerHTML = orderData.start;
        col3.innerHTML = "TEST CurrentStep";

        this.container.insertBefore(row, this.container.childNodes[0]);
    }
}

function formatDate(milliseconds)
{
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

Table.prototype.setRowStatus = function(rowData)
{
    var row = document.getElementById(rowData.oNo);
    switch(rowData.status)
        {
            case 1:
                row.setAttribute("class", "success");
                break;
            case 2:
                row.setAttribute("class", "warning");
                break;
            case 3:
                row.setAttribute("class", "danger");
                break;
        }
}

Table.prototype.removeRowByOno = function(ono)
{
    var row = document.getElementById(ono);
    if(row != undefined)
        this.container.removeChild(row);
}

Table.prototype.containsRow = function(ono)
{
    var row = document.getElementById(ono);
    return row != undefined;
}