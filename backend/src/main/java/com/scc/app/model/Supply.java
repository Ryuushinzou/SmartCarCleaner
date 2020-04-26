package com.scc.app.model;

import lombok.*;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
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
    private String name;

    @ApiObjectField
    private String description;

    @ApiObjectField
    private Long washingStationId;

    @ApiObjectField
    private Date date;

    @ApiObjectField
    private List<Long> resourceIds;

    @ApiObjectField
    private Double price;

    public Supply clone() {
        return Supply.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .washingStationId(this.washingStationId)
                .date(this.date)
                .resourceIds(this.resourceIds)
                .price(this.price)
                .build();
    }
}
