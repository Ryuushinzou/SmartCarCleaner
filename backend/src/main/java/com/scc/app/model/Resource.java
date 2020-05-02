package com.scc.app.model;

import lombok.*;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.persistence.*;

@Entity
@Data
@Builder
@Table(name = "resource")
@ToString
@EqualsAndHashCode(exclude = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@ApiObject(name = "Resource", description = "Resource entity saved in the database")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ApiObjectField
    private String name;

    @ApiObjectField
    private String description;

    public Resource clone() {
        return Resource.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .build();
    }
}
