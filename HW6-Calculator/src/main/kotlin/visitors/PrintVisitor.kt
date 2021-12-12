package visitors

import tokens.Brace
import tokens.NumberToken
import tokens.Operation
import tokens.Token
import java.io.OutputStream

class PrintVisitor(private val output: OutputStream) : TokenVisitor {

    fun print(tokens: List<Token>) {
        tokens.forEach { token -> token.accept(this) }
    }

    private fun writeToken(token: Token) {
        output.write("$token ".toByteArray())
    }

    override fun visit(token: NumberToken) = writeToken(token)

    override fun visit(token: Brace) = writeToken(token)

    override fun visit(token: Operation) = writeToken(token)
}