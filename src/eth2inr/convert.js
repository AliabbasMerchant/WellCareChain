var eth2USD;
var USD2INR;

function getConversionConstants()
{ 
    let response1;
    console.log("Getting Conversion Constants");
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
           // Typical action to be performed when the document is ready:
            response1 = xhttp.responseText;
            response1 = JSON.parse(response1);
            eth2USD = response1["ticker"]["price"];
        }
    };
    xhttp.open("GET", "https://api.cryptonator.com/api/ticker/eth-usd", true);
    xhttp.send();

    let response2;
    var xhttp2 = new XMLHttpRequest();
            xhttp2.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
            // Typical action to be performed when the document is ready:
                let response2 = xhttp2.responseText;
                response2 = JSON.parse(response2);
                USD2INR = response2["USD_INR"]["val"];

            }
            }   ;
            xhttp2.open("GET", "http://free.currencyconverterapi.com/api/v5/convert?q=USD_INR&compact=y", true);
            xhttp2.send();

    

}

getConversionConstants();

function convert2INR(ether)
{
    return ether*eth2USD*USD2INR;
}

function convert2ether(INR)
{
    return INR/eth2USD/USD2INR;
}

