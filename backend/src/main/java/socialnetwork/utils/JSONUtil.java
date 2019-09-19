package socialnetwork.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class JSONUtil {
    public List getEntityList() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            BufferedReader br = null;
            URL url = getClass().getResource("/types.json");
            InputStream inputStream = url.openStream();
            List<Entity> list = objectMapper.readValue(inputStream, new TypeReference<List<Entity>>(){});
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
