package kristi.heroofmythhaven

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import java.util.jar.Attributes

class GameView: View{
    constructor(context: Context): super(context) {}
    constructor(context: Context, attributes: AttributeSet): super(context, attributes) {}
    private var gameManager: GameManager? = null
    private var userInput: UserInput = UserInput.NOINPUT

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Fix This

        if (gameManager !=  null) {
            gameManager?.loadGameObject()
            gameManager?.update(userInput)
            for (gameObj in gameManager!!.getGameObjects()) {
                gameObj.draw(canvas)
            }
        }
        invalidate()
    }

    fun setGameManager(gameManager: GameManager) {
        this.gameManager = gameManager
    }

    fun setUserInput(userInput: UserInput) {
        this.userInput = userInput
    }
}