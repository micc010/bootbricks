package com.github.rogerli.security.form;

/**
 *
 *
 * @author roger.li
 * @since 2018/11/8
 */
public class LoginForm {
    private String username;
    private String password;
    private String validCode;

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

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }
}
