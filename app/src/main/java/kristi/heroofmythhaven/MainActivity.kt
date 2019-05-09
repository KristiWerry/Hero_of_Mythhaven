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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        quest1.setOnClickListener {
            val startQuest1 = Intent(this, LevelActivity::class.java)
            startActivity(startQuest1)
        }
    }
    /*fun showQuestPopup(view: View) {
        var dialogs = Dialog(this)
        dialogs.setCancelable(false)
        dialogs.setContentView(R.layout.pause_popup)
        pauseTitle.text = "Quest 1"
        val resumeBtn = dialogs.findViewById(R.id.resumeButton) as Button
        resumeBtn.text = getString(R.string.embark)
        val quitBtn = dialogs.findViewById(R.id.quitButton) as TextView
        quitBtn.text = getString(R.string.back)
        resumeBtn.setOnClickListener {
            dialogs.dismiss()
            val startQuest1 = Intent(this, LevelActivity::class.java)
            startActivity(startQuest1)
        }
        quitBtn.setOnClickListener {
            dialogs.dismiss()
        }
        dialogs.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogs.show()
    }*/
}
