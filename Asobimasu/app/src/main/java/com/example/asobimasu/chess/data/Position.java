package com.example.asobimasu.chess.data;


import com.example.asobimasu.chess.pieces.Piece;

/**
 * Created by DIPANSH KHANDELWAL on 03-06-2017
 */

public class Position {
    private Piece piece;


    public Position(Piece piece ) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;

    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

}
