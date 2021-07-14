package okon.ASE9.report;

import okon.ASE9.WorkingEnvironment;

public abstract class ReportFormatter {
    public String formatHeader(String[] input) {
        String result = null;
        if (WorkingEnvironment.getReportStyle().equals("short")) {
            result = input[1];
        } else if (WorkingEnvironment.getReportStyle().equals("normal")) {
            result = input[0] + " (" + input[1] + ")";
        } else if (WorkingEnvironment.getReportStyle().equals("decorative")) {
            result = "*** " + input[0] + " (" + input[1] + ")" + " ***";
        }
        return result;
    }
    public abstract String formatBody(String[] input);
}