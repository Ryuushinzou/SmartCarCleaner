package com.scc.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@Table(name = "user")
@ToString
@EqualsAndHashCode(exclude = {"id", "password"})
@NoArgsConstructor
@AllArgsConstructor
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ApiObjectField
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> carIds = new ArrayList<>();

    @ApiObjectField
    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User clone() {
        return User.builder()
                .id(this.id)
                .userName(this.userName)
                .email(this.email)
                .phoneNumber(this.phoneNumber)
                .carIds(this.carIds)
                .password(this.password)
                .userType(this.userType)
                .build();
    }
}
