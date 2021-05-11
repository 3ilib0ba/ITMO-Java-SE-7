package data.workwithrequest;

import collectionofflats.MyTreeMap;
import commands.Execute;
import commands.exceptions.ExitException;
import data.netdata.ClientIdentificate;
import data.netdata.Report;
import data.netdata.ReportState;
import data.netdata.Request;
import typesfiles.Flat;

import java.util.Scanner;

public abstract class ExecuteRequest {
    private static Scanner scannerOfCommands;
    public static StringBuilder answer = new StringBuilder();
    private static ReportState stateAnswer;

    public static void doingRequest(Request request, MyTreeMap myMap) {
        answer = new StringBuilder("");
        stateAnswer = ReportState.OK;
        System.out.println("Entering the command: " + request.getCommandName() +
                            ", FROM: " + request.getLoginClient());

        StringBuilder fullRequest = new StringBuilder(request.getCommandName() + " " + request.getArgument());
        scannerOfCommands = new Scanner(fullRequest.toString());
        ClientIdentificate aboutClient = new ClientIdentificate(request.getLoginClient(), request.getPasswordClient());


        answer = new StringBuilder();

        try {
            Execute.execute(false, myMap, scannerOfCommands,
                    request.getObjectArgument() == "null" ? null : (Flat) request.getObjectArgument(), aboutClient);
            stateAnswer = ReportState.OK;
        } catch (ExitException e) {
            answer.append("main.Server isn't worked now");
            throw e;
        } catch (Exception e) {
            stateAnswer = ReportState.ERROR;
            answer.append(e.getMessage());
        }
    }

    public static Report makeReport() {
        Report reportToClient = new Report(stateAnswer, answer.toString());

        return reportToClient;
    }
}
