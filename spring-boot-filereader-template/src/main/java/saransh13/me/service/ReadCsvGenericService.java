package saransh13.me.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import saransh13.me.util.IFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Log4j2
public class ReadCsvGenericService<T> {

    private BufferedReader br;
    private final IFactory<T> factory;

    public ReadCsvGenericService(IFactory<T> factory,
                          String filePath){
        this.factory = factory;

        try {
            this.br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(filePath),
                            StandardCharsets.UTF_8));
        }
        catch(FileNotFoundException e) {
            log.error("File not found");
        }
    }


    public T getNextObject() throws IOException {
        String line = null;
        if((line = br.readLine())!=null){
             String[] splitLine = line.split("\\|");
             return  this.factory.newObject(splitLine);
        }
        return null;
    }


}
