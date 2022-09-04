package me.saransh.runtimeentity;

import lombok.extern.slf4j.Slf4j;
import me.saransh.runtimeentity.models.Attribute;
import me.saransh.runtimeentity.models.Entity;
import me.saransh.runtimeentity.repository.MyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@Slf4j
//@EnableMongoRepositories
public class ClientSideApplication implements CommandLineRunner {

	@Autowired
	private MyRepository myRepository;

	public static void main(String[] args) {
		SpringApplication.run(ClientSideApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//address fields
		List<Attribute> attributesInner1 = new ArrayList<>();
		attributesInner1.add(Attribute.builder().identifier(UUID.randomUUID()).name("AddressLine1").type("String").build());
		attributesInner1.add(Attribute.builder().identifier(UUID.randomUUID()).name("Pincode").type("Integer").build());
		attributesInner1.add(Attribute.builder().identifier(UUID.randomUUID()).name("City").type("String").build());

		//payment fields
		List<Attribute> attributesInner2 = new ArrayList<>();
		attributesInner2.add(Attribute.builder().identifier(UUID.randomUUID()).name("CardName").type("String").build());
		attributesInner2.add(Attribute.builder().identifier(UUID.randomUUID()).name("CVV").type("Integer").build());
		attributesInner2.add(Attribute.builder().identifier(UUID.randomUUID()).name("cardNumber").type("String").build());

		Entity payment = Entity.builder()
				.identifier(UUID.randomUUID())
				.name("PaymentDetails")
				.namespace("com.namespace")
				.attributes(attributesInner2.toArray(new Attribute[0])).build();

		Entity address = Entity.builder()
				.identifier(UUID.randomUUID())
				.name("Address")
				.namespace("com.namespace")
				.attributes(attributesInner1.toArray(new Attribute[0])).build();


		myRepository.deleteAll();
		log.info("cleared all previous records from document");
		//outer fields
		List<Attribute> attributes = new ArrayList<>();
		attributes.add(Attribute.builder().identifier(UUID.randomUUID()).name("Name").type("String").build());
		attributes.add(Attribute.builder().identifier(UUID.randomUUID()).name("Email").type("String").build());
		attributes.add(Attribute.builder().identifier(UUID.randomUUID()).name("ActiveFlag").type("Long").build());


		Entity account = Entity.builder()
				.identifier(UUID.randomUUID())
				.name("Account")
				.namespace("com.namespace")
				.attributes(attributes.toArray(new Attribute[0]))
				.entityList(List.of(address, payment))
				.build();

		Entity saved = myRepository.save(account);
		log.info("saved entity {}", saved.toString());
//		myRepository.deleteAll();
//		log.info("cleared previous entries in collection");

	}
}
