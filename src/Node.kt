class Node(var type: Char = '?') {
    lateinit var north: Node
    lateinit var east: Node
    lateinit var south: Node
    lateinit var west: Node
    val neighbors get() = mutableListOf(north, east, south, west)

    var stench = false
    var breeze = false
    var glimmer = false

    fun updateState() {
        this.stench = this.neighbors.any { node -> node.type == 'W' && this.type == ' ' }
        this.breeze = this.neighbors.any { node -> node.type == '0' && this.type == ' ' }
        if (glimmer) this.type = '*'
    }
}