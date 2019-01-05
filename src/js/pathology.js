App = {
  web3Provider: null,
  contracts: {},
  account: '0x0',
  chemistId :-1,
  init: async function() {
    return await App.initWeb3();
  },
  makeCode:async function(){		
		$("#qrcode").empty();
		var qrcode = new QRCode(document.getElementById("qrcode"), {
		width : 100,
		height : 100
		});
		var instance = await App.contracts.WellCareChain.deployed();
		var report = document.getElementById("rep_na").value;
		var id =await instance.getPathologyId(App.account);
		qrcode.makeCode(report+","+id.toNumber().toString()+","+document.getElementById("amount").value);
	},
	accessData: async function(){
		var id = document.getElementById("pat_id").value;
		console.log(id);
		id = parseInt(id);
		var instance = await App.contracts.WellCareChain.deployed();
		var url = await instance.patients(id);
		url = url[6];
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
    App.pathologyId = await instance.getPathologyId(App.account);
	App.pathologyId = App.pathologyId.toNumber();
	if(App.pathologyId==-1){
		document.getElementById('pat_info').style.display = "None";
	}
	else{
		document.getElementById('doc-form').style.display = "None";
	}
  },
  Submit : async function(){
	var instance = await App.contracts.WellCareChain.deployed();
	var name = document.getElementById("name").value;
	var email = document.getElementById("email").value;
	var lic_no = document.getElementById("lic_no").value;
	var phy_addr = document.getElementById("phy_add").value;
	await instance.newPathology(name,email,lic_no,phy_addr);
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
