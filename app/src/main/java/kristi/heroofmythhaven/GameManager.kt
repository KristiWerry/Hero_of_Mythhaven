package kristi.heroofmythhaven

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PointF
import android.util.Log
import android.widget.Toast

// These constants are used as a standard by all game objects for physics operations
const val TIME = 0.25f
const val VX = 18f
const val VY = 18f
const val GRAVITY = 7f

class GameManager{
    private lateinit var gameObjects: MutableList<GameObject>
    private lateinit var monsterObjects: MutableList<Monster>
    private val context: LevelActivity
    private var loaded = false
    private var middlePoint = PointF()
    private lateinit var player: Player
    private lateinit var background: Background
    private lateinit var chest: Chest
    private var playerFrames = ArrayList<Bitmap>(8)
    private var ground: Float = 0f
    private var isJumping = true // Boolean representing if the player is jumping
    var rightUserInput: ActionUserInput = ActionUserInput.NOTHING
    var leftUserInput: MovementUserInput = MovementUserInput.NOINPUT
    private lateinit var gameView: GameView
    private var youWin = false

    constructor(level: Int, context: LevelActivity) {
        this.context = context
    }

    // Based on what level is inputted, go to a JSON file and grab the necessary information
    fun loadGameObject(){
            if (!loaded) {
                playerFrames.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.resources,
                    R.drawable.death_knight), 150, 150, false))
                playerFrames.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.resources,
                    R.drawable.death_knight2), 150, 150, false))
                playerFrames.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.resources,
                    R.drawable.death_knight3), 150, 150, false))
                playerFrames.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.resources,
                    R.drawable.death_knight4), 150, 150, false))
                playerFrames.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.resources,
                    R.drawable.death_knight_l), 150, 150, false))
                playerFrames.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.resources,
                    R.drawable.death_knight2_l), 150, 150, false))
                playerFrames.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.resources,
                    R.drawable.death_knight3_l), 150, 150, false))
                playerFrames.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.resources,
                    R.drawable.death_knight4_l), 150, 150, false))
                gameView = context.findViewById(R.id.gameView)

                middlePoint.x = gameView.width * 0.5f - (playerFrames[0].width/2)
                middlePoint.y = gameView.height * 0.59f
                ground = gameView.height.toFloat() * 0.79f
                player = Player(playerFrames, PointF(middlePoint.x - 500,middlePoint.y - 200), 5, 1)

                val floor = Floor(listOf(), PointF(gameView.width.toFloat(), gameView.height.toFloat()))

                loaded = true
                val loadedLevel = LevelLoader(context, 1)
                gameObjects = loadedLevel.getGameObjs()
                monsterObjects = loadedLevel.monsterObjs
                gameObjects.add(player)
                gameObjects.add(floor)
                background = loadedLevel.getBackground()
                chest = loadedLevel.getChest()
            }
    }

    fun update() {
        var movementContext = false
        var bottomCollision = false
        var isAttacking = false

        if (leftUserInput == MovementUserInput.LEFT && rightUserInput == ActionUserInput.ATTACK) {
            player.facingRight = false
            if (!isJumping) {
                player.velocityX = 0f
                isAttacking = true
                player.isAttacking = true
                player.isWalking = false
                player.isJumping = false
            }
        }
        else if (leftUserInput == MovementUserInput.RIGHT && rightUserInput == ActionUserInput.ATTACK) {
            player.facingRight = true
            if (!isJumping) {
                player.velocityX = 0f
                isAttacking = true
                player.isAttacking = true
                player.isWalking = false
                player.isJumping = false
            }
        }
        else if (leftUserInput == MovementUserInput.NOINPUT && rightUserInput == ActionUserInput.ATTACK) {
            if (!isJumping) {
                player.velocityX = 0f
                isAttacking = true
                player.isAttacking = true
                player.isWalking = false
                player.isJumping = false
            }
        }
        else {
            isAttacking = false
            player.isAttacking = false
        }

        if (!isAttacking) {
            if (leftUserInput == MovementUserInput.RIGHT && rightUserInput == ActionUserInput.JUMP) {
                //Log.i("HOM", "RIGHT and JUMP")
                if (!isJumping) {
                    player.location.y -= 1
                    player.gravity = GRAVITY
                    player.velocityY = VY
                    player.velocityX = VX
                    player.time = TIME
                    player.resetTime()
                    isJumping = true
                }
                player.facingRight = true
                player.isJumping = true
                player.isWalking = false
            } else if (leftUserInput == MovementUserInput.LEFT && rightUserInput == ActionUserInput.JUMP) {
                //Log.i("HOM", "LEFT and JUMP")
                if (!isJumping) {
                    player.location.y -= 1
                    player.gravity = GRAVITY
                    player.velocityY = VY
                    //if (player.location.x >= 0) {
                    player.velocityX = -VX
                    //}
                    if (player.location.x < 0) {
                        player.velocityX = 0f
                        player.location.x = 0f
                    }
                    player.time = TIME
                    player.resetTime()
                    isJumping = true
                }
                if (player.location.x < 0) {
                    player.velocityX = 0f
                    player.location.x = 0f
                }
                player.facingRight = false
                player.isJumping = true
                player.isWalking = false
            }
            else if (leftUserInput == MovementUserInput.NOINPUT && rightUserInput == ActionUserInput.NOTHING) {
//            Log.i("HOM", "NOINPUT and NOTHING")
                player.velocityX = 0f
                if (player.location.x < 0) {
                    player.location.x = 0f
                }

                if (!isJumping) {
                    player.velocityY = 0f
                    player.gravity = 0f
                }
                player.isJumping = false
                player.isWalking = false
            } else if (leftUserInput == MovementUserInput.NOINPUT && rightUserInput == ActionUserInput.JUMP) {
                //Log.i("HOM", "NOINPUT and JUMP")
                if (!isJumping) {
                    player.location.y -= 1
                    player.gravity = GRAVITY
                    player.velocityY = VY
                    player.time = TIME
                    player.resetTime()
                    isJumping = true
                }
                player.isJumping = true
                player.isWalking = false
            } else if (leftUserInput == MovementUserInput.LEFT && rightUserInput == ActionUserInput.NOTHING) {
                //Log.i("HOM", "LEFT and NOTHING")
                player.velocityX = -VX
                if (player.location.x < 0) {
                    player.velocityX = 0f
                    player.location.x = 0f
                }
                if (!isJumping) {
                    player.velocityY = 0f
                    player.gravity = 0f
                }
                player.facingRight = false
                player.isJumping = false
                player.isWalking = true
            } else if (leftUserInput == MovementUserInput.RIGHT && rightUserInput == ActionUserInput.NOTHING) {
                //Log.i("HOM", "RIGHT and NOTHING")
                player.velocityX = VX
                if (!isJumping) {
                    player.velocityY = 0f
                    player.gravity = 0f
                }
                player.facingRight = true
                player.isJumping = false
                player.isWalking = true
            }
        }

        // Collision has the side effect of possibly altering the player's internal physics parameters if a collision occurs
        for (gameObj in gameObjects) { //check the objects colliding with the player
            if (gameObj != player) {
                when (gameObj.collision(player)) {
                    Direction.NONE -> {} // Do nothing
                    Direction.TOP -> {}
                    Direction.BOTTOM -> {
//                    Log.i("HOM", "COLLISION BOTTOM")
                        isJumping = false
                        player.resetTime()
                        bottomCollision = true
                        if (gameObj == chest && !youWin) {
                            youWin()
                            youWin = true
                        }
                    }
                    Direction.LEFT -> {
                        if (gameObj == chest && !youWin) {
                            youWin()
                            youWin = true
                        }
                    }
                    Direction.RIGHT -> {
                        if (gameObj == background) {
                            movementContext = true
                        }
                        if (gameObj == chest && !youWin) {
                            youWin()
                            youWin = true
                        }
                    }
                }
            }
        }

        for (monster in monsterObjects) {
            for (gameObj in gameObjects) {
                if (gameObj != background && gameObj != player) {
                    when (gameObj.collision(monster)) { //check if the monsters collide with the game objects
                        Direction.NONE -> {
                        } // Do nothing
                        Direction.TOP -> {
                        }
                        Direction.BOTTOM -> {

                        }
                        Direction.LEFT -> {
                            monster.goingLeft = false
                            monster.velocityX = VX
                        }
                        Direction.RIGHT -> {
                            monster.goingLeft = true
                            monster.velocityX = -VX
                        }
                    }
                }
            }
            if (!isAttacking) {
                when (monster.collision(player)) { //check if the monster is hitting the player
                    Direction.NONE -> {
                    } // Do nothing
                    Direction.TOP -> {
                        if (monster.dealDamage(player) <= 0) {
                            youLose()
                        }
                    }
                    Direction.BOTTOM -> {
                        if (monster.dealDamage(player) <= 0) {
                            youLose()
                        }
                    }
                    Direction.LEFT -> {
                        if (monster.dealDamage(player) <= 0) {
                            youLose()
                        }
                    }
                    Direction.RIGHT -> {
                        if (monster.dealDamage(player) <= 0) {
                            youLose()
                        }
                    }
                }
            }
        }

        if (isAttacking) {
            for (monster in monsterObjects) {
                when(player.collision(monster)) { //check if the player is hitting a monster
                    Direction.NONE -> {} // Do nothing
                    Direction.TOP -> {
                        if (player.dealDamage(monster) <= 0) {
                            monsterObjects.remove(monster)
                        }
                    }
                    Direction.BOTTOM -> {
                        if (player.dealDamage(monster) <= 0) {
                            monsterObjects.remove(monster)
                        }
                    }
                    Direction.LEFT -> {
                        if (player.dealDamage(monster) <= 0) {
                            monsterObjects.remove(monster)
                        }
                    }
                    Direction.RIGHT -> {
                        if (player.dealDamage(monster) <= 0) {
                            monsterObjects.remove(monster)
                        }
                    }
                }
            }
        }

        // Addresses a bug where the player walks off an obstacle but does not fall
        if (!bottomCollision) {
            player.gravity = GRAVITY
        }

        for (gameObj in gameObjects) { //update all the game objects, background
            gameObj.update(movementContext) //we also update player here but it doesn't matter because of movement context
        }

        for (monster in monsterObjects) { //seperate update for all the monsters
            monster.update(movementContext)
        }
        player.animate(gameView)
        // The movement of the player and the other objects of the game are inversely related
        player.update(!movementContext) // This should eventually become the specific context for characters

    }

    fun youWin() {
        context.showEndPopup(true)
    }

    fun youLose() {
        context.showEndPopup(false)
    }

    fun getGameObjects(): List<GameObject> {
        return gameObjects
    }

    fun getMonsterObjects(): List<Monster> {
        return monsterObjects
    }
}