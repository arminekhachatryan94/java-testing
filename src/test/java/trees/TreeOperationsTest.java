package trees;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class TreeOperationsTest {
    public TreeOperations<Integer> tree = new TreeOperations<Integer>();

    @Test
    public void bfs_tree_with_one_element_returns_arraylist_of_one_element() {
        Node<Integer> n = new Node<Integer>((int)Math.random(), null, null);
        ArrayList<Integer> a = tree.bfs(n);
        assertTrue(a.size() == 1);
        assertEquals(n.getContents(), a.get(0));
    }

    @Test
    public void bfs_of_full_binary_tree_returns_same_order_of_elements_as_as_original() {
        int num_nodes = (int)Math.pow(2, 4) - 1;
        ArrayList< Node<Integer> > nodes = new ArrayList<>();
        for( int i = num_nodes; i >= 1; i-- ){
            Node<Integer> temp;
            if( i > num_nodes/2 && i <= num_nodes ){
                temp = new Node(i, null, null);
            } else {
                temp = new Node(i, nodes.get(i-1), nodes.get(i));
            }
            if( nodes.size() == 0 ){
                nodes.add(temp);
            }
            else nodes.add(0, temp);
        }

        ArrayList<Integer> bfs = tree.bfs(nodes.get(0));

        assertTrue(bfs.size() == nodes.size());
        for( int i = 0; i < bfs.size(); i++ ){
            assertEquals(bfs.get(i), nodes.get(i).getContents());
        }
    }
    
}
