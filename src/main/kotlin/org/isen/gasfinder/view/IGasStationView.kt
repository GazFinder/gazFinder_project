package org.isen.gasfinder.view

import java.beans.PropertyChangeListener

interface IGasStationView:PropertyChangeListener {
    fun display()
    fun close()
}