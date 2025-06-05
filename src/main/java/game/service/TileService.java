package game.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import game.model.TileJson;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

@Service
public class TileService {

    private final List<TileJson> convertedJsonTiles;
    private final Random rnd = new Random();

    public TileService() throws IOException {
        ObjectMapper om = new ObjectMapper();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("tiles.json")) {
            if (is == null) throw new IOException("tiles.json not found!");

            JsonNode root = om.readTree(is);
            JsonNode tilesNode = root.get("tiles");

            if (tilesNode == null || !tilesNode.isArray() || tilesNode.isEmpty()) {
                throw new IllegalStateException("No tiles in tiles.json");
            }

            convertedJsonTiles = om.readerForListOf(TileJson.class).readValue(tilesNode);
        }
    }

    public TileJson addStarter() {
        return new TileJson("starter", "(*)");
    }

    public TileJson rand() {
        return convertedJsonTiles.get(rnd.nextInt(convertedJsonTiles.size()));
    }
}
