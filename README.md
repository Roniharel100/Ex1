# Ex1

## Introduction:
In this project there are four classes.
Three of them use in order to implement three interfaces.
And there is one more class that serves as an auxiliary class.
The goal of the project is to implement an unidirectional weighted graph.

## The first class: NodeInfo
This is a node class.
The class purpose is to define a node in a graph and implement different function.
We can see in this class following functions:
change a key, change a tag, change a remark (info).
This is a private class that is in the graph class. 
As a result, nodes cannot be accessed directly.

## The second class: neighborsMap
This class is an auxiliary class.
The role of the class is to represent and save each node's neighbors.
In this class I chose to use data structure type HashMap.
 because it can perform actions by o(1).

## The third class: WGraph_DS
This is a graph class.
The class purpose is to present a graph that contains several nodes with edges between them.
In this class we used 2 hashMaps, one for all the nodes in the graph, and other to the neighbors and the weight.
I choose to use HashMap because it can perform by o(1).
We can see that the class contains following functions:
adding and removing a node, connecting and removing an edge between two nodes, returning a collection Of all the nodes in the graph, returning a collection of all the node's neighbors, counts the number of changes in the graph, check if two nodes are neighbors.


## The four class: WGraph_Algo
This is an algorithm graph class.
The class purpose is to implement a number of algorithms on a graph.
 I want to give a brief explanation of each algorithm ability:
Check if the graph is linkable, counts the length of shortest path between two given nodes, return a list of nodes that represents the shortest path between two given nodes, graph deep copying, saving a graph to a file, restoring the graph from the file (load).

In order to implement the above algorithms, I used the dijkstra algorithm.
This algorithm goes through the graph.
It start with the node he get (the start node) and counts fore each node the shortest distance for the start.
It returns a list of nodes, and to each of them return the parent node.

