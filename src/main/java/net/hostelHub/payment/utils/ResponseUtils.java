package net.hostelHub.payment.utils;

public class ResponseUtils {

    public static final String USER_EXISTS_CODE = "001";
    public static final String USER_EXISTS_MESSAGE = "User with provided email already exists!";
    public static final String SUCCESS = "002";
    public static final String SUCCESS_MESSAGE = "Successfully done";
    public static final String USER_NOT_FOUND_CODE = "003";
    public static final String USER_NOT_FOUND = "This user doesn't exist";
    public static final String USER_REGISTERED_SUCCESS = "User has been successfully registered";
    public static final String USER_CREATED_CODE="002";
    public static final String USER_CREATED_MESSAGE="Successfully Created";
    public static final String USER_DOES_NOT_EXISTS_CODE="003";
    public static final String USER_DOES_NOT_MESSAGE="User does not exists";
    public static final String SUCCESSFUL_LOGIN_RESPONSE_CODE="004";
    public static final String SUCCESSFUL_LOGIN_MESSAGE = "Successfully Login";
    public static final String SUCCESSFULLY_RESET_PASSWORD_CODE="005";
    public static final String  SUCCESSFULLY_RESET_PASSWORD_MESSAGE="password reset successfully ";
    public static final String SUCCESS_MESSAGE_CODE = "006";
    public static final String USER_NOT_FOUND_MESSAGE = "User Not Found";
    public static final String UNSUCCESSFUL_LOGIN_RESPONSE_CODE = "008";
    public static final String EMAIL_DOES_NOT_EXIST_MESSAGE = "User does not exist";
    public static final String UNSUCCESSFUL_LOGIN_STATUS = "False";
    public static final Integer STATUS_CODE_OK = 200;
    public static final Integer STATUS_CODE_CREATED = 201;
    public static final String PAYSTACK_INIT = "https://api.paystack.co/plan";
    public static final String PAYSTACK_INITIALIZE_PAY = "https://api.paystack.co/transaction/initialize";
    public static final String PAYSTACK_VERIFY = "https://api.paystack.co/transaction/verify/";
    public static final String GETATTENDEEURL = "http://localhost:8080/api/identity/attendee/single/attendee?email=";

}