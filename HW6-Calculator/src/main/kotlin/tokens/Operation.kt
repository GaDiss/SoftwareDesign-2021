package tokens

import visitors.TokenVisitor

enum class Operation(
    private val char: Char,
    val priority: Int
) : Token {
    PLUS('+', 0), MINUS('-', 0), MUL('*', 1), DIV('/', 1);

    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }

    override fun toString(): String = char.toString()
}