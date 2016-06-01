
public class Position {

	private Integer left;
	private Integer right;
	private Integer top;
	private Integer bottom;
	private String character;
	
	public Position (Integer left, Integer right, Integer top, Integer bottom, String character){
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
		this.character = character;
	}

	public Integer getLeft() {
		return left;
	}

	public void setLeft(Integer left) {
		this.left = left;
	}

	public Integer getRight() {
		return right;
	}

	public void setRight(Integer right) {
		this.right = right;
	}

	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}

	public Integer getBottom() {
		return bottom;
	}

	public void setBottom(Integer bottom) {
		this.bottom = bottom;
	}
	
	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public String toString(){
		return this.left + " " + this.right;
	}
	
	
}
