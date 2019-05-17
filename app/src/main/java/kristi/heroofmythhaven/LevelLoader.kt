package kristi.heroofmythhaven

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PointF
import android.util.Log
import org.json.JSONObject

const val BACKGROUND = "background"
const val GAMEOBJS = "gameobjects"

class LevelLoader {
    private val gameObjs = mutableListOf<GameObject>()
    private val monsterObjs = mutableListOf<Monster>()
    private val context: LevelActivity
    private val background: Background
    private val player: Player
    private lateinit var chest: Chest

    constructor(context: LevelActivity, level: Int, playerType: String, playerLevel: Int) {
        this.context = context
        val levelJsonObj: JSONObject // Holds all of the information extracted from the level specific json file
        val gameView = context.findViewById<GameView>(R.id.gameView)

        // Construct the json object with the level specific information
        val levelRes = context.resources.openRawResource(R.raw.level_info)
        val levelInfo = levelRes.reader().buffered().readLines()
        var jsonString = ""

        for (line in levelInfo) {
            jsonString += line
        }
        levelJsonObj = JSONObject(jsonString)

        // construct the background
        val bgRes = context.resources.getIdentifier(levelJsonObj.getJSONObject(level.toString()).getString(BACKGROUND), "drawable", context.packageName)
        val backgroundBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.resources, bgRes), gameView.width+5, gameView.height, false)
        background = Background(listOf<Bitmap>(backgroundBitmap,backgroundBitmap), PointF(gameView.width.toFloat(), gameView.height.toFloat()))
        gameObjs.add(background)

        // construct the player
        player = constructPlayer(playerType, playerLevel)
        gameObjs.add(player)

        // construct the remaining game objects
        val levelGameObjs = levelJsonObj.getJSONObject(level.toString()).getJSONArray(GAMEOBJS)

        for (i in 0 until levelGameObjs.length()) { // for every game object
            val gameObj = levelGameObjs.getJSONObject(i)
            val objTypeIterator = gameObj.keys()

            while(objTypeIterator.hasNext()) { // gets the game object type (key) without having to know what it is ahead of time
                val gameObjType = objTypeIterator.next()
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
                    "monster" -> {
                        val bitMap = getBitMap(jObj)
                        monsterObjs.add(Monster(bitMap, jObj.getInt("hp"), jObj.getInt("damage"), PointF(jObj.getInt("x").toFloat(), (gameView.height.toFloat() * 0.79f) - (bitMap.height * jObj.getInt("y").toFloat()))))
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

    fun getMonsterObjs(): MutableList<Monster> {
        return monsterObjs
    }

    fun getPlayer(): Player {
        return player
    }

    private fun getBitMap(jsonObject: JSONObject): Bitmap {
        val imageRes = context.resources.getIdentifier(jsonObject.getString("filename"), "drawable", context.packageName)
        return BitmapFactory.decodeResource(context.resources, imageRes)
    }

    private fun constructPlayer(playerType: String, playerLevel: Int): Player {
        val playerFrameList = ArrayList<Bitmap>()
        val gameView = context.findViewById<GameView>(R.id.gameView)
        // Construct the json object with the player specific information
        val playerRes = context.resources.openRawResource(R.raw.player_info)
        val playerInfo = playerRes.reader().buffered().readLines()
        var jsonString = ""

        for (line in playerInfo) {
            jsonString += line
        }
        val playerJsonObj = JSONObject(jsonString) // Holds all of the information extracted from the player specific json file
        val playerFrames = playerJsonObj.getJSONArray(playerType)
        val temp = arrayListOf("standing_right", "walking_right", "attack_right", "jumping_right", "standing_left", "walking_left", "attack_left", "jumping_left")
        for (i in 0 until playerFrames.length()) {
            Log.i("HOM", "FRAME: " + playerFrames.getJSONObject(i).getString(temp[i]))
            val frame = playerFrames.getJSONObject(i).getString(temp[i])
            val frameRes = context.resources.getIdentifier(frame, "drawable", context.packageName)
            playerFrameList.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.resources, frameRes), 150, 150, false))
        }
        return Player(playerFrameList, PointF(gameView.width * 0.5f - (playerFrameList[0].width/2) - 500,gameView.height * 0.59f - 200), 5*playerLevel, 1)
    }
}
