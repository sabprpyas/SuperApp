package com.sky.demo.ui.widget.pending;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 15/12/24 δΈε10:20
 */
public class JigsawImage {

    public static List<ImagePiece> jigsaw(Bitmap bitmap, int piece) {
        List<ImagePiece> imagePieces = new ArrayList<>();

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int pieceWidth=Math.min(width, height)/piece;
//        int pieceWidth = width / piece;
//        int pieceHeight = height / piece;
//        int index = 0;
        for (int i = 0; i < piece; i++) {//θ‘
            for (int j = 0; j < piece; j++) {//ε
                ImagePiece image = new ImagePiece();
//                image.setNumber(index);
                image.setNumber(i * piece + j);
                int x = j * pieceWidth;
                int y = i * pieceWidth;
                image.setBitmap(Bitmap.createBitmap(bitmap, x, y, pieceWidth, pieceWidth));
                imagePieces.add(image);
//                index++;
            }
        }
        return imagePieces;
    }
}
