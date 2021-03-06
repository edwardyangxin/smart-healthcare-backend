package com.springboot.enums;


public enum ResultEnum {

    UNKONW_ERROR(-1,"未知错误"),
    NOT_LOGIN(1,"未登录"),
    PASSWORD_ERROR(2,"密码错误"),
    PASSWORDREPEAT_ERROR(2,"新密码与旧密码相同，请重新输入！"),
    DIFPASSWORD_ERROR(2,"两次输入的新密码不同，请重试！"),
    OLDPASSWORD_ERROR(2,"旧密码输入错误，请重试！"),
    NOT_ACTIVE_ERROR(3,"您的账户尚未激活邮箱！"),
    EXIST_ERROR(6,"用户已存在！"),
    NOT_EXIST_ERROR(6,"用户不存在！"),
    DEL_ERROR(5,"此条信息不存在！"),
    VERIFICATION_CODE_ERROE(7,"请输入正确的验证码"),
    MATCHING_USER_TYPE_ERRPR(8,"匹配用户类型出现错误！"),
    NullPointerException(9,"空指针异常！"),
    Repeat_login_Error(10,"您已登录，请勿重复登录！"),
    Repeat_tel_Error(10,"手机号不正确！"),
    Repeat_eamil_Error(10,"邮箱不正确！"),
    file_empty_error(11,"文件(内容)为空！"),
    file_format_error(11,"文件格式不正确！"),
    file_exist_error(11,"已存在重名文件！继续上传请重命名！"),
    file_storage_error(11,"文件保存失败！IOException！"),
    file_rename_error(11,"文件改名失败！"),
    file_picture_error(12,"上传的文件不是图片格式！"),
    pid_repeat_error(12,"此身份证号已经存在！"),

    SUCCESS(200,"success"),
    DEL_SUCCESS(4,"删除信息成功！"),
    REGISTER_SUCCESS(4,"注册成功！"),
    LOGIN_SUCCESS(4,"登录成功! "),
    PASSRESET_SUCCESS(4,"密码修改成功！"),
    PASSSFIND_SUCCESS(4,"密码找回成功！"),
    UPDATE_SUCCESS(4,"更新信息成功！"),
    SAVE_SUCCESS(4,"保存成功！"),
    pid_repeat_success(12,"此身份证号可以使用！"),
    file_upload_success(6,"文件上传成功！");


    private Integer code;

    private String msg;

    ResultEnum(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
