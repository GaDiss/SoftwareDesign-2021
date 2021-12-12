package tokenizers

import tokens.Brace
import tokens.NumberToken
import tokens.Operation
import tokens.Token
import java.io.IOException
import java.io.InputStream
import java.lang.Character.isWhitespace
import java.text.ParseException

class Tokenizer(private val inputStream: InputStream) {
    private var curChar = 0
    private var curPos = 0
    private val tokens = ArrayList<Token>()

    private fun nextChar(): Int {
        curPos++
        try {
            curChar = inputStream.read()
            return curChar
        } catch (e: IOException) {
            throw ParseException(e.message, curPos)
        }
    }

    private fun addNumber() {
        var buffer = "" + curChar.toChar()
        while (nextChar() in '0'.code..'9'.code) {
            buffer += curChar.toChar()
        }
        tokens.add(NumberToken(buffer.toLong()))
    }

    private fun addToken(token: Token) {
        tokens.add(token)
        nextChar()
    }

    fun tokenize(): List<Token> {
        nextChar()
        while (curChar != EOF) {
            while (isWhitespace(curChar)) nextChar()
            when (curChar) {
                '('.code -> addToken(Brace.LEFT)
                ')'.code -> addToken(Brace.RIGHT)
                '+'.code -> addToken(Operation.PLUS)
                '-'.code -> addToken(Operation.MINUS)
                '*'.code -> addToken(Operation.MUL)
                '/'.code -> addToken(Operation.DIV)
                in '0'.code..'9'.code -> addNumber()
                -1 -> break
                else -> throw ParseException(
                    "Illegal Character " + curChar.toChar(),
                    curPos
                )
            }
        }
        return tokens
    }
}

private const val EOF = -1