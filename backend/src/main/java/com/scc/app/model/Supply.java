package com.scc.app.model;

import lombok.*;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@Entity
@Data
@Builder
@Table(name = "supply")
@ToString
@EqualsAndHashCode(exclude = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@ApiObject(name = "Supply", description = "Supply entity saved in the database")
public class Supply {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ApiObjectField
    private Long washingStationId;

    @ApiObjectField
    private Date date;

    @ApiObjectField
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<Long, Long> resourceIdToQuantity;

    @ApiObjectField
    private Double price;

    @ApiObjectField
    @Enumerated(EnumType.STRING)
    private SupplyStatus supplyStatus;

    public Supply clone() {

        return Supply.builder()
                .id(this.id)
                .washingStationId(this.washingStationId)
                .date(this.date)
                .resourceIdToQuantity(this.resourceIdToQuantity)
                .supplyStatus(this.supplyStatus)
                .price(this.price)
                .build();
    }
}
