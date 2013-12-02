import java.util.Random;


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
		
		Random random = new Random();
		
		// randomly fill array
		for(int i = 0; i < height; i++) {
			for(int r = 0; r < width; r++) {
				image[i][r] = random.nextInt(512)+0;
			}
		}
		savedImage = image;
		arrayToImage ati = new arrayToImage();
		ati.arrtoimage(image, "before compression.jpg");
		CompressRows(width,height);
		CompressCol(width,height);
		ati.arrtoimage(image, "compressed 64.jpg");
		CompressRows(width/2,height/2);
		CompressCol(width/2,height/2);
		ati.arrtoimage(image, "compressed 32.jpg");
		CompressRows(width/4,height/4);
		CompressCol(width/4,height/4);
		ati.arrtoimage(image, "compressed 16.jpg");
		UncompressCol(width/4,height/4);
		UncompressRow(width/4,height/4);
		ati.arrtoimage(image, "uncompressed 16.jpg");
		UncompressCol(width/2,height/2);
		UncompressRow(width/2,height/2);
		ati.arrtoimage(image, "uncompressed 32.jpg");
		UncompressCol(width,height);
		UncompressRow(width,height);
		ati.arrtoimage(image, "uncompressed 64.jpg");
	}
	

	
		public static int[][] multiplicar(int[][] A, float[][] B) {

        int aRows = A.length;
        int aColumns = A[0].length;
        int bRows = B.length;
        int bColumns = B[0].length;

        if (aColumns != bRows) {
            throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
        }

        int[][] C = new int[aRows][bColumns];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                C[i][j] = 0;
            }
        }

        for (int i = 0; i < aRows; i++) { // aRow
            for (int j = 0; j < bColumns; j++) { // bColumn
                for (int k = 0; k < aColumns; k++) { // aColumn
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return C;
    }
	public void CompressRows(int cwidth, int cheight) {
		int[][] new_image = new int[cheight][cwidth];
		
		for(int i = 0; i < cheight; i++) {
			for(int r = 0; r < cwidth; r+=2) {
				int detail = (image[i][r] + image[i][r+1])/2;
				int difference = image[i][r]-detail;
				new_image[i][r/2] = detail;
				new_image[i][cwidth/2+r/2] = difference;
			}
		}
		if(cwidth == width && cheight == height) {
			image = new_image;
			return;
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
		
		for(int r = 0; r < cwidth; r++) {
			for(int i = 0; i < cheight; i+=2) {
				int detail = (image[i][r] + image[i+1][r])/2;
				int difference = image[i][r]-detail;
				new_image[i/2][r] = detail;
				new_image[cheight/2+i/2][r] = difference;
				//System.out.println("("+i+","+r+") r = "+image[i][r]+", r + 1 ="+image[i+1][r]+", detail = "+detail+", difference = " +difference );
			}
		}
		if(cwidth == width && cheight == height) {
			image = new_image;
			return;
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
		
		for(int r = 0; r < cwidth; r++) {
			for(int i = 0; i < cheight; i+=2) {
				
				int detail = image[i/2][r];//(image[i][r] + image[i+1][r])/2;
				int difference = image[cheight/2+i/2][r];
				new_image[i][r] = detail+difference;
				new_image[i+1][r] = detail*2-(detail+difference);
				if(new_image[i][r] < 0)
					new_image[i][r] = 0;
				if(new_image[i+1][r] < 0)
					new_image[i+1][r] = 0;
				//System.out.println("("+i+","+r+") r = "+new_image[i][r]+", r + 1 ="+new_image[i+1][r]+", detail = "+detail+", difference = " +difference );
			}
		}
		if(cwidth == width && cheight == height) {
			image = new_image;
			return;
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
		
		for(int i = 0; i < cheight; i++) {
			for(int r = 0; r < cwidth; r+=2) {
				int detail = image[i][r/2];//(image[i][r] + image[i][r+1])/2;
				int difference = image[i][cwidth/2+r/2];//image[i][r]-detail;
				new_image[i][r] = detail+difference;
				new_image[i][r+1] = detail*2-(detail+difference);
				if(new_image[i][r] < 0)
					new_image[i][r] = 0;
				if(new_image[i][r+1] < 0)
					new_image[i][r+1] = 0;
				//System.out.println("r = "+new_image[i][r]+", r + 1 ="+new_image[i][r+1]+", detail = "+detail+", difference = " +difference );
				
			}
		}
		if(cwidth == width && cheight == height) {
			image = new_image;
			return;
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
		for(int i = 0; i < height; i++) {
			for(int r = 0; r < width; r++) {
				System.out.print(image[i][r]+" ");
			}
			System.out.println();
		}
	}
}
