import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import domain.Product;
import groovyjarjarcommonscli.CommandLine;
import repositories.ProductRepository;

@SpringBootApplication
@ComponentScan(basePackages = { "controller" })
@EntityScan(basePackages = { "domain" })
@EnableJpaRepositories
@EnableConfigurationProperties

public class SpringBootWebApplication implements CommandLineRunner {
	@Autowired
	ProductRepository rep;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebApplication.class, args);

	}

	@Override
	public void run(String... arg0) throws Exception {
		Product prod = new Product();
		prod.setId(1);
		prod.setDescription("prod1");
		prod.setPrice(500);
		rep.save(prod);

	}
}
