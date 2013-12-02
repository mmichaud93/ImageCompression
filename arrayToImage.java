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
		
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for(int y = 0; y < height; y++){
        	for(int x = 0; x < width; x++) {
        		bufferedImage.setRGB(x, y, rgbValue[y][x]);
        	}
        }
        return bufferedImage;
	}
	public static BufferedImage convertRGBImageWithHeader(int[][] rgbValue, String header) {
		int headerHeight = 13;
		int height = rgbValue.length+headerHeight;
		int width = rgbValue[0].length;
		
		 BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	        for(int y=headerHeight; y< height; y++){
	            for(int x=0; x< width; x++){
	                bufferedImage.setRGB(x,y,rgbValue[y-headerHeight][x]);  
	            }
	        }
	        //Draw the text (header)
	        Graphics2D graphic=bufferedImage.createGraphics();
	        graphic.setFont(new Font("Monospaced", Font.BOLD, 14) );
	        graphic.setColor(Color.white);
	        graphic.drawString(header,0,10);

	        return bufferedImage;  
	    }
public static void main(String[] args) {
	//test implementation (generate arrays)
	
	int width=255;
	int height=255;
	int pixel[][];
	pixel=new int[height][width];
	
	int red = 0;
	int green = 0;
	int blue = 0;
	for(int y = 0; y < height; y++) {
		for(int x = 0; x < width; x++) {
		red++;
		if(red>255) {
			red=0;
		}
		int color = (255 << 24) | (red << 16) | (green << 8) | blue;
		pixel[y][x] = color;
		//End test code
		BufferedImage image = arrayToImage.convertRGBImageWithHeader(pixel, "This is a test");
		try {
			arrayToImage.exportImageToFile("test.jpg",image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}
	}
	}
