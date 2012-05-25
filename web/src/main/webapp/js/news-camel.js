$(document).ready(function () {

    var socket1, socket2;

    $('#connect_form').submit(function () {

        var host1 = $("#url1").val();
        var host2 = $("#url2").val();

        socket1 = new WebSocket(host1);
        socket2 = new WebSocket(host2);

        $('#connect').fadeOut({ duration:'fast' });
        $('#disconnect').fadeIn();
        $('#send_form_input').removeAttr('disabled');

        // Add a connect listener
        socket1.onopen = function () {
            $('#msg').append('<p class="event">Socket News Status: ' + socket1.readyState + ' (open)</p>');
        }
        socket2.onopen = function () {
            $('#msg').append('<p class="event">Socket Tweet Status: ' + socket2.readyState + ' (open)</p>');
        }

        socket1.onmessage = function (msg) {

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

        socket2.onmessage = function (msg) {

            // $('#msg').append('<p class="message">Received: ' + msg.data + "</p>");

            var message = JSON.parse(msg.data);

            // extract the tweets
            var txt = message.tweet;

            var $res = $("<li/>");
            $res.append(txt);
            $res.appendTo($('#tweet'));

            setInterval(function () {
                tickTweet()
            }, 5000);
            $('#tweetList').show();

        }

        socket1.onclose = function () {
            $('#msg').append('<p class="event">Socket News Status: ' + socket1.readyState + ' (Closed)</p>');
        }

        socket2.onclose = function () {
            $('#msg').append('<p class="event">Socket Tweet Status: ' + socket2.readyState + ' (Closed)</p>');
        }


        return false;
    });

    $('#disconnect_form').submit(function () {

        socket1.close();
        socket2.close();

        $('#msg').append('<p class="event">Socket News Status: ' + socket1.readyState + ' (Closed)</p>');
        $('#msg').append('<p class="event">Socket Tweet Status: ' + socket2.readyState + ' (Closed)</p>');

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

function tickTweet() {
    $('#tweet li:first').slideUp(function () {
        $(this).appendTo($('#tweet')).slideDown();
    });
}

$(function() {
    $( "#tabs" ).tabs();
});