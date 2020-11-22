package ex1.tests;
import ex1.src.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test WGraph_Algo
 */
public class MyTest_WGraph_Algo {
    weighted_graph graph;
    weighted_graph_algorithms ga;

    public weighted_graph createMyGraph() {
        weighted_graph myG = new WGraph_DS();
        for (int i = 0; i <= 4; i++) {
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
        for (int i = 0; i <= size; i++) {
            myG.addNode(i);
        }
        return myG;
    }

    @BeforeEach
    public void resetGraph() {
        graph = new WGraph_DS();
        ga = new WGraph_Algo();
        ga.init(graph);
    }


    @Test
    void initTest() {
        weighted_graph g = createMyGraph();
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        assertEquals(ga.getGraph(), g);

    }

    @Test
    void getGraphTest() {
        weighted_graph g = createMyGraph();
        g = ga.getGraph();
        assertEquals(g, ga.getGraph());
    }

    @Test
    void copyTest() {
        weighted_graph g1 = createMyGraph();
        weighted_graph_algorithms g2 = new WGraph_Algo();
        g2.init(g1);
        weighted_graph g3 = g2.copy();
        System.out.println("g1 = " + g1);
        System.out.println("g3 = " + g3);
        System.out.println(g1.equals(g3));
        assertEquals(g1, g3);
        g1.connect(0, 2, 3);
        assertFalse(g3.hasEdge(0, 2));
        assertNotSame(g1, g3);//on memory

    }

    @Test
    void isConnectedTest() {
        weighted_graph g1 = createMyGraph();
        weighted_graph_algorithms g2 = new WGraph_Algo(g1);
        assertTrue(g2.isConnected());
        g1.addNode(5);
        assertFalse(g2.isConnected());
        weighted_graph g3 = new WGraph_DS();
        g2.init(g3);
        g3.addNode(5);
        assertTrue(g2.isConnected());// graph with 1 node is connected
        g3.addNode(2);
        assertFalse(g2.isConnected());
        g3.connect(5, 2, 1);
        assertTrue(g2.isConnected());
    }

    @Test
    void shortestPathDistTest() {
        weighted_graph g1 = createMyGraph();
        weighted_graph_algorithms g2 = new WGraph_Algo(g1);
        assertEquals(6, g2.shortestPathDist(1, 3));
        assertEquals(1, g2.shortestPathDist(1, 4));
        assertEquals(0, g2.shortestPathDist(1, 1));
        assertEquals(-1, g2.shortestPathDist(9, 2));
        weighted_graph g3 = createMyGraph2(5);
        g2.init(g3);
        g3.connect(2, 3, 5);
        assertEquals(-1, g2.shortestPathDist(0, 1));
        assertEquals(5, g2.shortestPathDist(2, 3));
        WGraph_Algo g4 = new WGraph_Algo();
        assertEquals(-1, g4.shortestPathDist(0, 1)); //empty graph

        g2.init(g1);
        g1.addNode(5);
        g1.addNode(6);
        g1.connect(5, 6, 4);
        assertEquals(-1, g2.shortestPathDist(1, 6));
        g1.connect(4, 5, 3);
        assertEquals(8, g2.shortestPathDist(1, 6));
    }

    @Test
    void shortestPathTest() {
        weighted_graph g1 = createMyGraph();
        weighted_graph_algorithms g2 = new WGraph_Algo(g1);
        List<node_info> List = g2.shortestPath(1, 3);
        int[] nodes = {1, 0, 3};
        int i = 0;
        for (node_info n : List) {
            assertEquals(n.getKey(), nodes[i]);
            i++;
        }
        List = g2.shortestPath(1, 1);
        for (node_info n : List) {
            assertEquals(n.getKey(), 1);
            i++;
        }
        g1.addNode(8);
        g1.addNode(7);
        g1.connect(8, 7, 3);
        assertNull(g2.shortestPath(1, 8));
        assertEquals(2, g2.shortestPath(8, 7).size());
    }

    @Test
    void save_load_Test() {
        weighted_graph g1 = createMyGraph();
        weighted_graph_algorithms g2 = new WGraph_Algo(g1);
        String name = "g2.object";
        assertTrue(g2.save(name));
        assertTrue(g2.load(name));
        assertEquals(g2.getGraph(), g1);
    }

    @Test
    void BuildGraphWithMillionsNodes() {
        assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
            long start = System.currentTimeMillis();
            weighted_graph g = createMyGraph2(1000000);
            for (int i = 0; i < g.nodeSize(); i++)
                for (int j = 0; j < 10; j++)
                    g.connect(i, j, 2.2);
            long end = System.currentTimeMillis();
            double time = end - start / 1000;
            System.out.println("time to build a graph with 1000000 nodes and 10 edges is: " + time);
        });
    }

    @Test
    void copyGraphMillion() {
        weighted_graph g = createMyGraph2(1000000);
        for (int i = 0; i < g.nodeSize(); i++)
            for (int j = 0; j < 10; j++)
                g.connect(i, j, 2.2);
        assertTimeoutPreemptively(Duration.ofMillis(10000), () -> {
            long start = System.currentTimeMillis();
            weighted_graph gCopy = new WGraph_DS(g);
            long end = System.currentTimeMillis();
            double time = end - start / 1000;
            System.out.println("running time " + time);
        });
    }

    @Test
    void shortestPathDistMillion() {
        weighted_graph g1 = createMyGraph2(1000000);
        for (int i = 0; i < g1.nodeSize(); i++)
            for (int j = 0; j < 10; j++)
                g1.connect(i, j, 2.2);
        weighted_graph_algorithms g2 = new WGraph_Algo(g1);
        assertTimeoutPreemptively(Duration.ofMillis(10000), () -> {
            long start = System.currentTimeMillis();
            double dist = g2.shortestPathDist(0, 1000000);
            long end = System.currentTimeMillis();
            double time = end - start / 1000;
            System.out.println("running time " + time);
        });
    }

}
