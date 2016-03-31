package snake.eng;

/**
 * Special types of food for the snake
 *
 */
public class PowerUps {
String type; // tipo de power up (andar mais depressa (S) , mais devagar (L) , matar (K) , ou comida (F)
int posX,posY;

PowerUps(String type){
	this.type = type;
}

public String getType() {
	
	return type;
}

public int getY() {
	return posY;
	
}
public void setY(int y){
	posY = y;
}

public int getX() {
	
	return posX;
}
public void setX(int X){
	posX = X;
}
}
