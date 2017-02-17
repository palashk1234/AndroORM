package palashk1234.androorm.utils;

import android.util.Log;


/**
 * Created by Palash on 29/11/2016.
 */
public class AndroUtils {
    private static final String TAG = "AndroUtils";

    /**
     * Checks whether the object is null or not.
     *
     * @param object object to be verified.
     * @return boolean
     */
    public static boolean isNull(Object object) {
        boolean isValid = false;
        try {
            if (object == null) {
                isValid = true;
            } else {
                if (object.equals("")) {
                    isValid = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }


    /**
     * Checks whether the String is null or not.
     *
     * @param object String to be verified.
     * @return boolean
     */
    public static boolean isNullString(String object) {
        boolean isValid = false;
        try {
            if (object == null) {
                isValid = true;
            } else {
                if (object.trim().isEmpty()) {
                    isValid = true;
                }

                if (object.trim().equalsIgnoreCase("null")) {
                    isValid = true;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }

    /**
     * Checks whether the string is valid mobile no or not
     *
     * @param mobileNo Mobile no string to be verified.
     * @return boolean
     */
    public static boolean isValidMobile(String mobileNo) {
        boolean isvalid = false;
        if (!isNullString(mobileNo)) {
            if (mobileNo.length() != 10 || mobileNo.length() > 14) {
                isvalid = false;
                // error message
            } else {
                isvalid = true;
            }
        } else {
            Log.e(TAG, "Entered mobile no is null.");
        }
        return isvalid;

    }

    /**
     * Checks whether the string is valid email or not.
     *
     * @param email Mobile no string to be verified.
     * @return boolean.
     */
    public static boolean isValidMail(String email) {
        boolean isvalid = false;
        if (isNullString(email)) {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                isvalid = true;
            } else {
                isvalid = false;
            }
        } else {
            Log.e(TAG, "Entered email is null.");
        }
        return isvalid;
    }

}

