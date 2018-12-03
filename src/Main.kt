fun main(args : Array<String>) {
    var temp = generateWorld(4)
    println("scored ${Agent().solve(temp)} points")
    for (row in temp.first) {
        for (node in row) {
            print(node.type)
        }
        println()
    }
}

fun printWorld(world: Pair<MutableList<MutableList<Node>>, Pair<Int, Int>>){
    for (row in world.first) {
        for (node in row) {
            print(node.type)
        }
        println()
    }
}