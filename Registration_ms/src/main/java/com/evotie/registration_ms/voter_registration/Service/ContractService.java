package com.evotie.registration_ms.voter_registration.Service;

import com.evotie.registration_ms.voter_registration.contract.Signing;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;

import java.util.concurrent.CompletableFuture;

@Service
public class ContractService {

    private final Web3j web3j;
    private final Credentials credentials;
    private final Signing contract;

    public ContractService(Web3j web3j, Credentials credentials) {
        this.web3j = web3j;
        this.credentials = credentials;
        this.contract = Signing.load(
                "0x10a31dd7da68cdae58bbf067b1e96c828e661b03",
                web3j,
                credentials,
                new DefaultGasProvider()
        );
    }

    public CompletableFuture<TransactionReceipt> addUser(String userId, String walletAddress) {
        return contract.addUser(userId, walletAddress).sendAsync();
    }

    public String getUserId(String walletAddress) throws Exception {
        return contract.getUserId(walletAddress).send();
    }

    public String getWalletAddress(String userId) throws Exception {
        return contract.getWalletAddress(userId).send();
    }

    public Boolean isWalletRegistered(String walletAddress) throws Exception {
        return contract.isWalletRegistered(walletAddress).send();
    }

    public String getOwner() throws Exception {
        return contract.owner().send();
    }
}