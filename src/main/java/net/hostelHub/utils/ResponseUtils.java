package net.hostelHub.utils;

import java.util.Random;

public class ResponseUtils {

    public static final String USER_EXISTS_CODE = "001";
    public static final String SUCCESS_CODE = "002";
    public static final String USER_EXISTS_MESSAGE = "User with provided email or username already exists!";
    public  static final int LENGTH_OF_TENANT_CODE = 6;
    public  static final int LENGTH_OF_OCCUPANT_CODE = 10;
    public static final String SUCCESS_MESSAGE = "Successfully Done!";
    public static final String FETCHED_MESSAGE = "User details successfully fetched!";
    public static final String USER_NOT_FOUND_MESSAGE = "This user doesn't exists";
    public static final String USER_NOT_FOUND_CODE = "003";



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
