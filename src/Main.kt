fun main() {
    var temp = generateWorld(4)

    for (row in temp) {
        for (node in row) {
            print(node.type)
        }
        println()
    }
}