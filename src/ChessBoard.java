
public class ChessBoard {
	
	private int height, width;
	private int defaultBombNum;
	private Position[] bombPosition;
	private Box[][] chessBoardBox;
	
	public ChessBoard() {
		this.height = 20;
		this.width = 20;
		this.defaultBombNum = 20;
	}
	
	public ChessBoard(int h, int w, int d) {
		this.height = h;
		this.width = w;
		this.defaultBombNum = d;
	}
	
	public void CreateBomb(int posX, int posY) {
		
		chessBoardBox = new Box[this.height][this.width];
		for(int i = 0; i < this.height; ++i) {
			for(int j = 0; j < this.width; ++j) {
				chessBoardBox[i][j] = new Box();
			}
		}
		
		// create a array for record bomb position
		bombPosition = new Position[defaultBombNum];
		
		short x, y;
		short bombNum = 0;
		while(bombNum < defaultBombNum){
			x = (short) (Math.random() * this.height);
			y = (short) (Math.random() * this.width);
			if(chessBoardBox[x][y].getBomb() == true || (x == posX && y == posY)){
				continue;
			}else{
				chessBoardBox[x][y].setBomb(true);
				bombPosition[bombNum] = new Position(x, y);
				++bombNum;	
			}
		}
	}
	
	public void countNeighborhoodBombNumberOfBox(){
		for(int i = 0; i < bombPosition.length; ++i){
			int x = bombPosition[i].getX();
			int y = bombPosition[i].getY();
			increaseNeighborhoodBombNumberInBox(x-1, y-1);
			increaseNeighborhoodBombNumberInBox(x-1, y);
			increaseNeighborhoodBombNumberInBox(x-1, y+1);
			increaseNeighborhoodBombNumberInBox(x, y-1);
			increaseNeighborhoodBombNumberInBox(x, y+1);
			increaseNeighborhoodBombNumberInBox(x+1, y-1);
			increaseNeighborhoodBombNumberInBox(x+1, y);
			increaseNeighborhoodBombNumberInBox(x+1, y+1);
		}
	}
	
	private void increaseNeighborhoodBombNumberInBox(int x, int y) {
		if(x >= 0 && x < this.height && y >= 0 && y < this.width){
			chessBoardBox[x][y].setCount(chessBoardBox[x][y].getCount()+1);
		}
	}
	
	public void reversalBox(int x, int y) {
		if(!(x >= 0 && x < this.height && y >= 0 && y < this.width)) {
			return;
		}
		if(chessBoardBox[x][y].getReversal() == true) {
			return;
		}
		if(chessBoardBox[x][y].getCount() != 0){
			chessBoardBox[x][y].setReversal(true);
			return;
		}
		chessBoardBox[x][y].setReversal(true);
		//reversalBox(x-1, y-1);
		reversalBox(x-1, y);
		//reversalBox(x-1, y+1);
		reversalBox(x, y-1);
		reversalBox(x, y+1);
		//reversalBox(x+1, y-1);
		reversalBox(x+1, y);
		//reversalBox(x+1, y+1);
	}
	
	//determine win and loss
	public int determineWinOrLoss(int x, int y) {
		
		for(int i = 0; i < bombPosition.length; ++i) {
			if(bombPosition[i].getX() == x && bombPosition[i].getY() == y) {
				for(int j = 0; j < bombPosition.length; ++j) {
					chessBoardBox[bombPosition[j].getX()][bombPosition[j].getY()].setReversal(true);
				}
				return 1;
			}
		}
		
		short boxNum = 0;
		for(int i = 0; i < this.height; ++i){
			for(int j = 0; j < this.width; ++j){
				if(chessBoardBox[i][j].getReversal() == false)
					++boxNum;
			}
		}
		
		if(boxNum == defaultBombNum){
			return 2;
		}
		return 3;
	}
	
	public class Position{
		private int x, y;
		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
		
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public Box[][] getChessBoardBox() {
		return chessBoardBox;
	}
	
	public int getScore() {
		
		short boxNum = 0;
		for(int i = 0; i < this.height; ++i){
			for(int j = 0; j < this.width; ++j){
				if(chessBoardBox[i][j].getReversal() == true && !chessBoardBox[i][j].getBomb()) // get reversal box and not bomb box
					++boxNum;
			}
		}
		double notBombBox = (this.height * this.width) - this.defaultBombNum;
		double percent = boxNum / notBombBox;
		double score = (percent) * 100;
		return (int)score;
	}

}
