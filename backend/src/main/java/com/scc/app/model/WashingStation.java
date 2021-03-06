package com.scc.app.model;

import lombok.*;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.persistence.*;
import java.util.Map;

@Entity
@Data
@Builder
@Table(name = "washingStation")
@ToString
@EqualsAndHashCode(exclude = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@ApiObject(name = "WashingStation", description = "WashingStation entity saved in the database")
public class WashingStation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ApiObjectField
    private Double longPos;

    @ApiObjectField
    private Double latPos;

    @ApiObjectField
    private Integer slots;

    @ApiObjectField
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<Long, Long> resourcesIdToQuantity;

    public WashingStation clone() {
        return WashingStation.builder()
                .id(this.id)
                .longPos(this.longPos)
                .latPos(this.latPos)
                .slots(this.slots)
                .resourcesIdToQuantity(this.resourcesIdToQuantity)
                .build();
    }
}
