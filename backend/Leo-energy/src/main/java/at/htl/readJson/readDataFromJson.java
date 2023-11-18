package at.htl.readJson;

import at.htl.entity.Unit;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.ws.rs.Path;

import java.util.Map;

@ApplicationScoped
public class readDataFromJson {
        void startup(@Observes StartupEvent ev) throws JsonProcessingException {
           String filePath = "../data/ftp-data/7-10979582-20221205194710.json";
            ObjectMapper objectMapper = new ObjectMapper();

           System.out.println(objectMapper.readValue(filePath, Unit.class));
        }
}
