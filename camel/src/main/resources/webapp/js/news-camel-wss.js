$(document).ready(function () {

    var socket;

    $('#connect_form').submit(function () {

        var host = $("#url").val();

        socket = new WebSocket(host);

        $('#connect').fadeOut({ duration:'fast' });
        $('#disconnect').fadeIn();
        $('#send_form_input').removeAttr('disabled');

        // Add a connect listener
        socket.onopen = function () {
            $('#msg').append('<p class="event">Socket News Status: ' + socket.readyState + ' (open)</p>');
        }

        socket.onmessage = function (msg) {

            // $('#msg').append('<p class="message">Received: ' + msg.data + "</p>");

            var news = JSON.parse(msg.data);

            // extract the news fields
            var symbol = news.symbol;
            var title = news.title;
            var picture = news.picture;
            var info = news.info;

            var $res = $("<li>");
            $res.append("<img src='" + picture + "' width='50' height='50' />");
            $res.append("&nbsp;" + symbol + " : " + title + "<p/>");
            $res.append("Description : " + info);
            $res.append("</li>");
            $res.appendTo('#news');


            setInterval(function(){ tickNews () }, 5000);
            $('#newsList').show();

        }

        socket.onclose = function () {
            $('#msg').append('<p class="event">Socket News Status: ' + socket.readyState + ' (Closed)</p>');
        }

        socket.onerror = function () {
            $('#msg').append('<p class="event">An error has occured</p>');
        }

        return false;
    });

    $('#disconnect_form').submit(function () {

        socket.close();

        $('#msg').append('<p class="event">Socket News Status: ' + socket.readyState + ' (Closed)</p>');

        $('#disconnect').fadeOut({ duration:'fast' });
        $('#connect').fadeIn();
        $('#send_form_input').addAttr('disabled');

        return false;
    });

});
function tickNews() {
    $('#news li:first').slideUp(function () {
        $(this).appendTo($('#news')).slideDown();
    });
}

$(function() {
    $( "#tabs" ).tabs();
});