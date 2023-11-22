package com.example.shemajamebeli3

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shemajamebeli3.dataProvider.list
import com.example.shemajamebeli3.databinding.ActivityThreeXthreeBinding

class ThreeXThreeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThreeXthreeBinding
    private lateinit var adapter: XOAdapter
    private var currentPlayer = 2
    // private var list = mutableListOf<Item>()


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThreeXthreeBinding.inflate(layoutInflater)
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
            rv.layoutManager = GridLayoutManager(this@ThreeXThreeActivity, 3)
        }

        adapter.submitList(setList())
        adapter.notifyDataSetChanged()
    }

    private fun setList(): MutableList<Item> {
        val currentList = mutableListOf<Item>()
        repeat(9) {
            currentList.add(Item(R.drawable.ic_square, null, true))
        }
        list = currentList
        return list
    }

    private fun setImage(item: Item) {
        if (currentPlayer % 2 == 0) {
            item.imgResource = R.drawable.ic_x
        } else {
            item.imgResource = R.drawable.ic_0
        }
        currentPlayer++
    }

    private fun checkForWinner() {
        // Check rows
        for (i in 0 until 3) {
            val row = i * 3
            if (checkRow(row)) {
                displayWinner(list[row].imgResource)
                return
            }
        }

        // Check columns
        for (i in 0 until 3) {
            val col = i
            if (checkColumn(col)) {
                displayWinner(list[col].imgResource)
                return
            }
        }

        // Check diagonals
        if (checkDiagonalLeftToRight() || checkDiagonalRightToLeft()) {
            displayWinner(list[4].imgResource)
            return
        }

        // Check for a draw
        if (list.none { it.isClickable }) {
            displayDraw()
        }
    }

    private fun checkRow(startIndex: Int): Boolean {
        val firstItem = list[startIndex]
        return (startIndex until startIndex + 3).all { list[it].imgResource == firstItem.imgResource }
    }

    private fun checkColumn(startIndex: Int): Boolean {
        val firstItem = list[startIndex]
        return (startIndex until startIndex + 6 step 3).all { list[it].imgResource == firstItem.imgResource }
    }

    private fun checkDiagonalLeftToRight(): Boolean {
        val firstItem = list[0]
        return (0 until 9 step 4).all { list[it].imgResource == firstItem.imgResource }
    }

    private fun checkDiagonalRightToLeft(): Boolean {
        val firstItem = list[2]
        return (2 until 7 step 2).all { list[it].imgResource == firstItem.imgResource }
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

object dataProvider {
    var list = mutableListOf<Item>()
}


