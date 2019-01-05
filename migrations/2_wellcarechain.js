var WellCareChain = artifacts.require("./WellCareChain.sol");

module.exports = function(deployer) {
  deployer.deploy(WellCareChain);
};