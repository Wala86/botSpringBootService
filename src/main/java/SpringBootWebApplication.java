import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import domain.Product;
import repositories.ProductRepository;

@SpringBootApplication
@LineMessageHandler

@ComponentScan(basePackages = { "controller" })
@EntityScan(basePackages = { "domain" })
@EnableJpaRepositories(basePackages = { "repositories" })
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

	@EventMapping
	public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
		System.out.println("event: " + event);
		return new TextMessage(event.getMessage().getText());
	}

	@EventMapping
	public void handleDefaultMessageEvent(Event event) {
		System.out.println("event: " + event);
	}
}
