package com.app.ModernKids.model.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterBindingModel {
    @Size(min =3 ,max = 20)
    private String firstName;
    @Size(min =3 ,max = 20)
    private String lastName;
    @Min(6)
    private String password;
    @Min(6)
    private String confirmPassword;
    @NotBlank
    @Email
    private String email;
   @Size(min = 10, max = 13)
    private String phoneNumber;
   @NotBlank
   private String address;
}
