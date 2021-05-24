package okon.ASE9;

import okon.ASE9.config.*;
import okon.ASE9.exception.AppException;
import okon.ASE9.report.ReportManager;
import okon.ASE9.messages.DataExtraction;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

public class App {
    private static final Logger logger = LogManager.getLogger(App.class);
    public static final List<List<DataExtraction>> extractions = new ArrayList();

    static final String CONNECTION_EXCEPTION_COMMUNICATE = "connection error to";

    static {
        App.initLogger("app.log","%d %p %c [%t] %m%n");
        Properties properties = ProgramConfigReader.loadProperties((new File("./config/program.properties")));
        WorkingEnvironment.setEnvironment(properties);
        WorkingObjects.setJobs(properties);
    }

    public static void main(String[] args) {
        Options options = createOptions();
        CommandLine cmd = handleArguments(args, options);
        if (isHelpRequired(cmd)) {
            printHelp(options);
        } else if (isVersionRequired(cmd)) {
            Version.printVersionInfo();
        } else {
            startThreadPool();
            print();
        }
    }

    private static void initLogger(String fileName, String pattern) {
        ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
        LayoutComponentBuilder layoutBuilder = builder.newLayout("PatternLayout")
                .addAttribute("pattern", pattern);
        AppenderComponentBuilder appenderBuilder = builder.newAppender("LogToRollingFile", "RollingFile")
                .addAttribute("fileName", fileName)
                .addAttribute("filePattern", fileName+"-%d{MM-dd-yy-HH-mm-ss}.log.")
                .add(layoutBuilder);
        if (!WorkingEnvironment.getLogFileSize().equals("0")) {
            ComponentBuilder triggeringPolicy = builder.newComponent("Policies")
                    .addComponent(builder.newComponent("SizeBasedTriggeringPolicy")
                            .addAttribute("size", WorkingEnvironment.getLogFileSize() + "MB"));
            appenderBuilder.addComponent(triggeringPolicy);
        }
        builder.add(appenderBuilder);
        RootLoggerComponentBuilder rootLogger = builder.newRootLogger(Level.DEBUG);
        rootLogger.add(builder.newAppenderRef("LogToRollingFile"));
        builder.add(rootLogger);
        Configurator.reconfigure(builder.build());


        //builder.setStatusLevel(Level.DEBUG);
        //builder.setConfigurationName("DefaultLogger");

        // create a console appender
        /*AppenderComponentBuilder appenderBuilder = builder.newAppender("Console", "CONSOLE").addAttribute("target",
                ConsoleAppender.Target.SYSTEM_OUT);
        appenderBuilder.add(builder.newLayout("PatternLayout")
                .addAttribute("pattern", pattern));
        RootLoggerComponentBuilder rootLogger = builder.newRootLogger(Level.DEBUG);
        rootLogger.add(builder.newAppenderRef("Console"));

        builder.add(appenderBuilder);*/

        // create a rolling file appender

    }

    static Options createOptions() {
        Options result = new Options();
        Option help = new Option("h", "help", false, "Display help message");
        help.setRequired(false);
        result.addOption(help);
        Option version = new Option("v", "version", false, "Display version number");
        version.setRequired(false);
        result.addOption(version);
        return result;
    }

    static CommandLine handleArguments(String[] args, Options opts) {
        CommandLine result = null;
        CommandLineParser parser = new DefaultParser();
        try {
            result = parser.parse(opts, args);
        } catch (ParseException e) {
            System.out.println(WorkingEnvironment.getApplicationName() + ": " + e.getMessage().toLowerCase());
            printUsage(opts);
            System.exit(1);
        }
        return result;
    }

    static boolean isHelpRequired(CommandLine cmd) {
        if (cmd != null && cmd.hasOption('h'))
            return true;
        return false;
    }

    static boolean isVersionRequired(CommandLine cmd) {
        if (cmd != null && cmd.hasOption('v'))
            return true;
        return false;
    }

    static void printHelp(Options opts) {
        HelpFormatter formatter = new HelpFormatter();
        String header = "\nAn application for monitoring average databases load.\n\nFunctions:\n";
        String footer = "\nExample: java -jar " + WorkingEnvironment.getApplicationName() + " -v\n\nReport bugs to: <grzegorz.programista@gmail.com>";
        formatter.printHelp(WorkingEnvironment.getApplicationName(), header, opts, footer, true);
    }

    static void printUsage(Options opts) {
        HelpFormatter formatter = new HelpFormatter();
        StringWriter out = new StringWriter();
        PrintWriter pw = new PrintWriter(out);
        formatter.printUsage(pw, formatter.getWidth(), WorkingEnvironment.getApplicationName(), opts);
        pw.flush();
        System.out.println(out.toString());
    }

    static void startThreadPool() {
        logger.debug("In startThreadPool()");
        int threadSum = WorkingObjects.jobs.size();
        logger.debug("Thread sum: " + threadSum);
        Thread[] threads = new Thread[threadSum];
        for (int i = 0; i < threadSum; i++) {
            threads[i] = new JobConsumentThread();
        }
        for (int i = 0; i < threadSum; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threadSum; i++) {
            try {
                threads[i].join();
            } catch (Exception e) {
                throw new AppException(e);
            }
        }
        logger.debug("End of startThreadPool()");
    }

