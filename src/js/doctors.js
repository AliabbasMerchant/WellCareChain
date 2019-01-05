

App = {
  web3Provider: null,
  contracts: {},
  account: '0x0',
  doctorId :-1,
  init: async function() {
    return await App.initWeb3();
  },
  makeCode:function(){		
		var qrcode = new QRCode(document.getElementById("qrcode"), {
		width : 100,
		height : 100
		});
		qrcode.makeCode(document.getElementById("fees").value);
	},
  
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
  
  setup:function(){
	  web3.eth.getCoinbase(function(err, account) {
      if (err === null) {
        App.account = account;
      }
    });
    var instance = await App.contracts.WellCareChain.deployed();
    App.doctorId = await instance.getDoctorId(App.account);
	App.doctorId = App.doctorId.toNumber();
	if(doctorId==-1){
		document.getElementById('doc-form').style.display = "None";
	}
	else{
		document.getElementById('pat_info').style.display = "None";
	}
    noOfTokens = noOfTokens.toNumber();
	  App.contracts.EstateToken.deployed().then(function(_instance) {
        inst = _instance;
        return _instance.tokens(_tokenId);
    }).then(function(token) {
        var sellValPerSqFt = token[2];
        sellValPerSqFt = sellValPerSqFt.toNumber();
        console.log(sellValPerSqFt);
        return inst.buy(_tokenId, { from: App.account, value:web3.toWei(sellValPerSqFt, 'ether')});
    }).then(function(result) {
        console.log(result);
    }).catch(function(err) {
        console.log(err);
    });
  },
  Submit :function(){
	  
  }
  
};

async function _init() {
    await App.init();
}

$(window).on('load', function(){
//  await App.init();
    _init();
});