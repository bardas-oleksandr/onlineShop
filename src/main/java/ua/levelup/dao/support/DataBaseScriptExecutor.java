package ua.levelup.dao.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Arrays;

@Component
@Profile("!test")
public class DataBaseScriptExecutor {

    @Autowired
    private DataSource dataSource;

    public void executeScripts(String... scripts) {
        ResourceDatabasePopulator action = new ResourceDatabasePopulator();
        action.setSqlScriptEncoding("UTF-8");
        Arrays.asList(scripts).forEach(script -> action.addScript(new ClassPathResource(script)));
        DatabasePopulatorUtils.execute(action, dataSource);
    }
}
