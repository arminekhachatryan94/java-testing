package trees;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;

public class TreeOperationsTest {
    public String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        + "abcdefghijklmnopqrstuvwxyz"
        + "0123456789";
    
    @Test
    public void bfs_tree_with_null_returns_empty_arraylist() {
        TreeOperations<Integer> tree = new TreeOperations<Integer>();
        Node<Integer> n = null;
        ArrayList<Integer> a = tree.bfs(n);
        assertTrue(a.size() == 0);
    }
    @Test
    public void bfs_tree_with_one_element_returns_arraylist_of_one_element() {
        TreeOperations<Integer> tree = new TreeOperations<Integer>();
        Node<Integer> n = new Node<Integer>((int)Math.random(), null, null);
        ArrayList<Integer> a = tree.bfs(n);
        assertTrue(a.size() == 1);
        assertEquals(n.getContents(), a.get(0));
    }

    @Test
    public void bfs_full_binary_tree_returns_same_order_of_elements_as_as_original() {
        TreeOperations<String> tree = new TreeOperations<String>();
        int num_nodes = (int)Math.pow(2, 4) - 1;
        ArrayList< Node<String> > nodes = new ArrayList<>();
        for( int i = num_nodes; i >= 1; i-- ){
            Node<String> temp;
            Random rand = new Random();
            Integer random_num = rand.nextInt(50) + 1;
            if( i > num_nodes/2 && i <= num_nodes ){
                temp = new Node(random_num.toString(), null, null);
            } else {
                temp = new Node(random_num.toString(), nodes.get(i-1), nodes.get(i));
            }
            if( nodes.size() == 0 ){
                nodes.add(temp);
            }
            else nodes.add(0, temp);
        }

        ArrayList<String> bfs = tree.bfs(nodes.get(0));

        assertTrue(bfs.size() == nodes.size());
        for( int i = 0; i < bfs.size(); i++ ){
            assertEquals(bfs.get(i), nodes.get(i).getContents());
        }
    }
    
}
