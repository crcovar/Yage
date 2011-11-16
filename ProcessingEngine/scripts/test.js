function gravity(player) {
	player.setVelocityY(player.getVelocityY()+1);
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