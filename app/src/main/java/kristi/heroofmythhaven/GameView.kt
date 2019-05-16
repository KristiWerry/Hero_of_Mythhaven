package kristi.heroofmythhaven

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.widget.Toast

class GameView: View{
    constructor(context: Context): super(context)
    constructor(context: Context, attributes: AttributeSet): super(context, attributes)
    private lateinit var gameManager: GameManager

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        gameManager.loadGameObject()
        gameManager.update()
        for (gameObj in gameManager.getGameObjects()) {
            gameObj.draw(canvas)
        }

        for (monster in gameManager.getMonsterObjects()) {
            monster.draw(canvas)
        }
        invalidate()
    }

    fun setGameManager(gameManager: GameManager) {
        this.gameManager = gameManager
    }

    fun setLeftUserInput(leftInput: MovementUserInput) {
        gameManager.leftUserInput = leftInput
    }
    fun setRightUserInput(rightInput: ActionUserInput) {
        gameManager.rightUserInput = rightInput
    }
}