    /*static void print() {
        if (isThreadPoolPresent()) {
            printWithThreadPoolColumnToConsole();
            printWithThreadPoolColumnToFile();
        } else {
            printWithoutThreadPoolColumnToConsole();
            printWithoutThreadPoolColumnToFile();
        }
    }*/

    /*static void printWithThreadPoolColumnToConsole() {
        ReportFormatter formatter = new ReportFormatter(true);
        System.out.println(formatter.format(new String[]{"Server", "Thread Pool", "CPU Busy"}));
        System.out.println(formatter.format(new String[]{"------", "-----------", "--------"}));
        for (Report report : extractions) {
            if (report instanceof PerformanceReport) {
                String formattedRow = formatter.format(new String[]{report.getAlias() + " (" + report.getServerIP() + ")",
                        report.getThreadPool(), formatter.getCPUBusy(report.getIdleTick()) + " %"});
                System.out.println(formattedRow);
            } else if (report instanceof ExceptionReport) {
                String formattedRow = formatter.format(new String[]{CONNECTION_EXCEPTION_COMMUNICATE + " " + report.getAlias() +
                        " (" + report.getServerIP() + ")", "", ""});
                System.out.println(formattedRow);
            }
        }
    }*/

    /*static void printWithThreadPoolColumnToFile() {
        try (Writer out = new FileWriter(new java.io.File(ASE9App.getJarFileName() + ".txt"))) {
            ReportFormatter formatter = new ReportFormatter(true);
            out.write(formatter.format(new String[]{"Server", "Thread Pool", "CPU Busy"}));
            out.write(System.getProperty("line.separator"));
            out.write(formatter.format(new String[]{"------", "-----------", "--------"}));
            out.write(System.getProperty("line.separator"));
            for (Report report : extractions) {
                if (report instanceof PerformanceReport) {
                    String formattedRow = formatter.format(new String[]{report.getAlias() + " (" + report.getServerIP() + ")",
                            report.getThreadPool(), formatter.getCPUBusy(report.getIdleTick()) + " %"});
                    out.write(formattedRow);
                    out.write(System.getProperty("line.separator"));
                } else if (report instanceof ExceptionReport) {
                    String formattedRow = formatter.format(new String[]{CONNECTION_EXCEPTION_COMMUNICATE + " " + report.getAlias() +
                            " (" + report.getServerIP() + ")", "", ""});
                    out.write(formattedRow);
                    out.write(System.getProperty("line.separator"));
                }
            }
        } catch (Exception e) {
            throw new AppException(e);
        }
    }*/

    /*static void printWithoutThreadPoolColumnToConsole() {
        ReportFormatter formatter = new ReportFormatter(false);
        System.out.println(formatter.format(new String[]{"Server", "CPU Busy"}));
        System.out.println(formatter.format(new String[]{"------", "--------"}));
        for (Report report : extractions) {
            if (report instanceof PerformanceReport) {
                String formattedRow = formatter.format(new String[]{report.getAlias() + " (" + report.getServerIP() + ")",
                        formatter.getCPUBusy(report.getIdleTick()) + " %"});
                System.out.println(formattedRow);
            } else if (report instanceof ExceptionReport) {
                String formattedRow = formatter.format(new String[]{CONNECTION_EXCEPTION_COMMUNICATE + " " + report.getAlias() +
                        " (" + report.getServerIP() + ")", ""});
                System.out.println(formattedRow);
            }
        }
    }*/

    /*static void printWithoutThreadPoolColumnToFile() {
        try (Writer out = new FileWriter(new java.io.File(ASE9App.getJarFileName() + ".txt"))) {
            ReportFormatter formatter = new ReportFormatter(false);
            out.write(formatter.format(new String[]{"Server", "CPU Busy"}));
            out.write(System.getProperty("line.separator"));
            out.write(formatter.format(new String[]{"------", "--------"}));
            out.write(System.getProperty("line.separator"));
            for (Report report : extractions) {
                if (report instanceof PerformanceReport) {
                    String formattedRow = formatter.format(new String[]{report.getAlias() + " (" + report.getServerIP() + ")",
                            formatter.getCPUBusy(report.getIdleTick()) + " %"});
                    out.write(formattedRow);
                    out.write(System.getProperty("line.separator"));
                } else if (report instanceof ExceptionReport) {
                    String formattedRow = formatter.format(new String[]{CONNECTION_EXCEPTION_COMMUNICATE + " " + report.getAlias() +
                            " (" + report.getServerIP() + ")", ""});
                    out.write(formattedRow);
                    out.write(System.getProperty("line.separator"));
                }
            }
        } catch (Exception e) {
            throw new AppException(e);
        }
    }*/

    /*static boolean isThreadPoolPresent() {
        for (Report report : extractions) {
            if (!report.getThreadPool().equals("")) {
                return true;
            }
        }
        return false;
    }*/

    private static void print() {
        new ReportManager().print();
    }
}
