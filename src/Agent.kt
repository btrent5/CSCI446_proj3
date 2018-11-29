class Agent(private val goldPoints: Int = 1000, private val movePoints: Int = 1,
            private val turnPoints: Int = 0, private val arrowPoints: Int = 10) {

    var hasArrow = true
    var hasGold = false
    var heardScream = false

    var moveCount = 0
    var turnCount = 0

    fun solve(world: MutableList<MutableList<Node>>): Int {
        var current = Node()

//        logic here eventually

//        if not on the start node when the game ends, the agent died
        return calculateScore(!current.start)
    }

    private fun calculateScore(died: Boolean): Int {
        var score = 0
//        assumes that this function will only get called
//        when the agent has returned to the start node or died
        if (died) {
            score -= goldPoints
        } else if (this.hasGold) {
            score += goldPoints
        }
        if (!hasArrow) {
            score -= arrowPoints
        }

        score -= moveCount * movePoints
        score -= turnCount * turnPoints

        return score
    }
}