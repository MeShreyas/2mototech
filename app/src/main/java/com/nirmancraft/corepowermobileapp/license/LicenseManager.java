package com.nirmancraft.corepowermobileapp.license;

import android.content.Context;
import android.util.Log;

import com.nirmancraft.corepowermobileapp.constants.CoreAppConstants;
import com.nirmancraft.corepowermobileapp.utils.db.DataBaseHelper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Shreyas on 7/30/2014.
 */
public class LicenseManager {
    public static boolean fetchLicenseDetails(String deviceId, Context context) {
        boolean licensed = false;
        DataBaseHelper helper = new DataBaseHelper(context);
        try {
            helper.createDataBase();
            helper.openDataBase();
            licensed = helper.checkIfLicensed(getMD5Hash(CoreAppConstants.PHONENUMBER,deviceId));
            helper.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return licensed;
    }

    private static String getMD5Hash(String userMobile, String ownerMobile)
    {
        String input=ownerMobile+userMobile;
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(input.getBytes("UTF-8"), 0, input.length());
            BigInteger i = new BigInteger(1,m.digest());
            Log.i("info", String.format("%1$032x", i));
            String license = String.format("%1$032x", i);
            return license.substring(0,10);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    public static boolean validateLicense(String deviceId,String license) {
        String generatedLicense = getMD5Hash(CoreAppConstants.PHONENUMBER,deviceId);
        return generatedLicense.equalsIgnoreCase(license)?true:false;
    }

    public static void saveLicense(String license,Context context) {
        DataBaseHelper helper = new DataBaseHelper(context);
        try {
            helper.createDataBase();
            helper.openDataBase();
            helper.saveLicense(license);
            helper.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
