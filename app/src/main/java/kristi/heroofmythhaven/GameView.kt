package kristi.heroofmythhaven

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

class GameView: View{
    constructor(context: Context): super(context)
    constructor(context: Context, attributes: AttributeSet): super(context, attributes)
    private lateinit var gameManager: GameManager

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Fix This


        gameManager.loadGameObject()
        gameManager.update()
        for (gameObj in gameManager.getGameObjects()) { // FIX THIS WILL YOU LAZY BUM
            gameObj.draw(canvas)
        }


//        this.postDelayed({invalidate()}, 10)
        invalidate()
    }

    fun setGameManager(gameManager: GameManager) {
        this.gameManager = gameManager
    }

    fun setUserInput(input: UserInput) {
        gameManager.userInput[input] = true
    }
}