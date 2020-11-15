/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetrisgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import tetrisgame.FigurasSetting.tetranomios;

/**
 *
 ** @author : Yuri Domingos 
 * Data    : 15 - 11 - 2020
 * Objectivo : Criar o tradicional e clássico jogo tetris
 * 
 */
public class Cenary extends JPanel {
    

    private int Column = 4;
    private Timer timer;
    private boolean isPaused = false;
    private boolean isFinished = false;
   
    private int tookedLine = 0 , cordY = 0,cordX = 0;
    private JLabel statusbar;
    
    
    private  FigurasSetting FiguresElement;
    private tetranomios [] cenary;
    
    //-- Constant 
    
    private final int CENARY_WIDTH = 12;
    private final int CENARY_HEIGHT = 24;
    private final int Timeer = 304;
    
    
    
    public Cenary(TetrisGame Element) {

        initCenary(Element);
    }

  
    private void initCenary(TetrisGame Element) {

        setFocusable(true);
        statusbar = Element.getStatusBar();
        addKeyListener(new KeyBoarddapter());
    }
    
    
     private int four_same_figures_Width() {

        return (int) getSize().getWidth() / CENARY_WIDTH;
    }

    private int four_same_figures_Height() {

        return (int) getSize().getHeight() / CENARY_HEIGHT;
    }

    private tetranomios figure_in_place(int x, int y) {

        return  cenary[(y *CENARY_WIDTH) + x];
    }

    
    private void put_in_pause() {

        isPaused = !isPaused;

        if (isPaused) {

            statusbar.setText("paused");
        } else {

            statusbar.setText(String.valueOf(tookedLine ));
        }

        repaint();
    }
    
    public void begin_the_game() {

        FiguresElement = new FigurasSetting();
         cenary = new tetranomios[CENARY_WIDTH * CENARY_HEIGHT];

        cenary_Cleaned_up();
        new_figures();

        timer = new Timer(Timeer, new GameCycle());
        timer.start();
    }

    
    
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        var size = getSize();
        int cenaryTop = (int) size.getHeight() - CENARY_HEIGHT * four_same_figures_Height();

        for (int i = 0; i < CENARY_HEIGHT; i++) {

            for (int j = 0; j < CENARY_WIDTH; j++) {

                tetranomios figure =  figure_in_place(j, CENARY_HEIGHT - i - 1);

                if (figure != tetranomios.firstFig) {

                    drawElement(g, j *  four_same_figures_Width(),
                            
                            cenaryTop + i * four_same_figures_Height(), figure);
                }
            }
        }

