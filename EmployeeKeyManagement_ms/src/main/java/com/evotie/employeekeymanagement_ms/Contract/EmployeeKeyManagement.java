package com.evotie.employeekeymanagement_ms.Contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.6.0.
 */
@SuppressWarnings("rawtypes")
public class EmployeeKeyManagement extends Contract {
    public static final String BINARY = "0x6080604052348015600f57600080fd5b50336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506113de8061005f6000396000f3fe608060405234801561001057600080fd5b506004361061007c5760003560e01c80633e9231cd1161005b5780633e9231cd146100e95780638da5cb5b14610105578063bff9082114610123578063f859a78a146101535761007c565b80623f07fa1461008157806315f680e1146100b15780632c40499a146100cd575b600080fd5b61009b60048036038101906100969190610a91565b610183565b6040516100a89190610ad9565b60405180910390f35b6100cb60048036038101906100c69190610c3a565b6101bc565b005b6100e760048036038101906100e29190610cb2565b61039a565b005b61010360048036038101906100fe9190610c3a565b61056f565b005b61010d610760565b60405161011a9190610d0a565b60405180910390f35b61013d60048036038101906101389190610cb2565b610784565b60405161014a9190610da4565b60405180910390f35b61016d60048036038101906101689190610cb2565b6108a5565b60405161017a9190610da4565b60405180910390f35b60008073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff1614159050919050565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461024a576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161024190610e38565b60405180910390fd5b60018260405161025a9190610e94565b908152602001604051809103902060020160009054906101000a900460ff166102b8576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102af90610ef7565b60405180910390fd5b60026001836040516102ca9190610e94565b90815260200160405180910390206001016040516102e8919061100f565b9081526020016040518091039020600061030291906109c2565b806001836040516103139190610e94565b9081526020016040518091039020600101908161033091906111c7565b50816002826040516103429190610e94565b9081526020016040518091039020908161035c91906111c7565b507f5dd91d3b89a240d7ad5cf20d30f51488e9d37b19938cce02f1b03323d68bece7828260405161038e929190611299565b60405180910390a15050565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610428576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161041f90610e38565b60405180910390fd5b6001816040516104389190610e94565b908152602001604051809103902060020160009054906101000a900460ff16610496576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161048d90610ef7565b60405180910390fd5b60026001826040516104a89190610e94565b90815260200160405180910390206001016040516104c6919061100f565b908152602001604051809103902060006104e091906109c2565b6001816040516104f09190610e94565b90815260200160405180910390206000808201600061050f91906109c2565b60018201600061051f91906109c2565b6002820160006101000a81549060ff021916905550507f9d78c6c256cb4b35a89948523afd170f0304204fe7e4caef229f1bce9eea10aa816040516105649190610da4565b60405180910390a150565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146105fd576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016105f490610e38565b60405180910390fd5b60018260405161060d9190610e94565b908152602001604051809103902060020160009054906101000a900460ff161561066c576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016106639061131c565b60405180910390fd5b60405180606001604052808381526020018281526020016001151581525060018360405161069a9190610e94565b908152602001604051809103902060008201518160000190816106bd91906111c7565b5060208201518160010190816106d391906111c7565b5060408201518160020160006101000a81548160ff021916908315150217905550905050816002826040516107089190610e94565b9081526020016040518091039020908161072291906111c7565b507fa63fbc2e8a2548b39f327966348925ae1c3316f3b6c115daa0ce9483c2ceb3fc8282604051610754929190611299565b60405180910390a15050565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60606001826040516107969190610e94565b908152602001604051809103902060020160009054906101000a900460ff166107f4576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016107eb90610ef7565b60405180910390fd5b6001826040516108049190610e94565b9081526020016040518091039020600101805461082090610f46565b80601f016020809104026020016040519081016040528092919081815260200182805461084c90610f46565b80156108995780601f1061086e57610100808354040283529160200191610899565b820191906000526020600020905b81548152906001019060200180831161087c57829003601f168201915b50505050509050919050565b606060006002836040516108b99190610e94565b908152602001604051809103902080546108d290610f46565b905011610914576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161090b90611388565b60405180910390fd5b6002826040516109249190610e94565b9081526020016040518091039020805461093d90610f46565b80601f016020809104026020016040519081016040528092919081815260200182805461096990610f46565b80156109b65780601f1061098b576101008083540402835291602001916109b6565b820191906000526020600020905b81548152906001019060200180831161099957829003601f168201915b50505050509050919050565b5080546109ce90610f46565b6000825580601f106109e057506109ff565b601f0160209004906000526020600020908101906109fe9190610a02565b5b50565b5b80821115610a1b576000816000905550600101610a03565b5090565b6000604051905090565b600080fd5b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000610a5e82610a33565b9050919050565b610a6e81610a53565b8114610a7957600080fd5b50565b600081359050610a8b81610a65565b92915050565b600060208284031215610aa757610aa6610a29565b5b6000610ab584828501610a7c565b91505092915050565b60008115159050919050565b610ad381610abe565b82525050565b6000602082019050610aee6000830184610aca565b92915050565b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b610b4782610afe565b810181811067ffffffffffffffff82111715610b6657610b65610b0f565b5b80604052505050565b6000610b79610a1f565b9050610b858282610b3e565b919050565b600067ffffffffffffffff821115610ba557610ba4610b0f565b5b610bae82610afe565b9050602081019050919050565b82818337600083830152505050565b6000610bdd610bd884610b8a565b610b6f565b905082815260208101848484011115610bf957610bf8610af9565b5b610c04848285610bbb565b509392505050565b600082601f830112610c2157610c20610af4565b5b8135610c31848260208601610bca565b91505092915050565b60008060408385031215610c5157610c50610a29565b5b600083013567ffffffffffffffff811115610c6f57610c6e610a2e565b5b610c7b85828601610c0c565b925050602083013567ffffffffffffffff811115610c9c57610c9b610a2e565b5b610ca885828601610c0c565b9150509250929050565b600060208284031215610cc857610cc7610a29565b5b600082013567ffffffffffffffff811115610ce657610ce5610a2e565b5b610cf284828501610c0c565b91505092915050565b610d0481610a53565b82525050565b6000602082019050610d1f6000830184610cfb565b92915050565b600081519050919050565b600082825260208201905092915050565b60005b83811015610d5f578082015181840152602081019050610d44565b60008484015250505050565b6000610d7682610d25565b610d808185610d30565b9350610d90818560208601610d41565b610d9981610afe565b840191505092915050565b60006020820190508181036000830152610dbe8184610d6b565b905092915050565b7f4f6e6c7920746865206f776e65722063616e20706572666f726d20746869732060008201527f616374696f6e0000000000000000000000000000000000000000000000000000602082015250565b6000610e22602683610d30565b9150610e2d82610dc6565b604082019050919050565b60006020820190508181036000830152610e5181610e15565b9050919050565b600081905092915050565b6000610e6e82610d25565b610e788185610e58565b9350610e88818560208601610d41565b80840191505092915050565b6000610ea08284610e63565b915081905092915050565b7f456d706c6f79656520646f6573206e6f74206578697374000000000000000000600082015250565b6000610ee1601783610d30565b9150610eec82610eab565b602082019050919050565b60006020820190508181036000830152610f1081610ed4565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b60006002820490506001821680610f5e57607f821691505b602082108103610f7157610f70610f17565b5b50919050565b60008190508160005260206000209050919050565b60008154610f9981610f46565b610fa38186610e58565b94506001821660008114610fbe5760018114610fd357611006565b60ff1983168652811515820286019350611006565b610fdc85610f77565b60005b83811015610ffe57815481890152600182019150602081019050610fdf565b838801955050505b50505092915050565b600061101b8284610f8c565b915081905092915050565b60006020601f8301049050919050565b600082821b905092915050565b6000600883026110737fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82611036565b61107d8683611036565b95508019841693508086168417925050509392505050565b6000819050919050565b6000819050919050565b60006110c46110bf6110ba84611095565b61109f565b611095565b9050919050565b6000819050919050565b6110de836110a9565b6110f26110ea826110cb565b848454611043565b825550505050565b600090565b6111076110fa565b6111128184846110d5565b505050565b5b818110156111365761112b6000826110ff565b600181019050611118565b5050565b601f82111561117b5761114c81610f77565b61115584611026565b81016020851015611164578190505b61117861117085611026565b830182611117565b50505b505050565b600082821c905092915050565b600061119e60001984600802611180565b1980831691505092915050565b60006111b7838361118d565b9150826002028217905092915050565b6111d082610d25565b67ffffffffffffffff8111156111e9576111e8610b0f565b5b6111f38254610f46565b6111fe82828561113a565b600060209050601f831160018114611231576000841561121f578287015190505b61122985826111ab565b865550611291565b601f19841661123f86610f77565b60005b8281101561126757848901518255600182019150602085019450602081019050611242565b868310156112845784890151611280601f89168261118d565b8355505b6001600288020188555050505b505050505050565b600060408201905081810360008301526112b38185610d6b565b905081810360208301526112c78184610d6b565b90509392505050565b7f456d706c6f79656520616c726561647920657869737473000000000000000000600082015250565b6000611306601783610d30565b9150611311826112d0565b602082019050919050565b60006020820190508181036000830152611335816112f9565b9050919050565b7f5075626c6963206b657920646f6573206e6f7420657869737400000000000000600082015250565b6000611372601983610d30565b915061137d8261133c565b602082019050919050565b600060208201905081810360008301526113a181611365565b905091905056fea2646970667358221220b65ce8685e7193b6e4435265aa87feb29d64d5c4e343ff8130c9513808e71ea364736f6c634300081b0033\n";

