var workingTimePieChart;

function initializePieChart(containerId, orderdata)
{
    var totalTime;
    if(orderdata.end != undefined)
        totalTime = orderdata.end - orderdata.start;
    else
        totalTime = new Date().getTime();
    
    var workingTime = 0;
    for(var i = 0; i < orderdata.orderPositions.length; i++)
    {
        var orderPos = orderdata[i];
        if(orderPos.end != undefined)
        {
            workingTime += orderPos.end - orderPos.start;
        }
        else
        {
            for(var j = 0; j < orderPos.steps.length; j++)
            {
                var step = orderPos.steps[j];
                if(step.end != undefined)
                {
                    workingTime += step.end - step.start;
                }
                else
                {
                    workingTime += new Date().getTime() - step.start;
                }
            }
        }
    }
    
    workingTimePieChart = Highcharts.chart(containerId, {
    chart: {
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false,
        type: 'pie'
    },
    title: {
        text: 'Working time'
    },
    tooltip: {
        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
    },
    plotOptions: {
        pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
                enabled: true,
                format: '{point.percentage:.1f} %',
                style: {
                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                }
            }
        }
    },
    series: [{
        colorByPoint: true,
        data: [{
            name: 'Microsoft Internet Explorer',
            y: 56.33
        }, {
            name: 'Chrome',
            y: 24.03,
            sliced: true,
            selected: true
        }, {
            name: 'Firefox',
            y: 10.38
        }, {
            name: 'Safari',
            y: 4.77
        }, {
            name: 'Opera',
            y: 0.91
        }, {
            name: 'Proprietary or Undetectable',
            y: 0.2
        }]
    }]
});
}

function clearPieChart()
{
    workingTimePieChart.destroy();
}