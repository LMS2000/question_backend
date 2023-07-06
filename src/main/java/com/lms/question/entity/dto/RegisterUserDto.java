package com.lms.question.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Slf4j
public class RegisterUserDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "用户名不能为空")
    @NotBlank(message = "用户名不能为空")
    @Length(min = 4,max = 20,message = "用户名必须大于4位小于20位")
    private String username;

    @NotNull(message = "密码不能为空")
    @NotBlank(message = "密码不能为空")
    @Length(min = 8,max = 20,message = "密码必须大于8位小于20位")
    private String password;

}
