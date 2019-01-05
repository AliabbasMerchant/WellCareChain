package com.example.max.wellcare;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tuples.generated.Tuple8;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.0.1.
 */
public class Factory_sol_Factory extends Contract {
    private static final String BINARY = "60806040526000805560006001556000600255600060035560006004556000600555600060065534801561003257600080fd5b5061242e806100426000396000f3006080604052600436106101325763ffffffff7c0100000000000000000000000000000000000000000000000000000000600035041663025cccea81146101375780630ba513eb1461024c5780632650afc01461027357806337dbc67014610288578063488a216f1461029d5780634bb7c6441461032a5780636efb1b841461050557806384b828ba146107465780638771883e1461075b57806393119a83146107735780639be23de614610a255780639d761aa014610b38578063a1816cc514610b50578063abc21a0114610b65578063b0a7a3b614610c78578063cf43684714610c8d578063dc30b57014610d39578063dfbce01414610e8a578063e81dbf9314610e9f578063f0ba844014610eb7578063f2907a8814610f55578063f7b8be9614610f6d578063fa4afb3914610f85575b600080fd5b34801561014357600080fd5b506040805160206004803580820135601f810184900484028501840190955284845261024a94369492936024939284019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a9998810197919650918201945092508291508401838280828437509497506111149650505050505050565b005b34801561025857600080fd5b5061026161122b565b60408051918252519081900360200190f35b34801561027f57600080fd5b50610261611231565b34801561029457600080fd5b50610261611237565b3480156102a957600080fd5b506102b560043561123d565b6040805160208082528351818301528351919283929083019185019080838360005b838110156102ef5781810151838201526020016102d7565b50505050905090810190601f16801561031c5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561033657600080fd5b506103426004356112f2565b604051808060200186600160a060020a0316600160a060020a0316815260200180602001806020018060200185810385528a818151815260200191508051906020019080838360005b838110156103a357818101518382015260200161038b565b50505050905090810190601f1680156103d05780820380516001836020036101000a031916815260200191505b5085810384528851815288516020918201918a019080838360005b838110156104035781810151838201526020016103eb565b50505050905090810190601f1680156104305780820380516001836020036101000a031916815260200191505b50858103835287518152875160209182019189019080838360005b8381101561046357818101518382015260200161044b565b50505050905090810190601f1680156104905780820380516001836020036101000a031916815260200191505b50858103825286518152865160209182019188019080838360005b838110156104c35781810151838201526020016104ab565b50505050905090810190601f1680156104f05780820380516001836020036101000a031916815260200191505b50995050505050505050505060405180910390f35b34801561051157600080fd5b5061051d600435611567565b604051808060200187600160a060020a0316600160a060020a031681526020018060200180602001806020018060200186810386528c818151815260200191508051906020019080838360005b8381101561058257818101518382015260200161056a565b50505050905090810190601f1680156105af5780820380516001836020036101000a031916815260200191505b5086810385528a5181528a516020918201918c019080838360005b838110156105e25781810151838201526020016105ca565b50505050905090810190601f16801561060f5780820380516001836020036101000a031916815260200191505b5086810384528951815289516020918201918b019080838360005b8381101561064257818101518382015260200161062a565b50505050905090810190601f16801561066f5780820380516001836020036101000a031916815260200191505b5086810383528851815288516020918201918a019080838360005b838110156106a257818101518382015260200161068a565b50505050905090810190601f1680156106cf5780820380516001836020036101000a031916815260200191505b50868103825287518152875160209182019189019080838360005b838110156107025781810151838201526020016106ea565b50505050905090810190601f16801561072f5780820380516001836020036101000a031916815260200191505b509b50505050505050505050505060405180910390f35b34801561075257600080fd5b5061026161186c565b34801561076757600080fd5b506102b5600435611872565b34801561077f57600080fd5b5061078b6004356118f0565b604051808060200189600160a060020a0316600160a060020a0316815260200180602001806020018060200180602001806020018815151515815260200187810387528f818151815260200191508051906020019080838360005b838110156107fe5781810151838201526020016107e6565b50505050905090810190601f16801561082b5780820380516001836020036101000a031916815260200191505b5087810386528d5181528d516020918201918f019080838360005b8381101561085e578181015183820152602001610846565b50505050905090810190601f16801561088b5780820380516001836020036101000a031916815260200191505b5087810385528c5181528c516020918201918e019080838360005b838110156108be5781810151838201526020016108a6565b50505050905090810190601f1680156108eb5780820380516001836020036101000a031916815260200191505b5087810384528b5181528b516020918201918d019080838360005b8381101561091e578181015183820152602001610906565b50505050905090810190601f16801561094b5780820380516001836020036101000a031916815260200191505b5087810383528a5181528a516020918201918c019080838360005b8381101561097e578181015183820152602001610966565b50505050905090810190601f1680156109ab5780820380516001836020036101000a031916815260200191505b5087810382528951815289516020918201918b019080838360005b838110156109de5781810151838201526020016109c6565b50505050905090810190601f168015610a0b5780820380516001836020036101000a031916815260200191505b509e50505050505050505050505050505060405180910390f35b348015610a3157600080fd5b506040805160206004803580820135601f810184900484028501840190955284845261024a94369492936024939284019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a999881019791965091820194509250829150840183828082843750949750611c8e9650505050505050565b348015610b4457600080fd5b506102b5600435611da5565b348015610b5c57600080fd5b50610261611db6565b348015610b7157600080fd5b506040805160206004803580820135601f810184900484028501840190955284845261024a94369492936024939284019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a999881019791965091820194509250829150840183828082843750949750611dbc9650505050505050565b348015610c8457600080fd5b50610261611ed3565b348015610c9957600080fd5b50610ca5600435611ed9565b6040518080602001868152602001858152602001848152602001838152602001828103825287818151815260200191508051906020019080838360005b83811015610cfa578181015183820152602001610ce2565b50505050905090810190601f168015610d275780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b348015610d4557600080fd5b506040805160206004803580820135601f810184900484028501840190955284845261024a94369492936024939284019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a999881019791965091820194509250829150840183828082843750949750611fa29650505050505050565b348015610e9657600080fd5b506102616120dc565b348015610eab57600080fd5b506103426004356120e2565b348015610ec357600080fd5b50610ecf6004356120f0565b6040518084815260200183815260200180602001828103825283818151815260200191508051906020019080838360005b83811015610f18578181015183820152602001610f00565b50505050905090810190601f168015610f455780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b348015610f6157600080fd5b506102b56004356121c0565b348015610f7957600080fd5b506103426004356121d1565b348015610f9157600080fd5b506040805160206004803580820135601f810184900484028501840190955284845261026194369492936024939284019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a9998810197919650918201945092508291508401838280828437509497506121df9650505050505050565b6040805160a081018252858152600160a060020a0333166020808301919091529181018590526060810184905260808101839052600a80546001810180835560009290925282518051929460059092027fc65a7bb8d6351c1cf70c95a316cc6a92839c986682d98bc35f958f4883f9d2a801926111949284920190612367565b5060208281015160018301805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03909216919091179055604083015180516111e09260028501920190612367565b50606082015180516111fc916003840191602090910190612367565b5060808201518051611218916004840191602090910190612367565b5050600380546001019055505050505050565b60065481565b60015481565b60055481565b6060600b8281548110151561124e57fe5b600091825260209182902060026005909202018101805460408051601f6000196101006001861615020190931694909404918201859004850284018501905280835291929091908301828280156112e65780601f106112bb576101008083540402835291602001916112e6565b820191906000526020600020905b8154815290600101906020018083116112c957829003601f168201915b50505050509050919050565b600a80548290811061130057fe5b60009182526020918290206005919091020180546040805160026001841615610100026000190190931692909204601f8101859004850283018501909152808252919350918391908301828280156113995780601f1061136e57610100808354040283529160200191611399565b820191906000526020600020905b81548152906001019060200180831161137c57829003601f168201915b505050506001838101546002808601805460408051602061010097841615979097026000190190921693909304601f81018690048602820186019093528281529596600160a060020a0390931695929450919283018282801561143d5780601f106114125761010080835404028352916020019161143d565b820191906000526020600020905b81548152906001019060200180831161142057829003601f168201915b5050505060038301805460408051602060026001851615610100026000190190941693909304601f81018490048402820184019092528181529495949350908301828280156114cd5780601f106114a2576101008083540402835291602001916114cd565b820191906000526020600020905b8154815290600101906020018083116114b057829003601f168201915b5050505060048301805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815294959493509083018282801561155d5780601f106115325761010080835404028352916020019161155d565b820191906000526020600020905b81548152906001019060200180831161154057829003601f168201915b5050505050905085565b600880548290811061157557fe5b60009182526020918290206006919091020180546040805160026001841615610100026000190190931692909204601f81018590048502830185019091528082529193509183919083018282801561160e5780601f106115e35761010080835404028352916020019161160e565b820191906000526020600020905b8154815290600101906020018083116115f157829003601f168201915b505050506001838101546002808601805460408051602061010097841615979097026000190190921693909304601f81018690048602820186019093528281529596600160a060020a039093169592945091928301828280156116b25780601f10611687576101008083540402835291602001916116b2565b820191906000526020600020905b81548152906001019060200180831161169557829003601f168201915b5050505060038301805460408051602060026001851615610100026000190190941693909304601f81018490048402820184019092528181529495949350908301828280156117425780601f1061171757610100808354040283529160200191611742565b820191906000526020600020905b81548152906001019060200180831161172557829003601f168201915b5050505060048301805460408051602060026001851615610100026000190190941693909304601f81018490048402820184019092528181529495949350908301828280156117d25780601f106117a7576101008083540402835291602001916117d2565b820191906000526020600020905b8154815290600101906020018083116117b557829003601f168201915b5050505060058301805460408051602060026001851615610100026000190190941693909304601f81018490048402820184019092528181529495949350908301828280156118625780601f1061183757610100808354040283529160200191611862565b820191906000526020600020905b81548152906001019060200180831161184557829003601f168201915b5050505050905086565b60005481565b606060088281548110151561188357fe5b600091825260209182902060026006909202018101805460408051601f6000196101006001861615020190931694909404918201859004850284018501905280835291929091908301828280156112e65780601f106112bb576101008083540402835291602001916112e6565b60078054829081106118fe57fe5b60009182526020918290206008919091020180546040805160026001841615610100026000190190931692909204601f8101859004850283018501909152808252919350918391908301828280156119975780601f1061196c57610100808354040283529160200191611997565b820191906000526020600020905b81548152906001019060200180831161197a57829003601f168201915b505050506001838101546002808601805460408051602061010097841615979097026000190190921693909304601f81018690048602820186019093528281529596600160a060020a03909316959294509192830182828015611a3b5780601f10611a1057610100808354040283529160200191611a3b565b820191906000526020600020905b815481529060010190602001808311611a1e57829003601f168201915b5050505060038301805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152949594935090830182828015611acb5780601f10611aa057610100808354040283529160200191611acb565b820191906000526020600020905b815481529060010190602001808311611aae57829003601f168201915b5050505060048301805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152949594935090830182828015611b5b5780601f10611b3057610100808354040283529160200191611b5b565b820191906000526020600020905b815481529060010190602001808311611b3e57829003601f168201915b5050505060058301805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152949594935090830182828015611beb5780601f10611bc057610100808354040283529160200191611beb565b820191906000526020600020905b815481529060010190602001808311611bce57829003601f168201915b5050505060068301805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152949594935090830182828015611c7b5780601f10611c5057610100808354040283529160200191611c7b565b820191906000526020600020905b815481529060010190602001808311611c5e57829003601f168201915b5050506007909301549192505060ff1688565b6040805160a081018252858152600160a060020a0333166020808301919091529181018590526060810184905260808101839052600980546001810180835560009290925282518051929460059092027f6e1540171b6c0c960b71a7020d9f60077f6af931a8bbf590da0223dacf75c7af0192611d0e9284920190612367565b5060208281015160018301805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0390921691909117905560408301518051611d5a9260028501920190612367565b5060608201518051611d76916003840191602090910190612367565b5060808201518051611d92916004840191602090910190612367565b5050600280546001019055505050505050565b6060600a8281548110151561124e57fe5b60035481565b6040805160a081018252858152600160a060020a0333166020808301919091529181018590526060810184905260808101839052600b80546001810180835560009290925282518051929460059092027f0175b7a638427703f0dbe7bb9bbf987a2551717b34e79f33b5b1008d1fa01db90192611e3c9284920190612367565b5060208281015160018301805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0390921691909117905560408301518051611e889260028501920190612367565b5060608201518051611ea4916003840191602090910190612367565b5060808201518051611ec0916004840191602090910190612367565b5050600480546001019055505050505050565b60045481565b600c805482908110611ee757fe5b60009182526020918290206005919091020180546040805160026001841615610100026000190190931692909204601f810185900485028301850190915280825291935091839190830182828015611f805780601f10611f5557610100808354040283529160200191611f80565b820191906000526020600020905b815481529060010190602001808311611f6357829003601f168201915b5050505050908060010154908060020154908060030154908060040154905085565b6040805160c081018252868152600160a060020a033316602080830191909152918101869052606081018590526080810184905260a08101839052600880546001810180835560009290925282518051929460069092027ff3f7a9fe364faab93b216da50a3214154f22a0a2b415b23a84c8169e8b636ee301926120299284920190612367565b5060208281015160018301805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03909216919091179055604083015180516120759260028501920190612367565b5060608201518051612091916003840191602090910190612367565b50608082015180516120ad916004840191602090910190612367565b5060a082015180516120c9916005840191602090910190612367565b5050600180548101905550505050505050565b60025481565b600980548290811061130057fe5b600d8054829081106120fe57fe5b9060005260206000209060030201600091509050806000015490806001015490806002018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156121b65780601f1061218b576101008083540402835291602001916121b6565b820191906000526020600020905b81548152906001019060200180831161219957829003601f168201915b5050505050905083565b606060098281548110151561124e57fe5b600b80548290811061130057fe5b6040805161010081018252878152600160a060020a033316602080830191909152918101879052606081018690526080810185905260a0810184905260c08101839052600160e082018190526007805480830180835560009283528451805193968796929492936008027fa66cc928b5edb82af9bd49922954155ab7b0942694bea4ce44661d9a8736c68801926122799284920190612367565b5060208281015160018301805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03909216919091179055604083015180516122c59260028501920190612367565b50606082015180516122e1916003840191602090910190612367565b50608082015180516122fd916004840191602090910190612367565b5060a08201518051612319916005840191602090910190612367565b5060c08201518051612335916006840191602090910190612367565b5060e091909101516007909101805460ff19169115159190911790556000805460010190550398975050505050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106123a857805160ff19168380011785556123d5565b828001600101855582156123d5579182015b828111156123d55782518255916020019190600101906123ba565b506123e19291506123e5565b5090565b6123ff91905b808211156123e157600081556001016123eb565b905600a165627a7a723058207c95a59e4cc20f1ae8c63cda1f9d1be92652687ddc9c6b8c51d715e06e1a80040029";

