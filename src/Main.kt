fun main(args : Array<String>) {
    var scores = mutableListOf<Int>()
    for (i in 0 .. 1000 ) {
        var temp = generateWorld(10)
        scores.add(Agent().solve(temp))
        println("scored ${scores[i]} points")
        printWorld(world = temp)
    }
    println("Average score is ${scores.average()}")
}

fun printWorld(world: Pair<MutableList<MutableList<Node>>, Pair<Int, Int>>){
    for (row in world.first) {
        for (node in row) {
            print(node.type)
        }
        println()
    }
}