package kristi.heroofmythhaven

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PointF
import android.util.Log

// These constants are used as a standard by all game objects for physics operations
const val TIME = 0.25f
const val VX = 13f
const val VY = 18f
const val GRAVITY = 7f

class GameManager{
    private lateinit var gameObjects: MutableList<GameObject>
    private val context: LevelActivity
    private var loaded = false
    private var middlePoint = PointF()
    private lateinit var player: Player
    private lateinit var background: Background
    private var playerFrames = ArrayList<Bitmap>(4)
    private var ground: Float = 0f
    private var isJumping = true // Boolean representing if the player is jumping
    //var userInput = mutableMapOf(UserInput.NOINPUT to false, UserInput.LEFT to false, UserInput.RIGHT to false, UserInput.JUMP to false, UserInput.ATTACK to false)
    var rightUserInput: ActionUserInput = ActionUserInput.NOTHING
    var leftUserInput: MovementUserInput = MovementUserInput.NOINPUT


    constructor(level: Int, context: LevelActivity) {
        this.context = context
    }

    // Based on what level is inputted, go to a JSON file and grab the necessary information
    fun loadGameObject(){
            if (!loaded) {
                playerFrames.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.resources,
                    R.drawable.death_knight), 200, 200, false))
                playerFrames.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.resources,
                    R.drawable.death_knight2), 200, 200, false))
                playerFrames.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.resources,
                    R.drawable.death_knight3), 200, 200, false))
                playerFrames.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.resources,
                    R.drawable.death_knight4), 200, 200, false))
                val gameView = context.findViewById<GameView>(R.id.gameView)

                middlePoint.x = gameView.width * 0.5f - (playerFrames[0].width/2)
                middlePoint.y = gameView.height * 0.59f
                ground = gameView.height.toFloat() * 0.79f
                player = Player(playerFrames, PointF(middlePoint.x - 500,middlePoint.y - 200))

                val backgroundBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.resources,
                    R.drawable.level1_background), gameView.width+5, gameView.height, false)
                background = Background(listOf<Bitmap>(backgroundBitmap,backgroundBitmap),
                    PointF(gameView.width.toFloat(), gameView.height.toFloat()))

                val floor = Floor(listOf(), PointF(gameView.width.toFloat(), gameView.height.toFloat()))

                val chest = Chest(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.resources,
                    R.drawable.chest), 100, 100, false), PointF(4*gameView.width.toFloat(),
                    gameView.height.toFloat()* 0.69f))

                val landing = Landing(BitmapFactory.decodeResource(context.resources,
                    R.drawable.landing1), PointF(gameView.width.toFloat()/2+500,gameView.height * 0.65f) )

                loaded = true
                gameObjects = mutableListOf(background, player, chest, landing, floor)
            }
    }

    fun update() {
        var context = false
        var bottomCollision = false

        if (leftUserInput == MovementUserInput.RIGHT && rightUserInput == ActionUserInput.JUMP) {
            Log.i("HOM", "RIGHT and JUMP")
            if (!isJumping) {
                player.location.y -= 1
                player.gravity = GRAVITY
                player.velocityY = VY
                player.velocityX = VX
                player.time = TIME
                player.resetTime()
                isJumping = true
            }
        }
        else if (leftUserInput == MovementUserInput.LEFT && rightUserInput == ActionUserInput.JUMP) {
            Log.i("HOM", "LEFT and JUMP")
            if (!isJumping) {
                player.location.y -= 1
                player.gravity = GRAVITY
                player.velocityY = VY
                if (player.location.x >= 0) {
                    player.velocityX = -VX
                }
                else {
                    player.velocityX = 0f
                    player.location.x = 0f
                }
                player.time = TIME
                player.resetTime()
                isJumping = true
            }
        }
//        else if (userInput[UserInput.LEFT] as Boolean && userInput[UserInput.RIGHT] as Boolean) {
//            player.velocityX = 0f
//        }
        else if (leftUserInput == MovementUserInput.NOINPUT && rightUserInput == ActionUserInput.NOTHING){
            Log.i("HOM", "NOINPUT and NOTHING")
            player.velocityX = 0f

            if (!isJumping) {
                player.velocityY = 0f
                player.gravity = 0f
            }
        }
        else if (leftUserInput == MovementUserInput.NOINPUT && rightUserInput == ActionUserInput.JUMP) {
            Log.i("HOM", "NOINPUT and JUMP")
            if (!isJumping) {
                player.location.y -= 1
                player.gravity = GRAVITY
                player.velocityY = VY
                player.time = TIME
                player.resetTime()
                isJumping = true
            }
        }
        else if (leftUserInput == MovementUserInput.LEFT && rightUserInput == ActionUserInput.NOTHING) {
            Log.i("HOM", "LEFT and NOTHING")
            if (player.location.x >= 0) {
                player.velocityX = -VX
            }
            else {
                player.velocityX = 0f
                player.location.x = 0f
            }
            if (!isJumping) {
                player.velocityY = 0f
                player.gravity = 0f
            }
        }
        else if (leftUserInput == MovementUserInput.RIGHT && rightUserInput == ActionUserInput.NOTHING){
            Log.i("HOM", "RIGHT and NOTHING")
            player.velocityX = VX
            if (!isJumping) {
                player.velocityY = 0f
                player.gravity = 0f
            }
        }

        // Collision has the side effect of possibly altering the player's internal physics parameters if a collision occurs
        for (gameObj in gameObjects) {
            when(gameObj.collision(player)) {
                Direction.NONE -> {} // Do nothing
                Direction.TOP -> {}
                Direction.BOTTOM -> {
                    Log.i("HOM", "COLLISION BOTTOM")
                    isJumping = false
                    player.resetTime()
                    bottomCollision = true
                }
                Direction.LEFT -> {}
                Direction.RIGHT -> {
                    if (gameObj == background) {
                        context = true
                    }
                }
            }
        }

        // Addresses a bug where the player walks off an obstacle but does not fall
        if (!bottomCollision) {
            player.gravity = GRAVITY
        }

        for (gameObj in gameObjects) {
            gameObj.update(context)
        }

        // The movement of the player and the other objects of the game are inversely related
        player.update(!context) // This should eventually become the specific context for characters

//        for (entry in userInput) {
//            entry.setValue(false)
//        }
    }

    fun getGameObjects(): List<GameObject> {
        return gameObjects
    }
}