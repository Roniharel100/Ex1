package ex1.src;

import java.util.*;

public class WGraph_DS implements weighted_graph, java.io.Serializable {

    /**
     * NodeInfo:
     * This class represents a individual node.
     */
    class NodeInfo implements node_info, java.io.Serializable, Comparable<node_info> {
        private int key;
        private String info;
        private double tag;

        /**
         * constructor for NodeInfo to create a new empty node with specific key.
         *
         * @param key int
         * @return none return
         */
        private NodeInfo(int key) {
            this.key = key;
            info = "";
            tag = 0;
        }

        /**
         * copy constructor for NodeInfo to create a new node with specific key, info and tag.
         *
         * @param n node_info
         * @return none return
         */
        private NodeInfo(node_info n) {
            this.key = n.getKey();
            this.info = n.getInfo();
            this.tag = n.getTag();
        }

        /**
         * This class sorts the priority queue from small to large tag.
         *
         * @param x node_info
         * @return ans int
         */
        public int compareTo(node_info x) {
            int ans = 0;
            if (this.getTag() > x.getTag())
                ans = 1;
            else if (this.getTag() < x.getTag())
                ans = -1;
            return ans;
        }

        /**
         * Return the key (id) associated with this node.
         * Note: each node_data should have a unique key.
         *
         * @return this.key int
         */
        @Override
        public int getKey() {
            return this.key;
        }

        /**
         * return the remark (meta data) associated with this node.
         * We use info to mark nodes in GraphAlgo class.
         *
         * @return info String
         */
        @Override
        public String getInfo() {
            return this.info;
        }

        /**
         * Allows changing the remark (meta data) associated with this node.
         * Update the value of the info.
         *
         * @param s String
         * @return non return
         */
        @Override
        public void setInfo(String s) {
            this.info = s;
        }

        /**
         * Temporal data (aka color: e,g, white, gray, black)
         * which can be used be algorithms
         * we use tag to count the path in GraphAlgo class.
         *
         * @return tag int
         */
        @Override
        public double getTag() {
            return this.tag;
        }

        /**
         * Allow setting the "tag" value for temporal marking an node - common
         * practice for marking by algorithms.
         * update the value of the tag.
         *
         * @param t - new value of the tag
         */
        @Override
        public void setTag(double t) {
            this.tag = t;
        }

        /**
         * get node's data.
         * return String
         */
        public String toString() {
            return " {NodeInfo key=" + key + ": tag=" + tag + ", info=" + info + "}\n";
        }

        /**
         * checks if two nodes are equal.
         *
         * @param o object
         * @return true- if they are equals, else- return false.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NodeInfo nodeInfo = (NodeInfo) o;
            return key == nodeInfo.key &&
                    Double.compare(nodeInfo.tag, tag) == 0 &&
                    Objects.equals(info, nodeInfo.info);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, info, tag);
        }

    }

    /**
     * neighborsMap:
     * This class represents a hashMap for the Neighbors.
     */
    private class neighborsMap implements java.io.Serializable {
        private HashMap<Integer, Double> neighborsMap;

        /**
         * constructor that create a new empty hashMap for the Neighbors.
         */
        private neighborsMap() {
            neighborsMap = new HashMap<Integer, Double>();
        }

        /**
         * checks if the Neighbors Hashmaps are equals.
         *
         * @return true- if they are equals, else- return false.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            neighborsMap that = (neighborsMap) o;
            return Objects.equals(neighborsMap, that.neighborsMap);
        }

        @Override
        public int hashCode() {
            return Objects.hash(neighborsMap);
        }
    }

    /**
     * WGraph_DS:
     * This class represents all the nodes in the graph.
     * This graph is an undirectional weighted graph.
     * The Data Structures that used is "HashMap" because it is very effective.
     * The first HashMap is represent the node in the graph.
     * The second HashMap is Type of "HashMap in HashMap" for the edges between one node to his Neighbors.
     */
    private HashMap<Integer, node_info> graphMap;
    private HashMap<Integer, neighborsMap> edgesMap;
    private int Edges = 0;
    private int Mc = 0;

    /**
     * constructor for WGraph_DS to create new empty hashMaps for the graph.
     *
     * @return none return
     */
    public WGraph_DS() {
        int Edges = 0;
        int Mc = 0;
        graphMap = new HashMap<Integer, node_info>();
        edgesMap = new HashMap<Integer, neighborsMap>();
    }

    /**
     * copy constructor for WGraph_DS to create a new graph with specific nodes and edges.
     *
     * @param g weighted_graph
     * @return none return
     */
    public WGraph_DS(weighted_graph g) {
        graphMap = new HashMap<Integer, node_info>();
        edgesMap = new HashMap<Integer, neighborsMap>();

        for (node_info temp1 : g.getV()) {
            this.graphMap.put(temp1.getKey(), new NodeInfo(temp1));
            this.edgesMap.put(temp1.getKey(), new neighborsMap());
        }
        for (node_info node : g.getV()) {
            for (node_info neighbor : g.getV(node.getKey())) {
                double edge = getEdge(node.getKey(), neighbor.getKey());
                connect(node.getKey(), neighbor.getKey(), edge);
            }
        }
        this.Edges = g.edgeSize();
        this.Mc = g.getMC();
    }

