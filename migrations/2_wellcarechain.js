var WellCareChain = artifacts.require("./Interactions.sol");

module.exports = function(deployer) {
  deployer.deploy(WellCareChain);
};