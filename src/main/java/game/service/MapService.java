package game.service;

import game.model.Tile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.*;
import java.util.List;

@Service
public class MapService {

    public final Map<Point, Tile> tiles = new HashMap<>();

    private Tile startTile;

    @Autowired
    private TileService pool;

    private static final List<Point> DIRS = List.of(
            new Point(0, 1),
            new Point(1, 0),
            new Point(0, -1),
            new Point(-1, 0)
    );

    public boolean add(Tile t) {
        return tiles.putIfAbsent(new Point(t.x(), t.y()), t) == null;
    }

    public Tile generateTileAt(int x, int y) {
        return tiles.putIfAbsent(new Point(x, y), new Tile(x, y, pool.rand()));
    }

    public List<Tile> getAdjacentTiles(Tile tile) {
        List<Tile> neighbors = new ArrayList<>();
        int x = tile.x();
        int y = tile.y();

        addIfPresent(neighbors, x + 1, y); // вправо
        addIfPresent(neighbors, x - 1, y); // влево
        addIfPresent(neighbors, x, y + 1); // вниз
        addIfPresent(neighbors, x, y - 1); // вверх

        return neighbors;
    }

    public void generateSurroundingTiles(Tile tile) {
        int x = tile.x();
        int y = tile.y();

        tryGenerateTile(x + 1, y);
        tryGenerateTile(x - 1, y);
        tryGenerateTile(x, y + 1);
        tryGenerateTile(x, y - 1);
    }

    private void tryGenerateTile(int x, int y) {
        if (getTileAt(x, y).isEmpty()) {
            generateTileAt(x, y);
        }
    }

    private void addIfPresent(List<Tile> neighbors, int x, int y) {
        getTileAt(x,y).ifPresent(neighbors::add);
    }

    private Optional<Tile> getTileAt(int x, int y) {
        return Optional.ofNullable(tiles.get(new Point(x,y)));
    }

    public void print() {
        if (tiles.isEmpty()) return;

        int minX = tiles.values().stream().mapToInt(Tile::x).min().getAsInt();
        int maxX = tiles.values().stream().mapToInt(Tile::x).max().getAsInt();
        int minY = tiles.values().stream().mapToInt(Tile::y).min().getAsInt();
        int maxY = tiles.values().stream().mapToInt(Tile::y).max().getAsInt();

        for (int y = maxY; y >= minY; y--) {
            for (int x = minX; x <= maxX; x++) {
                Tile activeTile = tiles.get(new Point(x, y));
                System.out.print("[" + (activeTile == null ? "   " : activeTile.type().symbol()) + "]");
            }
            System.out.println();
        }
    }

    public Tile getStartTile() {
        return startTile;
    }

    public void setStartTile(Tile tile) {
        this.startTile = tile;
    }
}
