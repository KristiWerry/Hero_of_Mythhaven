package kristi.heroofmythhaven

import android.graphics.Canvas
import android.graphics.PointF

interface DrawObjects {
    fun draw(canvas: Canvas)
    fun update(userInput: UserInput, location: PointF, isMiddle: Float)
}