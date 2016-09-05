package configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dmitrii.Smirnov
 */
@Configuration
@ComponentScan({"com.psc.service.impl", "com.psc.export.itconfig.services", "com.psc.export.translators.impl",
        "com.psc.export.translators.services", "com.psc.export.collectors.impl", "com.psc.export.collectors.helpers"})
public class ServicesConfig {

}
