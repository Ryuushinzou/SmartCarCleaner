package com.scc.app.service;

import com.scc.app.model.direction.DirectionResponse;
import com.scc.app.model.direction.Features;
import com.scc.app.model.direction.Properties;
import com.scc.app.model.direction.Summary;
import com.scc.app.utils.Constants;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Service
public class DirectionService {

    private static final String API_KEY = "5b3ce3597851110001cf62488e85dc321b484ce19b9fe4a6fc8b5223";
    private static final String API_URL = "https://api.openrouteservice.org/v2/directions/driving-car?api_key=";

    public Long getDurationBetweenPoints(Double startLat, Double startLong, Double endLat, Double endLong) {

        DirectionResponse directionResponse = new RestTemplate().getForObject(computeUrl(startLat, startLong, endLat, endLong), DirectionResponse.class);
        if (directionResponse != null) {
            Features[] features = directionResponse.getFeatures();
            if (!ArrayUtils.isEmpty(features)) {
                Properties properties = features[0].getProperties();
                if (properties != null) {
                    Summary summary = properties.getSummary();
                    if (summary != null) {
                        return TimeUnit.SECONDS.toMillis((long) summary.getDuration()) + Constants.DELAY;
                    }
                }
            }
        }
        return null;
    }

    private String computeUrl(Double startLat, Double startLong, Double endLat, Double endLong) {

        return API_URL +
                API_KEY +
                "&start=" + startLat + "," + startLong +
                "&end=" + endLat + "," + endLong;

    }
}
