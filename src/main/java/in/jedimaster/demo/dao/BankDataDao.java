package in.jedimaster.demo.dao;

import in.jedimaster.demo.model.Branch;

import java.util.List;

/**
 * Created by nishant on 18/3/16.
 */
public interface BankDataDao {

    /**
     * List details of all branches of this bank
     *
     * @param bankName
     * @return empty list when the bank name is not found or it does not have any branches
     */
    List<Branch> listBranches(String bankName);

    /**
     * Gets details of the bank in this city
     *
     * @param bankName
     * @param city
     * @return null when no branches are found
     */
    List<Branch> getBranch(String bankName, String city);

    /**
     * Get branch details by IFSC code
     *
     * @param ifsc
     * @return
     */
    Branch getBranchByIfsc(String ifsc);
}
