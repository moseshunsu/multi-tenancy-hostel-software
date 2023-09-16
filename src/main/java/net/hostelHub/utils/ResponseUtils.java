package net.hostelHub.utils;

import java.util.Random;

public class ResponseUtils {

    public static final String USER_EXISTS_CODE = "001";
    public static final String SUCCESS_CODE = "002";
    public static final String PROPERTY_REGISTER_SUCCESS_CODE = "003";
    public static final String PROPERTY_EXISTS_CODE = "004";
    public static final String HOSTEL_NOT_FOUND_CODE = "005";
    public static final String ROOM_TYPE_EXISTS_CODE = "006";
    public static final String ROOM_EXISTS_CODE = "007";
    public static final String BOOKING_SUCCESS_CODE = "008";
    public static final String ROOM_NOT_FOUND_CODE = "009";
    public static final String INVALID_TOKEN_CODE = "010";
    public static final String USER_EXISTS_MESSAGE = "User with provided email or username already exists!";
    public  static final int LENGTH_OF_TENANT_CODE = 6;
    public  static final int LENGTH_OF_OCCUPANT_CODE = 10;
    public  static final int LENGTH_OF_UNIQUE_BOOKING_NUMBER = 7;
    public static final String USER_REGISTRATION_MESSAGE = "Success! Please, check your email to complete your " +
            "registration";
    public static final String USER_NOT_FOUND_MESSAGE = "This user doesn't exists";
    public static final String DETAILS_ENTRY_SUCCESS = "Details successfully registered";
    public static final String REGISTER_PROPERTY_SUCCESS_MESSAGE = "Property successfully added";
    public static final String PROPERTY_EXISTS_MESSAGE = "Property with the same property and school name already " +
            "exists";
    public static final String PHOTO_UPDATE_MESSAGE = "Photo url successfully updated!";
    public static final String ROOM_TYPE_ENTRY_SUCCESS = "Room type successfully entered";
    public static final String ROOM_ENTRY_SUCCESS = "Room successfully entered";
    public static final String HOSTEL_NOT_FOUND_MESSAGE = "Hostel with associated school name not found";
    public static final String ROOM_TYPE_EXISTS_MESSAGE = "Room type already exists";
    public static final String ROOM_EXISTS_MESSAGE = "Room with such room number already exists";
    public static final String BOOKING_SUCCESS_MESSAGE = "Congrats, room successfully booked pending approval from " +
            "hostel admin after successful payment";
    public static final String ROOM_NOT_FOUND_MESSAGE = "Room not found";
    public static final String ACCOUNT_VERIFICATION_MESSAGE = "This account has already been verified, please login";
    public static final String EMAIL_VERIFICATION_MESSAGE = "Email verified successfully. Now you can login to your" +
            " account";
    public static final String INVALID_TOKEN_MESSAGE =  "Invalid verification token";

    public static String generateClientCode(int length) {
        String clientCode = "";
        int x;
        char[] stringChars = new char[length];

        for (int i = 0; i < length; i++) {
            Random random = new Random();
            x = random.nextInt(9);

            stringChars[i] = Integer.toString(x).toCharArray()[0];
        }

        clientCode = new String(stringChars);
        return clientCode.trim();
    }

}
