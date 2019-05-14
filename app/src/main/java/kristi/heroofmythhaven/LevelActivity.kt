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

enum class MovementUserInput {
    LEFT,
    RIGHT,
    NOINPUT
}
enum class ActionUserInput {
    ATTACK,
    JUMP,
    NOTHING
}

class LevelActivity : AppCompatActivity() {
    private lateinit var gameManager: GameManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)
    }

    override fun onStart() {
        super.onStart()
        gameManager = GameManager(1, this@LevelActivity)
        gameView.setGameManager(gameManager)

        leftButton.setOnTouchListener {_, motionEvent ->
            when(motionEvent.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {gameView.setLeftUserInput(MovementUserInput.LEFT)}
                MotionEvent.ACTION_UP -> {gameView.setLeftUserInput(MovementUserInput.NOINPUT)}
            }
            true
        }
        rightButton.setOnTouchListener {_, motionEvent ->
            when(motionEvent.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {gameView.setLeftUserInput(MovementUserInput.RIGHT)}
                MotionEvent.ACTION_UP -> {gameView.setLeftUserInput(MovementUserInput.NOINPUT)}
            }
            true
        }
        attackButton.setOnTouchListener {_, motionEvent ->
            when(motionEvent.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {gameView.setRightUserInput(ActionUserInput.ATTACK)}
                MotionEvent.ACTION_UP -> {gameView.setRightUserInput(ActionUserInput.NOTHING)}
            }
            true
        }
        jumpButton.setOnTouchListener {_, motionEvent ->
            when(motionEvent.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {gameView.setRightUserInput(ActionUserInput.JUMP)}
                MotionEvent.ACTION_UP -> {gameView.setRightUserInput(ActionUserInput.NOTHING)}
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
