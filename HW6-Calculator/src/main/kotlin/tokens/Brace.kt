package tokens

import visitors.TokenVisitor

enum class Brace(private val char: Char) : Token {
    LEFT('('), RIGHT(')');

    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }

    override fun toString(): String = char.toString()
}