fun main(args : Array<String>) {
    var scores = mutableListOf<Int>()
    var solveable = 0
    var solved = 0
    for (i in 0 .. 1000 ) {
        var temp = generateWorld(10)
        scores.add(Agent().solve(temp))
        if(worldIsSolveable(temp)) solveable++
        println("scored ${scores[i]} points")
        if(scores[i] > 0) solved++
        printWorld(world = temp)
    }
    println("Average score is ${scores.average()}")
    println("There were $solveable solveable puzzles out of 1000")
    println("There were $solved puzzles solved out of 1000")
}

fun worldIsSolveable(world: Pair<MutableList<MutableList<Node>>, Pair<Int, Int>>) : Boolean {
    var goldX = 0
    var goldY = 0
    for(i in 0 .. (world.first.count() - 1)){
        for (j in 0 .. (world.first[0].size - 1)){
            if(world.first[i][j].glimmer){
                goldX = j
                goldY = i
                println("Gold is $goldX,$goldY")
                break
            }
        }
    }
    if(world.first[goldY][goldX].type == '0' || world.first[goldY][goldX].type == 'W'){
        return false
    }
    else if(world.first[goldY][goldX].neighbors.all { neighbor -> neighbor.type == '0' || neighbor.type == '?' || neighbor.type == 'W' }){
        return false
    }
    //else if(!searchWorld(world)) return false

    return true
}

fun searchWorld(world: Pair<MutableList<MutableList<Node>>, Pair<Int, Int>>) : Boolean{
    for(row in world.first){
        for(node in row){
            node.visited = false
            node.safe = node.type == ' ' || node.type == '#' || node.type == '*'
        }
    }

    var currentNode = world.first[world.second.first][0]
    var safeNeighbors = mutableListOf(currentNode.neighbors.filter { neighbor -> neighbor.safe }.toMutableList())
    while(safeNeighbors.isNotEmpty()){
        if (currentNode.glimmer){
            return true
        }else{
            var temp = currentNode.neighbors.filter { neighbor -> neighbor.safe && !neighbor.visited && neighbor.type != '?'}
            if(temp.count() > 0) {
                safeNeighbors.add(temp.toMutableList())
            }

            currentNode = safeNeighbors.last().removeAt(0)
            currentNode.visited = true
        }
        if(safeNeighbors.last().isEmpty()){
            safeNeighbors.removeAt(safeNeighbors.count() - 1)
        }
    }
    print("magix")
    return false
}

fun printWorld(world: Pair<MutableList<MutableList<Node>>, Pair<Int, Int>>){
    for (row in world.first) {
        for (node in row) {
            print(node.type)
        }
        println()
    }
}