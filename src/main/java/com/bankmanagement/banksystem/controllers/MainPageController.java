package com.bankmanagement.banksystem.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bankmanagement.banksystem.dto.BankDto;
import com.bankmanagement.banksystem.dto.ChangePasswordOp;
import com.bankmanagement.banksystem.dto.LoanDto;
import com.bankmanagement.banksystem.dto.RegisterUserRequest;
import com.bankmanagement.banksystem.dto.TransferOp;
import com.bankmanagement.banksystem.dto.UserDto;
import com.bankmanagement.banksystem.entities.Bank;
import com.bankmanagement.banksystem.entities.Loan;
import com.bankmanagement.banksystem.mappers.BankMapper;
import com.bankmanagement.banksystem.mappers.LoanMapper;
import com.bankmanagement.banksystem.mappers.UserMapper;
import com.bankmanagement.banksystem.repositories.BankRepository;
import com.bankmanagement.banksystem.repositories.LoanRepository;
import com.bankmanagement.banksystem.repositories.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/main-page")
public class MainPageController {
    private final BankRepository bankRepository;
    private final BankMapper bankMapper;
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    
    
    // Displays a list of available banks in the database
    // Does not require arguments
    @GetMapping("/list-banks")
    public ResponseEntity<List<BankDto>> displayBanksList() {
        List<Bank> listBanks = bankRepository.findAll();
        
        List<BankDto> listBankDtos = listBanks.stream().map(bank -> bankMapper.toDto(bank)).toList();
        return ResponseEntity.ok(listBankDtos);
    }


    // Displays a list of loans made by the user
    // Does not require arguments
    @GetMapping("/loans-history")
    public ResponseEntity<List<LoanDto>> loansHistoryOperation() {
        List<Loan> listLoans = loanRepository.findAll();

        List<LoanDto> listLoanDtos = listLoans.stream().map(loan -> loanMapper.toDto(loan)).toList();
        return ResponseEntity.ok(listLoanDtos);
    }


