package saransh13.me;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import saransh13.me.model.User;
import saransh13.me.service.GetEnrichedService;
import saransh13.me.service.ReadCsvGenericService;
import saransh13.me.service.ReadCsvService;
import saransh13.me.util.UserFactoryImpl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


@SpringBootApplication
@Slf4j
public class FIleEnrichmentApplication implements CommandLineRunner {
    @Value("${file.path}")
    private String filePath;
    @Autowired
    UserFactoryImpl factory;

    public static void main(String[] args) {
        SpringApplication.run(FIleEnrichmentApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info(factory.toString());
        log.info(filePath);
        ReadCsvGenericService<User> csvGenericService = new ReadCsvGenericService<>(factory, filePath);
        User user = null;
        while((user=  csvGenericService.getNextObject())!=null )
        {
            log.info(user.toString());
        }

    }
}
