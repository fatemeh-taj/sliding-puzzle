package p1;

import java.util.*;

public class Game {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		char exit = 'n';
		int playRound = 0;
		while (exit != 'E') {
			if (playRound != 0) {// asking to exit for all rounds except first round
				System.out.println("press E to exit;(");
				exit = Character.toUpperCase(s.next().charAt(0));
			}
			if (exit == 'E')// game over
				continue;

			System.out.print("Enter a number:)");// n*n 2=<n=<10
			final int n = s.nextInt();// unchangeable

			if (n < 2 && n > 10)
				System.out.println("ERROR:(");
			else {
				playRound++;
				play(n, playRound);
			}
		}
	}

	public static void play(int n, int playRound) {
		Date start = new Date();// start time measuring
		String[][] board = new String[n][n];
		board = buildBoard(n);

		boolean first = true;
		String ij = showBoard(n, board, first);
		String[] ij_ = ij.split("a");
		int i = Integer.parseInt(ij_[0]), j = Integer.parseInt(ij_[1]);// # i & j
		game(n, i, j, board, start, playRound);

	}

	public static void game(int n, int i, int j, String[][] board, Date start, int playRound) {
		boolean first = false, win = false, ok;
		int move = 0;
		Scanner s = new Scanner(System.in);

		while (!win) {
			ok = false;
			System.out.println("Rounds play:" + playRound + "\tmove:" + move);
			System.out.println("Press R,L,U,D to move!");
			switch (Character.toUpperCase(s.next().charAt(0))) {// cast input to upper case
			case 'R':
				if (++j < n) {// bound
					board[i][j - 1] = board[i][j];// swap
					board[i][j] = "#";
					ok = true;
				} else
					j--;// back to original j
				break;
			case 'L':
				if (--j >= 0) {// bound
					board[i][j + 1] = board[i][j];// swap
					board[i][j] = "#";
					ok = true;
				} else
					j++;// back to original j
				break;
			case 'U':
				if (--i >= 0) {// bound
					board[i + 1][j] = board[i][j];// swap
					board[i][j] = "#";
					ok = true;
				} else
					i++;// back to original i
				break;
			case 'D':
				if (++i < n) {// bound
					board[i - 1][j] = board[i][j];// swap
					board[i][j] = "#";
					ok = true;
				} else
					i--;// back to original i
				break;

			default:
				System.out.println("ERROR");
				break;
			}
			if (ok) {// right move
				showBoard(n, board, first);
				Date end = new Date();
				long time = (end.getTime() - start.getTime()) / 1000;// time difference ~ seconds
				time(time);// convert to minutes & hours
				if (board[0][0].equals("2") && board[n - 1][n - 1].equals("#")) {// first & last table
					win = win(n, board);
					if (win) {
						double score = (1E3 * n * n) / (Math.log10(move) * Math.pow(time, 1.0 / 3));// calculate score
						System.out.println("congratulation, u won;D\nscore;)" + Math.round(score));
					}
				}
			} else
				System.out.println("wrong move, u lost 1 move:|");

			move++;
		}
	}

	public static String[][] buildBoard(int n) {
		Random r = new Random();
		String[][] board = new String[n][n]; // main board
		ArrayList<String> boardNumbers = new ArrayList<>();// include 1,2,...,n*n-1,#
		for (int i = 1; i < n * n; i++) {
			boardNumbers.add(i + "");
		}
		boardNumbers.add("#");

		int m = 0, x;
		for (int i = 0; i < n; i++) {// the board fill with boardNumbers Array randomly
			for (int j = 0; j < n; j++) {
				x = r.nextInt(n * n - m);// boardNumbers array index selected randomly
				board[i][j] = boardNumbers.get(x) + "";// fill
				boardNumbers.remove(x);
				m++;// to reduce random selection interval
			}
		}
		return board;
	}

	public static String showBoard(int n, String[][] board, boolean first) {
		String r = "";
		for (int i = 0; i < n; i++) {// include 1,...,9,10,...99,#
			for (int j = 0; j < n; j++) {
				if (board[i][j].equals("#")) {
					System.out.print("[# ] ");
					if (first)
						r = i + "a" + j;// return # i & j
				} else if (Integer.parseInt(board[i][j]) < 10)// 1digit
					System.out.print("[" + board[i][j] + " ] ");
				else// 2digits
					System.out.print("[" + board[i][j] + "] ");
			}
			System.out.println();
		}
		return r;
	}

	public static void time(long sec) {
		Scanner s = new Scanner(System.in);
		long min = 0, h = 0;
		int x = 0;
		if (sec > 60) {// convert to minutes
			min = sec / 60;
			sec %= 60;
			x++;
		}
		if (min > 60) {// convert to hours
			h = min / 60;
			min %= 60;
			x++;
		}
		switch (x) {
		case 0:
			System.out.println("sec:" + sec);
			break;

		case 1:
			System.out.println("min:" + min + " sec:" + sec);
			break;
		case 2:
			System.out.println("hour:" + h + " min:" + min + " sec:" + sec);
			break;
		}
	}

	public static boolean win(int n, String[][] board) {
		ArrayList<String> winSort = new ArrayList<>();// include 2,4,...,1,3,...,#
		for (int i = 2; i <= n * n - 1; i += 2) {
			winSort.add(i + "");
		}
		for (int i = 1; i <= n * n - 1; i += 2) {
			winSort.add(i + "");
		}
		winSort.add("#");
		boolean win = true;
		int k = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {// compare winsort array with board numbers
				if (!(board[i][j].equals(winSort.get(k)))) {
					win = false;
					break;
				}
				k++;
			}
		}
		return win;
	}
}
