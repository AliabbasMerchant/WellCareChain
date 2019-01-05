function makeCode(){		
	var qrcode = new QRCode(document.getElementById("qrcode2"), {
	width : 100,
	height : 100
	});
	qrcode.makeCode(document.getElementById("amount").value);
}

function makeCode1(){		
	var qrcode = new QRCode(document.getElementById("qrcode1"), {
	width : 100,
	height : 100
	});
	qrcode.makeCode("divy9881@gmail.com");
}

