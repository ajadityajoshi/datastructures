package problem_solving;
import java.util.*;
import problem_solving.Graph;

public class SnakeAndLadder {

	/* Do a BFS from the starting node and reach the 30
	 * vertex. BFS does this in minimum number of steps.
	 */
	private static int playLudo(Graph g, int startNode, HashMap<Integer, Integer> ladderPos) {
		boolean[] visited = new boolean[31];
		int[] distance = new int[31];

		Queue<Integer> q = new LinkedList<Integer>();
		q.add(startNode);
		visited[startNode] = true;

		while (!q.isEmpty()) {
			int start = q.remove();
			List<Integer> list = g.getEdge(start);
			Iterator<Integer> it = list.iterator();
			System.out.print("start node: " + start+ " ");
			while (it.hasNext()) {
				int neighbor = (Integer)it.next();
				if (!visited[neighbor]) {
					q.add(neighbor);
					System.out.print(neighbor + "->");
					visited[neighbor] = true;
					if (ladderPos.containsKey(neighbor)) {
						distance[neighbor] = distance[start];
					}
					else {
						distance[neighbor] = distance[start] + 1;
					}
				}
			}
			System.out.println();
		}
		for (int i = 1; i <= 30; i++) {
			System.out.print(distance[i] + " ");
		}
		System.out.println();
		for (int i = 1; i <= 30; i++) {
			System.out.print(i + " ");
		}
		return distance[30];
	}

	/* Create an adjacency list such that every box in the LUDO board denotes vertices,
	 * and it is connected to every vertex till the next six vertices. Since, the maximum value
	 * that can be attained is six on the dice. Since, we need to reach 30 in the minimum number
	 * of steps, we can ignore the snakes since it would always make the number of steps a little 
	 * more than the minimum steps.
	 * Failed Case: Only in one case can this logic fail -  If there are six snakes consecutively
	 * along a line, then we will need to consider a possibility of finding a ladder before the snakes so that 
	 * we are able to ignore them.
	 */
	private static void createLudoAdjacencyList(Graph g, HashMap<Integer, Integer> snakePos) {
		for (int i = 1; i <= 24; i++) {
			if (snakePos.containsKey(i)) {
				continue;
			}
			for (int j = i+1; j <= i+6; j++) {
				if (snakePos.containsKey(j)) {
					continue;
				}
				g.setEdge(i, j);
			}
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int testCase = sc.nextInt();

		while (testCase > 0) {
			testCase -= 1;
			/* Create a graph with 30 vertices.
			 * Add edges based on the duplication of the snake and ladder board.
			 */
			Graph g = new Graph(30);
			
			int snakeAndLadder = sc.nextInt();
			HashMap<Integer, Integer> snakePos = new HashMap<Integer, Integer>();
			HashMap<Integer, Integer> ladderPos = new HashMap<Integer, Integer>();

			while (snakeAndLadder > 0) {
				snakeAndLadder -= 1;
				int startVertex = sc.nextInt();
				int endVertex = sc.nextInt();
				if (endVertex > startVertex) {
					g.setEdge(startVertex, endVertex);
					ladderPos.put(endVertex, 1);
				}
				else {
					snakePos.put(startVertex, 1);
				}
			}
			createLudoAdjacencyList(g, snakePos);

			int startNode = 1;
			System.out.println(playLudo(g, startNode, ladderPos));
		}
	}
}