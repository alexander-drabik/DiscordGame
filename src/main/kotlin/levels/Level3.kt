package levels

class Level3 : Level() {
    override var size = 5
    override var layout = mutableListOf(
        0, 4, 0, 0, 0,
        0, 0, 0, 0, 0,
        0, 0, 0, 0, 0,
        1, 2, 2, 4, 0,
        0, 0, 0, 0, 0,
    )
}