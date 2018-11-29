fun main() {
    var temp = generateWorld(4)
    println("scored ${Agent().solve(temp)} points")
    for (row in temp) {
        for (node in row) {
            print(node.type)
        }
        println()
    }
}