package base

import java.util.stream.Stream
import kotlin.streams.asSequence

open class KtPuzzle(resourceLocation: String?) : Puzzle(resourceLocation) {

    fun sequenceInput(): Sequence<String> {
        return super.streamInput().asSequence()
    }

    override fun streamInput(): Stream<String> = throw RuntimeException("Use sequenceInput()!")
}