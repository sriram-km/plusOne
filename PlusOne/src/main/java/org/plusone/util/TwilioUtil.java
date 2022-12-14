package org.plusone.util;
/*
 * @author Sri Ram (zt-753)
 */


import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioUtil {

    private final String ACCOUNT_SID = System.getProperty("twilio.sid");
    private final String AUTH_TOKEN = System.getProperty("twilio.authToken");
    private final String FROM_PHONE_NUMBER = System.getProperty("twilio.fromPhoneNumber");
    private final String MESSAGE_SENT = "sent";
    private final String MESSAGE_UNDELIVERED = "undelivered";
    private final String MESSAGE_FAILED = "failed";
    private final String MESSAGE_CANCELED = "canceled";

    public boolean sendMessage(String phoneNumber, String messageToSent) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        boolean sent = true;
        try {
            Message message = Message.creator(new PhoneNumber(phoneNumber),
                    new PhoneNumber(FROM_PHONE_NUMBER), messageToSent).create();
            String status = String.valueOf(message.getStatus());
            if (status.equals(MESSAGE_UNDELIVERED) || status.equals(MESSAGE_FAILED) || status.equals(MESSAGE_CANCELED) ) {
                sent = false;
            }
        } catch(ApiException e ) {
            sent = false;
        }

        return sent;
    }

    public static void main(String args[]) {
        TwilioUtil twilioHelper = new TwilioUtil();
        System.setProperty("twilio.sid","ACd7ba45f63c17177f02d56754096fd2cb");
        System.setProperty("twilio.authToken","549bb719b224591a6278bc287878e7b4");
        System.setProperty("twilio.fromPhoneNumber","+19804007694");

        twilioHelper.sendMessage("+918550006006", "Hi, There. Your friend Sri Ram broke the streak of going to gym today. Help him.");
    }
}
