pragma solidity ^0.8.0;

interface Contract {
    event UserAdded(string userId, address walletAddress);

    function addUser(string memory _userId, address _walletAddress) external;
    function getUserId(address _walletAddress) external view returns (string memory);
    function getWalletAddress(string memory _userId) external view returns (address);
    function isWalletRegistered(address _walletAddress) external view returns (bool);
    function owner() external view returns (address);
}