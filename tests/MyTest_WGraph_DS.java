package ex1.tests;
import ex1.src.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test WGraph_DS
 */
public class MyTest_WGraph_DS {
    weighted_graph graph;

    public weighted_graph createMyGraph() {
        weighted_graph myG = new WGraph_DS();
        for (int i = 0; i <=4; i++) {
            myG.addNode(i);
        }
        myG.connect(0, 1, 2);
        myG.connect(0, 3, 4);
        myG.connect(1, 2, 1);
        myG.connect(1, 4, 2);
        myG.connect(2, 3, 16);
        myG.connect(2, 4, 0);
        return myG;
    }

    public weighted_graph createMyGraph2(int size) {
        weighted_graph myG = new WGraph_DS();
        for (int i = 0; i <= 4; i++) {
            myG.addNode(i);
        }
        return myG;
    }

    @BeforeEach
    public void resetGraph (){
            graph= new WGraph_DS();
    }

    @Test
    void getNodeTest() {
       weighted_graph g=createMyGraph();
       assertEquals(4, g.getNode(4).getKey());
       assertNotNull(g.getNode(3)); //node is exist
        assertNull(g.getNode(70), "the node is not exist");
    }

    @Test
    void hasEdgeTest() {
        weighted_graph g=createMyGraph();
        assertNotNull(g.getEdge(3, 2)); //edge is exist
        assertEquals(-1, g.getEdge(1, 3), "the edge is not exist");
        assertEquals(g.getEdge(1, 0), g.getEdge(1, 4)); // the edges are equals
        assertNotEquals(g.getEdge(1, 0), g.getEdge(1, 2), "the edges are not equals");
    }

    @Test
    void getEdgeTest() {
        weighted_graph g=createMyGraph();
        assertEquals(16, g.getEdge(3, 2));
        assertNotEquals(5, g.getEdge(0, 3));
    }

    @Test
    void addNodeTest() {
        weighted_graph g=createMyGraph();

        g.addNode(5); // add positive node
        assertNotNull(g.getNode(5));
        g.addNode(-5); // add negative node
    }

    @Test
    void connectTest() {
        weighted_graph g=createMyGraph();
        g.connect(0,2,6);
        assertNotNull(g.hasEdge(0,2));
        assertEquals(6,g.getEdge(0,2) );
    }

    @Test
    void getVTest() {
        weighted_graph g=createMyGraph();
        assertNotNull(g.getV());
        Collection <node_info> l = g.getV();
        int[] nodes = {0, 1, 2, 3, 4};
        int i=0;
        for(node_info n: l){
            assertEquals(n.getKey(), nodes[i]);
            i++;
        }
    }

    @Test
    void getVNeighborTest() {
        weighted_graph g=createMyGraph();
        assertNotNull(g.getV(1));
        Collection <node_info> l = g.getV(1);
        int[] nodes = {0,2,4};
        int i=0;
        for(node_info n: l){
            assertEquals(n.getKey(), nodes[i]);
            i++;
        }
    }


    @Test
    void removeNodeTest() {
        weighted_graph g=createMyGraph();
        g.removeNode(0);
        assertEquals(4,g.nodeSize());
        assertNull(g.getNode(0));
    }

    @Test
    void removeEdgeTest() {
        weighted_graph g=createMyGraph();
        g.removeEdge(0,3);
        assertEquals(-1,g.getEdge(0,3));
        assertEquals(5,g.nodeSize());

    }

    @Test
    void nodeSizeTest() {
        weighted_graph g=createMyGraph();
        assertEquals(5,g.nodeSize());
    }

    @Test
    void edgeSizeTest() {
        weighted_graph g=createMyGraph();
        assertEquals(6,g.edgeSize());
    }

    @Test
    void getMCTest() {
        weighted_graph g=createMyGraph();
        g.addNode(5);
        g.addNode(6);
        g.removeNode(3);
        g.removeEdge(1,4);
        assertEquals(15,g.getMC());
    }

    @Test
    void testToString() {
        weighted_graph g=createMyGraph();
        System.out.println(g);
    }

    @Test
    public void equalTest()
    {
        weighted_graph g= createMyGraph();
        assertEquals(g,g);
       weighted_graph g2=createMyGraph2(100);
       assertNotEquals(g,g2);
        weighted_graph g3= createMyGraph();
        g.connect(1,4,2);
        assertNotEquals(g,g2);
    }
}