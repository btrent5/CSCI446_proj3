import kotlin.random.Random

fun generateWorld(dimension: Int = 0, pitFreq: Double = 0.2): MutableList<MutableList<Node>> {
    val startI = dimension - 1
    val startJ = 0

//        create world that is a 2D array of Nodes
    var world = mutableListOf<MutableList<Node>>()
    for (i in 0 until dimension) {
        var row = mutableListOf<Node>()
        for (j in 0 until dimension) {
//                nodes start as empty
            row.add(Node(' '))
        }
        world.add(row)
    }

//        assigns neighbors for all nodes in the world
    for (i in 0 until world.size) {
        for (j in 0 until world[i].size) {
//            if a node is that start, mark it
            if (i == startI && j == startJ) world[i][j].start = true

//            if a node is on the border that neighbor is set to to type '?'
            if (i == 0) {
                world[i][j].north = Node()
            } else {
                world[i][j].north = world[i - 1][j]
            }

            if (i == world.lastIndex) {
                world[i][j].south = Node()
            } else {
                world[i][j].south = world[i + 1][j]
            }

            if (j == 0) {
                world[i][j].west = Node()
            } else {
                world[i][j].west = world[i][j - 1]
            }

            if (j == world[i].lastIndex) {
                world[i][j].east = Node()
            } else {
                world[i][j].east = world[i][j + 1]
            }
        }
    }

    addFeatures(world, pitFreq)
    return world
}


fun selectCell(world: MutableList<MutableList<Node>>): Pair<Int, Int> {
    var i = 0
    var j = 0
    var valid = false
    while (!valid) {
        i = Random.nextInt(world.size)
        j = Random.nextInt(world.size)
        valid = (!world[i][j].start && world[i][j].type == ' ')
    }
    return Pair(i, j)
}

fun addFeatures(world: MutableList<MutableList<Node>>, pitFreq: Double = 0.2) {
    var temp = selectCell(world)
    world[temp.first][temp.second].type = 'W'
    temp = selectCell(world)
    world[temp.first][temp.second].glimmer = true

    for (i in 0 until world.size) {
        for (j in 0 until world.size) {
            if (Random.nextDouble() <= pitFreq // stochastic element
                    && !world[i][j].start // not the start
                    && world[i][j].type == ' ' // not the wumpus
                    && !world[i][j].glimmer) { // not the gold
                world[i][j].type = '0'
            }
        }
    }

    world.forEach { row: MutableList<Node> ->
        row.forEach { node: Node -> node.updateState() }
    }
}
