package kristi.heroofmythhaven

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.pause_popup.*

class MainActivity : AppCompatActivity() {
    private var username: String = ""
    private var level: Int = 1
    private var gold: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        username = intent.getStringExtra("Username")
        playNameMain.text = username
        val levelTitle = getString(R.string.levelTitle) + level.toString()
        levelMain.text = levelTitle
        val goldTitle = getString(R.string.goldTitle) + gold.toString()
        goldMain.text = goldTitle

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
