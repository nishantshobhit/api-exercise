package in.jedimaster.demo.dao;

import in.jedimaster.demo.model.Branch;

import java.util.List;

/**
 * Created by nishant on 18/3/16.
 */
public interface BankDataDao {

    List<Branch> listBranches(String bankName);

    List<Branch> getBranch(String bankName, String city);

    Branch getBranchByIfsc(String ifsc);
}
