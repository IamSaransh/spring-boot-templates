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
public class RuntimeEntityCreationApplication implements CommandLineRunner {

	@Autowired
	private MyRepository myRepository;

	public static void main(String[] args) {
		SpringApplication.run(RuntimeEntityCreationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Attribute> attributesInnter = new ArrayList<>();
		attributesInnter.add(Attribute.builder().identifier(UUID.randomUUID()).name("AddressLine1").type("String").build());
		attributesInnter.add(Attribute.builder().identifier(UUID.randomUUID()).name("Pincode").type("integer").build());
		attributesInnter.add(Attribute.builder().identifier(UUID.randomUUID()).name("City").type("String").build());


		Entity Address = Entity.builder()
				.identifier(UUID.randomUUID())
				.name("Address")
				.namespace("com.namespace")
				.attributes(attributesInnter.toArray(new Attribute[0])).build();

		myRepository.deleteAll();
		log.info("cleared all previous records from document");
		List<Attribute> attributes = new ArrayList<>();
		attributes.add(Attribute.builder().identifier(UUID.randomUUID()).name("Name").type("String").build());
		attributes.add(Attribute.builder().identifier(UUID.randomUUID()).name("Email").type("String").build());
		attributes.add(Attribute.builder().identifier(UUID.randomUUID()).name("ActiveFlag").type("long").build());


		Entity acccount = Entity.builder()
				.identifier(UUID.randomUUID())
				.name("Acccount")
				.namespace("com.namespace")
				.attributes(attributes.toArray(new Attribute[0]))
				.entity(Address)
				.build();

		Entity saved = myRepository.save(acccount);
		log.info("saved entity {}", saved.toString());

	}
}
