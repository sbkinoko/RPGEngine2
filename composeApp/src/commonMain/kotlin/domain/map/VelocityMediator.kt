package domain.map

class VelocityMediator {

    companion object {

        /**
         * 移動可能領域とプレイヤーの位置を比較して、どっちをどう動かすかを調整するメソッド
         */
        fun mediateVelocity(
            player: Player,
            playerMoveArea: Square,
        ): Pair<Velocity, Velocity> {
            // 背景の移動速度
            val vbx: Float
            val vby :Float

            // プレイヤーの移動速度
            val vpx: Float
            val vpy:Float

            if ((player.square.isLeft(playerMoveArea) &&
                        player.velocity.x < 0) ||
                (player.square.isRight(playerMoveArea) &&
                        0 < player.velocity.x)
            ) {
                vbx = -(player.velocity.x)
                vpx = 0f
            } else {
                vpx = player.velocity.x
                vbx = 0f
            }

            if ((player.square.isUp(playerMoveArea) &&
                        player.velocity.y < 0) ||
                (player.square.isDown(playerMoveArea) &&
                        0 < player.velocity.y)
            ) {
                vby = -(player.velocity.y)
                vpy = 0f
            }else{
                vby = 0f
                vpy = player.velocity.y
            }

            val backGroundVelocity = Velocity(
                dx = vbx,
                dy = vby,
            )
            val playerVelocity = Velocity(
                dx = vpx,
                dy = vpy,
            )
            return Pair(playerVelocity, backGroundVelocity)
        }
    }
}
