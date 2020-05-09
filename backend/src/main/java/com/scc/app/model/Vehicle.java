package com.scc.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.persistence.*;

@Entity
@Data
@Builder
@Table(name = "vehicle")
@ToString
@EqualsAndHashCode(exclude = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@ApiObject(name = "Vehicle", description = "Vehicle entity saved in the database")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ApiObjectField
    private String make;

    @ApiObjectField
    private String model;

    @ApiObjectField
	@Enumerated(EnumType.STRING)
    private VehicleType type;

    @ApiObjectField
    private String year;

    public Vehicle clone() {
        return Vehicle.builder()
                .id(this.id)
                .make(this.make)
                .model(this.model)
                .type(this.type)
                .year(this.year)
                .build();
    }
}
