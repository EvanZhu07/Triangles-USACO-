//USACO Silver 2020 Feb Problem 2 Triangles

import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader("triangles.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("triangles.out")));
		
		int n = Integer.parseInt(in.readLine());
		List<Coordinate> cords = new ArrayList<>();
		Map<Coordinate, Long> verticalSum = new HashMap<>();
		Map<Coordinate, Long> horizontalSum = new HashMap<>();
		List<List<Coordinate>> groups = new ArrayList<>();
		int mod = (int) (1e9 + 7);
		long sum = 0;
		
		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			cords.add(new Coordinate(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
		}
		
		Collections.sort(cords, new SortX());
		
		List<Coordinate> currentGroup = new ArrayList<>();
		for (int i = 0; i < cords.size(); i++) {
			if (i > 0 && cords.get(i).x != cords.get(i - 1).x) {
				groups.add(currentGroup);
				currentGroup = new ArrayList<>();
			}
			currentGroup.add(cords.get(i));
		}
		groups.add(currentGroup);
		currentGroup = new ArrayList<>();
		
		for (List<Coordinate> group : groups) {
			long add = 0;
			long sub = group.size() - 2;
			int index = 1;
			long current = 0;
			for (int i = 1; i < group.size(); i++) {
				current += group.get(i).y - group.get(0).y;
			}
			verticalSum.put(group.get(0), current);
			while (sub >= 0) {
				int dif = group.get(index).y - group.get(index - 1).y;
				current = current + (add - sub) * dif ;
				verticalSum.put(group.get(index), current);
				index++;
				add++;
				sub--;
			}
		}
		
		Collections.sort(cords, new SortY());
		
		groups = new ArrayList<>();
		for (int i = 0; i < cords.size(); i++) {
			if (i > 0 && cords.get(i).y != cords.get(i - 1).y) {
				groups.add(currentGroup);
				currentGroup = new ArrayList<>();
			}
			currentGroup.add(cords.get(i));
		}
		groups.add(currentGroup);
		currentGroup = new ArrayList<>();
		
		for (List<Coordinate> group : groups) {
			long add = 0;
			long sub = group.size() - 2;
			int index = 1; 
			long current = 0;
			for (int i = 1; i < group.size(); i++) {
				current += group.get(i).x - group.get(0).x;
			}
			horizontalSum.put(group.get(0), current);
			while (sub >= 0) {
				int dif = group.get(index).x - group.get(index - 1).x;
				current = current + (add - sub) * dif;
				horizontalSum.put(group.get(index), current);
				index++;
				add++;
				sub--;
			}
		}
		
		for (Coordinate co : cords) {
			sum += (verticalSum.get(co) % mod)  * (horizontalSum.get(co) % mod);
			sum %= mod;
		}
		
		out.println(sum);
		
		in.close();
		out.close();
	}
}

class Coordinate {
	int x;
	int y;
	
	public Coordinate (int x, int y) {
		this.x = x;
		this.y = y;
	}
}

class SortX implements Comparator<Coordinate> {
	public int compare (Coordinate a, Coordinate b) {
		if (a.x == b.x) {
			return a.y - b.y;
		}
		return a.x - b.x;
	}
}

class SortY implements Comparator<Coordinate> {
	public int compare (Coordinate a, Coordinate b) {
		if (a.y == b.y) {
			return a.x - b.x;
		}
		return a.y - b.y;
	}
}