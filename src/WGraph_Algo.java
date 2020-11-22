package ex1.src;

import ex0.node_data;

import java.io.*;
import java.util.*;

/**
 * WGraph_Algo:
 * This class do algorithmic operations on an undirectional weighted graph that created by "WGraph_DS".
 */
public class WGraph_Algo implements weighted_graph_algorithms, java.io.Serializable {
    private weighted_graph g;

    /**
     * This constructor creates a new empty graph.
     * @return none return
     */
    public WGraph_Algo() {
        weighted_graph g = new WGraph_DS();
    }

    /**
     *  copy constructor for WGraph_Algo to create a new graph algo
     * @param newG graph
     * @return none return
     */
    public WGraph_Algo(weighted_graph newG) {
        this.g = newG;
    }

    /**
     * Init the graph on which this set of algorithms operates on.
     * change the value of the graph inside the object.
     * @param g
     * @return none return
     */
    @Override
    public void init(weighted_graph g) {
        this.g = g;
    }

    /**
     * Return the underlying graph of which this class works.
     * @return the this.g weighted_graph
     */
    @Override
    public weighted_graph getGraph() {
        return this.g;
    }

    /**
     * Compute a deep copy of this weighted graph.
     *  run first to add the nodes from the graph.
     * after run by the node to add all his Neighbors.
     * @return newG
     */
    @Override
    public weighted_graph copy() {
        weighted_graph newG = new WGraph_DS();
        for (node_info node : g.getV()){
            newG.addNode(node.getKey());
            newG.getNode(node.getKey()).setInfo(node.getInfo());
            newG.getNode(node.getKey()).setTag(node.getTag());
        }
        for (node_info node : g.getV()){
            for (node_info ni : g.getV(node.getKey())) {
                newG.connect(node.getKey(), ni.getKey(), g.getEdge(node.getKey(), ni.getKey()));
            }
        }
        return newG;
    }

    /**
     * dijkstra's algorithm:
     *  update the wight of the distance from the start node to each node by Priority Queue .
     *  return a hashMap with the previous node for each node.
     */

    private HashMap Dijkstra(node_info start) {

        if (g.getNode(start.getKey()) == null)  //check if the node is in the graph.
            return null;

        PriorityQueue<node_info> PQ = new PriorityQueue<>();
        HashMap<Integer, node_info> parentGraph = new HashMap<>();

        start.setTag(0); //set the first tag node to- 0
        start.setInfo("not visited");
        PQ.add(start);
        parentGraph.put(start.getKey(), null);

        //set all the tags to- infinity, and the info to- "not visited".
        for (node_info node : g.getV())
            if (node != start) {
                node.setTag(Integer.MAX_VALUE);
                node.setInfo("not visited");
                PQ.add(node);
            }

        while (!PQ.isEmpty()) {
            node_info current = PQ.peek();
            if (current.getTag() == Integer.MAX_VALUE) {
                PQ.poll();
                current.setInfo("outside node");
            }
            else {
                for (node_info n : g.getV(current.getKey())) { //check current neighbors
                    if (n.getInfo() == "not visited") {
                        double path = current.getTag() + g.getEdge(current.getKey(), n.getKey());
                        if (path < n.getTag()) {
                            n.setTag(path);
                            parentGraph.put(n.getKey(), current);
                        }
                    }
                }
                if (current.getInfo() != "outside node") {
                    current.setInfo("visited");
                    PQ.poll();
                }
            }
        }
        return parentGraph;
    }

    /**
     * Returns true if and only if (iff) there is a valid path from EVREY node to each other node.
     * NOTE: assume ubdirectional graph.
     * Uses Dijkstra algorithm to check if all the nodes have info="outside node",
     * if it is true ths graph is not connected.
     * @return true ig the graph is connected, else- return false.
     */
    @Override
    public boolean isConnected() {
        if (g == null) return true;
        if (g.nodeSize() == 1)
            return true;

        boolean temp = true;
        for (node_info start : g.getV()) {
            if (temp == true) {
                Dijkstra(g.getNode(start.getKey()));
                temp = false;
            }
            if (start.getInfo() == "outside node")
                return false;
        }
        return true;
    }

    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     * Uses Dijkstra algorithm to check if there is a path between src and dest.
     * if it is true- return the tag of the node. (the tag is the wight of the path).
     * @param src  - start node
     * @param dest - end (target) node
     * @return double of the weights by the path from src to dest,
     * if there is no path from src to dest return -1.
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (src == dest)
            return 0;

        if (g != null) {
            if (g.getNode(src) != null && g.getNode(dest) != null) {
                Dijkstra(g.getNode(src));
                if ( g.getNode(dest).getInfo() != "outside node")
                        return g.getNode(dest).getTag();
            }
        }
        return -1;
    }

    /**
     * @param parentHash HashMap
     * @param node node_info
     * @return Returns the HashMap of the previous nodes of the given node.
     */
    public node_info getFather(HashMap<Integer, node_info> parentHash, node_info node) {
        return parentHash.get(node.getKey());
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     * Uses Dijkstra algorithm to create the list of the path between src and dest.
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if (g.getNode(src) == null || g.getNode(dest) == null)
            return null;
        if(shortestPathDist(src, dest)==-1)
            return null;

        LinkedList<node_info> pathList = new LinkedList<node_info>();
        HashMap<Integer, node_info> parentHash = Dijkstra(g.getNode(dest));
        node_info node = parentHash.get(src);
        pathList.add(g.getNode(src));

        if (src == dest)
            return pathList;

        while (node.getKey() != dest) {
            pathList.add(node);
            node = getFather(parentHash, node);
        }
        pathList.add(g.getNode(dest));
        return pathList;

    }
    /**
     * Saves this weighted (undirected) graph to the given file name.
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved

     */
    @Override
    public boolean save(String file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.g);
            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            this.g = (weighted_graph) (objectInputStream.readObject());
            fileInputStream.close();
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}

