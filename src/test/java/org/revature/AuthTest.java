package org.revature;

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
    void registerUser_ShouldReturnUser_WhenUsernameNotExist(){
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
        when(authDAOMock.getAccountFromDB(username).thenReturn(null)); //Simulate no existing user
        doNothing().when(authDAOMock).createAccountInDB(any(Account.class));

        //Act
        Account createdAcc = authServiceMock.register(accountToRegister);

        //Assert



    }

}