    public static final String FUNC_NEWLAB = "newLab";

    public static final String FUNC_NOOFDATAREQUIREMENTS = "noOfDataRequirements";

    public static final String FUNC_NOOFDOCTORS = "noOfDoctors";

    public static final String FUNC_NOOFDATA = "noOfData";

    public static final String FUNC_GETPATHOLOGYEMAIL = "getPathologyEmail";

    public static final String FUNC_LABS = "labs";

    public static final String FUNC_DOCTORS = "doctors";

    public static final String FUNC_NOOFPATIENTS = "noOfPatients";

    public static final String FUNC_GETDOCTOREMAIL = "getDoctorEmail";

    public static final String FUNC_PATIENTS = "patients";

    public static final String FUNC_NEWCHEMIST = "newChemist";

    public static final String FUNC_GETLABEMAIL = "getLabEmail";

    public static final String FUNC_NOOFLABS = "noOfLabs";

    public static final String FUNC_NEWPATHOLOGY = "newPathology";

    public static final String FUNC_NOOFPATHOLOGIES = "noOfPathologies";

    public static final String FUNC_DATAREQUIREMENTS = "dataRequirements";

    public static final String FUNC_NEWDOCTOR = "newDoctor";

    public static final String FUNC_NOOFCHEMISTS = "noOfChemists";

