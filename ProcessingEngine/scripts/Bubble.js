function inverseGravity(bubble) {
	bubble.setVelocityY(bubble.getVelocityY()-1);
}

function holdY(bubble, bound) {
	if(bound < bubble.getBottomBound()) {
		bubble.setY(bound+bubble.getRadius());
	} else {
		bubble.setY(bound-bubble.getRadius());
	}
}