package com.scc.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.persistence.*;

@Entity
@Data
@Builder
@Table(name = "user")
@ToString
@EqualsAndHashCode(exclude = {"id", "password"})
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"password"})
@ApiObject(name = "User", description = "User entity saved in the database")
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
//    @JsonProperty(access = Access.WRITE_ONLY)//TODO
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
