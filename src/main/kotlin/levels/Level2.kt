package levels

object Level2 : Level() {
    override var size = 5
    override var layout = mutableListOf(
        4, 0, 0, 0,
        0, 0, 0, 0,
        0, 0, 2, 0,
        0, 0, 0, 0,
        0, 0, 1, 0,
    )
}