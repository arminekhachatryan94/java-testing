package trees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class TreeOperations<A> {
    protected static int max_depth = 0;

    public static <A> ArrayList<A> bfs(final Node<A> node){
        Queue queue = new LinkedList();
        ArrayList<A> breadth = new ArrayList<>();
        if( node != null ){
            queue.add(node);
            while(queue.size() != 0 ){
                Node<A> n = (Node<A>)queue.peek();
                queue.poll();
                breadth.add(n.getContents());
                if( n.getLeftChild() != null ){
                    queue.add(n.getLeftChild());
                }
                if( n.getRightChild() != null ){
                    queue.add(n.getRightChild());
                }
            }        }
        return breadth;
    }

    public static <A> ArrayList<A> dfs(final Node<A> node){
        ArrayList<A> depth = new ArrayList<>();
        helperDFS(node, depth);
        return depth;
    }
    
    public static <A> void helperDFS(Node<A> node, ArrayList<A> depth){
        if( node != null ){
            depth.add(node.getContents());
            helperDFS(node.getLeftChild(), depth);
            helperDFS(node.getRightChild(), depth);
        }
    }

    public static <A> int maxDepth(final Node<A> root){
        if( root == null ){
            return -1;
        }
        max_depth = 0;
        helperMaxDepth(root, 0);
        return max_depth;
    }

    public static <A> void helperMaxDepth(Node<A> root, int current_depth){
        if( current_depth > max_depth ){
            max_depth = current_depth;
        }
        if( root.getLeftChild() != null ){
            helperMaxDepth(root.getLeftChild(), current_depth+1);
        }
        if( root.getRightChild() != null ){
            helperMaxDepth(root.getRightChild(), current_depth+1);
        }
    }
}

