$(document).ready(function() {

    var client, destinationQuotes;

    $('#connect_form').submit(function() {
        var url = $("#connect_url").val();
        var login = $("#connect_login").val();
        var passcode = $("#connect_passcode").val();
        destinationQuotes = $("#destinationQuotes").val();

        client = Stomp.client(url);

        // this allows to display debug logs directly on the web page
        client.debug = function(str) {
            $("#debug").append(str + "\n");
        };
        // the client is notified when it is connected to the server.
        var onconnect = function(frame) {
            client.debug("connected to Stomp");
            $('#connect').fadeOut({ duration: 'fast' });
            $('#disconnect').fadeIn();
            $('#send_form_input').removeAttr('disabled');

            var stockTable = document.getElementById("stockTable");
            var stockRowIndexes = {};

            client.subscribe(destinationQuotes, function(message) {
                var quote = JSON.parse(message.body);
                $('.' + "stock-" + quote.symbol).replaceWith("<tr class=\"stock-" + quote.symbol + "\">" +
                    "<td>" + quote.symbol + "</td>" +
                    "<td>" + quote.open.toFixed(2) + "</td>" +
                    "<td>" + quote.last.toFixed(2) + "</td>" +
                    "<td>" + quote.change.toFixed(2) + "</td>" +
                    "<td>" + quote.high.toFixed(2) + "</td>" +
                    "<td>" + quote.low.toFixed(2) + "</td>" +
                    "</tr>");

                // extract the stock data fields
                var symbol   = quote.symbol;
                var open     = quote.open.toFixed(2);
                var last     = quote.last.toFixed(2);
                var high     = quote.high.toFixed(2);
                var low      = quote.low.toFixed(2);
                var newPrice = quote.change.toFixed(2);

                // lookup the table row
                var stockRowIndex = stockRowIndexes[symbol];
                var stockRow = stockTable.rows[stockRowIndex];

                // lazily populate the table row, with 6 cells
                if (stockRow === undefined) {
                    var stockRowIndex = stockTable.rows.length;
                    stockRow = stockTable.insertRow(stockRowIndex);
                    for (var cell=0; cell < 7; cell++) {
                        stockRow.insertCell(cell);
                    }
                    stockRow.cells[0].className = 'symbol';
                    stockRow.cells[1].className = 'open';
                    stockRow.cells[2].className = 'last';
                    stockRow.cells[3].className = 'change';
                    stockRow.cells[4].className = 'high';
                    stockRow.cells[5].className = 'low';
                    stockRow.cells[6].className = 'percent';
                    stockRowIndexes[symbol] = stockRowIndex;
                }


                // detect price change
                var oldPrice = Number(stockRow.cells[2].innerHTML);
                var oldChange = Number(stockRow.cells[3].innerHTML);
                var change = ((oldPrice != 0) ? (newPrice - oldPrice) : 0).toFixed(2);
                var percent = ((oldPrice != 0) ? (change / oldPrice * 100) : 0).toFixed(1);

                // update the table row cell data
                stockRow.cells[0].innerHTML = symbol;
                stockRow.cells[1].innerHTML = open;
                stockRow.cells[2].innerHTML = last;
                stockRow.cells[3].innerHTML = change;
                stockRow.cells[4].innerHTML = high;
                stockRow.cells[5].innerHTML = low;
                stockRow.cells[6].innerHTML = percent;

                // update the table row cell styles
                var oldSign = (oldChange != 0) ? oldChange / Math.abs(oldChange) : 0;
                var sign = (change != 0) ? change / Math.abs(change) : 0;
                if (sign != oldSign) {
                    switch (sign) {
                        case 1:
                            stockRow.cells[3].className = 'upChange';
                            stockRow.cells[4].className = 'upPercent';
                            break;
                        case -1:
                            stockRow.cells[3].className = 'downChange';
                            stockRow.cells[4].className = 'downPercent';
                            break;
                    }
                }


            });

        };
        client.connect(login, passcode, onconnect);

        return false;
    });

    $('#disconnect_form').submit(function() {
        client.disconnect(function() {
            $('#disconnect').fadeOut({ duration: 'fast' });
            $('#connect').fadeIn();
            $('#send_form_input').addAttr('disabled');
        });
        return false;
    });

    $('#send_form').submit(function() {
        var text = $('#send_form_input').val();
        if (text) {
            client.send(destination, {foo: 1}, text);
            $('#send_form_input').val("");
        }
        return false;
    });

});
