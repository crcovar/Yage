function gravity(player) {
	player.setVelocityY(player.getVelocityY()+1);
}

function jump(player) {
	if(player.getVelocityY() > -player.MAX_VELOCITY && player.getJumpTimer() > 0) { 
		player.setJumpTimer(player.getJumpTimer()-1);
		player.setVelocityY(player.getVelocityY()-player.MAX_VELOCITY/4);
	}
}

function moveLeft(player) {
	player.setVelocityX(player.getVelocityX()-1);
}

function moveRight(player) {
	player.setVelocityX(player.getVelocityX()+1);
}

function moveDown(player) {
	gravity(player);
}