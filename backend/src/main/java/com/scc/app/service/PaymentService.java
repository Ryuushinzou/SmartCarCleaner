package com.scc.app.service;

import com.scc.app.exception.PaymentException;
import com.scc.app.model.Appointment;
import com.scc.app.model.AppointmentStatus;
import com.scc.app.model.CardDetails;
import com.scc.app.utils.Constants;
import com.scc.app.utils.Utils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class PaymentService {

    @Autowired
    private AppointmentService appointmentService;

    public String makePayment(Long appointmentId, CardDetails cardDetails) throws PaymentException {

        verifyCardDetails(cardDetails);
        int random = Utils.getRandom(100);
        if (random == Constants.INSUFFICIENT_FUNDS_EXCEPTION_ID) {
            throw new PaymentException(Constants.INSUFFICIENT_FUNDS_EXCEPTION_ID);
        }

        if (random == Constants.PAYMENT_FAILED_EXCEPTION_ID) {
            throw new PaymentException(Constants.PAYMENT_FAILED_EXCEPTION_ID);
        }

        appointmentService.update(Appointment.builder().id(appointmentId).appointmentStatus(AppointmentStatus.PAID).build());

        return Constants.PAYMENT_SUCCESSFUL;
    }

    private void verifyCardDetails(CardDetails cardDetails) throws PaymentException {

        if (cardDetails == null) {
            throw new PaymentException(Constants.CARD_DETAILS_INVALID_EXCEPTION_ID);
        }

        if (StringUtils.isEmpty(cardDetails.getCardHolderName())) {
            throw new PaymentException(Constants.CARD_HOLDER_NAME_INVALID_EXCEPTION_ID);
        }

        if (StringUtils.isEmpty(cardDetails.getCardNo()) || cardDetails.getCardNo().length() != 16) {
            throw new PaymentException(Constants.CARD_NO_INVALID_EXCEPTION_ID);
        }

        if (StringUtils.isEmpty(cardDetails.getCvv()) | cardDetails.getCvv().length() != 3) {
            throw new PaymentException(Constants.CVV_INVALID_EXCEPTION_ID);
        }

        if (cardDetails.getExpirationDate() == null || new Date().after(cardDetails.getExpirationDate())) {
            throw new PaymentException(Constants.EXPIRATION_DATE_INVALID_EXCEPTION_ID);
        }
    }
}
