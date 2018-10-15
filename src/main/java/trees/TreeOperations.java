package trees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class TreeOperations<A> {
    public Queue queue = new LinkedList();
    public ArrayList<A> breadth = new ArrayList<A>();

    public <A> ArrayList<A> bfs(final Node<A> node){
        ArrayList<A> breadth = new ArrayList<>();
        if( node != null ){
            this.queue.add(node);
            while(this.queue.size() != 0 ){
                Node<A> n = (Node<A>)this.queue.peek();
                this.queue.poll();
                breadth.add(n.getContents());
                if( n.getLeftChild() != null ){
                    this.queue.add(n.getLeftChild());
                }
                if( n.getRightChild() != null ){
                    this.queue.add(n.getRightChild());
                }
            }        }
        return breadth;
    }

    /*
    public <A> ArrayList<A> DFS(final Node<A> node){
        ArrayList<A> depth = new ArrayList<A>();
        helperDFS(node, depth);
        return breadth;
    }
    
    public <A> void helperDFS(final Node<A> node, ArrayList<A> depth){
        if( node != null ){
            breadth.add(node.getContents());
            helperDFS(node.getLeftChild(), depth);
            helperDFS(node.getRightChild(), depth);
        }
    }
    */
}

