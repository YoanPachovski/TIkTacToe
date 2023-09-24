public class AlphaBeta {

    private static double maxDepth;
    private boolean optimal;
    public static final int WINNER_VALUE = 10;
    public static final int DRAW_VALUE = 0;

    public AlphaBeta(double maxDepth) {
        AlphaBeta.maxDepth = maxDepth;
        optimal = false;
    }

    public AlphaBeta(double maxDepth, boolean optimal) {
        AlphaBeta.maxDepth = maxDepth;
        this.optimal = optimal;
    }
    public void run(State player, Board board) {
        alphaBetaPruning(player, board, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0);
    }

    public int alphaBetaPruning (State player, Board board, double alpha, double beta, int currentDepth) {
        if (currentDepth++ == maxDepth || board.isGameOver()) {
            return optimal ? score(player, board, currentDepth) : score(player, board);
        }
        if (board.getTurn() == player) {
            return getMax(player, board, alpha, beta, currentDepth);
        } else {
            return getMin(player, board, alpha, beta, currentDepth);
        }
    }

    private int getMin(State player, Board board, double alpha, double beta, int currentDepth) {
        int indexOfBestMove = -1;

        for (Integer move: board.getAvailableMoves()) {
            Board tmp = board.getDeepCopy();
            tmp.move(move);

            int score = alphaBetaPruning(player, tmp, alpha, beta, currentDepth);
            if (score < beta) {
                beta = score;
                indexOfBestMove = move;
            }

            if (alpha >= beta) {
                break;
            }
        }

        if (indexOfBestMove != -1) {
            board.move(indexOfBestMove);
        }

        return (int)beta;
    }

    private int getMax(State player, Board board, double alpha, double beta, int currentDepth) {
        int indexOfBestMove = -1;

        for (Integer move : board.getAvailableMoves()) {

            Board modifiedBoard = board.getDeepCopy();
            modifiedBoard.move(move);
            int score = alphaBetaPruning(player, modifiedBoard, alpha, beta, currentDepth);

            if (score > alpha) {
                alpha = score;
                indexOfBestMove = move;
            }

            if (alpha >= beta) {
                break;
            }
        }

        if (indexOfBestMove != -1) {
            board.move(indexOfBestMove);
        }

        return (int)alpha;
    }
   public int score(State player, Board board) {
        if (player == State.BLANK) {
            throw new IllegalArgumentException("Player must be X or O.");
        }
        State opponent = (player == State.X) ? State.O : State.X;

        if (board.isGameOver() && board.getWinner() == player) {
            return WINNER_VALUE;
        } else if (board.isGameOver() && board.getWinner() == opponent) {
            return (-1) * WINNER_VALUE;
        } else {
            return DRAW_VALUE;
        }
    }


    public int score(State player, Board board, int currentDepth) {
        if (player == State.BLANK) {
            throw new IllegalArgumentException("Player must be X or O.");
        }

        State opponent = (player == State.X) ? State.O : State.X;

        if (board.isGameOver() && board.getWinner() == player) {
            return WINNER_VALUE - currentDepth;
        } else if (board.isGameOver() && board.getWinner() == opponent)
        {
            return (-1) * WINNER_VALUE + currentDepth;
        } else {
            return DRAW_VALUE;
        }
    }
}
