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
    void registerAccount_ShouldReturnAccount_WhenUsernameNotExist(){
        //If the username doesn’t exist, the account should be created and returned.

        String username = "alice";
        String password = "secret";
        int roleId = 1;
        Account accToReturn = new Account();
        accToReturn.setAccountId(1);
        accToReturn.setUsername(username);
        accToReturn.setPassword(password);
        accToReturn.setRoleId(roleId);

        //when(...).thenReturn(...): Tells Mockito what to return when a specific method on the mock is called.
        // We mock getUserByUsername to return null => user doesn't exist yet
        when(authDAOMock.getAccountByUsername(username)).thenReturn(null);

        // We mock createUser to return our user with an assigned ID
        when(authDAOMock.createAccount(any(Account.class))).thenReturn(accToReturn);

        // Act
        Account createdUser = authServiceMock.registerUser(username, password, roleId);

        // Assert
        assertNotNull(createdUser);
        assertEquals(1, createdUser.getAccountId());
        assertEquals(username, createdUser.getUsername());
        assertEquals(password, createdUser.getPassword());

        //verify(...): Ensures a mock method was called or not called.
        verify(authDAOMock).getAccountByUsername(username);
        verify(authDAOMock).createAccount(any(Account.class));
    }

    @Test
    void registerAccount_ShouldReturnNull_WheUsernameAlreadyExists(){
        //If the username already exists, the registration should fail (return null).

        //Arrange
        String username = "exists";
        String password = "1234";
        int roleId = 1;

        Account existingAccount = new Account();
        existingAccount.setUsername(username);
        existingAccount.setPassword(password);
        when(authDAOMock.getAccountByUsername(username)).thenReturn(existingAccount);

        //Act
        Account result = authServiceMock.registerUser(username, password, roleId);

        //Assert
        assertNull(result, "Expected null when username already exists");
        verify(authDAOMock).getAccountByUsername(username);
        // Notice we do NOT expect createUser(...) to be called if user already exists
        verify(authDAOMock, never()).createAccount(any(Account.class));

    }

    @Test
    void loginUser_ShouldReturnTrue_WhenCredentialsMatch(){
        //If the username exists and the password matches, login should be successful.

        //Arrange
        String username = "bob";
        String password = "1234";

        Account existingAcc = new Account();
        existingAcc.setUsername(username);
        existingAcc.setPassword(password);

        when(authDAOMock.getAccountByUsername(username)).thenReturn(existingAcc);

        //Act
        boolean success = authServiceMock.loginAccount(username, password);

        //Assert
        assertTrue(success, "Login should succeed when passwords match");
        verify(authDAOMock).getAccountByUsername(username);

    }

}
