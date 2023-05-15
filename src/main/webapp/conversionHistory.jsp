<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
  <head>
    <title>CryptoCurrency Prices</title>
    <style>
      html,
      body {
        font-family: "Arial", sans-serif;
        height: 100%;
        margin: 0;
      }

      .center {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        height: 100%;
      }

      select,
      input[type="text"] {
        width: 250px;
        box-sizing: border-box;
        padding: 5px;
        margin-bottom: 20px;
      }

      .price {
        text-align: center;
      }

      #result p {
        margin-left: -80px;
      }

      #history-table {
        margin-top: 20px;
        display: none;
        border-collapse: collapse;
      }

      #history-table th,
      #history-table td {
        border: 1px solid black;
        padding: 5px;
      }

      #hide-btn {
        margin-top: 10px;
        display: none;
      }

      #show-history-btn {
        margin-top: 10px;
      }
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  </head>
  <body>
    <div class="center">
      <form>
        <table id="history-table">
          <thead>
            <tr>
              <th>Date and Time</th>
              <th>Name</th>
              <th>Price</th>
              <th>From Currency</th>
              <th>To Currency</th>
            </tr>
          </thead>
          <tbody>
            <!-- table rows will be dynamically populated -->
          </tbody>
        </table>
      </form>
    </div>
    <script>
      $(document).ready(function() {
            var username = localStorage.getItem("username");
            console.log(username);
            var historyTable = $('#history-table');
            $.ajax({
                url: 'ConversionHistory',
                type: 'GET',
                data: {
                  username: username
                },
                success: function(response) {
                  console.log(response);
                  var historyData = JSON.parse(response);
                  for (var i = 0; i < historyData.length; i++) {
                    var conversionTime = historyData[i].dateTime;
                    var cryptoCurrency = historyData[i].cryptocurrency;
                    var convertedAmount = historyData[i].convertedAmount;
                    var fromCurrency = historyData[i].fromCurrency;
                    var toCurrency = historyData[i].toCurrency;
                    historyTable.append('<tr><td>' + conversionTime + '</td><td>' + cryptoCurrency + '</td><td>' + convertedAmount + '</td><td>' + fromCurrency + '</td><td>' + toCurrency + '</td></tr>');
                    }
                    historyTable.show();
                  },
                  error: function(xhr, status, error) {
                    console.log(error);
                  }
                });
            });
    </script>
  </body>
</html>