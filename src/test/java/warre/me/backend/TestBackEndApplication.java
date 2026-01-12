package warre.me.backend;

import org.springframework.boot.SpringApplication;

public class TestBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.from(FrontEndApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
