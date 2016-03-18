package in.jedimaster.demo.dao;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import in.jedimaster.demo.model.Branch;

import java.util.*;

/**
 * Created by nishant on 18/3/16.
 */
public class InMemoryBankDataDao implements BankDataDao {

    private static final String BANK_BRANCH_CSV_FILE = "bank_branches.csv";
    public  static final InMemoryBankDataDao instance = new InMemoryBankDataDao();

    // indexes
    Map<String, Branch> ifscToBranch = new HashMap<>();
    Map<String, Map<String, List<Branch>>> mainIndex = new HashMap<>();

    private InMemoryBankDataDao() {
        initialize();
    }

    @Override
    public List<Branch> listBranches(String bankName) {
        if (!mainIndex.containsKey(bankName)) {
            return Collections.emptyList();
        }
        Map<String, List<Branch>> cityIndex = mainIndex.get(bankName);
        List<Branch> allBranches = new ArrayList<>();
        for (Map.Entry<String, List<Branch>> entry : cityIndex.entrySet()) {
            allBranches.addAll(entry.getValue());
        }
        return allBranches;
    }

    @Override
    public List<Branch> getBranch(String bankName, String city) {
        if (mainIndex.containsKey(bankName)) {
            if (mainIndex.get(bankName).containsKey(city)) {
                return mainIndex.get(bankName).get(city);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public Branch getBranchByIfsc(String ifsc) {
        return ifscToBranch.get(ifsc);
    }

    protected void initialize() {
        CsvParserSettings settings = new CsvParserSettings();
        settings.getFormat().setLineSeparator("\n");
        CsvParser parser = new CsvParser(settings);
        parser.beginParsing(InMemoryBankDataDao.class.getClassLoader().getResourceAsStream(BANK_BRANCH_CSV_FILE));
        String[] row = parser.parseNext(); // ignores the first row
        while ((row = parser.parseNext()) != null) {
            Branch branch = getBranch(row);
            ifscToBranch.put(branch.getIfsc(), branch);
            if (!mainIndex.containsKey(branch.getBankName())) {
                mainIndex.put(branch.getBankName(), new LinkedHashMap<String, List<Branch>>());
            }
            Map<String, List<Branch>> cityIndex = mainIndex.get(branch.getBankName());
            if (!cityIndex.containsKey(branch.getCity())) {
                cityIndex.put(branch.getCity(), new ArrayList<Branch>());
            }
            cityIndex.get(branch.getCity()).add(branch);
        }
        parser.stopParsing();
    }

    //ifsc,bank_id,branch,address,city,district,state,bank_name
    protected Branch getBranch(String[] row) {
        Branch.Builder builder = new Branch.Builder();
        builder.ifsc(row[0])
                .bankId(Long.parseLong(row[1]))
                .branch(row[2])
                .addr(row[3])
                .city(row[4])
                .district(row[5])
                .state(row[6])
                .bankName(row[7]);
        return builder.build();
    }
}