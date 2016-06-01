import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BoxReader {

	private String path = null;
	private List<String> lines = null;
	
	public BoxReader(String path) throws IOException{
		this.path = path;
		this.readBoxFile();
	}
	
	public void readBoxFile() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(this.path));
		this.lines = new ArrayList<>();
		try {

		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        this.lines.add(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();

		    }
		   // String everything = sb.toString();
		   // System.out.println(lines);
		} finally {
		    br.close();
		}

		//return lines;
	}
	
	public List<String> getLines(){
		return this.lines;
	}
	
	public List<Integer> calculateCentralPoints() throws IOException{
		List<Integer> points = new ArrayList<>();
		List<String> lines = this.lines;
		for(String line : lines){
			if(line == null){
				continue;
			}
			String[] elements = line.split(" ");
			Integer left = Integer.parseInt(elements[1]);
			Integer right = Integer.parseInt(elements[3]);
			Integer mid = (left + right) / 2;
			points.add(mid);
		}
		return points;
	}
	
}
