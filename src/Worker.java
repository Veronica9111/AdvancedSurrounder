import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Worker {

	private String path = null;
	
	public Worker(String path){
		this.path = path;
	}
	
	
	public Integer getImageWidth() throws IOException{
		BufferedImage image = ImageIO.read(new File(this.path));
		return image.getWidth();
	}
	
	public Integer getImageHeight() throws IOException{
		BufferedImage image = ImageIO.read(new File(this.path));
		return image.getHeight();
	}
	
	public List<Position> calculateBox() throws IOException{
		BufferedImage image = ImageIO.read(new File(this.path) );
		
		int[][] colors = generateColorMatrix(image, image.getWidth(), image.getHeight());
		List<Position> boxes = new ArrayList<>();
		List<Position> tmpBoxes = new ArrayList<>();
		List<Position> tmp2Boxes = new ArrayList<>();
		List<Integer>leftPoints = new ArrayList<>();
		List<Integer>rightPoints = new ArrayList<>();

		Integer tmp = -1;

		for(int i = 0; i < colors.length; i++){
			Integer whiteCount = 0;
			for(int j = 0; j < colors[i].length; j++){
				if(colors[i][j] == 1){
					whiteCount += 1;
				}
			}
			if(whiteCount == colors[i].length){
				if(i == tmp + 1){
					tmp = i;

				}else{
						if(tmp == -1){
							tmp = 0;
						}
						leftPoints.add(tmp);
						rightPoints.add(i);

					tmp = i;
				}
				
			}
		}
		
		List<Integer>topPoints = new ArrayList<>();
		List<Integer>bottomPoints = new ArrayList<>();
		for(int n = 0; n < leftPoints.size(); n++){
			Integer top = image.getHeight();
			Integer bottom = 0;
			for(int i = leftPoints.get(n); i < rightPoints.get(n); i++){
				for(int j = 0; j < image.getHeight(); j++){
					if (colors[i][j] == 0){
						if(j <= top){
							top = j;
						}
						if(j >= bottom){
							bottom = j;
						}
					}
				}
			}
			topPoints.add(top);
			bottomPoints.add(bottom);
		}

		
		for(int i = 0; i < leftPoints.size(); i++){
			Integer left = leftPoints.get(i);
			Integer right = rightPoints.get(i);
			Integer top = topPoints.get(i);
			Integer bottom = bottomPoints.get(i);
			Position position = new Position(left, right, top, bottom, "");
			tmpBoxes.add(position);
			String str= "字 " + left.toString() + " " + String.valueOf((image.getHeight() - bottom )) + " " + right.toString() + " " + String.valueOf((image.getHeight() - top)) + " 0";
			System.out.println(str);
		}
		
		//The minumun gap in one character	
		Integer magicNumber = 0 ;
		
		for (int i  = 0; i < tmpBoxes.size()-1; i++){
			if(tmpBoxes.get(i+1).getLeft() - tmpBoxes.get(i).getRight() <= magicNumber){
				Position newBox = mergeBox(tmpBoxes.get(i), tmpBoxes.get(i+1));
				tmp2Boxes.add(newBox);
				i += 1; 
			}
			else{
				tmp2Boxes.add(tmpBoxes.get(i));
			}
		}
		tmp2Boxes.add(tmpBoxes.get(tmpBoxes.size()-1));


		for (int i  = 0; i < tmp2Boxes.size()-1; i++){
			if(tmp2Boxes.get(i+1).getLeft() - tmp2Boxes.get(i).getRight() <= magicNumber){
				Position newBox = mergeBox(tmp2Boxes.get(i), tmp2Boxes.get(i+1));
				boxes.add(newBox);
				i += 1;
			}
			else{
				boxes.add(tmp2Boxes.get(i));
			}
		}
		boxes.add(tmp2Boxes.get(tmp2Boxes.size()-1));		
		

		
		for(Position box:boxes){
			Integer left = box.getLeft();
			Integer right = box.getRight();
			Integer top = box.getTop();
			Integer bottom =box.getBottom();

			String str= "字 " + left.toString() + " " + String.valueOf((image.getHeight() - bottom )) + " " + right.toString() + " " + String.valueOf((image.getHeight() - top)) + " 0";
			System.out.println(str);
		}
		
		return boxes;
	}
	
	public Position mergeBox(Position box1, Position box2){
		Integer left = Math.min(box1.getLeft(), box2.getLeft());
		Integer right = Math.max(box1.getRight(), box2.getRight());
		Integer top = Math.min(box1.getTop(), box2.getTop());
		Integer bottom = Math.max(box1.getBottom(), box2.getBottom());
		Position box = new Position(left, right, top, bottom, "");
		return box;
	}
	
	
	public int[][] generateColorMatrix(BufferedImage bfImage, int width, int height){
		int[][] colors = new int[width][height];

		for(int i = 0; i < colors.length; i++){

			for(int j = 0; j < colors[i].length; j++){

				if(bfImage.getRGB(i, j) == 0xff000000){
					colors[i][j] = 0;
				}else{
					colors[i][j] = 1;
				}

			}
		}

		return colors;
	}

	
	
}
