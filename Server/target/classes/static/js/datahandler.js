var stompClient;
var contentTable;

function initializeTableHandler(table)
{
    contentTable = table;
}

function initializeStompClient()
{
    var socket = new SockJS("/socket");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame){
       console.log(frame);
       stompClient.subscribe("/livedata", function(output){
           console.log(output);
       });
    });
}

function connect()
            {
                var socket = new SockJS('http://localhost:28862/socket');
                stompClient = Stomp.over(socket);
                stompClient.connect({}, function(frame){
                   console.log(frame); 
                    stompClient.subscribe('/livedata', function(output)
                {
                    var jsonData = JSON.parse(output);
                    if(jsonData instanceof Array)
                    {
                        for(var i = 0; i < jsonData.length; i++)
                        {
                            contentTable.addRow(jsonData[i]);
                        }
                        
                    }
                    else
                    {
                        contentTable.addRow(jsonData);
                    }
                });
                });
                
            }

function getDataByOno(ono, ready)
{
    $.getJSON("/mesdata/" + ono, function(result){
        ready(result);
    });
}

