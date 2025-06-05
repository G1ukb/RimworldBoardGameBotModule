package game.model;

public record Tile(
        //расположение в мапе
        int x,
        int y,

        //пока что только имя
        TileJson type
) {
}
