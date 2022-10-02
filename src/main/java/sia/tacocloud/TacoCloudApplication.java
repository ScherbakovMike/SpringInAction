package sia.tacocloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sia.tacocloud.tacos.AppProperties;

@SpringBootApplication
public class TacoCloudApplication {

    public final AppProperties appProperties;

    public TacoCloudApplication(AppProperties appProperties) {
        this.appProperties = appProperties;
        System.out.println(this.appProperties.getParam1());
        System.out.println(this.appProperties.getParam2());
    }

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);
    }

}
