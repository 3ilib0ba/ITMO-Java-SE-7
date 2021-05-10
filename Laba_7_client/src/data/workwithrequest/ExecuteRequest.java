package data.workwithrequest;

import collectionofflats.MyTreeMap;
import commands.Execute;
import commands.exceptions.ExitException;
import data.netdata.Report;
import data.netdata.ReportState;
import data.netdata.Request;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public abstract class ExecuteRequest {
    public static String pepper = "134F@!!9hTn4-@+*dfs*12";
    public static Request sendingRequest;

    public static Request doingRequest() {
        try {
            sendingRequest = Execute.execute(true, new Scanner(System.in));
        } catch (RuntimeException e) {
            throw e;
        }

        return sendingRequest;
    }

    public static String hashPassword(String password) {
        String MD5 = password /*+ pepper*/;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            MD5 = new String(md.digest(password.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Problem with hashing! Password isn't hashed");
        }
        return MD5;
    }
}
