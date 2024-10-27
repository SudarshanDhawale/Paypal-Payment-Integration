package com.cpt.payments.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String endUserID;
    private String firstName;
    private String lastName;
    private String email;
    private String mobilePhone;
}