    public static final String FUNC_CHEMISTS = "chemists";

    public static final String FUNC_DATA = "data";

    public static final String FUNC_GETCHEMISTEMAIL = "getChemistEmail";

    public static final String FUNC_PATHOLOGIES = "pathologies";

    public static final String FUNC_NEWPATIENT = "newPatient";

    public static final Event NEWDOCTOR_EVENT = new Event("NewDoctor", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event NEWCHEMIST_EVENT = new Event("NewChemist", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event NEWLAB_EVENT = new Event("NewLab", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event NEWPATHOLOGY_EVENT = new Event("NewPathology", 
            Arrays.<TypeReference<?>>asList());
    ;

    @Deprecated
    protected Factory_sol_Factory(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Factory_sol_Factory(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Factory_sol_Factory(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Factory_sol_Factory(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> newLab(String _name, String _email, String _license, String _details) {
        final Function function = new Function(
                FUNC_NEWLAB, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.Utf8String(_email), 
                new org.web3j.abi.datatypes.Utf8String(_license), 
                new org.web3j.abi.datatypes.Utf8String(_details)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> noOfDataRequirements() {
        final Function function = new Function(FUNC_NOOFDATAREQUIREMENTS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> noOfDoctors() {
        final Function function = new Function(FUNC_NOOFDOCTORS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> noOfData() {
        final Function function = new Function(FUNC_NOOFDATA, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> getPathologyEmail(BigInteger pathologyId) {
        final Function function = new Function(FUNC_GETPATHOLOGYEMAIL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(pathologyId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Tuple5<String, String, String, String, String>> labs(BigInteger param0) {
        final Function function = new Function(FUNC_LABS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple5<String, String, String, String, String>>(
                new Callable<Tuple5<String, String, String, String, String>>() {
                    @Override
                    public Tuple5<String, String, String, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<String, String, String, String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (String) results.get(4).getValue());
                    }
                });
    }

    public RemoteCall<Tuple6<String, String, String, String, String, String>> doctors(BigInteger param0) {
        final Function function = new Function(FUNC_DOCTORS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple6<String, String, String, String, String, String>>(
                new Callable<Tuple6<String, String, String, String, String, String>>() {
                    @Override
                    public Tuple6<String, String, String, String, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<String, String, String, String, String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (String) results.get(5).getValue());
                    }
                });
    }

    public RemoteCall<BigInteger> noOfPatients() {
        final Function function = new Function(FUNC_NOOFPATIENTS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> getDoctorEmail(BigInteger doctorId) {
        final Function function = new Function(FUNC_GETDOCTOREMAIL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(doctorId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Tuple8<String, String, String, String, String, String, String, Boolean>> patients(BigInteger param0) {
        final Function function = new Function(FUNC_PATIENTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}));
        return new RemoteCall<Tuple8<String, String, String, String, String, String, String, Boolean>>(
                new Callable<Tuple8<String, String, String, String, String, String, String, Boolean>>() {
                    @Override
                    public Tuple8<String, String, String, String, String, String, String, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple8<String, String, String, String, String, String, String, Boolean>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (String) results.get(5).getValue(), 
                                (String) results.get(6).getValue(), 
                                (Boolean) results.get(7).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> newChemist(String _name, String _email, String _license, String _physicalAdd) {
        final Function function = new Function(
                FUNC_NEWCHEMIST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.Utf8String(_email), 
                new org.web3j.abi.datatypes.Utf8String(_license), 
                new org.web3j.abi.datatypes.Utf8String(_physicalAdd)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> getLabEmail(BigInteger labId) {
        final Function function = new Function(FUNC_GETLABEMAIL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(labId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> noOfLabs() {
        final Function function = new Function(FUNC_NOOFLABS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> newPathology(String _name, String _email, String _license, String _physicalAdd) {
        final Function function = new Function(
                FUNC_NEWPATHOLOGY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.Utf8String(_email), 
                new org.web3j.abi.datatypes.Utf8String(_license), 
                new org.web3j.abi.datatypes.Utf8String(_physicalAdd)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> noOfPathologies() {
        final Function function = new Function(FUNC_NOOFPATHOLOGIES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Tuple5<String, BigInteger, BigInteger, BigInteger, BigInteger>> dataRequirements(BigInteger param0) {
        final Function function = new Function(FUNC_DATAREQUIREMENTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple5<String, BigInteger, BigInteger, BigInteger, BigInteger>>(
                new Callable<Tuple5<String, BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple5<String, BigInteger, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<String, BigInteger, BigInteger, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> newDoctor(String _name, String _email, String _license, String _physicalAdd, String _specialization) {
        final Function function = new Function(
                FUNC_NEWDOCTOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.Utf8String(_email), 
                new org.web3j.abi.datatypes.Utf8String(_license), 
                new org.web3j.abi.datatypes.Utf8String(_physicalAdd), 
                new org.web3j.abi.datatypes.Utf8String(_specialization)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> noOfChemists() {
        final Function function = new Function(FUNC_NOOFCHEMISTS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Tuple5<String, String, String, String, String>> chemists(BigInteger param0) {
        final Function function = new Function(FUNC_CHEMISTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple5<String, String, String, String, String>>(
                new Callable<Tuple5<String, String, String, String, String>>() {
                    @Override
                    public Tuple5<String, String, String, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<String, String, String, String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (String) results.get(4).getValue());
                    }
                });
    }

    public RemoteCall<Tuple3<BigInteger, BigInteger, String>> data(BigInteger param0) {
        final Function function = new Function(FUNC_DATA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple3<BigInteger, BigInteger, String>>(
                new Callable<Tuple3<BigInteger, BigInteger, String>>() {
                    @Override
                    public Tuple3<BigInteger, BigInteger, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<BigInteger, BigInteger, String>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (String) results.get(2).getValue());
                    }
                });
    }

    public RemoteCall<String> getChemistEmail(BigInteger chemistId) {
        final Function function = new Function(FUNC_GETCHEMISTEMAIL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(chemistId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Tuple5<String, String, String, String, String>> pathologies(BigInteger param0) {
        final Function function = new Function(FUNC_PATHOLOGIES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple5<String, String, String, String, String>>(
                new Callable<Tuple5<String, String, String, String, String>>() {
                    @Override
                    public Tuple5<String, String, String, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<String, String, String, String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (String) results.get(4).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> newPatient(String _name, String _email, String _driveURL, String _presURL, String _infoURL, String _reportsURL) {
        final Function function = new Function(
                FUNC_NEWPATIENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.Utf8String(_email), 
                new org.web3j.abi.datatypes.Utf8String(_driveURL), 
                new org.web3j.abi.datatypes.Utf8String(_presURL), 
                new org.web3j.abi.datatypes.Utf8String(_infoURL), 
                new org.web3j.abi.datatypes.Utf8String(_reportsURL)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<NewDoctorEventResponse> getNewDoctorEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NEWDOCTOR_EVENT, transactionReceipt);
        ArrayList<NewDoctorEventResponse> responses = new ArrayList<NewDoctorEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewDoctorEventResponse typedResponse = new NewDoctorEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NewDoctorEventResponse> newDoctorEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, NewDoctorEventResponse>() {
            @Override
            public NewDoctorEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NEWDOCTOR_EVENT, log);
                NewDoctorEventResponse typedResponse = new NewDoctorEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<NewDoctorEventResponse> newDoctorEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWDOCTOR_EVENT));
        return newDoctorEventFlowable(filter);
    }

    public List<NewChemistEventResponse> getNewChemistEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NEWCHEMIST_EVENT, transactionReceipt);
        ArrayList<NewChemistEventResponse> responses = new ArrayList<NewChemistEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewChemistEventResponse typedResponse = new NewChemistEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NewChemistEventResponse> newChemistEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, NewChemistEventResponse>() {
            @Override
            public NewChemistEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NEWCHEMIST_EVENT, log);
                NewChemistEventResponse typedResponse = new NewChemistEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<NewChemistEventResponse> newChemistEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWCHEMIST_EVENT));
        return newChemistEventFlowable(filter);
    }

    public List<NewLabEventResponse> getNewLabEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NEWLAB_EVENT, transactionReceipt);
        ArrayList<NewLabEventResponse> responses = new ArrayList<NewLabEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewLabEventResponse typedResponse = new NewLabEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NewLabEventResponse> newLabEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, NewLabEventResponse>() {
            @Override
            public NewLabEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NEWLAB_EVENT, log);
                NewLabEventResponse typedResponse = new NewLabEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<NewLabEventResponse> newLabEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWLAB_EVENT));
        return newLabEventFlowable(filter);
    }

    public List<NewPathologyEventResponse> getNewPathologyEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NEWPATHOLOGY_EVENT, transactionReceipt);
        ArrayList<NewPathologyEventResponse> responses = new ArrayList<NewPathologyEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewPathologyEventResponse typedResponse = new NewPathologyEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NewPathologyEventResponse> newPathologyEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, NewPathologyEventResponse>() {
            @Override
            public NewPathologyEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NEWPATHOLOGY_EVENT, log);
                NewPathologyEventResponse typedResponse = new NewPathologyEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<NewPathologyEventResponse> newPathologyEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWPATHOLOGY_EVENT));
        return newPathologyEventFlowable(filter);
    }

    @Deprecated
    public static Factory_sol_Factory load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Factory_sol_Factory(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Factory_sol_Factory load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Factory_sol_Factory(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Factory_sol_Factory load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Factory_sol_Factory(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Factory_sol_Factory load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Factory_sol_Factory(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Factory_sol_Factory> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Factory_sol_Factory.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Factory_sol_Factory> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Factory_sol_Factory.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Factory_sol_Factory> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Factory_sol_Factory.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Factory_sol_Factory> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Factory_sol_Factory.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class NewDoctorEventResponse {
        public Log log;
    }

    public static class NewChemistEventResponse {
        public Log log;
    }

    public static class NewLabEventResponse {
        public Log log;
    }

    public static class NewPathologyEventResponse {
        public Log log;
    }
}
