package org.revature;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.revature.DAO.AuthDAO;
import org.revature.Model.Account;
import org.revature.Service.AuthService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthTest {
    private AuthDAO authDAOMock;
    private AuthService authServiceMock;

    @BeforeEach
    void setup (){
        authDAOMock = Mockito.mock(AuthDAO.class);
        authServiceMock = new AuthService(authDAOMock);
    }

    @Test
    void registerAccount_ShouldReturnAccount_WhenUsernameNotExist(){
        //Arrange
        String username = "MANAGER";
        String password = "1234";
        int roleId = 2;

        //Create Account object to pass to the register method
        Account accountToRegister = new Account();
        accountToRegister.setUsername(username);
        accountToRegister.setPassword(password);
        accountToRegister.setRoleId(roleId);

        //Mock behavior
        when(authDAOMock.getAccountFromDB(username)).thenReturn(null); //Simulate no existing user
        doNothing().when(authDAOMock).createAccount(any(Account.class)); //Simulate user creation (void method)

        //Act
        Account createdAcc = authServiceMock.createAccountInDB(accountToRegister);

        //Assert
        assertNotNull(createdAcc);
        assertEquals(accountToRegister, createdAcc);
        verify(authDAOMock, times(1)).createAccount(any(Account.class));
    }

//    @Test
//    void registerAccount_ShouldReturnNull_WheUsernameAlreadyExists(){
//        //Arrange
//        String username = "exists";
//        String password = "1234";
//
//        Account existingAccount = new Account();
//        existingAccount.setUsername(username);
//        existingAccount.setPassword(password);
//        when(authDAOMock.getAccountFromDB(username)).thenReturn(existingAccount);
//
//        //Act
//        Account result = authServiceMock.createAccountInDB(existingAccount);
//
//        //Assert
//        assertNull(result, "Expected null when username already exists");
//        verify(authDAOMock).getAccountFromDB(username);
//        // Notice we do NOT expect createUser(...) to be called if user already exists
//        verify(authDAOMock, never()).createAccount(any(Account.class));
//
//    }

    @Test
    void loginUser_ShouldReturnTrue_WhenCredentialsMatch(){
        //Arrange
        String username = "bob";
        String password = "1234";

        Account existingAcc = new Account();
        existingAcc.setUsername(username);
        existingAcc.setPassword(password);

        when(authDAOMock.getAccountFromDB(username)).thenReturn(existingAcc);

        //Act
        boolean success = authServiceMock.accountExists(username);

        //Assert
        assertTrue(success, "Login should succeed when passwords match");
        verify(authDAOMock).getAccountFromDB(username);

    }

}
