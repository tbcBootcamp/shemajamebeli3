package com.example.shemajamebeli3

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shemajamebeli3.databinding.ActivityFiveXfiveBinding
import com.example.shemajamebeli3.databinding.ActivityMainBinding

class FiveXFiveActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFiveXfiveBinding
    private lateinit var adapter: XOAdapter
    private var currentPlayer = 2

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFiveXfiveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setList()
        adapter = XOAdapter {
            if (it.isClickable) {
                setImage(it)
                it.isClickable = false
                checkForWinner()
                adapter.notifyDataSetChanged()
            }
        }

        binding.apply {
            btnStartAgain.setOnClickListener {
                adapter.submitList(setList())
                adapter.notifyDataSetChanged()
                currentPlayer = 2
            }
            rv.adapter = adapter
            rv.layoutManager = GridLayoutManager(this@FiveXFiveActivity, 5)
        }

        adapter.submitList(setList())
        adapter.notifyDataSetChanged()
    }

    private fun setImage(item: Item) {
        if (currentPlayer % 2 == 0) {
            item.imgResource = R.drawable.ic_x
        } else {
            item.imgResource = R.drawable.ic_0
        }
        currentPlayer++
    }

    private fun setList(): MutableList<Item> {
        val currentList = mutableListOf<Item>()
        repeat(25) {
            currentList.add(Item(R.drawable.ic_square, null, true))
        }
        dataProvider.list = currentList
        return dataProvider.list
    }

    private fun checkForWinner() {
        // Check rows
        for (i in 0 until 5) {
            val row = i * 5
            if (checkRow(row)) {
                displayWinner(dataProvider.list[row].imgResource)
                return
            }
        }

        // Check columns
        for (i in 0 until 5) {
            val col = i
            if (checkColumn(col)) {
                displayWinner(dataProvider.list[col].imgResource)
                return
            }
        }

        // Check diagonals
        if (checkDiagonalLeftToRight() || checkDiagonalRightToLeft()) {
            displayWinner(dataProvider.list[12].imgResource)
            return
        }

        // Check for a draw
        if (dataProvider.list.none { it.isClickable }) {
            displayDraw()
        }
    }

    private fun checkRow(startIndex: Int): Boolean {
        val firstItem = dataProvider.list[startIndex]
        return (startIndex until startIndex + 5).all { dataProvider.list[it].imgResource == firstItem.imgResource }
    }

    private fun checkColumn(startIndex: Int): Boolean {
        val firstItem = dataProvider.list[startIndex]
        return (startIndex until startIndex + 20 step 5).all { dataProvider.list[it].imgResource == firstItem.imgResource }
    }

    private fun checkDiagonalLeftToRight(): Boolean {
        val firstItem = dataProvider.list[0]
        return (0 until 25 step 6).all { dataProvider.list[it].imgResource == firstItem.imgResource }
    }

    private fun checkDiagonalRightToLeft(): Boolean {
        val firstItem = dataProvider.list[4]
        return (4 until 21 step 4).all { dataProvider.list[it].imgResource == firstItem.imgResource }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun displayWinner(winnerIcon: Int?) {
        val winner = when (winnerIcon) {
            R.drawable.ic_x -> "X"
            R.drawable.ic_0 -> "O"
            else -> ""
        }
        if (winner.isNotEmpty())
        Toast.makeText(this, "Player $winner wins!", Toast.LENGTH_SHORT).show()
    }

    private fun displayDraw() {
        Toast.makeText(this, "It's a draw!", Toast.LENGTH_SHORT).show()
    }
}