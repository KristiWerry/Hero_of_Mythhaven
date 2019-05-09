package kristi.heroofmythhaven

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.util.Log
import java.util.logging.Level

class GameManager{

    // Step 1: on creation should load all of the drawable objects for the level
    //      - Main character, background
    //      - Save in a list
    //      - Should have a n update function that updates all of the objects
    //      -
    // Step 2(FUTURE): Collision/ Jumping / Game management
    private lateinit var gameObjects: MutableList<DrawObjects>
    private val context: LevelActivity
    private var loaded = false
    private var playerLocation = PointF()
    private var middlePoint = PointF()
    private lateinit var player: Player
    private lateinit var background: Background

    constructor(level: Int, context: LevelActivity) {
        this.context = context
    }

    // Based on what level is inputted, go to a JSON file and grab the necessary information
    fun loadGameObject(){
            if (!loaded) {
                Log.i("HOM", "Load Game Objects")
                val bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.resources,
                    R.drawable.death_knight), 200, 200, false)
                val gameView = context.findViewById<GameView>(R.id.gameView)
                middlePoint.x = gameView.width * 0.5f - (bitmap.width/2)
                middlePoint.y = gameView.height * 0.59f

                playerLocation.x = middlePoint.x
                playerLocation.y = middlePoint.y
                player = Player(bitmap, playerLocation)

                val backgroundBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.resources,
                    R.drawable.level1_background), gameView.width+5, gameView.height, false)
                background = Background(listOf<Bitmap>(backgroundBitmap,backgroundBitmap),
                    PointF(gameView.width.toFloat(), gameView.height.toFloat()))

                val chest = Chest(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.resources,
                    R.drawable.chest), 200, 200, false), PointF(4*gameView.width.toFloat(), gameView.height.toFloat()* 0.59f))

                loaded = true
                gameObjects = mutableListOf(background, player, chest)
            }
    }

    fun update(userInput: UserInput) {
        player.getLocation(playerLocation)
        for (gameObj in gameObjects) {
            gameObj.update(userInput, playerLocation, middlePoint.x)
        }

        Log.i("HOM", "NUMBER OF BGS: " + background.numCompletedBackgrounds)
    }

    fun getGameObjects(): List<DrawObjects> {
        return gameObjects
    }
}