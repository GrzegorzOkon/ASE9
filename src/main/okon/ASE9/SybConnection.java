package okon.ASE9;

import javax.sql.DataSource;
import java.io.Closeable;
import java.sql.*;

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
        String sql = "{call sp_sysmon '00:00:05', kernel}";
        StringBuffer text = new StringBuffer();

        try (CallableStatement stmt = connection.prepareCall(sql)){
            stmt.executeUpdate();
            SQLWarning connectionWarning = stmt.getWarnings();

            do {
                text.append(connectionWarning.getMessage()).append("\n");
                connectionWarning = connectionWarning.getNextWarning();
            } while (connectionWarning != null);
        } catch (SQLException e) {
            throw new AppException(e);
        }

        return new Message(text.toString());
        //return new Message(startingFreeCacheSize, endingFreeCacheSize, startTime, endTime);
    }

    /*public Message execute() {
        int startingFreeCacheSize = 0;
        int endingFreeCacheSize = 0;
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;

        startingFreeCacheSize = checkFreeCacheSize();
        startTime = getTimeStamp();
        freeUnusedCache();
        endTime = getTimeStamp();
        endingFreeCacheSize = checkFreeCacheSize();

        return new Message(startingFreeCacheSize, endingFreeCacheSize, startTime, endTime);
    }*/

    /*public int checkFreeCacheSize() {
        String sql = "sp_monitorconfig 'procedure cache_size'";
        int freeCacheSize = 0;

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                freeCacheSize = rs.getInt("Num_free");
            }
        } catch (SQLException e) {
            throw new AppException(e);
        }

        return freeCacheSize;
    }*/

    /*public LocalDateTime getTimeStamp() {
        return LocalDateTime.now();
    }*/

    /*public void freeUnusedCache() {
        String sql = "dbcc proc_cache(free_unused)";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new AppException(e);
        }
    }*/

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new AppException(e);
        }
    }
}