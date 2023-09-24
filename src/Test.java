import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Board board = new Board();
        System.out.println(board.toString());
        while (!board.isGameOver()) {
            Scanner myObj = new Scanner(System.in);
            int x=myObj.nextInt();
            int y=myObj.nextInt();
            if (x<=0 || x>3)
            {
              System.out.println("Write Number in the range of 1 to 3");
              x= myObj.nextInt();
            }
            if (y<=0 || y>3)
            {
                System.out.println("Write Number in the range of 1 to 3");
                y= myObj.nextInt();
            }
            int move=0;
            if (x==1){  move= y-1;}
            if (x==2){  move= (3+y)-1;}
            if (x==3){  move=(6+y)-1;};
            if (!board.isGameOver() && move != -1) {
                boolean validMove = board.move(move);
                if (!board.isGameOver()) {
                    AlphaBeta alphaBeta = new AlphaBeta(Double.POSITIVE_INFINITY, true);
                    alphaBeta.run(board.getTurn(), board);
                }
            }
            System.out.println(board.toString());
        }
        System.out.println(board.getWinner());
    }
}
