package model.structures;

public class Triple<S, T, U> {
    private S _first;
    private T _second;
    private U _third;

    public Triple(S first, T second, U third) {
        _first = first;
        _second = second;
        _third = third;
    }
    public S getFirst() {
        return _first;
    }
    public T getSecond() {
        return _second;
    }
    public U getThird() {
        return _third;
    }
}
