package okon.ASE9;

import com.sybase.jdbc4.jdbc.SybDataSource;
import okon.ASE9.exception.AppException;

import javax.sql.DataSource;
import java.io.Closeable;
import java.sql.*;

public class GatewaySybase implements Closeable {
    private static final String findLoadStatement = "sp_sysmon '00:00:15', kernel";
    private Connection db;

    public GatewaySybase(Job job) {
        try {
            db = createDataSource(job.getServer().getIp(), job.getServer().getPort(), job.getAuthorization().getUsername(),
                    job.getAuthorization().getPassword()).getConnection();
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

    private DataSource createDataSource (String serverName, int portNumber, String user, String password){
        SybDataSource dataSource = new SybDataSource();
        dataSource.setServerName(serverName);
        dataSource.setPortNumber(portNumber);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setDatabaseName("master");
        dataSource.setAPPLICATIONNAME("ASE9");
        return dataSource;
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