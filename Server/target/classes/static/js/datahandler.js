var stompClient;

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
                    stompClient.subscribe('/livedata', function(output){
                    console.log(output);
                    stompClient.send("/app/livedata", {}, "asdsad");
                });
                });
                
            }

function getDataByOno(ono, ready)
{
    $.getJSON("/mesdata/" + ono, function(result){
        ready(result);
    });
}

