
function getDataByOno(ono, ready)
{
    $.getJSON("/mesdata/" + ono, function(result){
        ready(result);
    });
}

