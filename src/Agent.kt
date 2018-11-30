class Agent(private val goldPoints: Int = 1000, private val movePoints: Int = 1,
            private val turnPoints: Int = 0, private val arrowPoints: Int = 10) {

    var hasArrow = true
    var hasGold = true
    var heardScream = false

    var direction = 1
//    val compass = mutableListOf("north", "east", "south", "west")

    var moveCount = 0
    var turnCount = 0

    lateinit var currentNode: Node

    fun solve(world: Pair<MutableList<MutableList<Node>>, Pair<Int, Int>>): Int {
        currentNode = world.first[world.second.first][world.second.second]

//        logic here eventually
        this.move()
        this.turn(3)
        this.move()

//        if not on the start node when the game ends, the agent died
        return calculateScore(!currentNode.start)
    }

    private fun move() {
        when (this.direction) {
            0 -> this.currentNode = this.currentNode.north
            1 -> this.currentNode = this.currentNode.east
            2 -> this.currentNode = this.currentNode.south
            3 -> this.currentNode = this.currentNode.west
            else -> error("Tried to move in an invalid direction")
        }
        print("moved into a node with the following traits: ${this.currentNode.type}")
        if (this.currentNode.start) print(", start")
        if (this.currentNode.breeze) print(", breeze")
        if (this.currentNode.stench) print(", stench")
        if (this.currentNode.glimmer) print(", glimmer")
        println()

        this.moveCount++

        this.currentNode.type = '#'
    }

    private fun turn(times: Int) {
        this.direction += times
        this.direction %= 4
        this.turnCount++
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