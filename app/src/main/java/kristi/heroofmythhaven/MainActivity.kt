package kristi.heroofmythhaven

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var username: String = ""
    private var level: Int = 1 //Player level
    private var gold: Int = 0
    private val QUESTCODE = 123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        username = intent.getStringExtra("Username")
        playNameMain.text = username
        levelMain.text = String.format(getString(R.string.levelTitle) + level.toString())
        goldMain.text = String.format(getString(R.string.goldTitle) + gold.toString())

        quest1.setOnClickListener {
            val startQuest1 = Intent(this, LevelActivity::class.java)
            startQuest1.putExtra("Username", username)
            startQuest1.putExtra("Level", level)
            startQuest1.putExtra("Gold", gold)
            startQuest1.putExtra("QuestNumber", 1)
            startActivityForResult(startQuest1, QUESTCODE)
        }
        quest2.setOnClickListener {
            val startQuest2 = Intent(this, LevelActivity::class.java)
            startQuest2.putExtra("Username", username)
            startQuest2.putExtra("Level", level)
            startQuest2.putExtra("Gold", gold)
            startQuest2.putExtra("QuestNumber", 2)
            startActivityForResult(startQuest2, QUESTCODE)
        }
        quest3.setOnClickListener {
            val startQuest3 = Intent(this, LevelActivity::class.java)
            startQuest3.putExtra("Username", username)
            startQuest3.putExtra("Level", level)
            startQuest3.putExtra("Gold", gold)
            startQuest3.putExtra("QuestNumber", 3)
            startActivityForResult(startQuest3, QUESTCODE)
        }
        quest4.setOnClickListener {
            val startQuest4 = Intent(this, LevelActivity::class.java)
            startQuest4.putExtra("Username", username)
            startQuest4.putExtra("Level", level)
            startQuest4.putExtra("Gold", gold)
            startQuest4.putExtra("QuestNumber", 4)
            startActivityForResult(startQuest4, QUESTCODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode != QUESTCODE) { //check if the request code is the same
            return
        }
        when (resultCode) {
            Activity.RESULT_OK -> {
                //get the result from level activity
                val finalLevelResult = data!!.getBooleanExtra("DidWin", false)
                Log.i("onActivityResult", "$finalLevelResult")
                if(finalLevelResult){
                    //we won the level so increase gold and level
                    gold+= 2000
                    level++
                    val levelTitle = getString(R.string.levelTitle) + level.toString()
                    levelMain.text = levelTitle
                    val goldTitle = getString(R.string.goldTitle) + gold.toString()
                    goldMain.text = goldTitle
                }
            }
            Activity.RESULT_CANCELED ->
                Log.i("onActivityResult", "cancel")
        }
    }
}
