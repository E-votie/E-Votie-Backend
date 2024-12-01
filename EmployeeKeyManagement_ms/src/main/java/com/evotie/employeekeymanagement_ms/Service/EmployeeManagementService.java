package com.evotie.employeekeymanagement_ms.Service;

import com.evotie.employeekeymanagement_ms.Contract.EmployeeKeyManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

@Service
public class EmployeeManagementService {

    private final EmployeeKeyManagement contract;

    @Autowired
    public EmployeeManagementService(Web3j web3j,
                           @Value("${eth.wallet.privateKey}") String privateKey,
                           @Value("${eth.contract.address}") String contractAddress) {
        Credentials credentials = Credentials.create(privateKey);
        ContractGasProvider gasProvider = new DefaultGasProvider();
        this.contract = EmployeeKeyManagement.load(contractAddress, web3j, credentials, gasProvider);
    }

    // 1. Add Employee
    public TransactionReceipt addEmployee(String employeeId, String publicKey) throws Exception {
        return contract.addEmployee(employeeId, publicKey).send();
    }

    // 2. Update Employee
    public TransactionReceipt updateEmployee(String employeeId, String publicKey) throws Exception {
        return contract.updateEmployee(employeeId, publicKey).send();
    }

    // 3. Remove Employee
    public TransactionReceipt removeEmployee(String employeeId) throws Exception {
        return contract.removeEmployee(employeeId).send();
    }

    // 4. Get Employee Public Key
    public String getEmployeePublicKey(String employeeId) throws Exception {
        return contract.getPublicKey(employeeId).send();
    }

    // 5. Check if Address is Registered
    public Boolean isAddressRegistered(String address) throws Exception {
        return contract.isValidAddress(address).send();
    }
}
