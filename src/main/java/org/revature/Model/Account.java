package org.revature.Model;

public class Account {
    private int accountId;
    private String username;
    private String password;
    private int roleId;

    public int getAccount_id() {
        return accountId;
    }
    public void setAccount_id(int account_id) {
        this.accountId = account_id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

}
