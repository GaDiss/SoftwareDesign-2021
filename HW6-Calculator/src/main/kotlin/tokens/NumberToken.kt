package tokens

import visitors.TokenVisitor

class NumberToken(val value: Long) : Token {

    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }

    override fun toString(): String = value.toString()
}