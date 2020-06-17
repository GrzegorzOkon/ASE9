package okon.ASE9;

import com.sybase.jdbc4.jdbc.SybDataSource;
import okon.ASE9.exception.AppException;
import okon.ASE9.exception.ConnectionException;

import javax.sql.DataSource;
import java.io.Closeable;
import java.sql.*;

public class GatewaySybase implements Closeable {
    private static final String sysmonStatement = "sp_sysmon ?, kernel";
    private Connection db;

    public GatewaySybase(Job job) {
        try {
            db = createDataSource(job.getServer().getIp(), job.getServer().getPort(), job.getAuthorization().getUsername(),
                    job.getAuthorization().getPassword()).getConnection();
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
    }

    public SQLWarning findLoadFor(String time) throws SQLException {
        PreparedStatement stmt = db.prepareStatement(sysmonStatement);
        stmt.setString(1, time);
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