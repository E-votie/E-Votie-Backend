package com.evotie.employeekeymanagement_ms.Controller;


import com.evotie.employeekeymanagement_ms.DTO.EmployeeRequest;
import com.evotie.employeekeymanagement_ms.Service.EmployeeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employeeKyeManagement")
public class EmployeeKeyManagementController {

    @Autowired
    private EmployeeManagementService contractService;

    // 1. Add Employee
    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeRequest request) {
        try {
            var receipt = contractService.addEmployee(request.getEmployeeId(), request.getPublicKey());
            return ResponseEntity.ok().body("Employee added successfully, transaction hash: " + receipt.getTransactionHash());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // 2. Update Employee
    @PostMapping ("/update/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable String id, @RequestBody EmployeeRequest request) {
        try {
            var receipt = contractService.updateEmployee(id, request.getPublicKey());
            return ResponseEntity.ok().body("Employee updated successfully, transaction hash: " + receipt.getTransactionHash());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // 3. Remove Employee
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> removeEmployee(@PathVariable String id) {
        try {
            var receipt = contractService.removeEmployee(id);
            return ResponseEntity.ok().body("Employee removed successfully, transaction hash: " + receipt.getTransactionHash());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // 4. Get Employee Public Key
    @GetMapping("/{id}/publicKey")
    public ResponseEntity<?> getEmployeePublicKey(@PathVariable String id) {
        try {
            String publicKey = contractService.getEmployeePublicKey(id);
            return ResponseEntity.ok().body("Public Key: " + publicKey);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // 5. Check if Address is Registered
    @GetMapping("/isRegistered/{address}")
    public ResponseEntity<?> isAddressRegistered(@PathVariable String address) {
        try {
            boolean isRegistered = contractService.isAddressRegistered(address);
            return ResponseEntity.ok().body("Is Registered: " + isRegistered);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
