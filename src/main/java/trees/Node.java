package trees;

public class Node<A> {
    public final A contents;
    public final Node<A> left;
    public final Node<A> right;

    public Node(final A contents,
                final Node<A> left,
                final Node<A> right) {
        this.contents = contents;
        this.left = left;
        this.right = right;
    }

    public A getContents(){
        return this.contents;
    }

    public Node<A> getLeftChild(){
        return this.left;
    }

    public Node<A> getRightChild(){
        return this.right;
    }
}
