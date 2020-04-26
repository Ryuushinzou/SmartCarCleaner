package com.scc.app.model;

import lombok.*;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.persistence.*;
import java.util.List;

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
    private List<Long> resourcesIds;

    @ApiObjectField
    @Enumerated(EnumType.STRING)
    private WashingStationStatus washingStationStatus;

    @ApiObjectField
    @Enumerated(EnumType.STRING)
    private WashingStationType washingStationType;

    public WashingStation clone() {
        return WashingStation.builder()
                .id(this.id)
                .longPos(this.longPos)
                .latPos(this.latPos)
                .slots(this.slots)
                .resourcesIds(this.resourcesIds)
                .washingStationType(this.washingStationType)
                .washingStationStatus(this.washingStationStatus)
                .build();
    }
}
