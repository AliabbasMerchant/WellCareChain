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
        string presURL;
        string infoURL;
        string reportsURL;
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

    function newPatient(string _name, string _email, string _driveURL, string _presURL, string _infoURL, string _reportsURL) public
    {
        //        TODO FW: msg.sender must not already registered
        patients.push(Patient(_name, msg.sender, _email, _driveURL, _presURL, _infoURL, _reportsURL, true)) - 1;
        noOfPatients++;
    }

    function newDoctor(string _name, string _email, string _license, string _physicalAdd, string _specialization) public
    {
        //        TODO FW: msg.sender must not already registered
        doctors.push(Doctor(_name, msg.sender, _email, _license, _physicalAdd, _specialization)) - 1;
        noOfDoctors++;
    }

    function newChemist(string _name, string _email, string _license, string _physicalAdd) public
    {
        //        TODO FW: msg.sender must not already registered
        chemists.push(Chemist(_name, msg.sender, _email, _license, _physicalAdd)) - 1;
        noOfChemists++;
    }

    function newLab(string _name, string _email, string _license, string _details) public
    {
        //        TODO FW: msg.sender must not already registered
        labs.push(Lab(_name, msg.sender, _email, _license, _details)) - 1;
        noOfLabs++;
    }

    function newPathology(string _name, string _email, string _license, string _physicalAdd) public
    {
        //        TODO FW: msg.sender must not already registered
        pathologies.push(Pathology(_name, msg.sender, _email, _license, _physicalAdd)) - 1;
        noOfPathologies++;
    }
}
