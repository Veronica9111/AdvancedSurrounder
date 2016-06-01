import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AdvancedSurrounder {

public static void main(String[] args) throws IOException{

		//Put the .box, .tif, and .info in one folder
	    //The .info file is the right characters we need
		//It will rewrite the box file
		Files.walk(Paths.get("/Users/veronica/Desktop/new3/")).forEach(filePath -> {
		    if (Files.isRegularFile(filePath)) {

		    	String boxPath = filePath.toString().substring(0, filePath.toString().length()-3) + "box";
		    	String textPath = filePath.toString().substring(0, filePath.toString().length()-3) + "info";
		    	if(filePath.toString().contains(".tif")){
		    	
		    		Worker worker = new Worker(filePath.toString());
		    		try {
						List<Position> boxes = worker.calculateBox();
						BoxReader br = new BoxReader(boxPath);
						List<Integer> points = br.calculateCentralPoints();
						//System.out.println(points);
						int[] hit = new int[boxes.size()];
						for(int i = 0; i < boxes.size(); i++){
							hit[i] = 0;
						}
						Integer count = 0;
						for(Position box: boxes){
							for(Integer point: points){
								if(point > box.getLeft() && point < box.getRight()){
									hit[count] = 1;
								}                                                                                                                                                                                                                                                              
							}
							count += 1;
						}
						
						Boolean valid = true;    
						for(int i = 0; i < hit.length; i++){
							if(hit[i] == 0){

								System.out.println(filePath);
								System.out.println(i);
								valid = false;
							}
						}
						if(valid == true){
							//Wrap the text file into box
							TextReader tr = new TextReader(textPath);
							List<String> characters = tr.readTextFile();
							Integer height = worker.getImageHeight();
							List<String> lines = new ArrayList<>();
							if(boxes.size() == characters.size()){
								int index = 0;
								for(Position box: boxes){
									box.setCharacter(characters.get(index));
									index++;
									String str= box.getCharacter() + " " + box.getLeft().toString() + " " + String.valueOf((height - box.getBottom() )) + " " + box.getRight().toString() + " " + String.valueOf((height - box.getTop())) + " 0";
									System.out.println(str);
									lines.add(str);
									Path file = Paths.get(boxPath);
									Files.write(file, lines, Charset.forName("UTF-8"));
								}
							}else{
								//Compare with the real box file
								List<String> boxLines = br.getLines();
								List<String> newBoxLines = new ArrayList<>();
								if(boxLines.size() == characters.size()){
									for(int i = 0; i < boxLines.size(); i++){
										newBoxLines.add(characters.get(i) + boxLines.get(i).substring(1));
									}
									Path file = Paths.get(boxPath);
									Files.write(file, newBoxLines, Charset.forName("UTF-8"));
								}
								System.out.println("Path: " + textPath + "box size: " + boxLines.size() + " characters size: " + characters.size());
								System.out.println("Path: " + textPath + "box size: " + boxes.size() + " characters size: " + characters.size());
							}
							
							
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println(e);
						e.printStackTrace();
					}
		    	}
		    }
		});
		
	}
	
}
