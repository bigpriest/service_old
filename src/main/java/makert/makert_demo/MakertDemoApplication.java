package makert.makert_demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("makert.makert_demo.dao")
@SpringBootApplication
public class MakertDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MakertDemoApplication.class, args);
    }

}
