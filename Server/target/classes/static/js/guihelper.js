var workingTimePieChart;

function initializePieChart(containerId, orderdata)
{
    var totalTime = 0;
    if(orderdata.end != undefined)
        totalTime = orderdata.end - orderdata.start;
    else
        totalTime = new Date().getTime() - orderdata.start;
    
    var workingTime = 0;
    for(var i = 0; i < orderdata.orderPositions.length; i++)
    {
        var orderPos = orderdata.orderPositions[i];
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
            name: 'Idle',
            y: totalTime - workingTime
        },
        {
            name: "Working",
            y: workingTime
        }]
    }]
});
}

function clearPieChart()
{
    workingTimePieChart.destroy();
}

