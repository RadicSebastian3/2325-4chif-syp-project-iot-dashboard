package at.htl.readJson;

import at.htl.entity.Unit;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;

@ApplicationScoped
public class readDataFromJson {
    public static String mapJsonFileToEntity(String filePath) {
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            Long id = jsonNode.path("Device").path("Id").asLong();
            String name = jsonNode.path("Device").path("Name").asText();

            Unit unit = new Unit();
            unit.setId(BigInteger.valueOf(id));
            unit.setName(name);

            System.out.println(unit.getName());
            System.out.println(unit.getId());
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
