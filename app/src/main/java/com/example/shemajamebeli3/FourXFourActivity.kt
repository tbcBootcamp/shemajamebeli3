package com.example.shemajamebeli3

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shemajamebeli3.databinding.ActivityFourXfourBinding
import com.example.shemajamebeli3.databinding.ActivityMainBinding

class FourXFourActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFourXfourBinding
    private lateinit var adapter: XOAdapter
    private var currentPlayer = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFourXfourBinding.inflate(layoutInflater)
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
            rv.layoutManager = GridLayoutManager(this@FourXFourActivity, 4)
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
        repeat(16) {
            currentList.add(Item(R.drawable.ic_square, null, true))
        }
        dataProvider.list = currentList
        return dataProvider.list
    }

    private fun checkForWinner() {
        // Check rows
        for (i in 0 until 4) {
            val row = i * 4
            if (checkRow(row)) {
                displayWinner(dataProvider.list[row].imgResource)
                return
            }
        }

        // Check columns
        for (i in 0 until 4) {
            val col = i
            if (checkColumn(col)) {
                displayWinner(dataProvider.list[col].imgResource)
                return
            }
        }

        // Check diagonals
        if (checkDiagonalLeftToRight() || checkDiagonalRightToLeft()) {
            displayWinner(dataProvider.list[5].imgResource)
            return
        }

        // Check for a draw
        if (dataProvider.list.none { it.isClickable }) {
            displayDraw()
        }
    }

    private fun checkRow(startIndex: Int): Boolean {
        val firstItem = dataProvider.list[startIndex]
        return (startIndex until startIndex + 4).all { dataProvider.list[it].imgResource == firstItem.imgResource }
    }

    private fun checkColumn(startIndex: Int): Boolean {
        val firstItem = dataProvider.list[startIndex]
        return (startIndex until startIndex + 13 step 4).all { dataProvider.list[it].imgResource == firstItem.imgResource }
    }

    private fun checkDiagonalLeftToRight(): Boolean {
        val firstItem = dataProvider.list[0]
        return (0 until 16 step 5).all { dataProvider.list[it].imgResource == firstItem.imgResource }
    }

    private fun checkDiagonalRightToLeft(): Boolean {
        val firstItem = dataProvider.list[3]
        return (3 until 15 step 3).all { dataProvider.list[it].imgResource == firstItem.imgResource }
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