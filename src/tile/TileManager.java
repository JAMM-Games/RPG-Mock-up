package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][][] mapTileNum;

    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[16];
        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];


        getTileImage();
        loadMap("/maps/clean_worldV2.txt", 0);
        loadMap("/maps/clean_village-Map.txt", 1);

    }

    public void getTileImage(){

        //            tile[0] = new Tile();
//            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/earths/flowers.png")));
        setup(0, "earths/flowers", false); // grass tile
        setup(1, "earths/grass1", false); // grass tile
        setup(2, "water1", true); // grass tile
        setup(3, "stones/stone-top", true); // grass tile
        setup(4, "earths/tree", true); // grass tile
        setup(5, "earths/path", false); // grass tile
        setup(6, "stones/left-corner-stone", true); // grass tile
        setup(7, "stones/stone-left-corner", true); // grass tile
        setup(8, "stones/right-corner-stone", true); // grass tile
        setup(9, "stones/stone-right-corner", true); // grass tile
        setup(10, "stones/stone-back", true); // grass tile
        setup(11, "stones/left-stone-door-way", true); // grass tile
        setup(12, "stones/right-stone-door-way", true); // grass tile
        setup(13, "earths/mountain", true); // grass tile
        setup(14, "/village", false); // village tile
        setup(15, "/blue-carpet", false); // carpet tile
//            tile[1] = new Tile();
//            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/earths/grass1.png")));
//            tile[2] = new Tile();
//            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water1.png")));
//            tile[2].collision = true;
//            tile[3] = new Tile();
//            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/stones/stone-top.png")));
//            tile[3].collision = true;
//            tile[4] = new Tile();
//            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/earths/tree.png")));
//            tile[4].collision = true;
//            tile[5] = new Tile();
//            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/earths/path.png")));
//            tile[6] = new Tile();
//            tile[6].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/stones/left-corner-stone.png")));
//            tile[6].collision = true;
//            tile[7] = new Tile();
//            tile[7].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/stones/stone-left-corner.png")));
//            tile[7].collision = true;
//            tile[8] = new Tile();
//            tile[8].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/stones/right-corner-stone.png")));
//            tile[8].collision = true;
//            tile[9] = new Tile();
//            tile[9].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/stones/stone-right-corner.png")));
//            tile[9].collision = true;
//            tile[10] = new Tile();
//            tile[10].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/stones/stone-back.png")));
//            tile[10].collision = true;
//            tile[11] = new Tile();
//            tile[11].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/stones/left-stone-door-way.png")));
//            tile[11].collision = true;
//            tile[12] = new Tile();
//            tile[12].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/stones/right-stone-door-way.png")));
//            tile[12].collision = true;
//            tile[13] = new Tile();
//            tile[13].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/earths/mountain.png")));
//            tile[13].collision = true;
//            tile[14] = new Tile();
//            tile[14].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/village.png")));

    }

    public void setup(int index , String imagePath, boolean collision) {

        UtilityTool uTool = new UtilityTool();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + imagePath + ".png")));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void loadMap(String filePath, int map){
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while(col < gp.maxWorldCol) {
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error loading map file");
        }
    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while ((worldCol < gp.maxWorldCol) && (worldRow < gp.maxWorldRow)) {

            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

            int worldX = worldCol *  gp.tileSize;
            int worldY = worldRow *  gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            //STOP MOVING CAMERA AT EDGE OF MAP
            if(gp.player.screenX > gp.player.worldX) {
                screenX = worldX;
            }
            if(gp.player.screenY > gp.player.worldY) {
                screenY = worldY;
            }
            int rightLimit = gp.screenWidth - gp.player.screenX;
            if(rightLimit > gp.worldWidth - gp.player.worldX) {
                screenX = gp.screenWidth - (gp.worldWidth - worldX);
            }
            int bottomLimit = gp.screenHeight - gp.player.screenY;
            if(bottomLimit > gp.worldHeight - gp.player.worldY){
                screenY = gp.screenHeight - (gp.worldHeight - worldY);
            }
            // Simplified visibility check - only draw tiles visible on screen
            if(screenX + gp.tileSize > 0 && screenX < gp.screenWidth &&
                    screenY + gp.tileSize > 0 && screenY < gp.screenHeight) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }
//
//            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
//               worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
//               worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
//               worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
//
//                // Only draw the tile if it is within the player's view
//                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
//            }
//            else if (gp.player.screenX > gp.player.worldX ||
//                       gp.player.screenY > gp.player.worldY ||
//                       rightLimit > gp.worldWidth - gp.player.worldX ||
//                       bottomLimit > gp.worldHeight - gp.player.worldY) {
//                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
//            }

            worldCol++;

            if(worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
