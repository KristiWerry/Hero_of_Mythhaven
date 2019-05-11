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

                playerLocation.x = middlePoint.x - 500
                playerLocation.y = middlePoint.y - 200
                player = Player(playerFrames, playerLocation)

                val backgroundBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.resources,
                    R.drawable.level1_background), gameView.width+5, gameView.height, false)
                background = Background(listOf<Bitmap>(backgroundBitmap,backgroundBitmap),
                    PointF(gameView.width.toFloat(), gameView.height.toFloat()))

                val chest = Chest(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.resources,
                    R.drawable.chest), 100, 100, false), PointF(4*gameView.width.toFloat(),
                    gameView.height.toFloat()* 0.69f))

                val landing = Landing(BitmapFactory.decodeResource(context.resources,
                    R.drawable.landing1), PointF(gameView.width.toFloat()/2+500,gameView.height * 0.65f) )

                loaded = true
                gameObjects = mutableListOf(background, player, chest, landing)
            }
    }

    fun update(userInput: UserInput) {
        var context = false
        player.getLocation(playerLocation)
        // I have an initial step to reset the playerFrames physics parameter conditions, but im not sure i need to do this

        when(userInput) {
            UserInput.NOINPUT -> {
                player.velocityX = 0f
                player.velocityY = 0f
            } //Do nothing
            UserInput.JUMP -> {player.gravity = 9f
                player.velocityY = 18f
                player.time = 0.5f
                player.resetTime()
            }
            UserInput.LEFT -> { player.velocityX = -5f} // Fix this
            UserInput.RIGHT -> {player.velocityX = 5f}
        }

        // Collision has the side effect of possibly altering the player's internal physics parameters
        for (gameObj in gameObjects) {
            if (gameObj.collision(player)) {
                if (gameObj == background) {
                    if (userInput == UserInput.RIGHT) {
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
    }

    fun getGameObjects(): List<GameObject> {
        return gameObjects
    }
}