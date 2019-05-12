package kristi.heroofmythhaven

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PointF
import android.util.Log

class GameManager{

    // Step 1: on creation should load all of the drawable objects for the level
    //      - Main character, background
    //      - Save in a list
    //      - Should have a n update function that updates all of the objects
    //      -
    // Step 2(FUTURE): Collision/ Jumping / Game management
    private lateinit var gameObjects: MutableList<GameObject>
    private val context: LevelActivity
    private var loaded = false
    private var playerLocation = PointF()
    private var middlePoint = PointF()
    private lateinit var player: Player
    private lateinit var background: Background
    private var playerFrames = ArrayList<Bitmap>(4)
    private var ground: Float = 0f

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

                Log.i("HOM", "GAME VIEW HEIGHT: " + gameView.height)

                middlePoint.x = gameView.width * 0.5f - (playerFrames[0].width/2)
                middlePoint.y = gameView.height * 0.59f
                ground = gameView.height.toFloat() * 0.79f
                playerLocation.x = middlePoint.x - 500
                playerLocation.y = middlePoint.y - 200
                player = Player(playerFrames, playerLocation)

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

    private var isAerial = true
    fun update(userInput: UserInput) {
        var context = false

        when(userInput) {
            UserInput.NOINPUT -> {
                player.velocityX = 0f

                if (!isAerial) {
                    player.velocityY = 0f
                    player.gravity = 0f
                }
            }
            UserInput.JUMP -> {
                if (!isAerial) {
                    playerLocation.y -= 1
                    player.setPlayerLocation(playerLocation)
                    player.gravity = 9f
                    player.velocityY = 18f
                    player.time = 0.5f
                    player.resetTime()
                    isAerial = true
                }
            }
            UserInput.LEFT -> {
                if (playerLocation.x >= 0) {
                    player.velocityX = -5f
                }
                else {
                    player.velocityX = 0f
                }
            }
            UserInput.RIGHT -> {player.velocityX = 5f}
        }

        // Collision has the side effect of possibly altering the player's internal physics parameters if a collision occurs
        for (gameObj in gameObjects) {
            when(gameObj.collision(player)) {
                Direction.NONE -> {} // Do nothing
                Direction.TOP -> {}
                Direction.BOTTOM -> {
                    isAerial = false
                    player.resetTime()
                }
                Direction.LEFT -> {}
                Direction.RIGHT -> {
                    if (gameObj == background) {
                        context = true
                    }
                }
            }
        }

        for (gameObj in gameObjects) {
            gameObj.update(context)
        }

        // The movement of the player and the other objects of the game are inversely related
        player.update(!context) // This should eventually become the specific context for characters
        player.getLocation(playerLocation)

        if (playerLocation.y + player.getPlayerHeight() >= ground) {
            playerLocation.y -= playerLocation.y - ground + player.getPlayerHeight()
            player.setPlayerLocation(playerLocation)
        }
    }

    fun getGameObjects(): List<GameObject> {
        return gameObjects
    }
}