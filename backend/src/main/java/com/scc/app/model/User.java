package com.scc.app.model;

import lombok.*;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.persistence.*;

@Entity
@Data
@Builder
@Table(name = "user")
@ToString(exclude = {"password"})
@EqualsAndHashCode(exclude = {"id", "password"})
@NoArgsConstructor
@AllArgsConstructor
@ApiObject
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ApiObjectField
    private String userName;
    @ApiObjectField
    private String email;
    @ApiObjectField
    private String phoneNumber;
    @ApiObjectField
    private String password;
//    private Long[] carIds;

    @ApiObjectField
    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User clone() {
        return User.builder()
                .id(this.id)
                .userName(this.userName)
                .email(this.email)
                .phoneNumber(this.phoneNumber)
                .password(this.password)
                .userType(this.userType)
                .build();
    }
}
