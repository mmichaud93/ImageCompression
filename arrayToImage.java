import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;



public class arrayToImage {
	public static void exportImageToFile(String fileName,RenderedImage image) throws IOException{
        File file = new File(fileName);
        ImageIO.write(image, "jpg", file);
	}

	public static BufferedImage convertRGBImage(int[][] rgbValue) {
		int height = rgbValue.length;
		int width = rgbValue[0].length;
		int r;
		int g;
		int b;
		int col;
		 BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	        for(int y=0; y< height; y++){
	            for(int x=0; x< width; x++){
	            	int val = rgbValue[y][x];
	            	if(val < 0)
	            		val = 0;
	            	r = val;
	            	g = val;
	            	b = val;
	            	col = (r << 16 ) | ( g << 8) | b;
	            	
	                bufferedImage.setRGB(x,y,col);  
	            }
	        }

	        return bufferedImage;  
	    }
	public static void arrtoimage(int[][] image, String title) {
		BufferedImage image1 = arrayToImage.convertRGBImage(image);
		try {
			arrayToImage.exportImageToFile(title,image1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
