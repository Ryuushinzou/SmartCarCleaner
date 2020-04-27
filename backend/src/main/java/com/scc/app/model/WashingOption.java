package com.scc.app.model;

import lombok.*;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.persistence.*;
import java.util.Map;

@Entity
@Data
@Builder
@Table(name = "washingOption")
@ToString
@EqualsAndHashCode(exclude = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@ApiObject(name = "Washing Options", description = "Washing option entity saved in the database")
public class WashingOption {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ApiObjectField
    private String name;

    @ApiObjectField
    private String description;

    @ApiObjectField
    private Map<Long, Long> requiredResourcesIdToQuantity;

    @ApiObjectField
    private Double price;

    @ApiObjectField
    private Long duration;

    public WashingOption clone() {
        return WashingOption.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .requiredResourcesIdToQuantity(this.requiredResourcesIdToQuantity)
                .price(this.price)
                .duration(this.duration)
                .build();
    }
}
