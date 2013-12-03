import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;


public class ImageCompression {

	int[][] image;
	int[][] savedImage;
	int width = 512;
	int height =512;
	
	public static void main(String[] args) {
		new ImageCompression();
	}
	
	public ImageCompression() {
		
		image = new int[height][width];
		arrayToImage ati = new arrayToImage();
		
		String filename = "einstein.png";
		try {
			/*
			 * Import the image, must be the same size at the array specified above
			 */
			Image importImage = ImageIO.read(new File(filename));
			BufferedImage img = new BufferedImage(importImage.getWidth(null), importImage.getHeight(null), BufferedImage.TYPE_BYTE_GRAY);
			Graphics g = img.createGraphics();
			g.drawImage(importImage, 0, 0, null);
			g.dispose();
			int w = img.getWidth();
			int h = img.getHeight();
			Raster raster = img.getData();
			/* populate the array with the grayscale data from the picture */
			for(int i = 0; i < h; i++) {
				for(int r = 0; r < w; r++) {
					image[i][r] = raster.getSample(r, i, 0);
				}
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		/* save the image */
		savedImage = image;
		
		
		ati.arrtoimage(image, "before compression.jpg");	//	before compression
		CompressRows(width,height);							//
		CompressCol(width,height);							//
		ati.arrtoimage(image, "compressed by half.jpg");	//	Compressed by half
		CompressRows(width/2,height/2);						//
		CompressCol(width/2,height/2);						//
		ati.arrtoimage(image, "fully compressed.jpg");		//	compressed by half again, fully compressed
		UncompressCol(width/2,height/2);					//
		UncompressRow(width/2,height/2);					//
		ati.arrtoimage(image, "uncompressed by half.jpg");	//	uncompress to get half the imae
		UncompressCol(width,height);						//
		UncompressRow(width,height);						//
		ati.arrtoimage(image, "after compressed.jpg");		//	after compression
	}
	

	public void CompressRows(int cwidth, int cheight) {
		int[][] new_image = new int[cheight][cwidth];
		
		/*
		 * iterate through image, get the average of two pixels and encode them in the new array accordingly
		 */
		
		for(int i = 0; i < cheight; i++) {
			for(int r = 0; r < cwidth; r+=2) {
				int detail = (image[i][r] + image[i][r+1])/2;
				int difference = image[i][r]-detail;
				new_image[i][r/2] = detail;
				new_image[i][cwidth/2+r/2] = difference;
			}
		}
		for(int i = 0; i < height; i++) {
			if (i < new_image.length) {
				for(int r = 0; r < width; r++) {
					if( r < new_image[i].length) {
						image[i][r] = new_image[i][r];
					}
				}
			}
		}
	}
	public void CompressCol(int cwidth, int cheight) {
		int[][] new_image = new int[cheight][cwidth];
		
		/*
		 * iterate through image, get the average of two pixels and encode them in the new array accordingly
		 */
		
		for(int r = 0; r < cwidth; r++) {
			for(int i = 0; i < cheight; i+=2) {
				int detail = (image[i][r] + image[i+1][r])/2;
				int difference = image[i][r]-detail;
				new_image[i/2][r] = detail;
				new_image[cheight/2+i/2][r] = difference;
			}
		}
		for(int i = 0; i < height; i++) {
			if (i < new_image.length) {
				for(int r = 0; r < width; r++) {
					if( r < new_image[i].length) {
						image[i][r] = new_image[i][r];
					}
				}
			}
		}
	}
	
	public void UncompressCol(int cwidth, int cheight) {
		int[][] new_image = new int[cheight][cwidth];
		
		/*
		 * iterate through image, pull the detail and difference from the image and calculate the probable new pixels from it 
		 */
		
		for(int r = 0; r < cwidth; r++) {
			for(int i = 0; i < cheight; i+=2) {
				
				int detail = image[i/2][r];
				int difference = image[cheight/2+i/2][r];
				new_image[i][r] = detail+difference;
				new_image[i+1][r] = detail*2-(detail+difference);
				if(new_image[i][r] < 0)
					new_image[i][r] = 0;
				if(new_image[i+1][r] < 0)
					new_image[i+1][r] = 0;
			}
		}
		for(int i = 0; i < height; i++) {
			if (i < new_image.length) {
				for(int r = 0; r < width; r++) {
					if( r < new_image[i].length) {
						image[i][r] = new_image[i][r];
					}
				}
			}
		}
	}
	public void UncompressRow(int cwidth, int cheight) {
		int[][] new_image = new int[cheight][cwidth];
		
		/*
		 * iterate through image, pull the detail and difference from the image and calculate the probable new pixels from it 
		 */
		
		for(int i = 0; i < cheight; i++) {
			for(int r = 0; r < cwidth; r+=2) {
				int detail = image[i][r/2];
				int difference = image[i][cwidth/2+r/2];
				new_image[i][r] = detail+difference;
				new_image[i][r+1] = detail*2-(detail+difference);
				if(new_image[i][r] < 0)
					new_image[i][r] = 0;
				if(new_image[i][r+1] < 0)
					new_image[i][r+1] = 0;
			}
		}
		for(int i = 0; i < height; i++) {
			if (i < new_image.length) {
				for(int r = 0; r < width; r++) {
					if( r < new_image[i].length) {
						image[i][r] = new_image[i][r];
					}
				}
			}
		}
	}
	
	public void PrintArray(int width, int height) {
		// print the array, duh
		for(int i = 0; i < height; i++) {
			for(int r = 0; r < width; r++) {
				System.out.print(image[i][r]+" ");
			}
			System.out.println();
		}
	}
}
