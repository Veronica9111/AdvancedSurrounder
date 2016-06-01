import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextReader {
private String path = null;
	
	public TextReader(String path){
		this.path = path;
	}
	
	public List<String> readTextFile() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(this.path));
		List<String> characters = new ArrayList<>();
		String content = "";
		try {

		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		       // lines.add(line);
		        content = line;
		        break;



		    }

		} finally {
		    br.close();
		}
		for(int i = 0; i < content.length(); i++){
			characters.add(content.substring(i, i+1));
		}

		return characters;
	}
	
}
