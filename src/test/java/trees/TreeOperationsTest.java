package trees;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class TreeOperationsTest {
    public String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        + "abcdefghijklmnopqrstuvwxyz"
        + "0123456789";
    
    public int MAX_DEPTH = 0;
    public int count_nodes = 0;

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
    public void bfs_full_binary_tree_returns_same_order_of_elements_as_original() {
        // generate random full tree and store in an array (bfs order)
        // root of tree is indexed at 0
        int num_nodes = (int)Math.pow(2, 10) - 1;
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
    public void bfs_incomplete_binary_tree_returns_same_order_of_elements_as_original() {
        // generate random incomplete tree
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

    public Node<String> createFullTree(ArrayList< Node<String> > nodes, int index, int num_nodes ){
        if( index <= num_nodes ){
            int rand_index = (int)(Math.random()*chars.length());
            Node<String> ret = new Node(chars.charAt(rand_index) + "", null, null);
            nodes.add(ret);
            Node<String> left = createFullTree(nodes, index*2, num_nodes);
            Node<String> right = createFullTree(nodes, index*2+1, num_nodes);
            ret.setLeftChild(left);
            ret.setRightChild(right);
            return ret;
        } else {
            return null;
        }
    }
    
    @Test
    public void dfs_full_binary_tree_returns_same_order_of_elements_as_original() {
        // generate random full tree and store in an array (dfs order)
        // root of tree is indexed at 0
        int root = 10;
        int num_nodes = (int)Math.pow(2, root) - 1;

        ArrayList< Node<String> > nodes = new ArrayList<>();
        createFullTree(nodes, 1, num_nodes);

        // get bfs of random tree
        TreeOperations<String> tree = new TreeOperations<String>();
        ArrayList<String> dfs = tree.dfs(nodes.get(0));

        // test results
        assertTrue(dfs.size() == nodes.size());
        for( int i = 0; i < dfs.size(); i++ ){
            assertEquals(dfs.get(i), nodes.get(i).getContents());
        }
    }

    public Node<Double> createIncompleteTree(ArrayList< Node<Double> > nodes, int current_depth, int depth ){
        if( current_depth <= depth ){
            if( current_depth > this.MAX_DEPTH ){
                this.MAX_DEPTH = current_depth;
            }
            double min = 0;
            double max = 50;
            Random rand = new Random();
            double rand_double = min+(max-min)*rand.nextDouble();
            Node<Double> ret = new Node(rand_double, null, null);
            nodes.add(ret);
            this.count_nodes++;

            int children = (int) (Math.random()*4);
            if( children == 0 ){
                ;
            } else if( children == 1 ){
                Node<Double> left = createIncompleteTree(nodes, current_depth+1, depth);
                ret.setLeftChild(left);
            } else if( children == 2 ){
                Node<Double> right = createIncompleteTree(nodes, current_depth+1, depth);
                ret.setRightChild(right);
            } else if( children == 3 ){
                Node<Double> left = createIncompleteTree(nodes, current_depth+1, depth);
                Node<Double> right = createIncompleteTree(nodes, current_depth+1, depth);
                ret.setLeftChild(left);
                ret.setRightChild(right);
            }
            return ret;
        } else {
            return null;
        }
    }

    public void dfs_incomplete_binary_tree_returns_same_order_of_elements_as_original(){
        // generate random incomplete tree and store in an array (dfs order)
        // root of tree is indexed at 0
        int depth = 10;

        ArrayList< Node<Double> > nodes = new ArrayList<>();
        createIncompleteTree(nodes, 0, depth);

        // get bfs of random tree
        TreeOperations<Double> tree = new TreeOperations<Double>();
        ArrayList<Double> dfs = tree.dfs(nodes.get(0));

        // test results
        assertEquals(nodes.size(), dfs.size());
        for( int i = 0; i < dfs.size(); i++ ){
            assertEquals(nodes.get(i).getContents(), dfs.get(i));
        }
    }

    @Test
    public void max_depth_on_empty_binary_tree_returns_negative_one() {
        Node<Double> n = null;

        TreeOperations<Double> tree = new TreeOperations<Double>();
        int max_depth = tree.maxDepth(n);
        assertEquals(max_depth, -1);
    }

    @Test
    public void max_depth_on_binary_tree_with_one_element_returns_zero() {
        Node<Double> n = new Node(1, null, null);

        TreeOperations<Double> tree = new TreeOperations<Double>();
        int max_depth = tree.maxDepth(n);
        assertEquals(max_depth, 0);
    }

    @Test
    public void max_depth_on_binary_tree_with_fixed_depth_n_returns_n() {
        // generate full binary tree
        int depth = (int)(Math.random()*10);
        int num_nodes = (int)Math.pow(2, depth) - 1;

        ArrayList< Node<String> > nodes = new ArrayList<>();
        Node<String> n = createFullTree(nodes, 1, num_nodes);

        // test
        TreeOperations<String> tree = new TreeOperations<String>();
        int max_depth = tree.maxDepth(n);
        assertEquals(depth-1, max_depth);
    }

    public Node<Double> generateTreeWithLeftChildren(int current_depth, int depth) {
        if( current_depth <= depth ){
            Random rand = new Random();
            Node<Double> ret = new Node<Double>(rand.nextDouble()*10, null, null);
            ret.setLeftChild(generateTreeWithLeftChildren(current_depth+1, depth));
            return ret;
        } else {
            return null;
        }
    }

    @Test
    public void max_depth_on_binary_tree_with_left_children_returns_max_depth_on_left_branch() {
        int depth = (int)(Math.random()*10);
        Node<Double> n = generateTreeWithLeftChildren(0, depth);

        TreeOperations<Double> tree = new TreeOperations<Double>();
        int max_depth = tree.maxDepth(n);

        Node<Double> left = n;
        Node<Double> right = n;
        while(left != null ){
            assertTrue(left != null);
            left = left.getLeftChild();
        }
        while(right != null ){
            assertTrue(right != null);
            right = right.getLeftChild();
        }
        assertTrue(left == null);
        assertTrue(right == null);
        
        assertEquals(depth, max_depth);
    }

    public Node<Double> generateTreeWithRightChildren(int current_depth, int depth) {
        if( current_depth <= depth ){
            Random rand = new Random();
            Node<Double> ret = new Node<Double>(rand.nextDouble()*10, null, null);
            ret.setRightChild(generateTreeWithLeftChildren(current_depth+1, depth));
            return ret;
        } else {
            return null;
        }
    }

    @Test
    public void max_depth_on_binary_tree_with_right_children_returns_max_depth_on_right_branch() {
        int depth = (int)(Math.random()*10);
        Node<Double> n = generateTreeWithRightChildren(0, depth);

        TreeOperations<Double> tree = new TreeOperations<Double>();
        int max_depth = tree.maxDepth(n);

        Node<Double> left = n;
        Node<Double> right = n;
        while(left != null ){
            assertTrue(left != null);
            left = left.getLeftChild();
        }
        while(right != null ){
            assertTrue(right != null);
            right = right.getLeftChild();
        }
        assertTrue(left == null);
        assertTrue(right == null);

        assertEquals(depth, max_depth);
    }

    @Test
    public void max_depth_on_incomplete_binary_tree_returns_correct_max_depth() {
        // generate full binary tree
        int depth = (int)(Math.random()*10);
        this.MAX_DEPTH = 0;

        ArrayList< Node<Double> > nodes = new ArrayList<>();
        Node<Double> n = createIncompleteTree(nodes, 0, depth);

        // test
        TreeOperations<Double> tree = new TreeOperations<Double>();
        int max_depth = tree.maxDepth(n);
        assertEquals(this.MAX_DEPTH, max_depth);
    }

    @Test
    public void node_with_left_and_right_children_pointing_to_null() {
        Node<Character> n = new Node<Character>('c', null, null);
        assertFalse(n == null);
        assertFalse(n.getContents() == null);
        assertTrue(n.getContents() instanceof Character);
        assertTrue(n.getContents() == 'c');
        assertTrue(n.getLeftChild() == null);
        assertTrue(n.getRightChild() == null);
    }

    @Test
    public void node_with_left_child_not_pointing_to_null() {
        Node<String> m = new Node<String>("efgh", null, null);
        assertFalse(m == null);
        assertFalse(m.getContents() == null);
        assertTrue(m.getContents() instanceof String);
        assertTrue(m.getContents().equals("efgh"));
        assertTrue(m.getLeftChild() == null);
        assertTrue(m.getRightChild() == null);

        Node<String> n = new Node<String>("abcd", m, null);
        assertFalse(n == null);
        assertFalse(n.getContents() == null);
        assertTrue(n.getContents() instanceof String);
        assertTrue(n.getContents().equals("abcd"));
        assertFalse(n.getLeftChild() == null);
        assertTrue(n.getLeftChild() == m);
        assertTrue(n.getRightChild() == null);
    }

    @Test
    public void node_with_right_child_not_pointing_to_null() {
        Node<String> m = new Node<String>("efgh", null, null);
        assertFalse(m == null);
        assertFalse(m.getContents() == null);
        assertTrue(m.getContents() instanceof String);
        assertTrue(m.getContents().equals("efgh"));
        assertTrue(m.getLeftChild() == null);
        assertTrue(m.getRightChild() == null);

        Node<String> n = new Node<String>("abcd", null, m);
        assertFalse(n == null);
        assertFalse(n.getContents() == null);
        assertTrue(n.getContents() instanceof String);
        assertTrue(n.getContents().equals("abcd"));
        assertTrue(n.getLeftChild() == null);
        assertFalse(n.getRightChild() == null);
        assertTrue(n.getRightChild() == m);
    }

    @Test
    public void node_with_left_and_right_children_not_pointing_to_null() {
        Node<String> l = new Node<String>("efgh", null, null);
        assertFalse(l == null);
        assertFalse(l.getContents() == null);
        assertTrue(l.getContents() instanceof String);
        assertTrue(l.getContents().equals("efgh"));
        assertTrue(l.getLeftChild() == null);
        assertTrue(l.getRightChild() == null);

        Node<String> m = new Node<String>("ijkl", null, null);
        assertFalse(m == null);
        assertFalse(m.getContents() == null);
        assertTrue(m.getContents() instanceof String);
        assertTrue(m.getContents().equals("ijkl"));
        assertTrue(m.getLeftChild() == null);
        assertTrue(m.getRightChild() == null);

        Node<String> n = new Node<String>("abcd", l, m);
        assertFalse(n == null);
        assertFalse(n.getContents() == null);
        assertTrue(n.getContents() instanceof String);
        assertTrue(n.getContents().equals("abcd"));
        assertFalse(n.getLeftChild() == null);
        assertTrue(n.getLeftChild() == l);
        assertFalse(n.getRightChild() == null);
        assertTrue(n.getRightChild() == m);
    }

    @Test
    public void node_count_on_empty_tree_returns_0() {
        Node<Double> n = null;
        TreeOperations<Double> tree = new TreeOperations<Double>();
        int count = tree.nodeCount(n);
        assertEquals(count, 0);
    }

    @Test
    public void node_count_on_tree_with_one_node_returns_1() {
        Node<Character> n = generateRandomNode();
        TreeOperations<Double> tree = new TreeOperations<Double>();
        int count = tree.nodeCount(n);
        assertEquals(count, 1);
    }

    @Test
    public void node_count_on_tree_with_only_left_children_returns_number_of_left_children_and_root() {
        int depth = (int)(Math.random()*10);
        Node<Double> n = generateTreeWithLeftChildren(0, depth);

        TreeOperations<Double> tree = new TreeOperations<Double>();
        int count = tree.nodeCount(n);
        assertEquals(count, depth+1);
    }

    @Test
    public void node_count_on_tree_with_only_right_children_returns_number_of_right_children_and_root() {
        int depth = (int)(Math.random()*10);
        Node<Double> n = generateTreeWithRightChildren(0, depth);

        TreeOperations<Double> tree = new TreeOperations<Double>();
        int count = tree.nodeCount(n);
        assertEquals(count, depth+1);
    }

    @Test
    public void node_count_on_full_binary_tree_returns_number_of_nodes_in_the_tree() {
        int num_nodes = (int)Math.pow(2, 10) - 1;
        ArrayList< Node<String> > nodes = new ArrayList<>();
        createFullTree(nodes, 1, num_nodes);

        TreeOperations<String> tree = new TreeOperations<String>();
        int count = tree.nodeCount(nodes.get(0));
        assertEquals(count, num_nodes);
    }
}
