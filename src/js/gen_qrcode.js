var qrcode = new QRCode(document.getElementById("qrcode"), {
	width : 100,
	height : 100
});

function makeCode (string) {		
	var elText = document.getElementById("text");
	qrcode.makeCode(string);
}
makeCode("125,divy9881@gmail.com,17227182782787,fhgfgfgfhfgfhghg");