package org.isen.gasfinder.view.map

import java.awt.Graphics
import java.awt.Image
import javax.swing.ImageIcon
import javax.swing.JPanel

class StationsMapPanel : JPanel() {
    private var loadingAssets: List<Image>? = null
    private var currentFrame = 0
    private var stationsMap: Image? = null
    private var loading : Boolean = true
    private var loadingOpacity = 1.0f
    init {
        val frames = mutableListOf<Image>()
        for (i in 1..86) {
            frames.add(ImageIcon(javaClass.getResource("/loading/frame-$i.png")).image)
        }
        loadingAssets = frames
        loadingThread()
    }

    private fun loadingThread() {
        Thread {
            while (loading) {
                currentFrame = (currentFrame + 1) % loadingAssets!!.size
                repaint()
                Thread.sleep(17)
            }
        }.start()
    }

    private fun fadeInLoading() {
        Thread {
            loadingThread()
            while (loadingOpacity < 1.0f) {
                loadingOpacity += 0.05f
                repaint()
                Thread.sleep(10)
            }
            this.loading = true
            repaint()
        }.start()
    }

    private fun fadeOutLoading() {
        Thread {
            loadingThread()
            while (loadingOpacity > 0.0f) {
                loadingOpacity -= 0.01f
                repaint()
                Thread.sleep(5)
            }
            this.loading = false
            repaint()
        }.start()
    }
    fun setLoading(loading: Boolean) {
        if(loading) {
            fadeInLoading()
        } else {
            fadeOutLoading()
        }
    }

    fun setStationsMap(stationsMap: Image) {
        this.stationsMap = stationsMap
        repaint()
    }

    override fun paint(g: Graphics) {
        super.paint(g)
        g.color = java.awt.Color(0x333333)
        g.fillRect(0, 0, width, height)

        if (stationsMap != null) {
            //draw map based on opacity
            g.drawImage(stationsMap, 0, 0, this)
        }
        if(loading) {
            // paint a white transparent overlay 30% opacity
            g.color = java.awt.Color(255, 255, 255, (loadingOpacity * 100).toInt())
            g.fillRect(0, 0, width, height)
        }
        if (loading) {
            //render at center
            g.drawImage(loadingAssets!![currentFrame], (width - loadingAssets!![currentFrame].getWidth(this)) / 2, (height - loadingAssets!![currentFrame].getHeight(this)) / 2, this)
        }
    }
}