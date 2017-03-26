function Table(containerId)
{
    this.container = document.getElementById(containerId);
}

Table.prototype.addRow = function()
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
    
    // ID GENERATOR
    var id = Math.round(Math.random() * 1000);
    
    var glyph = document.createElement("span");
    glyph.classList.add("glyphicon");
    glyph.classList.add("glyphicon-info-sign");
    glyph.classList.add("pull-right");
    glyph.setAttribute("onclick", "infoClicked('" + id + "')");
    col4.appendChild(glyph);
    
    row.setAttribute("id", id);
    row.setAttribute("class", "success");
    
    col1.innerHTML = id + "TestContent";
    col2.innerHTML = "TestContent";
    col3.innerHTML = "TestContent";
    
    this.container.insertBefore(row, this.container.childNodes[0]);
}