pragma solidity ^0.4.23;

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
        string medNotes;
        string allergiesReactions;
        string medication;
        string bloodType;
        string weight;
        string height;
        string emergencyContacts;
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
//        TODO
    }
    struct Pathology {
        string name;
        address add;
        string email;
        string license;
        string physicalAdd;
    }
    uint public noOfPatients = 0;
    uint public noOfDoctors = 0;
    uint public noOfChemists = 0;
    uint public noOfLabs = 0;
    uint public noOfPathologies = 0;

    Patient[] public patients;
    Doctor[] public doctors;
    Chemist[] public chemists;
    Lab[] public labs;
    Pathology[] public pathologies;

    function newPatient(string _name, string _email, string _driveURL, uint8 _DOBDate, uint8 _DOBMonth, uint16 _DOBYear, string _medCondition,
        string _medNotes, string _allergiesReactions, string _medication, string _bloodType, string _weight, string _height, string _emergencyContacts) public
    {
        //        TODO FW: msg.sender must not already registered
        uint _patientId = patients.push(Patient(_name, msg.sender, _email, _driveURL, _DOBDate, _DOBMonth, _DOBYear, _medCondition, _medNotes, _allergiesReactions, _medication,
            _bloodType, _weight, _height, _emergencyContacts)) - 1;
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
    function newLab() public
    {
        //        TODO FW: msg.sender must not already registered
        uint _labId = labs.push(Lab()) - 1;
        noOfLabs++;
    }
    function newPathology(string _name, string _email, string _license, string _physicalAdd) public
    {
        //        TODO FW: msg.sender must not already registered
        uint _pathologyId = pathologies.push(Pathology(_name, msg.sender, _email, _license, _physicalAdd)) - 1;
        noOfPathologies++;
    }
}
