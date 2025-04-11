package com.example.offlinegames

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NumberPuzzleGame : AppCompatActivity() {

    private val gridSize = 3
    private val totalCells = gridSize * gridSize
    private val cells = MutableList(totalCells) { it + 1 }
    private lateinit var buttons: List<Button>
    private var emptyCellIndex = totalCells - 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_number_game_puzzle)

        buttons = List(totalCells) { index ->
            findViewById<Button>(resources.getIdentifier("button$index", "id", packageName))
        }

        initializeGame()

        val newGameButton = findViewById<Button>(R.id.newGameButton)
        val settingsButton = findViewById<Button>(R.id.settingsButton)

        newGameButton.setOnClickListener {
            initializeGame()
        }

        settingsButton.setOnClickListener {
            Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show()
        }

        for (button in buttons) {
            button.setOnClickListener {
                onCellClick(button)
            }
        }
    }

    private fun initializeGame() {
        shuffleCells()
        updateButtonLabels()
    }

    private fun shuffleCells() {
        emptyCellIndex = totalCells - 1
        cells.shuffle()
    }

    private fun updateButtonLabels() {
        for (i in 0 until totalCells) {
            buttons[i].text = if (cells[i] == totalCells) "" else cells[i].toString()
        }
    }

    private fun onCellClick(clickedButton: Button) {
        val clickedIndex = buttons.indexOf(clickedButton)

        if (isAdjacentToEmpty(clickedIndex)) {
            swapCells(clickedIndex, emptyCellIndex)
            updateButtonLabels()

            if (isPuzzleSolved()) {
                Toast.makeText(this, "Congratulations! Puzzle Solved!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Invalid Move!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isAdjacentToEmpty(index: Int): Boolean {
        val row = index / gridSize
        val col = index % gridSize

        val emptyRow = emptyCellIndex / gridSize
        val emptyCol = emptyCellIndex % gridSize

        return (row == emptyRow && (col == emptyCol - 1 || col == emptyCol + 1)) ||
                (col == emptyCol && (row == emptyRow - 1 || row == emptyRow + 1))
    }

    private fun swapCells(index1: Int, index2: Int) {
        val temp = cells[index1]
        cells[index1] = cells[index2]
        cells[index2] = temp

        emptyCellIndex = if (cells[index1] == totalCells) index1 else index2
    }

    private fun isPuzzleSolved(): Boolean {
        for (i in 0 until totalCells - 1) {
            if (cells[i] != i + 1) {
                return false
            }
        }
        return true
    }
}
