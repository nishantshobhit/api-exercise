package in.jedimaster.demo.dao;

import in.jedimaster.demo.model.Branch;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.List;

/**
 * Created by nishant on 19/3/16.
 */
public class PostgresBankDataDao implements BankDataDao {

    private static Connection getConnection() {
        URI dbUri = null;
        try {
            dbUri = new URI(System.getenv("DATABASE_URL"));
            if (dbUri == null) {
                throw new RuntimeException("No DATABASE_URL found in environment");
            }
        } catch (URISyntaxException cause) {
            throw new RuntimeException("Bad DATABASE_URL", cause);
        }

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
        try {
            return DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException cause) {
            throw new RuntimeException("unable to get connection", cause);
        }
    }

    @Override
    public List<Branch> listBranches(String bankName) {
        return null;
    }

    @Override
    public List<Branch> getBranch(String bankName, String city) {
        return null;
    }

    @Override
    public Branch getBranchByIfsc(String ifsc) {
        if (ifsc == null || "".equals(ifsc)) {
            return null;
        }
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("select bank_id, branch, address, city, " +
                    "district, state from branches where ifsc = ?");
            statement.setString(1, ifsc);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Branch.Builder builder = new Branch.Builder();
                builder.bankId(result.getLong("bank_id"))
                        .branch(result.getString("branch"))
                        .addr(result.getString("address"))
                        .city(result.getString("city"))
                        .district(result.getString("district"))
                        .state(result.getString("state"));
                return builder.build();
            }
        } catch (SQLException cause) {
            throw new RuntimeException("unable to execute query", cause);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException cause) {
                    // log an error may be?
                }
            }
        }
        return null;
    }
}
