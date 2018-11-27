import kotlin.random.Random

fun generateWorld(dimension: Int = 0, pitFreq: Double = 0.2): MutableList<MutableList<Node>> {

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

fun addFeatures(world: MutableList<MutableList<Node>>, pitFreq: Double = 0.2, printing: Boolean = true) {

    if (printing) print("trying to add wumpus")
    var i = 0
    var j = 0
    var valid = false
    while (!valid){
        i = Random.nextInt(world.size)
        j = Random.nextInt(world.size)
        valid = (!(i == 0 && j == 0) && world[i][j].type == ' ')
        if (printing) print(".")
    }
    world[i][j].type = 'W'
    if (printing) println()

    if (printing) print("trying to add gold")
    i = Random.nextInt(world.size)
    j = Random.nextInt(world.size)
    while ((i > 1 && j > 1) && world[i][j].type != ' ') {
        i = Random.nextInt(world.size)
        j = Random.nextInt(world.size)
        if (printing) print(".")
    }
    world[i][j].glimmer = true
    if (printing) println()

    if (printing) println("adding pits")
    for (x in 0 until world.size) {
        for (y in 0 until world.size) {
            if (Random.nextDouble() <= pitFreq
                    && !(x == 0 && y == 0)
                    && world[x][y].type == ' ') {
                world[x][y].type = '0'
            }
        }
    }

    world.forEach { row: MutableList<Node> ->
        row.forEach { node: Node -> node.updateState() }
    }
}

