package okon.ASE9.report;

import okon.ASE9.ASE9App;
import okon.ASE9.exception.AppException;

import java.io.FileWriter;
import java.io.Writer;

public class Printer {
    public void print(String fullReport) {
        printToConsole(fullReport);
        printToFile(fullReport);
    }

    private void printToConsole(String fullReport) {
        System.out.print(fullReport);
    }

    private void printToFile(String fullReport) {
        try (Writer out = new FileWriter(new java.io.File(ASE9App.getJarFileName() + ".txt"))) {
            out.write(fullReport);
        } catch (Exception e) {
            throw new AppException(e);
        }
    }
}
