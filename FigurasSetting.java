/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetrisgame;

import java.util.Random;

/**
 *
 * @author : Yuri Domingos 
 * Data    : 15 - 11 - 2020
 * Objectivo : Criar o tradicional e clássico jogo tetris
 */
public class FigurasSetting {
    
    
    // Criar uma lista de constantes ( de maneira profissional )
    
    private int Column = 4, helper = 7;
    private int line = 2;
    
    protected enum tetranomios {
        
        
        firstFig,  secondFig, thirdFig , 
        fourthFig,fifthFig, sixthFig, 
        seventhFig, eighthFig, ninthFig
        
    }
    
    private  int coordenates [][];
    private tetranomios environment;

    public FigurasSetting() {
        
        coordenates = new int [Column][line];
        
        
    }
    
    
    
    public void addFiguras(tetranomios figure)
    {
        
        // initialize game space 
        
        int generalCoordenates [][][] = new int [][][]
        {
                 {{0, 0}, {0, 0}, {0, 0}, {0, 0}},
                {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}},
                {{0, -1}, {0, 0}, {1, 0}, {1, 1}},
                {{0, -1}, {0, 0}, {0, 1}, {0, 2}},
                {{-1, 0}, {0, 0}, {1, 0}, {0, 1}},
                {{0, 0}, {1, 0}, {0, 1}, {1, 1}},
                {{-1, -1}, {0, -1}, {0, 0}, {0, 1}},
                {{1, -1}, {0, -1}, {0, 0}, {0, 1}}
            
        };
        
        // probabily I will translate 
        
        for (int i = 0; i < Column; i++ )
        {
            System.arraycopy(generalCoordenates[figure.ordinal()], 0, coordenates, 0, Column);
        }
        
         environment = figure;  
      
    }
    
    private void setCoodX(int position, int value)
    {
        coordenates[position][0] = value;
    }
    
  
    private void setCoordY(int position, int value)
    {
         coordenates[position][1] = value;
    }
   
    
    public int ShortestX(int position)
    {
        return coordenates[position][0];
    }
    
   public int ShortestY(int positionRelative)
   {
       return coordenates[positionRelative][1];
   }
   
   tetranomios takeFigures()
   {
       return environment;
   }


public void addRandomFiguresGame()
{
    // - No java 10 temos o tipo var ( estática para o escopo local da nossa function )
    
    var randomNumber = new Random();
    
    int RealValue = Math.abs(randomNumber.nextInt())%helper +1;
    
      tetranomios [] similarCoordenates =   tetranomios.values();
      
      addFiguras(similarCoordenates[ RealValue]);
      
    
}

   public int ShortX()
   {
       int temporary = coordenates[0][0];
       
       for(int i = 0; i< Column; i++ )
       {
           temporary = Math.min(temporary, coordenates[i][0]);
       }
       
       
       return temporary;
   }

   
   public int ShortY()
   {
       int temporary = coordenates[0][1];
       
       for(int i = 0; i < Column; i++ )
       {
          temporary = Math.min(temporary, coordenates[i][1]);
       }
       
       return temporary;
   }


  FigurasSetting girarEsquerda()
  {
      if (environment ==  tetranomios.fifthFig)
      {
          return this;
      }
      
      var temporary = new FigurasSetting ();
      temporary.environment = environment;
      
      for (int i = 0; i < Column; i++ )
      {
         temporary.setCoodX(i, ShortestY(i));
         temporary.setCoordY(i, -ShortestX(i));
      }
      
      return temporary;
  }
  
    FigurasSetting girarDireita()
  {
      if (environment ==  tetranomios.fifthFig)
      {
          return this;
      }
      
      var temporary = new FigurasSetting ();
      temporary.environment = environment;
      
      for (int i = 0; i < Column; i++ )
      {
         temporary.setCoodX(i, -ShortestY(i));
         temporary.setCoordY(i, ShortestX(i));
      }
      
      return temporary;
  }


    
    
}
