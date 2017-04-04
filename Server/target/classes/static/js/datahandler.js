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
            var data = JSON.parse(output.body);
            contentTable.update(data);
       });
    });
}

function connect()
            {
                var socket = new SockJS('/socket');
                stompClient = Stomp.over(socket);
                stompClient.connect({}, function(frame){
                   console.log(frame); 
                    stompClient.subscribe('/livedata', function(output)
                    {
                        var jsonData = JSON.parse(output);
                        contentTable.update(jsonData);
                    });
                });
                
            }

function getDataByOno(ono, ready)
{
    $.getJSON("/mesdata/" + ono, function(result){
        ready(result);
    });
}

