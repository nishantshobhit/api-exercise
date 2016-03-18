package in.jedimaster.demo.dao;

import in.jedimaster.demo.model.Branch;
import org.apache.log4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nishant on 19/3/16.
 */
public class PostgresBankDataDao implements BankDataDao {

    private static final Logger logger = Logger.getLogger(PostgresBankDataDao.class);

    private Connection getConnection() {
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
        return executeQuery("select B.id, BR.ifsc, BR.branch, BR.address, BR.city, BR.district, BR.state, B.name " +
                "from banks B, branches BR where B.id = BR.bank_id and B.name = ?", bankName);
    }

    @Override
    public List<Branch> getBranch(String bankName, String city) {
        return executeQuery("select B.id, BR.ifsc, BR.branch, BR.address, BR.city, BR.district, BR.state, B.name " +
                "from banks B, branches BR where B.id = BR.bank_id and B.name = ? and BR.city = ?", bankName, city);
    }

    @Override
    public Branch getBranchByIfsc(String ifsc) {
        List<Branch> branches = executeQuery("select B.id, BR.ifsc, BR.branch, BR.address, BR.city, BR.district, BR.state, B.name " +
                "from banks B, branches BR where B.id = BR.bank_id and BR.ifsc = ?", ifsc);
        if (branches.size() > 1) {
            logger.warn("More than one record found for ifsc=" + ifsc);
        }
        return (branches.isEmpty()) ? null : branches.get(0);
    }

    protected List<Branch> executeQuery(String query, String...args) {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            if (args != null && args.length > 0) {
                for (int i=0; i<args.length; i++) {
                    statement.setString(i+1, args[i]);
                }
            }
            ResultSet result = statement.executeQuery();
            List<Branch> branches = new ArrayList<>();
            while (result.next()) {
                Branch.Builder builder = new Branch.Builder();
                builder.bankId(result.getLong("id"))
                        .ifsc(result.getString("ifsc"))
                        .branch(result.getString("branch"))
                        .addr(result.getString("address"))
                        .city(result.getString("city"))
                        .district(result.getString("district"))
                        .state(result.getString("state"))
                        .bankName(result.getString("name"));
                branches.add(builder.build());
            }
            return branches;
        } catch (SQLException cause) {
            throw new RuntimeException("unable to execute query", cause);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException cause) {
                    logger.error("Unable to close db conn", cause);
                }
            }
        }
    }
}
