package trees;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import org.junit.Test;

public class TreeOperationsTest {
    public String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        + "abcdefghijklmnopqrstuvwxyz"
        + "0123456789";
    

    public Node<Character> generateRandomNode() {
        int rand_char = (int)(Math.random()*this.chars.length());
        return new Node<Character>(this.chars.charAt(rand_char), null, null);
    }

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
        // generate random full tree and store in an array (bfs order)
        // root of tree is indexed at 0
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

        // get bfs of random tree
        TreeOperations<String> tree = new TreeOperations<String>();
        ArrayList<String> bfs = tree.bfs(nodes.get(0));

        // test results
        assertTrue(bfs.size() == nodes.size());
        for( int i = 0; i < bfs.size(); i++ ){
            assertEquals(bfs.get(i), nodes.get(i).getContents());
        }
    }

    @Test
    public void bfs_incomplete_binary_tree_returns_same_order_of_elements_as_as_original() {
        // generate random incomplete tree
        // https://www.careercup.com/question?id=5701069362429952
        int num_nodes = (int) (Math.random() * 20 ) + 1;
        Queue q = new LinkedList();
        q.add(generateRandomNode());
        ArrayList< Node<Character> > nodes = new ArrayList<>();
        while( nodes.size() + q.size() <= num_nodes && q.size() > 0 ){
            int c = (int) Math.random() * 4;
            Node<Character> temp = (Node<Character>)q.poll();
            if( c == 0 ){ // node is a leaf
                ;
            } else if( c == 1 ){ // node has a left pointer
                Node<Character> left = generateRandomNode();
                q.add(left);
                temp.setLeftChild(left);
            } else if( c == 2 ){ // node has a right pointer
                Node<Character> right = generateRandomNode();
                q.add(right);
                temp.setRightChild(right);
            } else if ( c == 3 ){ // node has both left and right pointers
                Node<Character> left = generateRandomNode();
                Node<Character> right = generateRandomNode();
                q.add(left);
                q.add(right);
                temp.setLeftChild(left);
                temp.setRightChild(right);
            }
            nodes.add(temp);
        }

        TreeOperations<Character> tree = new TreeOperations<Character>();
        ArrayList<Character> bfs = tree.bfs(nodes.get(0));
        
        assertEquals(nodes.size(), bfs.size());
        for( int i = 0; i < bfs.size(); i++ ){
            assertTrue(nodes.get(i).getContents() == bfs.get(i));
        }
    }

    @Test
    public void dfs_tree_with_null_returns_empty_arraylist() {
        TreeOperations<Double> tree = new TreeOperations<Double>();
        Node<Double> n = null;
        ArrayList<Double> dfs = tree.dfs(n);
        assertTrue(dfs.size() == 0);
    }

    @Test
    public void dfs_tree_with_one_element_returns_arraylist_of_one_element() {
        TreeOperations<Character> tree = new TreeOperations<Character>();
        Node<Character> n = generateRandomNode();
        ArrayList<Character> dfs = tree.dfs(n);
        assertTrue(dfs.size() == 1);
        assertEquals(n.getContents(), dfs.get(0));
    }

    public Node<String> createTree(ArrayList< Node<String> > nodes, int index, int num_nodes ){
        if( index <= num_nodes ){
            int rand_index = (int)(Math.random()*chars.length());
            Node<String> ret = new Node(chars.charAt(rand_index) + "", null, null);
            nodes.add(ret);
            Node<String> left = createTree(nodes, index*2, num_nodes);
            Node<String> right = createTree(nodes, index*2+1, num_nodes);
            ret.setLeftChild(left);
            ret.setRightChild(right);
            return ret;
        } else {
            return null;
        }
    }
    
    @Test
    public void dfs_full_binary_tree_returns_same_order_of_elements_as_as_original() {
        // generate random full tree and store in an array (dfs order)
        // root of tree is indexed at 0
        int root = 4;
        int num_nodes = (int)Math.pow(2, root) - 1;

        ArrayList< Node<String> > nodes = new ArrayList<>();
        createTree(nodes, 1, num_nodes);

        // get bfs of random tree
        TreeOperations<String> tree = new TreeOperations<String>();
        ArrayList<String> dfs = tree.dfs(nodes.get(0));

        // test results
        assertTrue(dfs.size() == nodes.size());
        for( int i = 0; i < dfs.size(); i++ ){
            assertEquals(dfs.get(i), nodes.get(i).getContents());
        }
    }

}
