package in.jedimaster.demo.resources;

import in.jedimaster.demo.AppConfiguration;
import in.jedimaster.demo.dao.BankDataDao;
import in.jedimaster.demo.dao.InMemoryBankDataDao;
import in.jedimaster.demo.dao.PostgresBankDataDao;
import in.jedimaster.demo.model.Branch;
import in.jedimaster.demo.model.Branches;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
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
    public Branches getBank(@PathParam("bank") String bankName, @Context HttpServletRequest request) {
        return new Branches(getDao(request).listBranches(bankName));
    }

    @GET
    @Path("/banks/{bank}/cities/{city}")
    public Branches getBranch(@PathParam("bank") String bankName, @PathParam("city") String city,
                              @Context HttpServletRequest request) {
        return new Branches(getDao(request).getBranch(bankName, city));
    }

    @GET
    @Path("/branches/{ifsc}")
    public Branch getBranchByIfsc(@PathParam("ifsc") String ifsc, @Context HttpServletRequest request) {
        return getDao(request).getBranchByIfsc(ifsc);
    }

    // choose between MySql and inmemory data store
    public BankDataDao getDao(HttpServletRequest request) {
        String header = request.getHeader("X-Demo-Data-Source");
        BankDataDao dao = InMemoryBankDataDao.instance;
        if ("db".equals(header)) {
            dao = new PostgresBankDataDao();
        }
        return dao;
    }
}