        if (FiguresElement.takeFigures() != tetranomios.firstFig) {

            for (int i = 0; i < Column ; i++) {

                int x = cordX + FiguresElement.ShortestX(i);
                int y =  cordY - FiguresElement.ShortestY(i);

                drawElement(g, x *  four_same_figures_Width(), 
                        
                         cenaryTop + (CENARY_HEIGHT - y - 1) * four_same_figures_Height(),
                        FiguresElement.takeFigures());
            }
        }
    }

 private void put_down_the_figures() {

        int newY = cordY;

        while (newY > 0) {

            if (!Change_figures_form(FiguresElement, cordX, newY - 1)) {

                break;
            }

            newY--;
        }

        Figures_dropped();
    }

    private void LineDown() {

        if (!Change_figures_form(FiguresElement, cordX,  cordY - 1)) {

            Figures_dropped();
        }
    }

    private void cenary_Cleaned_up() {

        for (int i = 0; i < CENARY_HEIGHT * CENARY_WIDTH; i++) {

             cenary[i] = tetranomios.firstFig;
        }
    }

    private void Figures_dropped() {

        for (int i = 0; i < Column; i++) {

            int x = cordX + FiguresElement.ShortestX(i);
            int y = cordY - FiguresElement.ShortestY(i);
            cenary [(y *CENARY_WIDTH) + x] = FiguresElement.takeFigures();
        }

        take_absent_element();

        if (!isFinished) {

            new_figures();
        }
    }

    private void new_figures() {

        FiguresElement.addRandomFiguresGame();
        cordX = CENARY_WIDTH / 2 + 1;
         cordY = CENARY_HEIGHT - 1 + FiguresElement.ShortY();

        if (!Change_figures_form(FiguresElement, cordX,  cordY)) {

            FiguresElement.addFiguras( tetranomios.firstFig);
            timer.stop();

            var msg = String.format("Game over - Pontos: %d", tookedLine );
            statusbar.setText(msg);
        }
    }

    private boolean Change_figures_form( FigurasSetting new_figures, int new_coordenate_x, int new_coordenate_Y) {

        for (int i = 0; i < Column; i++) {

            int x = new_coordenate_x +  new_figures.ShortestX(i);
            int y = new_coordenate_Y -  new_figures.ShortestY(i);

            if (x < 0 || x >= CENARY_WIDTH || y < 0 || y >= CENARY_HEIGHT) {

                return false;
            }

            if ( figure_in_place(x, y) != tetranomios.firstFig) {

                return false;
            }
        }

        FiguresElement = new_figures;
        cordX =new_coordenate_x;
        cordY = new_coordenate_Y;

        repaint();

        return true;
    }

    private void take_absent_element() {

        int all_number = 0;

        for (int i = CENARY_HEIGHT - 1; i >= 0; i--) {

            boolean lineIsFull = true;

            for (int j = 0; j < CENARY_WIDTH; j++) {

                if ( figure_in_place(j, i) == tetranomios.firstFig) {

                    lineIsFull = false;
                    break;
                }
            }

            if (lineIsFull) {

                all_number++;

                for (int k = i; k < CENARY_HEIGHT- 1; k++) {
                    for (int j = 0; j < CENARY_WIDTH; j++) {
                         cenary[(k * CENARY_WIDTH) + j] =  figure_in_place(j, k + 1);
                    }
                }
            }
        }

        if (all_number > 0) {

            tookedLine  += all_number;

            statusbar.setText(String.valueOf(tookedLine ));
            isFinished= true;
           FiguresElement.addFiguras(tetranomios.firstFig);
        }
    }

    private void drawElement(Graphics g, int x, int y, tetranomios figure) {

        Color colors[] = {new Color(0, 0, 0), new Color(204, 102, 102),
                new Color(42, 204, 102), new Color(12, 102, 204),
                new Color(204, 204, 102), new Color(54, 102, 104),
                new Color(102, 204, 204), new Color(18, 110, 0)
        };

        var color = colors[ figure.ordinal()];

        g.setColor(color);
        g.fillRect(x + 1, y + 1, four_same_figures_Width() - 2, four_same_figures_Height() - 2);

        g.setColor(color.brighter());
        g.drawLine(x, y + four_same_figures_Height() - 1, x, y);
        g.drawLine(x, y, x +  four_same_figures_Width() - 1, y);

        g.setColor(color.darker());
        g.drawLine(x + 1, y + four_same_figures_Height() - 1,
                x +  four_same_figures_Width() - 1, y + four_same_figures_Height() - 1);
        g.drawLine(x +  four_same_figures_Width() - 1, y + four_same_figures_Height() - 1,  x +  four_same_figures_Width() - 1, y + 1);
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

           rulled_up_game();
        }
    }

    private void rulled_up_game() {

        update_game();
        repaint();
    }

    private void update_game() {

        if (isPaused) {

            return;
        }

        if (isFinished) {

            isFinished = false;
            new_figures();
        } else {

            LineDown();
        }
    }

    class KeyBoarddapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            if (FiguresElement.takeFigures() == tetranomios.firstFig) {

                return;
            }

            int keycode = e.getKeyCode();

            // Java 12 switch expressions
           switch (keycode) {

          case  KeyEvent.VK_P :
              
                 put_in_pause();
                 
                break;
                
          case KeyEvent.VK_LEFT :
              
              Change_figures_form(FiguresElement, cordX - 1,  cordY);
              break;
              
          case KeyEvent.VK_RIGHT :
              
              Change_figures_form(FiguresElement,cordX + 1,  cordY);
              break;
              
          case KeyEvent.VK_DOWN :
              
              Change_figures_form(FiguresElement.girarDireita(), cordX, cordY);
              break;
              
          case KeyEvent.VK_UP :
              
             Change_figures_form(FiguresElement.girarEsquerda(), cordX,  cordY);
              break;
              
          case KeyEvent.VK_SPACE :
              
              put_down_the_figures();
              break;
              
          case KeyEvent.VK_F:
              
              LineDown();
              break;
           }

        }
    
}
    
    
    
}
