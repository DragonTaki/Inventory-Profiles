package io.github.jsnimda.common.gui.screen

import io.github.jsnimda.common.gui.widget.AnchorStyles
import io.github.jsnimda.common.gui.widget.Widget
import io.github.jsnimda.common.gui.widget.moveToCenter
import io.github.jsnimda.common.vanilla.Text
import io.github.jsnimda.common.vanilla.VHLine
import io.github.jsnimda.common.vanilla.VanillaRender

private const val COLOR_BORDER = -0x666667
private const val COLOR_BG = -0x1000000

open class BaseDialog : BaseOverlay {
  constructor(text: Text) : super(text)
  constructor() : super()

  var renderBlackOverlay = true
  var closeWhenClickOutside = true

  val dialogWidget = object : Widget() {
    override fun render(mouseX: Int, mouseY: Int, partialTicks: Float) {
      VHLine.fill(absoluteBounds, COLOR_BG)
      VHLine.outline(absoluteBounds, COLOR_BORDER)
      super.render(mouseX, mouseY, partialTicks)
    }
  }.apply {
    anchor = AnchorStyles.none
    this@BaseDialog.addWidget(this)
    moveToCenter()
    sizeChanged += {
      moveToCenter()
    }
  }

  override fun render(mouseX: Int, mouseY: Int, partialTicks: Float) {
    if (renderBlackOverlay) VanillaRender.renderBlackOverlay()
    super.render(mouseX, mouseY, partialTicks)
  }

  override fun mouseClicked(d: Double, e: Double, i: Int): Boolean {
    return super.mouseClicked(d, e, i) || if (i == 0 && dialogWidget.absoluteBounds.contains(d.toInt(), e.toInt())) {
      onClose()
      true
    } else false
  }

}