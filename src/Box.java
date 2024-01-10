
public class Box {
	private boolean bomb;
	private int count;
	private boolean reversal;
	private boolean marker;
	
	public Box() {
		this.count = 0;
		this.reversal = false;
		this.bomb = false;
		this.marker = false;
	}

	public boolean getBomb() {
		return bomb;
	}

	public void setBomb(boolean bomb) {
		this.bomb = bomb;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean getReversal() {
		return reversal;
	}

	public void setReversal(boolean reversal) {
		this.reversal = reversal;
	}

	public boolean getMarker() {
		return marker;
	}

	public void setMarker(boolean marker) {
		this.marker = marker;
	}
	
}
