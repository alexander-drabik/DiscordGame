object Level {
    const val size = 5

    var layout = mutableListOf(
        0, 0, 0, 0, 0,
        0, 1, 0, 0, 0,
        0, 0, 0, 0, 0,
        0, 0, 2, 0, 0,
        0, 0, 0, 0, 0
    )

    fun generateMessage(): String {
        var level = ""
        for ((index, pole) in Level.layout.withIndex()) {
            if (index%5 == 0) {
                level += '\n'
            }
            when (pole) {
                0 -> level += ":black_large_square:"
                1 -> level += ":grinning:"
                2 -> level += ":white_square_button:"
            }
        }

        return level
    }
}