pragma solidity ^0.4.23;
//pragma experimental ABIEncoderV2;

import "./Ownable.sol";
import "./SafeMath.sol";

contract Factory is Ownable {
    using SafeMath for uint256;
    using SafeMath for uint16;
    using SafeMath for uint8;

    //    TODO
    event NewDoctor();
    event NewChemist();
    event NewLab();
    event NewPathology();

    struct Patient {
        string name;
        address add;
        string email;
        string driveURL;
        uint8 DOBDate;
        uint8 DOBMonth;
        uint16 DOBYear;
        string medCondition;
        string allergiesReactions;
        string medication;
        string bloodType;
        string weight;
        string height;
        string emergencyContacts;
        bool presUsed;
    }

    struct Doctor {
        string name;
        address add;
        string email;
        string license;
        string physicalAdd;
        string specialization;
    }

    struct Chemist {
        string name;
        address add;
        string email;
        string license;
        string physicalAdd;
    }

    struct Lab {
        string name;
        address add;
        string email;
        string license;
        string details;
    }

    struct Pathology {
        string name;
        address add;
        string email;
        string license;
        string physicalAdd;
    }

    struct DataRequirement {
        string message;
        uint labId;
        uint bountyForEach;
        uint number;
        uint gotData;
    }

    struct Data {
        uint requirementId;
        uint patientId;
        string url;
    }

    uint public noOfPatients = 0;
    uint public noOfDoctors = 0;
    uint public noOfChemists = 0;
    uint public noOfLabs = 0;
    uint public noOfPathologies = 0;
    uint public noOfData = 0;
    uint public noOfDataRequirements = 0;

    Patient[] public patients;
    Doctor[] public doctors;
    Chemist[] public chemists;
    Lab[] public labs;
    Pathology[] public pathologies;
    DataRequirement[] public dataRequirements;
    Data[] public data;

    function newPatient(string _name, string _email, string _driveURL, uint8 _DOBDate, uint8 _DOBMonth, uint16 _DOBYear, string _medCondition,
        string _allergiesReactions, string _medication, string _bloodType, string _weight, string _height, string _emergencyContacts) public
    {
        //        TODO FW: msg.sender must not already registered
        uint _patientId = patients.push(Patient(_name, msg.sender, _email, _driveURL, _DOBDate, _DOBMonth, _DOBYear, _medCondition, _allergiesReactions, _medication,
            _bloodType, _weight, _height, _emergencyContacts, true)) - 1;
        noOfPatients++;
    }

    function newDoctor(string _name, string _email, string _license, string _physicalAdd, string _specialization) public
    {
        //        TODO FW: msg.sender must not already registered
        uint _doctorId = doctors.push(Doctor(_name, msg.sender, _email, _license, _physicalAdd, _specialization)) - 1;
        noOfDoctors++;
    }

    function newChemist(string _name, string _email, string _license, string _physicalAdd) public
    {
        //        TODO FW: msg.sender must not already registered
        uint _chemistId = chemists.push(Chemist(_name, msg.sender, _email, _license, _physicalAdd)) - 1;
        noOfChemists++;
    }

    function newLab(string _name, string _email, string _license, string _details) public
    {
        //        TODO FW: msg.sender must not already registered
        uint _labId = labs.push(Lab(_name, msg.sender, _email, _license, _details)) - 1;
        noOfLabs++;
    }

    function newPathology(string _name, string _email, string _license, string _physicalAdd) public
    {
        //        TODO FW: msg.sender must not already registered
        uint _pathologyId = pathologies.push(Pathology(_name, msg.sender, _email, _license, _physicalAdd)) - 1;
        noOfPathologies++;
    }
}
