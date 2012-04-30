$(document).ready(function () {

        var socket;

        $('#connect_form').submit(function () {

            var stockTable = document.getElementById("stockTable");
            var stockRowIndexes = {};
            var host = $("#connect_url").val();
            socket = new WebSocket(host);

            $('#connect').fadeOut({ duration:'fast' });
            $('#disconnect').fadeIn();
            $('#send_form_input').removeAttr('disabled');

            // Add a connect listener
            socket.onopen = function () {
                $('#msg').append('<p class="event">Socket Status: ' + socket.readyState + ' (open)</p>');
            }

            socket.onmessage = function (msg) {
                // $('#msg').append('<p class="message">Received: ' + msg.data + "</p>");

                var quote = JSON.parse(msg.data);

            // extract the stock data fields
            var symbol = quote.symbol;
            var open = quote.open.toFixed(2);
            var last = quote.last.toFixed(2);
            var high = quote.high.toFixed(2);
            var low = quote.low.toFixed(2);
            var newPrice = quote.change.toFixed(2);

            // lookup the table row
            var stockRowIndex = stockRowIndexes[symbol];
            var stockRow = stockTable.rows[stockRowIndex];

            // lazily populate the table row, with 6 cells
            if (stockRow === undefined) {
                var stockRowIndex = stockTable.rows.length;
                stockRow = stockTable.insertRow(stockRowIndex);
                for (var cell = 0; cell < 7; cell++) {
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


        }

        socket.onclose = function () {
            $('#msg').append('<p class="event">Socket Status: ' + socket.readyState + ' (Closed)</p>');
        }

        return false;
    });

    $('#disconnect_form').submit(function () {

        socket.close();

        $('#msg').append('<p class="event">Socket Status: ' + socket.readyState + ' (Closed)</p>');
        $('#disconnect').fadeOut({ duration:'fast' });
        $('#connect').fadeIn();
        $('#send_form_input').addAttr('disabled');

        return false;
    });

});
