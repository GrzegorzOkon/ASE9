package okon.ASE9;

import javax.sql.DataSource;
import java.io.Closeable;
import java.sql.*;
import java.util.List;

public class SybConnection implements Closeable {
    private final Connection connection;

    protected String response;

    public SybConnection(DataSource dataSource) {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new AppException(e);
        }
    }

    public List<Message> execute() {
        SQLWarning warnings = createPerformanceReport();
        response = transform(warnings);

        return accept(new SybConnectionVisitor());
    }

    private SQLWarning createPerformanceReport() {
        String sql = "sp_sysmon '00:00:15', kernel";
        SQLWarning response = null;

        try (Statement stmt = connection.createStatement()){
            stmt.executeUpdate(sql);
            response = stmt.getWarnings();
        } catch (SQLException e) {
            throw new AppException(e);
        }

        return response;
    }

    String transform(SQLWarning response) {
        StringBuilder warnings = new StringBuilder();

        do {
            warnings.append(response.getMessage().trim()).append("\n");
            response = response.getNextWarning();
        } while (response != null);

        return warnings.toString();
    }

    private List<Message> accept(SybConnectionVisitor visitor) {
        return visitor.searchPerformanceReport(this);
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