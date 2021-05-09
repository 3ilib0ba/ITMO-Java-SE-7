package data.workwithrequest;

import collectionofflats.MyTreeMap;
import commands.Execute;
import commands.exceptions.ExitException;
import data.netdata.Report;
import data.netdata.ReportState;
import data.netdata.Request;

import java.util.Scanner;

public abstract class ExecuteRequest {
    public static Request sendingRequest;

    public static Request doingRequest() {
        try {
            sendingRequest = Execute.execute(true, new Scanner(System.in));
        } catch (RuntimeException e) {
            throw e;
        }

        return sendingRequest;
    }

}
