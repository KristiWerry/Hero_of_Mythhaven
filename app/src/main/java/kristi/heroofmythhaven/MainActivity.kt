package kristi.heroofmythhaven

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var username: String = ""
    private var level: Int = 1 //Player level
    private var gold: Int = 0
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
            startActivity(startQuest1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}
