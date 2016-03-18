package in.jedimaster.demo;

import in.jedimaster.demo.resources.BankResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by nishant on 18/3/16.
 */
public class Main extends Application<AppConfiguration> {

    public static void main(String[] args) throws Exception {
        new Main().run(args);
    }

    @Override
    public String getName() {
        return "demo";
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {

    }

    @Override
    public void run(AppConfiguration conf, Environment environment) throws Exception {
        BankResource resource = new BankResource(conf);
        environment.jersey().register(resource);
    }
}
