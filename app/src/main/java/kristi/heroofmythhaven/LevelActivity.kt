package kristi.heroofmythhaven

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_level.*

enum class UserInput {
    LEFT,
    RIGHT,
    JUMP,
    ATTACK,
    NOINPUT
}

class LevelActivity : AppCompatActivity() {
    private lateinit var gameManager: GameManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)
        Log.i("HOM", gameView.height.toString())
    }

    override fun onStart() {
        super.onStart()
        gameManager = GameManager(1, this@LevelActivity)
        gameView.setGameManager(gameManager)
        leftButton.setOnTouchListener {_, motionEvent ->
            when(motionEvent.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {gameManager.update(UserInput.LEFT); gameView.setUserInput(UserInput.LEFT)}
                MotionEvent.ACTION_UP -> {gameManager.update(UserInput.NOINPUT);gameView.setUserInput(UserInput.NOINPUT)}
            }
            true
        }
        rightButton.setOnTouchListener {_, motionEvent ->
            when(motionEvent.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {gameManager.update(UserInput.RIGHT); gameView.setUserInput(UserInput.RIGHT)}
                MotionEvent.ACTION_UP -> {gameManager.update(UserInput.NOINPUT);gameView.setUserInput(UserInput.NOINPUT)}
            }
            true
        }
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
