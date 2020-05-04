package okon.ASE9;

import javax.sql.DataSource;
import java.io.Closeable;
import java.sql.*;

public class SybGateway implements Closeable {
    private static final String findLoadStatement = "sp_sysmon '00:00:15', kernel";
    private Connection db;

    public SybGateway(DataSource dataSource) {
        try {
            db = dataSource.getConnection();
        } catch (SQLException e) {
            throw new AppException(e);
        }
    }

    public SQLWarning findLoadFor(int seconds) throws SQLException {
        PreparedStatement stmt = db.prepareStatement(findLoadStatement);
        //stmt.set...
        stmt.executeUpdate();
        SQLWarning result = stmt.getWarnings();
        return result;
    }

    @Override
    public void close() {
        try {
            db.close();
        } catch (SQLException e) {
            throw new AppException(e);
        }
    }
}