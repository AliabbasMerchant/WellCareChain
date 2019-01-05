pragma solidity ^0.4.23;
pragma experimental ABIEncoderV2;

import "./Factory.sol";
import "./SafeMath.sol";

contract Interactions is Factory {
    using SafeMath for uint256;

    event newDataRequirement(string message, uint labId, uint bountyForEach);

    function getPatientId(address addr) public view returns(int){
        for (uint i = 0; i < noOfPatients; i++)
            if (addr == patients[i].add)
                return int(i);
        return - 1;
    }

    function getDoctorId(address addr) public view returns(int){
        for (uint i = 0; i < noOfDoctors; i++)
            if (addr == doctors[i].add)
                return int(i);
        return - 1;
    }

    function getLabId(address addr) public view returns(int){
        for (uint i = 0; i < noOfLabs; i++)
            if (addr == labs[i].add)
                return int(i);
        return - 1;
    }

    function getPathologyId(address addr) public view returns(int){
        for (uint i = 0; i < noOfPathologies; i++)
            if (addr == pathologies[i].add)
                return int(i);
        return - 1;
    }

    function getChemistId(address addr) public view returns(int){
        for (uint i = 0; i < noOfChemists; i++)
            if (addr == chemists[i].add)
                return int(i);
        return - 1;
    }

    modifier senderIsLab() {
        int id = getLabId(msg.sender);
        require(id >= 0, "Function can be called by a lab only");
        _;
    }
    modifier senderIsPatient() {
        int id = getPatientId(msg.sender);
        require(id >= 0, "Function can be called by a patient only");
        _;
    }

    function payToDoctor(uint _patientId, uint _doctorId, uint _fees) public payable
    {
        require(0 <= _doctorId && _doctorId < noOfDoctors, "Doctor does not exist");
        require(msg.value == _fees, "Fees not matching");
        address docAddress = doctors[_doctorId].add;
        patients[_patientId].presUsed = false;
        docAddress.transfer(_fees);
    }

    function payToPathology(uint _patientId, uint _pathologyId, uint _fees, string info) public payable
    {
        require(0 <= _pathologyId && _pathologyId < noOfPathologies, "Pathology does not exist");
        require(msg.value == _fees, "Fees not matching");
        address pathologyAddress = pathologies[_pathologyId].add;
        pathologyAddress.transfer(_fees);
    }

    function payToChemist(uint _patientId, uint _chemistId, uint _fees) public payable
    {
        require(0 <= _chemistId && _chemistId < noOfChemists, "Chemist does not exist");
        require(!patients[_patientId].presUsed, "Medicines have already been taken!");
        require(msg.value == _fees, "Fees not matching");
        patients[_patientId].presUsed = true;
        address chemistAddress = chemists[_chemistId].add;
        chemistAddress.transfer(_fees);
    }

    function alertDataRequirement(string _message, uint _number) public payable senderIsLab {
        require(msg.value > _number * 100);
        uint _labId = uint(getLabId(msg.sender));
        dataRequirements.push(DataRequirement(_message, _labId, uint(msg.value.div(_number)), _number, 0)) - 1;
        noOfDataRequirements++;
        emit newDataRequirement(_message, _labId, uint(msg.value.div(_number)));
    }

    function getBounty(uint _requirementId, string _url) public senderIsPatient {
        uint _patientId = uint(getPatientId(msg.sender));
        require(dataRequirements[_requirementId].gotData < dataRequirements[_requirementId].number, "Requirement has already been fulfilled");
        for (uint i = 0; i < noOfData; i++) {
            if (data[i].requirementId == _requirementId) {
                if (data[i].patientId == _patientId) {
                    require(false, "You have already submitted data for this requirement");
                }
            }
        }
        data.push(Data(_requirementId, _patientId, _url));
        noOfData += 1;
        msg.sender.transfer(dataRequirements[_requirementId].bountyForEach);
    }

    function accessData(uint _requirementId) public view senderIsLab returns(Data[] memory ans){
        uint _labId = uint(getLabId(msg.sender));
        require(dataRequirements[_requirementId].labId == _labId, "You cannot access this data");
        uint no = dataRequirements[_requirementId].gotData;
        ans = new Data[](no);
        for (uint i = 0; i < noOfData; i++) {
            if (data[i].requirementId == _requirementId) {
                ans[i] = data[i];
            }
        }
        return ans;
    }
//  TODO Supply chain
}
