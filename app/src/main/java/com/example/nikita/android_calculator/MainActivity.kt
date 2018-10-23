package com.example.nikita.android_calculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText

import  org.mariuszgromada.math.mxparser.*;


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<EditText>(R.id.resultText)
        val savedSate = savedInstanceState?.getString("resultText") ?: "0"
        textView.text = Editable.Factory.getInstance().newEditable(savedSate)
        textView.showSoftInputOnFocus = false
        for (i in 0..19) {
            val buttonId = resources.getIdentifier("button" + i.toString(), "id", packageName)
            val button = findViewById<Button>(buttonId)
            button.setOnClickListener {
                val currentButtonText = (it as Button).text.toString()
                val currentEditText = textView.text.toString()
                when (currentButtonText) {
                    "=" -> {
                        val result = Expression(currentEditText).calculate()
                        if ((result as Number) == Double.NaN) {
                            textView.text = Editable.Factory.getInstance().newEditable("Error")
                        } else {
                            textView.text = Editable.Factory.getInstance().newEditable("$result")
                        }
                        textView.setSelection(textView.text.toString().length)
                    }
                    "√" -> {
                        val result =
                            insertBetween(
                                textView.text.toString(), "*sqrt(",
                                textView.selectionStart, textView.selectionEnd
                            )
                        textView.text = Editable.Factory.getInstance().newEditable(result.first)
                        textView.setSelection(result.second)
                    }
                    else -> {
                        val result =
                            insertBetween(
                                textView.text.toString(), currentButtonText,
                                textView.selectionStart, textView.selectionEnd
                            )
                        textView.text = Editable.Factory.getInstance().newEditable(result.first)
                        textView.setSelection(result.second)

                    }
                }

            }
        }
        findViewById<Button>(R.id.button21).setOnClickListener {
            val currentString = textView.text.toString()
            val result = deleteBetween(currentString, textView.selectionStart, textView.selectionEnd)
            textView.text = Editable.Factory.getInstance().newEditable(result.first)
            textView.setSelection(result.second)
        }
        findViewById<Button>(R.id.button20).setOnClickListener {
            textView.text = Editable.Factory.getInstance().newEditable("")
        }

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        val textView = findViewById<EditText>(R.id.resultText)
        outState?.putString("resultText", textView.text.toString())
    }

    private fun deleteBetween(text: String, start: Int, end: Int): Pair<String, Int> {
        if (start == end) {
            var update = false
            val rightString = when (end > 0 && end < text.length) {
                true -> {
                    update = true
                    "${text.substring(0, end - 1)}${text.substring(end)}"
                }
                false -> when (end > 0 && end == text.length) {
                    true -> {
                        update = true
                        text.substring(0, end - 1)
                    }
                    false -> text
                }
            }
            if (update) {
                return Pair(rightString, end - 1)
            }
            return Pair(rightString, end)
        } else {
            val leftString = text.substring(0, start)
            val rightString = text.substring(end, text.length)
            return Pair("$leftString$rightString", start)
        }
    }

    private fun insertBetween(text: String, inner: String, start: Int, end: Int): Pair<String, Int> {
        val rightString = "${text.substring(0, start)}$inner${text.substring(end, text.length)}"
        return Pair(rightString, start + inner.length)

    }
}
