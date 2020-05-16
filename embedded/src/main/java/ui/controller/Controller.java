package ui.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.*;

public class Controller {

    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    private static final String WASHING_STATION_ID = "8";
    private static final String WASHING_STATION_URL = "http://localhost:8080//washingStations/" + WASHING_STATION_ID;
    private static final String APPOINTMENT_URL = "http://localhost:8080//appointments/";
    private static final String FINISHED = "finished";
    private static final String IN_PROGRESS = "inProgress";
    public static final String SLOTS = "slots";
    public static final String END_DATE = "endDate";
    private static Integer NO_SLOTS;
    private static final Date DATE_0 = new Date(0);
    private static final ConcurrentMap<Integer, Date> SLOT_NO_TO_OCCUPY_DATE = new ConcurrentHashMap<>();

    private LoginService loginService;

    public Controller() {

        loginService = new LoginService();
        try {
            initSlots();
        } catch (IOException e) {
            e.printStackTrace();
        }

        EXECUTOR_SERVICE.schedule(() -> {
            final Date currentDate = new Date();

            for (Map.Entry<Integer, Date> entry : SLOT_NO_TO_OCCUPY_DATE.entrySet()) {
                Integer k = entry.getKey();
                Date v = entry.getValue();
                if (currentDate.after(v)) {
                    SLOT_NO_TO_OCCUPY_DATE.put(k, DATE_0);
                }
            }

        }, 1, TimeUnit.MINUTES);
    }

    private void initSlots() throws IOException {

        String validJwt = loginService.getValidJwt();
        URL obj = new URL(WASHING_STATION_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", validJwt);

        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                if (inputLine.contains(SLOTS)) {
                    NO_SLOTS = Integer.valueOf(getValue(inputLine, SLOTS));
                }
            }
            in.close();
        }

        if (NO_SLOTS != null) {
            for (int i = 0; i < NO_SLOTS; i++) {
                SLOT_NO_TO_OCCUPY_DATE.put(i, DATE_0);
            }
        }
    }

    public Integer getAvailableSlotNo(String appointmentId) {

        Date endDate = null;
        try {
            endDate = getEndDateFromAppointment(appointmentId);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (endDate == null) {
            return null;
        }
        final Date currentDate = new Date();

        for (Map.Entry<Integer, Date> entry : SLOT_NO_TO_OCCUPY_DATE.entrySet()) {
            Integer k = entry.getKey();
            Date v = entry.getValue();
            if (currentDate.after(v)) {
                SLOT_NO_TO_OCCUPY_DATE.put(k, endDate);
                try {
                    setAppointmentStatus(appointmentId, IN_PROGRESS);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return k;
            }
        }

        return null;
    }

    private void setAppointmentStatus(String appointmentId, String status) throws IOException {

        String validJwt = loginService.getValidJwt();
        URL obj = new URL(APPOINTMENT_URL + appointmentId + status);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", validJwt);
        int responseCode = con.getResponseCode();
    }

    private Date getEndDateFromAppointment(String appointmentId) throws IOException {

        String validJwt = loginService.getValidJwt();
        URL obj = new URL(APPOINTMENT_URL + appointmentId);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", validJwt);

        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                if (inputLine.contains(END_DATE)) {
                    return new Date(getValue(inputLine, END_DATE));
                }
            }
            in.close();
        }
        return null;
    }

    private String getValue(String inputLine, String key) {

        return inputLine.replace("\"", "").replace(":", "").split(key)[1].split(",")[0];
    }

    public void finishAppointment(String appointmentId) throws IOException {

            setAppointmentStatus(appointmentId, FINISHED);
    }
}
