package com.evotie.registration_ms.voter_registration.contract;

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
public class Signing extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_ADDUSER = "addUser";

    public static final String FUNC_GETUSERID = "getUserId";

    public static final String FUNC_GETWALLETADDRESS = "getWalletAddress";

    public static final String FUNC_ISWALLETREGISTERED = "isWalletRegistered";

    public static final String FUNC_OWNER = "owner";

    public static final Event USERADDED_EVENT = new Event("UserAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
    ;

    @Deprecated
    protected Signing(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Signing(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Signing(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Signing(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<UserAddedEventResponse> getUserAddedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(USERADDED_EVENT, transactionReceipt);
        ArrayList<UserAddedEventResponse> responses = new ArrayList<UserAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UserAddedEventResponse typedResponse = new UserAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.userId = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.walletAddress = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static UserAddedEventResponse getUserAddedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(USERADDED_EVENT, log);
        UserAddedEventResponse typedResponse = new UserAddedEventResponse();
        typedResponse.log = log;
        typedResponse.userId = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.walletAddress = (String) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<UserAddedEventResponse> userAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getUserAddedEventFromLog(log));
    }

    public Flowable<UserAddedEventResponse> userAddedEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(USERADDED_EVENT));
        return userAddedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> addUser(String _userId, String _walletAddress) {
        final Function function = new Function(
                FUNC_ADDUSER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_userId), 
                new org.web3j.abi.datatypes.Address(160, _walletAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> getUserId(String _walletAddress) {
        final Function function = new Function(FUNC_GETUSERID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _walletAddress)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getWalletAddress(String _userId) {
        final Function function = new Function(FUNC_GETWALLETADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_userId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> isWalletRegistered(String _walletAddress) {
        final Function function = new Function(FUNC_ISWALLETREGISTERED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _walletAddress)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static Signing load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new Signing(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Signing load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Signing(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Signing load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new Signing(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Signing load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Signing(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class UserAddedEventResponse extends BaseEventResponse {
        public String userId;

        public String walletAddress;
    }
}
