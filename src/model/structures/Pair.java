package model.structures;

public class Pair<S, T> {
    private S _left;
    private T _right;

    public Pair(S left, T right) {
        _left = left;
        _right = right;
    }

    public S getLeft() {
        return _left;
    }
    public T getRight() {
        return _right;
    }
    /*
    public void setLeft(S left) {
        _left = left;
    }
    public void setRight(T right){
        _right = right;
    }
    */
}
