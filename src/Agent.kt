class Agent(private val goldPoints: Int = 1000, private val movePoints: Int = 1,
            private val turnPoints: Int = 0, private val arrowPoints: Int = 10) {

    var hasArrow = true
    var hasGold = false
    var heardScream = false

    var direction = 1
//    val compass = mutableListOf("north", "east", "south", "west")

    var moveCount = 0
    var turnCount = 0
    lateinit var world: Pair<MutableList<MutableList<Node>>, Pair<Int, Int>>

    lateinit var currentNode: Node

    var directionsMoved = mutableListOf<Int>()

    fun solve(world: Pair<MutableList<MutableList<Node>>, Pair<Int, Int>>): Int {
        this.world = world
        currentNode = world.first[world.second.first][world.second.second]

//        logic here eventually
        //Cautious solve vs Risk-taker solve


        // Depth First Search all safe nodes 'connected' to start
        var safeNeighbors = currentNode.neighbors.filter { neighbor -> neighbor.safe }
        loop@ while(safeNeighbors.isNotEmpty()){
            if(hasGold) break
            var nodeAhead = nodeAhead()
            if (nodeAhead.type != '?' && nodeAhead.safe && !nodeAhead.visited ){
                this.move()
            }
            else{
                when {
                    checkNode(this.currentNode.north) -> {
                        turn("north")
                        move()
                    }
                    checkNode(this.currentNode.east) -> {
                        turn("east")
                        move()
                    }
                    checkNode(this.currentNode.south) -> {
                        turn("south")
                        move()
                    }
                    checkNode(this.currentNode.west) -> {
                        turn("west")
                        move()
                    }
                    else -> {
                        if(currentNode.start) break@loop
                        var dir = directionsMoved.removeAt(directionsMoved.size - 1)
                        when(dir) {
                            0 -> turn(2 - this.direction)
                            1 -> turn(3 - this.direction)
                            2 -> turn(0 - this.direction)
                            3 -> turn(1 - this.direction)
                        }
                        move(true)
                    }
                }
            }
        }

        if(hasGold){
            while(directionsMoved.isNotEmpty()){
                var dir = directionsMoved.removeAt(directionsMoved.size - 1)
                when(dir)
                {
                    0 -> turn(2 - this.direction)
                    1 -> turn(3 - this.direction)
                    2 -> turn(0 - this.direction)
                    3 -> turn(1 - this.direction)
                }
                move(true)
            }
        }

//        if not on the start node when the game ends, the agent died
        return calculateScore(!currentNode.start)
    }

    private fun checkNode(node : Node) : Boolean{
        return node.safe && !node.visited && node.type != '?'
    }


    private fun turn(direction : String){
        var directions = HashMap<String, Int>()
        directions["north"] = 0
        directions["east"] = 1
        directions["south"] = 2
        directions["west"] = 3
        turn(directions[direction]?.minus(this.direction) ?: 0)
    }

    private fun nodeAhead() : Node {
        return when (this.direction) {
            0 -> this.currentNode.north
            1 -> this.currentNode.east
            2 -> this.currentNode.south
            3 -> this.currentNode.west
            else -> error("Tried to move in an invalid direction")
        }
    }

    private fun move(inverting : Boolean = false) {
        printWorld(world)
        if(!inverting) {
            directionsMoved.add(this.direction)
        }
        when (this.direction) {
            0 -> {
                this.currentNode = this.currentNode.north
                println("Moved north")
            }
            1 -> {
                this.currentNode = this.currentNode.east
                println("Moved east")
            }
            2 -> {
                this.currentNode = this.currentNode.south
                println("Moved south")
            }
            3 -> {
                this.currentNode = this.currentNode.west
                println("Moved west")
            }
            else -> error("Tried to move in an invalid direction")
        }
        this.currentNode.visited = true
        print("moved into a node with the following traits: ${this.currentNode.type}")
        if (this.currentNode.start){
            print(", start")
        }
        if (this.currentNode.breeze) {
            print(", breeze")
            currentNode.neighbors.forEach { neighbor ->
                if (!neighbor.safe && neighbor.possibleValue.contains('W'))
                    neighbor.safe = true
                else if (neighbor.possibleValue.isEmpty()) {
                    neighbor.possibleValue.add('0')
                    neighbor.safe = false
                }
            }
        }
        if (this.currentNode.stench) {
            print(", stench")
            currentNode.neighbors.forEach { neighbor ->
                if (!neighbor.safe && neighbor.possibleValue.contains('0'))
                    neighbor.safe = true
                else if (neighbor.possibleValue.isEmpty()) {
                    neighbor.possibleValue.add('W')
                    neighbor.safe = false
                }
            }
        }
        if (this.currentNode.glimmer) {
            print(", glimmer")
            println()
            print("Picks up gold")
            this.hasGold = true
        }
        println()

        this.moveCount++

        if(this.currentNode.type != '*') this.currentNode.type = '#'
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