    // Displays the name of the bank
    // Maximum amount of transfer, deposit, withdraw possible in single operation
    // Maximum amount of loan possible in single operation given user's active loans
    // Minimum number of years liven in the country
    // Contact number
    @GetMapping("/bank/{bankId}")
    public ResponseEntity<BankDto> getBankDetails (@PathVariable Long bankId) {
        var bank = bankRepository.findById(bankId).orElse(null);
        
        if (bank == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(bankMapper.toDto(bank));
    }


    // Transfers cash from user to the client
    // Requires client id and amount of cash to transfer
    // Needs to meet the requirements of the bank specified by bankId
    // Checks the validity of client Id, balance of the user and cash amount ( > 0)
    @PostMapping("/bank/{bankId}/transfer")
    public ResponseEntity<String> transferOperation(
        HttpServletRequest request,
        @RequestBody TransferOp transferOp,
        @PathVariable Long bankId
    ) {
        var bank = bankRepository.findById(bankId).orElse(null);
        if (bank == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("MainPageController/Transfer Error: bank not found");
        }

        if (transferOp.getClientId() < 0L || transferOp.getTransferSum() <= 0L) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid client id or amount of cash to transfer");
        } else if (transferOp.getTransferSum() > bank.getMaxTransfer()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Max amount of cash to transfer exceeded");
        }
        
        Cookie[] cookies = request.getCookies();
        Long userId = -1L;
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userId".equals(cookie.getName())) {
                    userId = Long.parseLong(cookie.getValue());
                    break;
                }
            }
        }
        
        if (userId == -1L) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("MainPageController/Transfer Error: user id not found");
        }

        var user = userRepository.findById(userId).orElse(null);
        var client = userRepository.findById(transferOp.getClientId()).orElse(null);

        if (user == null || client == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("MainPageController/Transfer Error: user or client were not found");
        }

        if (user.getUserBalance() < transferOp.getTransferSum()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not enough cash in balance to transfer");
        }

        client.setUserBalance(client.getUserBalance() + transferOp.getTransferSum());
        user.setUserBalance(user.getUserBalance() - transferOp.getTransferSum());
        userRepository.save(client);
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(bank.getBankName() + ". Transfer success. Remaining balance " + user.getUserBalance());
    }


    // Simulates operation of transferring money to the digital format
    // Needs to meet the requirements of the bank specified by bankId
    // Requires the amount of cash for the operation
    // Checks the validity of cash amount ( > 0)
    @PostMapping("/bank/{bankId}/deposit")
    public ResponseEntity<String> depositOperation(
        @RequestParam Long depositSum,
        HttpServletRequest request,
        @PathVariable Long bankId
    ) {
        var bank = bankRepository.findById(bankId).orElse(null);
        if (bank == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("MainPageController/Deposit Error: bank not found");
        }

        if (depositSum <= 0L) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid amount of cash to deposit");
        } else if (depositSum > bank.getMaxTransfer()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Max amount of cash to deposit exceeded");
        }

        Cookie[] cookies = request.getCookies();
        Long userId = -1L;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userId")) {
                    userId = Long.parseLong(cookie.getValue());
                    break;
                }
            }
        }

        if (userId == -1L) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("MainPageController/Deposit Error: user id not found");
        }

        var user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("MainPageController/Deposit Error: user was not found");
        }

        user.setUserBalance(user.getUserBalance() + depositSum);
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(bank.getBankName() + ". Deposit successful. Remaining balance: " + user.getUserBalance());
    }


    // Simulates operation of withdrawing cash from digital format
    // Needs to meet the requirements of the bank specified by bankId
    // Requires the amount of cash for the operation
    // Checks the validity of cash amount ( > 0)
    @PostMapping("/bank/{bankId}/withdraw")
    public ResponseEntity<String> withdrawOperation(
        HttpServletRequest request,
        @RequestParam Long withdrawSum,
        @PathVariable Long bankId
    ) {
        var bank = bankRepository.findById(bankId).orElse(null);
        if (bank == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("MainPageController/Withdraw Error: bank not found");
        }

        if (withdrawSum <= 0L) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid amount of cash to withdraw");
        } else if (withdrawSum > bank.getMaxTransfer()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Max amount of cash to withdraw exceeded");
        }

        Long userId = -1L;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userId")) {
                    userId = Long.parseLong(cookie.getValue());
                    break;
                }
            }
        }

        if (userId == -1L) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("MainPageController/Withdraw Error: user id not found");
        }

        var user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("MainPageController/Withdraw Error: user was not found");
        }

        if (withdrawSum > user.getUserBalance()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not enough cash in balance to withdraw");
        }

        user.setUserBalance(user.getUserBalance() - withdrawSum);
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(bank.getBankName() + ". Withdraw success. Remaining balance: " + user.getUserBalance());
    }


    // Simulates operation of loan from the bank
    // Needs to meet the requirements of the bank specified by bankId
    // Requires the amount of cash for the operation
    // Checks the validity of cash amount ( > 0)
    @PostMapping("/bank/{bankId}/loans")
    public ResponseEntity<String> loansOperation(
        @RequestParam Long loansSum,
        HttpServletRequest request,
        @PathVariable Long bankId
    ) {
        var bank = bankRepository.findById(bankId).orElse(null);
        if (bank == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("MainPageController/Loans Error: bank not found");
        }

        if (loansSum <= 0L) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid amount of cash to loan");
        } else if (loansSum > bank.getMaxTotalLoans()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Max amount of cash to loan exceeded");
        }

        Long userId = -1L;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userId")) {
                    userId = Long.parseLong(cookie.getValue());
                    break;
                }
            }
        }

        if (userId == -1L) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("MainPageController/Loans Error: user id not found");
        }

        var user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("MainPageController/Loans Error: user was not found");
        }

        if (user.getTotalLoans() + loansSum > bank.getMaxTotalLoans()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Max amount of cash to loan exceeded: User with current amount of loans can not make a loan in this amount");
        } else if (user.getEmployed() == 0 || user.getYearsCountry() < bank.getRequireYearsCountry()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User's profile does not meet requirements to make a loan.");
        }

        user.setUserBalance(user.getUserBalance() + loansSum);
        user.setTotalLoans(loansSum);
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(bank.getBankName() + ". Loan success. Remaining balance: " + user.getUserBalance());
    }


    // Shows information about the user
    // Does not require arguments
    // Password is hidden
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> sendProfile(@PathVariable Long userId) {
        var user = userRepository.findById(userId).orElse(null);
        
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userMapper.toDto(user));
    }


    // Changes user's password
    // Requires user login and old password prior operation
    // The new password must not be the same as the old one
    // Password must consist of at least 1 lowercase, 1 uppercase character, 1 special symbol, 1 digit and be 8 characters long
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(
        @RequestBody ChangePasswordOp changePasswordOp
    ) {

        var user = userRepository.findByUserLogin(changePasswordOp.getUserLogin()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("MainPageController/change-password: user with this Login was not found");
        }

        if (user.getUserPassword().equals(changePasswordOp.getNewUserPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("MainPageController/change-password: new password can not be the same as the old password");
        }

        if (!changePasswordOp.getNewUserPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8}$")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password must include at least 1 digit, 1 lowercase, 1 uppercase and 1 special character and be 8 characters long");
        }

        user.setUserPassword(changePasswordOp.getNewUserPassword());
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body("Password change success");
    }


    // Searches bank from the database according to the bank name
    // Provides information of the bank
    @GetMapping("/search")
    public ResponseEntity<List<BankDto>> searchOperation(@RequestParam String bankName) {
        List<Bank> matchedBanks = bankRepository.findByBankName(bankName);

        if (matchedBanks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<BankDto> matcheBankDtos = matchedBanks.stream().map(bank -> bankMapper.toDto(bank)).toList();

        return ResponseEntity.ok(matcheBankDtos);
    }


    // Allows to delete other users from the database
    // Checks whether current user is admin
    // Requires and checks validity of other user's id
    @PostMapping("/admin-delete")
    public ResponseEntity<UserDto> adminDelete(
        @RequestParam String clientLogin,
        HttpServletRequest request
    ) {
        Cookie[] cookies = request.getCookies();
        Long userId = -1L;
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userId".equals(cookie.getName())) {
                    userId = Long.parseLong(cookie.getValue());
                    break;
                }
            }
        }
        
        if (userId == -1L) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        var user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (user.getIsAdmin() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        var client = userRepository.findByUserLogin(clientLogin).orElse(null);
        if (client == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        userRepository.delete(client);

        return ResponseEntity.status(HttpStatus.OK).build();
    }


    // Allows to change information of other users in the database
    // Checks whether current user is admin
    // Requires and checks validity of other user's id
    @PostMapping("/admin-change")
    public ResponseEntity<UserDto> adminChange(
        @RequestParam String clientLogin,
        @RequestBody RegisterUserRequest requestRegister,
        HttpServletRequest request
    ) {
        Cookie[] cookies = request.getCookies();
        Long userId = -1L;
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userId".equals(cookie.getName())) {
                    userId = Long.parseLong(cookie.getValue());
                    break;
                }
            }
        }
        
        if (userId == -1L) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        var user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (user.getIsAdmin() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        var clientToChange = userRepository.findByUserLogin(clientLogin).orElse(null);
        if (clientToChange == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        userRepository.delete(clientToChange);
        
        var clientChanged = userMapper.toEntity(requestRegister);
        userRepository.save(clientChanged);

        return ResponseEntity.status(HttpStatus.OK).build();
    }


    // Allows to create a new user in the database
    // Checks whether current user is admin
    // Checks format of password and contact number
    @PostMapping("/admin-create")
    public ResponseEntity<UserDto> adminCreate(
        @RequestBody RegisterUserRequest requestRegister,
        HttpServletRequest request
    ) {

        Cookie[] cookies = request.getCookies();
        Long userId = -1L;
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userId".equals(cookie.getName())) {
                    userId = Long.parseLong(cookie.getValue());
                    break;
                }
            }
        }
        
        if (userId == -1L) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        var user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (user.getIsAdmin() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (userRepository.findByUserLogin(requestRegister.getUserLogin()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        var userSave = userMapper.toEntity(requestRegister);
        userRepository.save(userSave);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
