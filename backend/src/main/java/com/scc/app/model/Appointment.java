package com.scc.app.model;

import lombok.*;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Data
@Builder
@Table(name = "appoinment")
@ToString
@EqualsAndHashCode(exclude = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@ApiObject(name = "Appointment", description = "Appointment entity saved in the database")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ApiObjectField
    private Long userId;

    @ApiObjectField
    private Long vehicleId;

    @ApiObjectField
    private Long washingStationId;

    @ApiObjectField
    private Date dateStart;

    @ApiObjectField
    private Date dateEnd;

    @ApiObjectField
    private Double price;

    @ApiObjectField
    private List<Long> washingOptionsIds;

    @ApiObjectField
    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;

    public Appointment clone() {
        return Appointment.builder()
                .id(this.id)
                .vehicleId(this.vehicleId)
                .vehicleId(this.userId)
                .washingStationId(this.washingStationId)
                .dateStart(this.dateStart)
                .dateEnd(this.dateEnd)
                .price(this.price)
                .appointmentStatus(this.appointmentStatus)
                .washingOptionsIds(this.washingOptionsIds)
                .build();
    }
}
