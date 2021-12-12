package visitors

import tokens.Brace
import tokens.NumberToken
import tokens.Operation
import tokens.Token

class ParserVisitor : TokenVisitor {
    private val rpn = ArrayList<Token>()
    private val stack = ArrayDeque<Token>()

    fun parse(tokens: List<Token>): List<Token> {
        tokens.forEach { token -> token.accept(this) }

        while (!stack.isEmpty()) {
            val curToken = stack.removeLast()
            check(curToken is Operation) { MISSING_RB_ERROR_MSG }
            rpn.add(curToken)
        }

        return rpn
    }

    override fun visit(token: NumberToken) {
        rpn.add(token)
    }

    override fun visit(token: Brace) {
        if (token == Brace.LEFT) {
            stack.add(token)
            return
        }

        check(!stack.isEmpty()) { MISSING_LB_ERROR_MSG }
        var top: Token
        while (stack.removeLast().also { top = it } != Brace.LEFT) {
            check(!stack.isEmpty()) { MISSING_LB_ERROR_MSG }
            rpn.add(top)
        }
    }

    override fun visit(token: Operation) {
        while (!stack.isEmpty()) {
            val top = stack.last()
            if (top is Operation && top.priority >= token.priority) {
                rpn.add(stack.removeLast())
            } else break
        }

        stack.add(token)
    }
}

private const val MISSING_LB_ERROR_MSG = "Parse error: Missing left bracket"
private const val MISSING_RB_ERROR_MSG = "Parse error: Missing right bracket"