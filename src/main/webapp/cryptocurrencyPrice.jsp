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

      #show-history-btn {
        margin-left: 65px;
        display: block;
        margin-top: -12px;
      }
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  </head>
  <body>
    <div class="center">
      <form>
        <select id="cryptocurrency">
          <option value="" selected disabled>Select Cryptocurrency</option>
          <optgroup label="Cryptocurrency">
            <option value="bitcoin">Bitcoin (BTC)</option>
            <option value="ethereum">Ethereum (ETH)</option>
            <option value="tether">Tether (USDT)</option>
            <option value="dogecoin">Dogecoin (DOGE)</option>
            <option value="polygon">Polygon (MATIC)</option>
          </optgroup>
        </select>
        <br>
        <input id="ipAddress" type="text" placeholder="IP Address">
        <span> (Optional)</span>
        <input type="button" id="show-history-btn" value="Conversion History" />
        <br>
        <div class="price" id="result"></div>
      </form>
    </div>

  </body>
</html>