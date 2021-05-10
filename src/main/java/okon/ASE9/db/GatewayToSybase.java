package okon.ASE9.db;

import com.sybase.jdbc4.jdbc.SybDataSource;
import okon.ASE9.Job;
import okon.ASE9.exception.AppException;
import okon.ASE9.exception.ConnectionException;

import javax.sql.DataSource;
import java.sql.*;

public class GatewayToSybase extends Gateway {
    private static final String sysmonStatement = "sp_sysmon ?, kernel";
    private Connection db;

    public GatewayToSybase(Job job) {
        try {
            db = createDataSource(job.getIp(), job.getPort(), job.getLogin(), job.getPasssword()).getConnection();
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
    }

    @Override
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
        dataSource.setAPPLICATIONNAME("okon/ASE9");
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