    private static String librariesLinkedBinary;

    public static final String FUNC_ADDEMPLOYEE = "addEmployee";

    public static final String FUNC_GETEMPLOYEEIDBYPUBLICKEY = "getEmployeeIdByPublicKey";

    public static final String FUNC_GETPUBLICKEY = "getPublicKey";

    public static final String FUNC_ISVALIDADDRESS = "isValidAddress";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_REMOVEEMPLOYEE = "removeEmployee";

    public static final String FUNC_UPDATEEMPLOYEE = "updateEmployee";

    public static final Event EMPLOYEEADDED_EVENT = new Event("EmployeeAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event EMPLOYEEREMOVED_EVENT = new Event("EmployeeRemoved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event EMPLOYEEUPDATED_EVENT = new Event("EmployeeUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected EmployeeKeyManagement(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected EmployeeKeyManagement(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected EmployeeKeyManagement(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected EmployeeKeyManagement(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<EmployeeAddedEventResponse> getEmployeeAddedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(EMPLOYEEADDED_EVENT, transactionReceipt);
        ArrayList<EmployeeAddedEventResponse> responses = new ArrayList<EmployeeAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            EmployeeAddedEventResponse typedResponse = new EmployeeAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.employeeId = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.publicKey = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static EmployeeAddedEventResponse getEmployeeAddedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(EMPLOYEEADDED_EVENT, log);
        EmployeeAddedEventResponse typedResponse = new EmployeeAddedEventResponse();
        typedResponse.log = log;
        typedResponse.employeeId = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.publicKey = (String) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<EmployeeAddedEventResponse> employeeAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getEmployeeAddedEventFromLog(log));
    }

