package saransh13.me.service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Service;
import saransh13.me.model.User;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReadCsvService {

    public List<User> getFileArray(List<String> columns, char colSeparator, String filePath) throws FileNotFoundException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema.Builder schemaBuilder = CsvSchema.builder().setColumnSeparator(colSeparator);
        for(String column: columns)
            schemaBuilder.addColumn(column);
        CsvSchema schema = schemaBuilder.build();
        ObjectReader oReader = csvMapper.readerFor(User.class).with(schema);
        List<User> users = new ArrayList<>();
        try (Reader reader = new FileReader(filePath)) {
            MappingIterator<User> mi = oReader.readValues(reader);
            while (mi.hasNext()) {
                User current = mi.next();
                users.add(current);
                System.out.println(current);
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("The file was not present in the mentioned path [%s]");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

}