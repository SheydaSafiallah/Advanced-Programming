package simpletetris;

public enum Tetromino {
    I(new int[][][]{
            {{0,1},{1,1},{2,1},{3,1}},
            {{2,0},{2,1},{2,2},{2,3}}
    },1),
    O(new int[][][]{
            {{1,0},{2,0},{1,1},{2,1}}
    },2),
    T(new int[][][]{
            {{1,0},{0,1},{1,1},{2,1}},
            {{1,0},{1,1},{2,1},{1,2}},
            {{0,1},{1,1},{2,1},{1,2}},
            {{1,0},{0,1},{1,1},{1,2}}
    },3),
    L(new int[][][]{
            {{0,0},{0,1},{1,1},{2,1}},
            {{1,0},{2,0},{1,1},{1,2}},
            {{0,1},{1,1},{2,1},{2,2}},
            {{1,0},{1,1},{1,2},{0,2}}
    },4);

    public final int[][][] rotations;
    public final int colorId;

    Tetromino(int[][][] rotations, int colorId) {
        this.rotations = rotations;
        this.colorId = colorId;
    }
}
