package snake.eng;

import java.util.Vector;

/**
 * The snake content and all the operations for increase or decrease its size
 *
 */
public class Snake {
int  direction;
Vector<int[]> pieces = new Vector<int[]>();
Vector<int[]> remover = new Vector<int[]>();
public Snake(int xpos,int ypos){
	int pos[] = {ypos,xpos};
		pieces.add(pos);

	}
public Vector<int[]> getPieces(){
	return pieces;
}
public void addPiece(int y, int x) {
	int[] pos = {y , x};
	pieces.add(0, pos);
	
}
public int getDirection() {
	return direction;
}
public void setDirection(int direction) {
	this.direction = direction;
}
public void actualizePiece(int i ,int[] newPos){
	pieces.set(i, newPos) ;
	
	
}
public void cutSnakeHalf() {
	if(pieces.size() > 4){
	Vector<int[]> temp = new Vector<int[]>();
	int newsize = pieces.size()/2;
	for (int i = 0 ; i < pieces.size() ; i++){
		if (i < newsize)
			temp.add(pieces.get(i));
		else
			remover.add(pieces.get(i));
	}
	pieces = temp;
}
}
public Vector<int[]> getRemove() {
	
	return remover;
}
public void cleanRemover() {
	remover = new Vector<int[]>();
	
}
}
