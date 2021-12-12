package visitors

import tokens.Brace
import tokens.NumberToken
import tokens.Operation
import tokens.Token
import java.lang.IllegalStateException
import kotlin.collections.ArrayDeque
import kotlin.collections.List


class CalcVisitor : TokenVisitor {
    private var stack = ArrayDeque<Long>()

    fun evaluate(tokens: List<Token>): Long {
        tokens.forEach { token -> token.accept(this) }
        check(stack.size == 1) { CALCULATION_ERROR_MSG }
        return stack.removeLast()
    }

    override fun visit(token: NumberToken) {
        stack.add(token.value)
    }

    override fun visit(token: Brace) {
        throw IllegalStateException(UNSUPPORTED_TOKEN_ERROR_MSG)
    }

    override fun visit(token: Operation) {
        check(stack.size >= 2) { CALCULATION_ERROR_MSG }
        val a = stack.removeLast()
        val b = stack.removeLast()
        stack.add(
            when (token) {
                Operation.PLUS -> a.plus(b)
                Operation.MINUS -> a.minus(b)
                Operation.MUL -> a.times(b)
                Operation.DIV -> a.div(b)
            }
        )
    }
}

private const val UNSUPPORTED_TOKEN_ERROR_MSG = "Reverse Polish notation required"
private const val CALCULATION_ERROR_MSG = "Calculation error"