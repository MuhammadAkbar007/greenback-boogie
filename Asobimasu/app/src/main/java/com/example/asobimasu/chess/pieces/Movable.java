package com.example.asobimasu.chess.pieces;

import com.example.asobimasu.chess.data.Coordinates;
import com.example.asobimasu.chess.data.Position;

import java.util.ArrayList;

/**
 * Created by dipansh on 1/12/17.
 */

public interface Movable {
    public ArrayList<Coordinates> AllowedMoves(Coordinates coordinates , Position[][] board);
}
