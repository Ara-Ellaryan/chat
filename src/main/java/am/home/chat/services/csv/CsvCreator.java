package am.home.chat.services.csv;

import java.util.List;

public interface CsvCreator {
    char[] createCsv(List<?> data) throws Exception;
}
