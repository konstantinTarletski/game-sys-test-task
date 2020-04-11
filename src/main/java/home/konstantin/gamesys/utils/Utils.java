package home.konstantin.gamesys.utils;

import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Utils {

    public static LocalDateTime javaDateToLocalDateTime(Date dateToConvert) {
        return dateToConvert == null ? null : dateToConvert.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
    }

    public static String resourceAsString(Resource resource) throws IOException {
        if(resource == null || !resource.exists()){
            return null;
        }
        var reader = new InputStreamReader(resource.getInputStream());
        return FileCopyUtils.copyToString(reader);
    }

}
