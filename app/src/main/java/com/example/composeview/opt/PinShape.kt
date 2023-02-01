package com.example.composeview.opt

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2023/2/1 11:21
 * Desc  :
 */
sealed interface PinShape {

    // Draw method to be implemented by different Shapes
    fun drawShape(size: Size): Path

    /**
     * Underline shape _ _ _ _
     *
     * @param strokeWidth: width of the underline
     */
    class LineShape(private val strokeWidth: Float): PinShape {
        override fun drawShape(size: Size): Path {
            return Path().apply {
                reset()
                addRect(Rect(0f, size.height - (strokeWidth / 2), size.width, size.height))
                close()
            }
        }
    }

    /**
     * Rectangular shape
     *
     * @param cornerRadius: If greater than zero than rounded rect will be shown
     */
    class RectangularShape(private val cornerRadius: Float): PinShape {
        override fun drawShape(size: Size): Path {
            return Path().apply {
                reset()
                val cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                addRoundRect(RoundRect(left = 0f, top = 0f, right = size.width, bottom = size.height, topLeftCornerRadius = cornerRadius, topRightCornerRadius = cornerRadius, bottomLeftCornerRadius = cornerRadius, bottomRightCornerRadius = cornerRadius))
                close()
            }
        }
    }

    /**
     * Circle shape pin
     *
     */
    object CircleShape : PinShape {
        override fun drawShape(size: Size): Path {
            return Path().apply {
                reset()
                addOval(Rect(0f, 0f, size.width, size.height))
                close()
            }
        }
    }
}