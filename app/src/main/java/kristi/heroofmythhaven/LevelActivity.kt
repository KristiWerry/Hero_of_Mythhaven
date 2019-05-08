package kristi.heroofmythhaven

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_level.*

class LevelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)
    }

    fun showPausePopup(view: View) {
        var dialogs = Dialog(this)
        dialogs.setCancelable(false)
        dialogs.setContentView(R.layout.pause_popup)
        val resumeBtn = dialogs.findViewById(R.id.resumeButton) as Button
        val quitBtn = dialogs.findViewById(R.id.quitButton) as TextView
        resumeBtn.setOnClickListener {
            dialogs.dismiss()
        }
        quitBtn.setOnClickListener {
            dialogs.dismiss()
            finish()
        }
        dialogs.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogs.show()
    }
}
