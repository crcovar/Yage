/**
 * Moves free bubbles up towards the top.
 * @param bubble The <code>Bubble</code> to move
 * @param top Highest screen position held by any <code>Bubble</code>
 */
function inverseGravity(/*Bubble*/ bubble, /*int*/ top) {
	if(bubble.getY() > top && bubble.isFree()) {
		bubble.setVelocityY(bubble.getVelocityY()-1);
	}
}

function holdY(/*Bubble*/ bubble,/*int*/ bound) {
	if(bound < bubble.getBottomBound()) {
		bubble.setY(bound+bubble.getRadius());
	} else {
		bubble.setY(bound-bubble.getRadius());
	}
}