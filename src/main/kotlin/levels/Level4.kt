package levels

class Level4 : Level() {
    override var size = 5
    override var layout = mutableListOf(
         3, 3, 1, 3, 0,
         3, 3, 2, 4, 0,
         0, 2, 0, 0, 0,
         0, 4, 0, 0, 0,
         0, 0, 0, 0, 0,
    )
}