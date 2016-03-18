package in.jedimaster.demo.resources;

import in.jedimaster.demo.AppConfiguration;
import in.jedimaster.demo.dao.BankDataDao;
import in.jedimaster.demo.dao.InMemoryBankDataDao;
import in.jedimaster.demo.model.Branch;
import in.jedimaster.demo.model.Branches;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by nishant on 18/3/16.
 */
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class BankResource {

    private AppConfiguration config;

    public BankResource(AppConfiguration config) {
        this.config = config;
    }

    @GET
    @Path("/banks/{bank}")
    public Branches getBank(@PathParam("bank") String bankName) {
        return new Branches(getDao().listBranches(bankName));
    }

    @GET
    @Path("/banks/{bank}/cities/{city}")
    public Branches getBranch(@PathParam("bank") String bankName, @PathParam("city") String city) {
        return new Branches(getDao().getBranch(bankName, city));
    }

    @GET
    @Path("/branches/{ifsc}")
    public Branch getBranchByIfsc(@PathParam("ifsc") String ifsc) {
        return getDao().getBranchByIfsc(ifsc);
    }

    public BankDataDao getDao() {
        // choose between MySql and inmemory data store
        return InMemoryBankDataDao.instance;
    }
}
