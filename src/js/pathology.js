function makeCode (){		
	var qrcode = new QRCode(document.getElementById("qrcode"), {
	width : 100,
	height : 100
	});
	qrcode.makeCode(document.getElementById("amount").value);
}