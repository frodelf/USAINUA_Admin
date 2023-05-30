package com.avadamedia.USAINUA_Admin.models;

import com.avadamedia.USAINUA_Admin.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private long id;
    @NotBlank(message = "Поле повинно бути заповнене")
    @Length(max = 40, message = "Довжина поля повинна бути до 40 символів")
    @Email(message = "Поле повинно бути e-mail адресою")
    private String email;
    @NotBlank(message = "Поле повинно бути заповнене")
    @Length(max = 10, message = "Довжина поля повинна бути до 10 символів")
    @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$", message = "Поле повинно містити десяткове число")
    private String money;
    @Length(max = 15, message = "Довжина поля повинна бути до 15 символів")
    @Pattern(regexp = "^[0-9]+$", message = "Поле повино містити тільки цифри")
    private String phone;
    private List<Role> roles ;
}