    public Flowable<EmployeeAddedEventResponse> employeeAddedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EMPLOYEEADDED_EVENT));
        return employeeAddedEventFlowable(filter);
    }

    public static List<EmployeeRemovedEventResponse> getEmployeeRemovedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(EMPLOYEEREMOVED_EVENT, transactionReceipt);
        ArrayList<EmployeeRemovedEventResponse> responses = new ArrayList<EmployeeRemovedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            EmployeeRemovedEventResponse typedResponse = new EmployeeRemovedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.employeeId = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static EmployeeRemovedEventResponse getEmployeeRemovedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(EMPLOYEEREMOVED_EVENT, log);
        EmployeeRemovedEventResponse typedResponse = new EmployeeRemovedEventResponse();
        typedResponse.log = log;
        typedResponse.employeeId = (String) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<EmployeeRemovedEventResponse> employeeRemovedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getEmployeeRemovedEventFromLog(log));
    }

    public Flowable<EmployeeRemovedEventResponse> employeeRemovedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EMPLOYEEREMOVED_EVENT));
        return employeeRemovedEventFlowable(filter);
    }

    public static List<EmployeeUpdatedEventResponse> getEmployeeUpdatedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(EMPLOYEEUPDATED_EVENT, transactionReceipt);
        ArrayList<EmployeeUpdatedEventResponse> responses = new ArrayList<EmployeeUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            EmployeeUpdatedEventResponse typedResponse = new EmployeeUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.employeeId = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.publicKey = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static EmployeeUpdatedEventResponse getEmployeeUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(EMPLOYEEUPDATED_EVENT, log);
        EmployeeUpdatedEventResponse typedResponse = new EmployeeUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.employeeId = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.publicKey = (String) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<EmployeeUpdatedEventResponse> employeeUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getEmployeeUpdatedEventFromLog(log));
    }

    public Flowable<EmployeeUpdatedEventResponse> employeeUpdatedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EMPLOYEEUPDATED_EVENT));
        return employeeUpdatedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> addEmployee(String _employeeId,
            String _publicKey) {
        final Function function = new Function(
                FUNC_ADDEMPLOYEE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_employeeId), 
                new org.web3j.abi.datatypes.Utf8String(_publicKey)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> getEmployeeIdByPublicKey(String _publicKey) {
        final Function function = new Function(FUNC_GETEMPLOYEEIDBYPUBLICKEY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_publicKey)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getPublicKey(String _employeeId) {
        final Function function = new Function(FUNC_GETPUBLICKEY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_employeeId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> isValidAddress(String _addr) {
        final Function function = new Function(FUNC_ISVALIDADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _addr)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> removeEmployee(String _employeeId) {
        final Function function = new Function(
                FUNC_REMOVEEMPLOYEE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_employeeId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateEmployee(String _employeeId,
            String _newPublicKey) {
        final Function function = new Function(
                FUNC_UPDATEEMPLOYEE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_employeeId), 
                new org.web3j.abi.datatypes.Utf8String(_newPublicKey)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static EmployeeKeyManagement load(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new EmployeeKeyManagement(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static EmployeeKeyManagement load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new EmployeeKeyManagement(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static EmployeeKeyManagement load(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        return new EmployeeKeyManagement(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static EmployeeKeyManagement load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new EmployeeKeyManagement(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<EmployeeKeyManagement> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EmployeeKeyManagement.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    public static RemoteCall<EmployeeKeyManagement> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EmployeeKeyManagement.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<EmployeeKeyManagement> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EmployeeKeyManagement.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<EmployeeKeyManagement> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EmployeeKeyManagement.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class EmployeeAddedEventResponse extends BaseEventResponse {
        public String employeeId;

        public String publicKey;
    }

    public static class EmployeeRemovedEventResponse extends BaseEventResponse {
        public String employeeId;
    }

    public static class EmployeeUpdatedEventResponse extends BaseEventResponse {
        public String employeeId;

        public String publicKey;
    }
}
