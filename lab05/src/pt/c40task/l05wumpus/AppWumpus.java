package pt.c40task.l05wumpus;

public class AppWumpus {

   public static void main(String[] args) {
      AppWumpus.executaJogo(
            (args.length > 0) ? args[0] : null,
            (args.length > 1) ? args[1] : null,
            (args.length > 2) ? args[2] : null);
   }
   
   public static void executaJogo(String arquivoCaverna, String arquivoSaida,
                                  String arquivoMovimentos) {
      Toolkit tk = Toolkit.start(arquivoCaverna, arquivoSaida, arquivoMovimentos);
      
      String cave[][] = tk.retrieveCave();
      System.out.println("=== Caverna");
      for (int l = 0; l < cave.length; l++) {
         for (int c = 0; c < cave[l].length; c++)
            System.out.print(cave[l][c] + ((c < cave[l].length-1) ? ", " : ""));
         System.out.println();
      }
      
      String movements = tk.retrieveMovements();
      System.out.println("=== Movimentos");
      System.out.println(movements);
      
      //cria o objeto caverna
      Caverna caverna = new Caverna();

      //cria o objeto montador
      Montador montador = new Montador();
      montador.conecta(caverna);

      //instancia o heroi que sempre começa na sala 1,1
      int[] posInicial = {0,0};
      Heroi heroi = new Heroi(1, posInicial, caverna);
      caverna.getMatriz()[0][0].setComps(heroi, 0);

      //monta a caverna
      for(int i=1; i<cave.length; i++){
         montador.montaCaverna(cave[i]);
      }

      //vetor de char para impressao da caverna
      char partialCave[][] = new char[4][4];

      //instancia o controle e roda os comandos do arquivo movements
      Controle controle = new Controle(heroi);
      for(int i=0; i<movements.length(); i++){
         boolean saiu = false;
         if(movements.charAt(i) == 'c' ||movements.charAt(i) == 'k' || movements.charAt(i) == 'q'){
            controle.acao(movements.charAt(i), saiu);
         }
         else{
            controle.acao(movements.charAt(i));
         }
         for(int j=0; j<caverna.getMatriz().length; j++){
            for(int k=0; k<caverna.getMatriz().length; k++){
               partialCave[j][k] = caverna.getMatriz()[j][k].getPrioComponente();
            }
         }
         tk.writeBoard(partialCave, controle.getPontos(), controle.getStatus());;
      }
      /* System.out.println("=== Caverna Intermediaria");
      char partialCave[][] = {
         {'#', '#', 'b', '-'},
         {'#', 'b', '-', '-'},
         {'b', '-', '-', '-'},
         {'p', '-', '-', '-'}
      };
      int score = -120;
      char status = 'x'; // 'w' para venceu; 'n' para perdeu; 'x' intermediárias
      tk.writeBoard(partialCave, score, status);

      System.out.println("=== Última Caverna");
      char finalCave[][] = {
         {'#', '#', 'b', '-'},
         {'#', 'b', '#', 'f'},
         {'b', '-', '-', 'w'},
         {'#', '-', '-', '-'}
      };
      score = -1210;
      status = 'n'; // 'w' para venceu; 'n' para perdeu; 'x' intermediárias
      tk.writeBoard(finalCave, score, status); */
      
      tk.stop();
   }

}
