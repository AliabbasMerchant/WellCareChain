package com.example.max.wellcare;
import android.util.Log;

import org.json.JSONObject;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import java.util.UUID;
import static org.web3j.crypto.Keys.createEcKeyPair;
import static org.web3j.tx.ManagedTransaction.GAS_PRICE;
import static org.web3j.tx.Transfer.GAS_LIMIT;

class BlockchainHelper {
    static String sPrivateKeyInHex, sAddress;
    private static String URL = "https://rinkeby.infura.io/<your token>";
    private static String ContractAddress = "0x";
    private static final String TAG = "BlockchainHelper";
    static BigInteger register(String _name, String _email, String _driveURL, String _presURL, String _infoURL, String _reportsURL) {
//        Web3j web3j = Web3jFactory.build(new HttpService(URL));
        Web3j web3j = Web3j.build(new HttpService(URL));
        BigInteger patientId = new BigInteger("-1");
        try {
            logger("Connected to Ethereum client version: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Credentials credentials = getCredentials(sPrivateKeyInHex);
        logger("Credentials loaded");
        WellCareChain contract = WellCareChain.load(ContractAddress, web3j, credentials, GAS_PRICE, GAS_LIMIT);
        try {
            contract.newPatient(_name, _email, _driveURL, _presURL, _infoURL, _reportsURL).send();
            patientId = contract.noOfPatients().send();
            patientId = patientId.subtract(BigInteger.ONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patientId;
    }
    private static void logger(String msg) {
        Log.e(TAG, "logger: " + msg);
    }
//    static void function() throws Exception{
//        logger("Sending 1 Wei ("
//                + Convert.fromWei("1", Convert.Unit.ETHER).toPlainString() + " Ether)");
//        TransactionReceipt transferReceipt = Transfer.sendFunds(
//                web3j, credentials,
//                "0x19e03255f667bdfd50a32722df860b1eeaf4d635",  // you can put any address here
//                BigDecimal.ONE, Convert.Unit.WEI)  // 1 wei = 10^-18 Ether
//                .send();
//        logger("Transaction complete, view it at https://rinkeby.etherscan.io/tx/" + transferReceipt.getTransactionHash());
//
//        ContractGasProvider contractGasProvider = new DefaultGasProvider();
//        Greeter contract = Greeter.deploy(
//                web3j,
//                credentials,
//                contractGasProvider,
//                "test"
//        ).send();
//
//        String contractAddress = contract.getContractAddress();
//        logger("Smart contract deployed to address " + contractAddress);
//        logger("View contract at https://rinkeby.etherscan.io/address/" + contractAddress);
//
//        logger("Value stored in remote smart contract: " + contract.greet().send());
//
//        // Lets modify the value in our smart contract
//        TransactionReceipt transactionReceipt = contract.newGreeting("Well hello again").send();
//
//        logger("New value stored in remote smart contract: " + contract.greet().send());
//
//        // Events enable us to log specific events happening during the execution of our smart
//        // contract to the blockchain. Index events cannot be logged in their entirety.
//        // For Strings and arrays, the hash of values is provided, not the original value.
//        // For further information, refer to https://docs.web3j.io/filters.html#filters-and-events
//        for (Greeter.ModifiedEventResponse event : contract.getModifiedEvents(transactionReceipt)) {
//            logger("Modify event fired, previous value: " + event.oldGreeting
//                    + ", new value: " + event.newGreeting);
//            logger("Indexed event previous value: " + Numeric.toHexString(event.oldGreetingIdx)
//                    + ", new value: " + Numeric.toHexString(event.newGreetingIdx));
//        }
//    }

    static JSONObject generateCredentials() {
        String seed = UUID.randomUUID().toString();
        JSONObject processJson = new JSONObject();
        try {
            ECKeyPair ecKeyPair = createEcKeyPair();
            BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
            sPrivateKeyInHex = privateKeyInDec.toString(16);
            WalletFile aWallet = Wallet.createLight(seed, ecKeyPair);
            sAddress = aWallet.getAddress();
            processJson.put("address", "0x" + sAddress);
            processJson.put("privateKey", sPrivateKeyInHex);
        } catch (Exception e) {
            Log.e(TAG, "generateCredentials: ", e);
        }
        return processJson;
    }
    static Credentials getCredentials (String privateKeyInHex){
        BigInteger privateKeyInBT = new BigInteger(privateKeyInHex, 16);
        ECKeyPair aPair = ECKeyPair.create(privateKeyInBT);
        return Credentials.create(aPair);
    }
//    static String getChemistDetails(long chemistId) {
////         TODO
//        String name="", add="", email="", license="", physicalAdd="";
//        return "Name: " + name + "\nEmail Address: " + email + "\nLicense: " + license + "\nAddress: " + physicalAdd;
//    }
    static void payToDoctor(BigInteger patientId, BigInteger doctorId, BigInteger fees) {
        Web3j web3j = Web3j.build(new HttpService(URL));
        try {
            logger("Connected to Ethereum client version: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Credentials credentials = getCredentials(sPrivateKeyInHex);
        logger("Credentials loaded");
        WellCareChain contract = WellCareChain.load(ContractAddress, web3j, credentials, GAS_PRICE, GAS_LIMIT);
        try {
            contract.payToDoctor(patientId, doctorId, fees, fees).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void payToPathology(BigInteger patientId, BigInteger pathologyId, BigInteger fees, String info) {
        Web3j web3j = Web3j.build(new HttpService(URL));
        try {
            logger("Connected to Ethereum client version: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Credentials credentials = getCredentials(sPrivateKeyInHex);
        logger("Credentials loaded");
        WellCareChain contract = WellCareChain.load(ContractAddress, web3j, credentials, GAS_PRICE, GAS_LIMIT);
        try {
            contract.payToPathology(patientId, pathologyId, fees, info, fees).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void payToChemist(BigInteger patientId, BigInteger chemistId, BigInteger fees) {
        Web3j web3j = Web3j.build(new HttpService(URL));
        try {
            logger("Connected to Ethereum client version: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Credentials credentials = getCredentials(sPrivateKeyInHex);
        logger("Credentials loaded");
        WellCareChain contract = WellCareChain.load(ContractAddress, web3j, credentials, GAS_PRICE, GAS_LIMIT);
        try {
            contract.payToChemist(patientId, chemistId, fees, fees).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String getDoctorEmail(BigInteger doctorId) {
        Web3j web3j = Web3j.build(new HttpService(URL));
        try {
            logger("Connected to Ethereum client version: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Credentials credentials = getCredentials(sPrivateKeyInHex);
        logger("Credentials loaded");
        WellCareChain contract = WellCareChain.load(ContractAddress, web3j, credentials, GAS_PRICE, GAS_LIMIT);
        try {
            return contract.getDoctorEmail(doctorId).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    static String getPathologyEmail(BigInteger pathologyId) {
        Web3j web3j = Web3j.build(new HttpService(URL));
        try {
            logger("Connected to Ethereum client version: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Credentials credentials = getCredentials(sPrivateKeyInHex);
        logger("Credentials loaded");
        WellCareChain contract = WellCareChain.load(ContractAddress, web3j, credentials, GAS_PRICE, GAS_LIMIT);
        try {
            return contract.getPathologyEmail(pathologyId).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    static void getBounty(BigInteger requirementId, String URL) {
        // TODO
    }
}
