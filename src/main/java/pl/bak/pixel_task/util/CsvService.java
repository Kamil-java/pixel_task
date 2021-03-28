package pl.bak.pixel_task.util;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.List;

public class CsvService<T> {
    private final Class<T> type;

    public CsvService(Class<T> type) {
        this.type = type;
    }

    private CsvToBean<T> readFile(String pathToFile){
        try {
            return new CsvToBeanBuilder<T>(new FileReader(pathToFile))
                    .withType(this.type)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .withSkipLines(1)
                    .build();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException("Cannot find this path to file: " + pathToFile);
        }
    }

    protected List<T> getListOfObjects(String fileName) {
        URL resource = getClass().getResource("/csv/" + fileName);

        return new CsvService<>(this.type)
                .readFile(resource.getPath())
                .parse();
    }
}
