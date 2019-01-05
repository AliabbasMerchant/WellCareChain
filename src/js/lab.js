App = {
  web3Provider: null,
  contracts: {},
  account: '0x0',
  chemistId :-1,
  init: async function() {
    return await App.initWeb3();
  },
  makeCode:async function(){		
		$("#qrcode2").empty();
		var qrcode = new QRCode(document.getElementById("qrcode2"), {
		width : 100,
		height : 100
		});
		var instance = await App.contracts.WellCareChain.deployed();
		var id =await instance.getChemistId(App.account);
		qrcode.makeCode(id.toNumber().toString()+","+document.getElementById("amount").value);
	},
	makeCode1:async function(){		
	
		$("#qrcode1").empty();
		var qrcode = new QRCode(document.getElementById("qrcode1"), {
		width : 100,
		height : 100
		});
		var instance = await App.contracts.WellCareChain.deployed();
		var id =await instance.getChemistId(App.account);
		var email = await instance.getChemistEmail(id.toNumber());
		qrcode.makeCode(email.toString());
	},
	accessData: async function(){
		var id = document.getElementById("pat_id").value;
		console.log(id);
		id = parseInt(id);
		var instance = await App.contracts.WellCareChain.deployed();
		var url = await instance.patients(id);
		url = url[4];
		console.log(url);
		var win = window.open(url,'_blank');
		win.focus();
	}
  ,
  initWeb3: async function() {
    if (typeof web3 !== 'undefined') {
      App.web3Provider = web3.currentProvider;
    } else {
      App.web3Provider = new Web3.providers.HttpProvider('http://localhost:8545');
    }
    web3 = new Web3(App.web3Provider);
    return await App.initContract();
  },
  
  initContract: async function() {
    var a = false;
    $.getJSON("Interactions.json", function(wellCareChain) {
      App.contracts.WellCareChain = TruffleContract(wellCareChain);
      App.contracts.WellCareChain.setProvider(App.web3Provider);
      return App.setup();
    });
  },
  
  setup:async function(){
	  web3.eth.getCoinbase(function(err, account) {
      if (err === null) {
        App.account = account;
      }
    });
    var instance = await App.contracts.WellCareChain.deployed();
    App.chemistId = await instance.getChemistId(App.account);
	App.chemistId = App.chemistId.toNumber();
	if(App.chemistId==-1){
		document.getElementById('pat_info').style.display = "None";
	}
	else{
		App.makeCode1();
		document.getElementById('doc-form').style.display = "None";
	}
  },
  Submit : async function(){
	var instance = await App.contracts.WellCareChain.deployed();
	var name = document.getElementById("name").value;
	var email = document.getElementById("email").value;
	var lic_no = document.getElementById("lic_no").value;
	var phy_addr = document.getElementById("phy_add").value;
	await instance.newChemist(name,email,lic_no,phy_addr);
  }
  
};

async function _init() {
    await App.init();
}

async function onSubmit() {
    await App.Submit();
}

$(window).on('load', function(){
    _init();
});

$("#gen_qr").click(function(){
	App.makeCode();
});

$("#acc_data").click(function(){
	App.accessData();
});

$("#sub").click(function(){
	onSubmit();
});
