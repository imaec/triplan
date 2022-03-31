package com.imaec.triplan.ui.common

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import com.imaec.triplan.ext.dp

/**
 * RecyclerView Item 구분선
 * @param height 구분선 높이 (dp 값)
 * @param padding 구분선 여백 (dp 값)
 * @param color 구분선 컬러
 * @param startPosition 구분선 시작 position
 */
open class RecyclerViewDividerDecoration(
    protected val height: Float = 1f,
    protected val padding: Float = 16f,
    @ColorInt private val color: Int = Color.parseColor("#f1f1f1"),
    protected val startPosition: Int = 0
) : RecyclerView.ItemDecoration() {

    protected val paint = Paint()

    init {
        paint.color = color
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val height = this.height.dp
        val padding = this.padding.dp

        val left = parent.paddingStart + padding
        val right = parent.width - parent.paddingEnd - padding
        for (i in 0 until parent.childCount - 1) {
            val child = parent.getChildAt(i)
            if (parent.getChildAdapterPosition(child) < startPosition) continue
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = (child.bottom + params.bottomMargin).toFloat()
            val bottom = top + height
            c.drawRect(left, top, right, bottom, paint)
        }
    }
}
