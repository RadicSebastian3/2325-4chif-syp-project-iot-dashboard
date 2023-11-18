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

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@ApplicationScoped
public class readDataFromJson {
    public static String mapJsonFileToEntity(String filePath) {
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));

            ObjectMapper objectMapper = new ObjectMapper();
            Unit unit = objectMapper.readValue(jsonString, Unit.class);

            System.out.println("Unit: " + unit.getClass().getName());

            return jsonString;
        } catch (Exception e) {
            e.printStackTrace();
            return "Fehler beim Lesen der Datei: " + e.getMessage();
        }
    }

    public static void main(String[] args) {
        String filePath = "../../data/ftp-data/7-10979582-20221205194710.json";
        String jsonString = mapJsonFileToEntity(filePath);
        System.out.println(jsonString);
    }
}
