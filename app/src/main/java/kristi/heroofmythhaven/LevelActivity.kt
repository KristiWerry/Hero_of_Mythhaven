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
import kotlinx.android.synthetic.main.activity_main.*

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
    private var level = 0
    private lateinit var gameManager: GameManager
    private var questLevelNumber: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)
        val username = intent.getStringExtra("Username")
        playNameLevel.text = username
        val level = intent.getIntExtra("Level", 0)
        val levelTitle = getString(R.string.levelTitle) + level.toString()
        levelLevel.text = levelTitle
        val gold = intent.getIntExtra("Gold", 0)
        val goldTitle = getString(R.string.goldTitle) + gold.toString()
        goldLevel.text = goldTitle
        questLevelNumber = intent.getIntExtra("QuestNumber", 1)
    }

    override fun onStart() {
        super.onStart()
        gameManager = GameManager(questLevelNumber, this@LevelActivity, level)
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
        val dialogs = Dialog(this)
        dialogs.setCancelable(false)
        dialogs.setContentView(R.layout.pause_popup)
        val resumeBtn = dialogs.findViewById(R.id.resumeButton) as Button
        val quitBtn = dialogs.findViewById(R.id.quitButton) as TextView
        resumeBtn.setOnClickListener {
            dialogs.dismiss()
        }
        quitBtn.setOnClickListener {
            dialogs.dismiss()
            val levelResult = intent
            levelResult.putExtra("DidWin", false)
            setResult(RESULT_OK, levelResult) //return the final score to main activity
            finish() //finish and go back to main activity //kills this activity //not on back stack
        }
        dialogs.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogs.show()
    }

    fun showEndPopup(didWin: Boolean) {
        var titleText: String
        if(didWin) {
            titleText = getString(R.string.you_win)
            val levelResult = intent
            levelResult.putExtra("DidWin", true)
            setResult(RESULT_OK, levelResult) //return the final score to main activity
        }
        else {
            titleText = getString(R.string.you_lose)
            val levelResult = intent
            levelResult.putExtra("DidWin", false)
            setResult(RESULT_OK, levelResult) //return the final score to main activity
        }
        val dialogs = Dialog(this)
        dialogs.setCancelable(false)
        dialogs.setContentView(R.layout.end_popup)
        val endTitle = dialogs.findViewById(R.id.endTitle) as TextView
        endTitle.text = titleText
        val restartBtn = dialogs.findViewById(R.id.restartButtonEnd) as Button
        val quitBtn = dialogs.findViewById(R.id.quitButtonEnd) as TextView
        restartBtn.setOnClickListener {
            //restart game~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            dialogs.dismiss()
            gameManager = GameManager(questLevelNumber, this@LevelActivity, level)
            gameView.setGameManager(gameManager)
        }
        quitBtn.setOnClickListener {
            dialogs.dismiss()
            finish() //finish and go back to main activity //kills this activity //not on back stack
        }
        dialogs.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogs.show()
    }
}
