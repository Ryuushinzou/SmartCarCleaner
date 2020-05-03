package com.scc.app.model.direction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Summary {

    private double distance;
    private double duration;
}
