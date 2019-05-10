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
    private var players = ArrayList<Bitmap>(4)

    constructor(level: Int, context: LevelActivity) {
        this.context = context
    }

    // Based on what level is inputted, go to a JSON file and grab the necessary information
    fun loadGameObject(){
            if (!loaded) {
                players.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.resources,
                    R.drawable.death_knight), 200, 200, false))
                players.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.resources,
                    R.drawable.death_knight2), 200, 200, false))
                players.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.resources,
                    R.drawable.death_knight3), 200, 200, false))
                players.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.resources,
                    R.drawable.death_knight4), 200, 200, false))
                val gameView = context.findViewById<GameView>(R.id.gameView)
                middlePoint.x = gameView.width * 0.5f - (players[0].width/2)
                middlePoint.y = gameView.height * 0.59f

                playerLocation.x = middlePoint.x - 500
                playerLocation.y = middlePoint.y
                player = Player(players, playerLocation)

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
        player.getLocation(playerLocation)
        player.collisions[0] = false
        player.collisions[1] = false
        player.collisions[2] = false
        player.collisions[3] = false
        player.collisions[4] = false

        for (gameObj in gameObjects) {
            if (gameObj != player) {
                gameObj.collision(player)
            }
        }

        for (gameObj in gameObjects) {
            gameObj.update(userInput,  player.collisions)
        }
    }

    fun getGameObjects(): List<GameObject> {
        return gameObjects
    }
}