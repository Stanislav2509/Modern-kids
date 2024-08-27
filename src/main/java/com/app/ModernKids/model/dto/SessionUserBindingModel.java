package com.app.ModernKids.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionUserBindingModel {
    @Size(min =3 ,max = 20)
    private String firstName;
    @Size(min =3 ,max = 20)
    private String lastName;
    @NotBlank
    @Email
    private String email;
    @Size(min = 10, max = 13)
    private String phoneNumber;
    @NotBlank
    private String address;
}
