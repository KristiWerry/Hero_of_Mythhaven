package kristi.heroofmythhaven

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PointF
import android.util.Log
import org.json.JSONObject

const val BACKGROUND = "background"
const val GAMEOBJS = "gameobjects"

class LevelLoader {
//    private val file: File
    private val jsonObj: JSONObject
    private val gameObjs = mutableListOf<GameObject>()
    private val context: LevelActivity
    private val background: Background
    private lateinit var chest: Chest

    constructor(context: LevelActivity, level: Int) {
        this.context = context

        // Construct the json object with the level specific information
        val rawRes = context.resources.openRawResource(R.raw.level_info)
        val levelInfo = rawRes.reader().buffered().readLines()
        var jsonString = ""

        for (line in levelInfo) {
            jsonString += line
        }

        jsonObj = JSONObject(jsonString)

        // construct the background
        val gameView = context.findViewById<GameView>(R.id.gameView)
        val bgRes = context.resources.getIdentifier(jsonObj.getJSONObject(level.toString()).getString(BACKGROUND), "drawable", context.packageName)
        val backgroundBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.resources, bgRes), gameView.width+5, gameView.height, false)
        background = Background(listOf<Bitmap>(backgroundBitmap,backgroundBitmap), PointF(gameView.width.toFloat(), gameView.height.toFloat()))
        gameObjs.add(background)

        // construct the remaining game objects
        val levelGameObjs = jsonObj.getJSONObject(level.toString()).getJSONArray(GAMEOBJS)
        for (i in 0 until levelGameObjs.length()) { // for every game object
            val gameObj = levelGameObjs.getJSONObject(i)

            val iter = gameObj.keys()
            while(iter.hasNext()) { // gets the game object type without having to know what it is ahead of time
                val gameObjType = iter.next()
                val jObj = gameObj.getJSONObject(gameObjType)
                when(gameObjType) {
                    "landing" -> {
                        val bitMap = getBitMap(jObj)
                        gameObjs.add(Landing(bitMap, PointF(jObj.getInt("x").toFloat(), (gameView.height.toFloat() * 0.79f) - (bitMap.height * jObj.getInt("y").toFloat()))))
                    }
                    "chest" -> {
                        val bitMap = getBitMap(jObj)
                        chest = Chest(bitMap, PointF(jObj.getInt("x").toFloat(), (gameView.height.toFloat() * 0.79f) - (bitMap.height * jObj.getInt("y").toFloat())))
                        gameObjs.add(chest)
                    }
                }
            }
        }
    }

    fun getBackground(): Background {
        return background
    }

    fun getChest(): Chest {
        return chest
    }

    fun getGameObjs(): MutableList<GameObject> {
        return gameObjs
    }

    private fun getBitMap(jsonObject: JSONObject): Bitmap {
        val imageRes = context.resources.getIdentifier(jsonObject.getString("filename"), "drawable", context.packageName)
        return BitmapFactory.decodeResource(context.resources, imageRes)
    }
}