package pt.c40task.l05wumpus;
import java.util.Random;

public class Controle {
    private Heroi hero;
    private int pontos;
    private char status;

    public Controle(Heroi hero){
        this.hero = hero;
        this.pontos = 0;
        this.status = 'x';
    }
    public int getPontos(){
        return pontos;
    }
    public char getStatus(){
        return status;
    }
    public void acao(char mov){
        //Método que recebe um char indicando o movimento
        int i = hero.pos[0], j = hero.pos[1];
        //Atualizacao do i e j conforme o movimento adequado
        if(mov == 'w')
            i--;
        else if(mov == 's')
            i++;
        else if(mov == 'a')
            j--;
        else if(mov == 'd')
            j++;
        if(i <= hero.cav.getMatriz().length && j <= hero.cav.getMatriz()[0].length){ //Confere se é posicao válida dentro do mapa
            hero.mover(i, j);
            this.pontos -= 15;
            hero.cav.getMatriz()[i][j].visitou();

            if(hero.flechaEquipada){ //O herói se moveu com a flecha equipada, ou seja, ele a atirou
                hero.atiraFlecha();
                this.pontos -= 100;

                if(hero.cav.getMatriz()[i][j].temComponente('W')){ //Se tem um Wumpus na sala o heroi tem 50% de chance de matá-lo
                    Random rand = new Random();
                    if(rand.nextInt(1) == 1){ //Herói morreu
                        this.pontos -= 1000;
                        hero.setSymbol(' ');
                        this.status = 'n';
                    }
                    else{ //Herói matou o Wumpus
                        this.pontos += 500;
                        hero.cav.getMatriz()[i][j].eliminaComponente('W');
                        this.status = 'n';
                    }
                }
            }
            else{ //Se ele não equipou a flecha e encontrou um Wumpus
                if(hero.cav.getMatriz()[i][j].temComponente('W')){
                    this.pontos -= 1000;
                    hero.setSymbol(' '); //Heroi morreu
                    this.status = 'n';
                }
            }
            if(hero.cav.getMatriz()[i][j].temComponente('B')){
                this.pontos -= 1000;
                hero.setSymbol(' '); //Heroi morreu
                this.status = 'n';
            }
        }
    }
    public void acao(char c, boolean saiu){
        //Recebe um char q será equivalente a uma das ações abaixo, e altera um boolean (saiu) se o jogador saiu ou não do jogo,
        //se a acao for 'q' ele saiu do jogo
        if(c == 'k')
            hero.carregaFlecha();
        else if(c == 'c'){
            if(hero.cav.getMatriz()[hero.pos[0]][hero.pos[1]].temComponente('O')){
                hero.pegaOuro();
                this.pontos += 1000;
                hero.cav.getMatriz()[hero.pos[0]][hero.pos[1]].eliminaComponente('O');
                this.status = 'w';
            }
        }
        else if(c == 'q'){
            saiu = true;
        }
        saiu = false;
    }
}
