/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetrisgame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author : Yuri Domingos
 * Data    : 15 - 11 - 2020
 * Objectivo : Criar o tradicional e clássico jogo tetris
 */
public class TetrisGame extends JFrame {

    /**
     * @param args the command line arguments
     */
    
    private JLabel statusbar;

    public TetrisGame() {

        initUI();
    }

    private void initUI() {

       
        statusbar = new JLabel(" 0");
        add(statusbar, BorderLayout.SOUTH);

        var cenary = new Cenary(this);
        add(cenary);
        cenary.begin_the_game();

        setTitle("Tetris with YuriSystems");
        setSize(260, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setBackground(Color.WHITE);
        setLocationRelativeTo(null);
    }

    JLabel getStatusBar() {

        return statusbar;
    }

    public static void main(String[] args) {
       
        
        
          EventQueue.invokeLater(() -> {

            var Funning = new TetrisGame();
            Funning.setVisible(true);
        });
    }
    
}
