package com.springboot.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by liuyongg on 17/7/2017.
 */
@Data
public class Password {

    private String name;

    @NotEmpty(message = "旧密码不能为空！")
    @NotNull(message = "旧密码不能为空！")
    @Size(min = 6, max = 16, message="旧密码长度必须在6到16之间！")
    private String password;

    @NotEmpty(message = "新密码不能为空！")
    @NotNull(message = "新密码不能为空！")
    @Size(min = 6, max = 16, message="新密码长度必须在6到16之间！")
    private String newPassword;

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getRetypePassword() {
        return retypePassword;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setRetypePassword(String retypePassword) {
        this.retypePassword = retypePassword;
    }

    @NotEmpty(message = "再次输入的新密码不能为空！")
    @NotNull(message = "再次输入的新密码不能为空！")
    @Size(min = 6, max = 16, message="再次输入的新密码长度必须在6到16之间！")
    private String retypePassword;
}
