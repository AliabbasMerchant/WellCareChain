pragma solidity ^0.4.23;

import "./Factory.sol";
import "./SafeMath.sol";

contract Interactions is Factory {
    using SafeMath for uint256;
    using SafeMath for uint16;
    using SafeMath for uint8;

    modifier isDoctor(uint _doctorId) {
        bool isDoc = true;
        // TODO FW: Some checks
        require(isDoc, "Can only pay a doctor");
        _;
    }
    modifier isPathology(uint _pathologyId) {
        bool isPath = true;
        // TODO FW: Some checks
        require(isPath, "Can only pay a pathology");
        _;
    }
    modifier isChemist(uint _chemistId) {
        bool isChem = true;
        // TODO FW: Some checks
        require(isChem, "Can only pay a chemist");
        _;
    }
    modifier isLab(uint _labId) {
        bool isL = true;
        // TODO FW: Some checks
        require(isL, "Can only pay a lab");
        _;
    }

    function payToDoctor(uint _doctorId, uint _fees) public payable isDoctor(_doctorId)
    {
        require(msg.value == _fees, "Fees not matching");
        address docAddress = doctors[_doctorId].add;
        docAddress.transfer(_fees);
    }
    function payToPathology(uint _pathologyId, uint _fees, string info) public payable isPathology(_pathologyId)
    {
        require(msg.value == _fees, "Fees not matching");
        address pathologyAddress = pathologies[_pathologyId].add;
        pathologyAddress.transfer(_fees);
    }
    function payToChemist(uint _chemistId, uint _fees) public payable isChemist(_chemistId)
    {
        require(msg.value == _fees, "Fees not matching");
        address chemistAddress = chemists[_chemistId].add;
        chemistAddress.transfer(_fees);
    }
    // TODO Lab and stuff
}