    /**
     * return the node_data by the node_id,
     * check if the node is in the graph by his key.
     * @param key key int
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_info getNode(int key) {
        if (this.graphMap.containsKey(key))
            return this.graphMap.get(key);
        else
            return null;
    }

    /**
     * return true iff (if and only if) there is an edge between node1 and node2
     * Note: this method should run in O(1) time.
     * check if the two nodes are in the graph by their keys.
     * if the two nodes are exist- check if the nodes are neighbors.
     * @param node1
     * @param node2
     * @return true -if they are Neighbors, else- false.
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        return graphMap.containsKey(node1) && graphMap.containsKey(node2) &&
                this.edgesMap.get(node1).neighborsMap.containsKey(node2);
    }

    /**
     * return the weight if the edge (node1, node1). In case
     * there is no such edge - should return -1
     * Note: this method should run in O(1) time.
     * check if node1 and node2 are Neighbors, and return the edge between them.
     * @param node1
     * @param node2
     * @return the edge between the nodes, -1 if the edge not exist.
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (hasEdge(node1, node2))
            return this.edgesMap.get(node1).neighborsMap.get(node2);
        else
            return -1;
    }

    /**
     * add a new node to the graph with the given key.
     * Note: this method should run in O(1) time.
     * Note2: if there is already a node with such a key -> no action should be performed.
     * check if the node is in the graph by his key.
     * if the node is not in the graph we add it to the graph.
     * else doing nothing.
     * @param key int
     * @return non return
     */
    @Override
    public node_info addNode(int key) {
        if (!this.graphMap.containsKey(key)) {
            node_info n = new NodeInfo(key);
            this.graphMap.put(key, n);
            this.edgesMap.put(key, new neighborsMap());
            Mc++;
        }
        return null;
    }

    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * Note: this method should run in O(1) time.
     * Note2: if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     * check if the nodes is in the graph by their keys.
     * check if the nodes are different.
     * If both are true- check if the nodes are not neighbors and connect between them.
     * if they are already neighbors- update their wight.
     * @param node1
     * @param node2
     * @return non return
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (this.graphMap.get(node1) != null && this.graphMap.get(node2) != null && node1 != node2) {
            if (!hasEdge(node1, node2)) {
                this.edgesMap.get(node1).neighborsMap.put(node2, w);
                this.edgesMap.get(node2).neighborsMap.put(node1, w);
                Edges++;
                Mc++;
            } else if (this.getEdge(node1, node2) != w) {
                this.edgesMap.get(node1).neighborsMap.put(node2, w);
                this.edgesMap.get(node2).neighborsMap.put(node1, w);
                Mc++;
            }
        }
    }

    /**
     * This method return a pointer (shallow copy) for a Collection representing all the nodes in the graph.
     * Note: this method should run in O(1) tim
     * return all Vertex in the graph
     * @return Collection<node_info>
     */
    @Override
    public Collection<node_info> getV() {
        return this.graphMap.values();
    }

    /**
     * This method returns a Collection containing all the nodes connected to node_id
     * Note: this method can run in O(k) time, k - being the degree of node_id.
     * check if the node is in the graph by his key, if it is true-  return a collection of the node Neighbors.
     * else return a empty collection.
     * @param node_id int
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        LinkedList<node_info> cN = new LinkedList<>();
        if (this.graphMap.containsKey(node_id)) {
            Collection<Integer> cI = this.edgesMap.get(node_id).neighborsMap.keySet();
            Iterator<Integer> temp = cI.iterator();
            while (temp.hasNext()) {
                int key = temp.next();
                cN.add(this.getNode(key));
            }
        }
        return cN;
    }

    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(n), |V|=n, as all the edges should be removed.
     * check if the node is in the graph by his key, if it is true-  delete the node from the graph.
     *  and return the deleted node, else return null.
     * @param key int
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_info removeNode(int key) {
        Mc++;
        if (this.graphMap.containsKey(key)) {
            Collection<Integer> c = this.edgesMap.get(key).neighborsMap.keySet();
            Iterator<Integer> i = c.iterator();
            while (i.hasNext()) {
                int node = i.next();
                this.edgesMap.get(node).neighborsMap.remove(key);
                Edges--;
            }
            return this.graphMap.remove(key);
        } else
            return null;
    }

    /**
     * Delete the edge from the graph,
     * Note: this method should run in O(1) time.
     * * check if the two nodes is in the graph by their keys, if it is true-  delete the edge between them.
     * @param node1
     * @param node2
     * @return non return
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (hasEdge(node1, node2)) {
            this.edgesMap.get(node1).neighborsMap.remove(node2);
            this.edgesMap.get(node2).neighborsMap.remove(node1);
            Edges--;
            Mc++;
        }
    }

    /**
     * return the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     * return the number of the nodes in the graph.
     * @return graphMap.size int, else return 0
     */
    @Override
    public int nodeSize() {
        return this.graphMap.size();
    }

    /**
     * return the number of edges (undirectional graph).
     * Note: this method should run in O(1) time.
     * return the number of the edges in the graph.
     * @return this.Edges int
     */
    @Override
    public int edgeSize() {
        return this.Edges;
    }

    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     * @return this.Mc int
     */
    @Override
    public int getMC() {
        return this.Mc;
    }

    /**
     * get all data in the graph.
     * @return s String
     */
    public String toString() {
        String s = new String("");
        for (node_info n : this.getV()) {
            s = s + n + edgesMap.get(n.getKey()).neighborsMap.keySet() + "\n";
        }
        return s;
    }

    /**
     * checks if the graphs are equals.
     *
     * @return true- if they are equals, else- return false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        return Edges == wGraph_ds.Edges &&
                Objects.equals(graphMap, wGraph_ds.graphMap) &&
                Objects.equals(edgesMap, wGraph_ds.edgesMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(graphMap, edgesMap, Edges, Mc);
    }

}