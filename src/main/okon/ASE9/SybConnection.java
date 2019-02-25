package okon.ASE9;

import javax.sql.DataSource;
import java.io.Closeable;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SybConnection implements Closeable {
    private final Connection connection;

    public SybConnection(DataSource dataSource) {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new AppException(e);
        }
    }

    public Message execute() {
        SQLWarning warnings = executeStoredProcedure();
        String response = changeToText(warnings);

        return new Message(checkServerName(response));
        //return new Message(startingFreeCacheSize, endingFreeCacheSize, startTime, endTime);
    }

    public SQLWarning executeStoredProcedure() {
        //String sql = "{call sp_sysmon '00:00:05', kernel}";
        String sql = "sp_sysmon '00:00:05', kernel";
        SQLWarning response = null;

        try (Statement stmt = connection.createStatement()){
            stmt.executeUpdate(sql);
            response = stmt.getWarnings();
        } catch (SQLException e) {
            throw new AppException(e);
        }

        return response;
    }

    public String changeToText(SQLWarning response) {
        StringBuilder warnings = new StringBuilder();

        do {
            warnings.append(response.getMessage().trim()).append("\n");
            response = response.getNextWarning();
        } while (response != null);

        return warnings.toString();
    }

    public String checkServerName(String response) {
        Pattern pattern = Pattern.compile("Server Name:\\s+(\\w+)\n");
        Matcher matcher = pattern.matcher(response);
        matcher.find();

        return matcher.group(1);
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new AppException(e);
        }
    }
}