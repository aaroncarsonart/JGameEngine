package utility;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

/**
 * Encapsulates all data for a Maze entity.
 *
 * @author Aaron Carson
 * @version Dec 18, 2014
 */
public class Maze {
    // ************************************************************************
    // Fields
    // ************************************************************************

    public static final byte PATH = 0;
    public static final byte WALL = 1;
    public static final byte OUT_OF_BOUNDS = 2;


    private int width;
    private int height;
    private byte[][] maze;

    // ************************************************************************
    // Constructors
    // ************************************************************************

    /**
     * Create a Maze initialized to be all paths.
     *
     * @param width The width of the maze in cells.
     * @param height The height of the maze in cells.
     */
    public Maze(int width, int height) {
        this.width = width;
        this.height = height;
        maze = new byte[width][height];
    }
    
    /**
     * Create a Maze initialized to be all paths.
     *
     * @param cells A two-dimensional array of cells to initialize the maze with.
     */
    public Maze(byte[][] cells) {
        if(cells.length < 3) throw new IllegalArgumentException("Maze must be at least 3 cells wide.");
        if(cells[0].length < 3) throw new IllegalArgumentException("Maze must be at least 3 cells tall.");

        width = cells.length;
        height = cells[0].length;

        maze = cells;
    }


    /**
     * Create a Maze initialized to a set value.
     *
     * @param width The width of the maze in cells.
     * @param height The height of the maze in cells.
     * @param initialValue The initial value (Maze.PATH or Maze.WALL);
     */
    public Maze(int width, int height, byte initialValue) {
        this.width = width;
        this.height = height;
        maze = new byte[width][height];
        fillWith(initialValue);
    }

    // ************************************************************************
    // Methods
    // ************************************************************************

    /**
     * Get the width of the maze.
     * @return the number of cells.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height of the maze.
     * @return the number of cells.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get the entire 2D array of bytes that represent each cell in the maze
     * @return All cells of the maze.
     */
    public byte[][] getCells(){
        return maze;
    }

    /**
     * Check if the given coordinates are contained within this maze (counting by cell)
     * @param x The horizontal coordinate.
     * @param y The vertical coordinate.
     * @return True, if the coordinates are within the maze.
     */
    public boolean withinBounds(int x, int y){
        return 0 <= x && x < width && 0 <= y && y < height;
    }

    /**
     * Check if the given coordinates are contained within this maze (counting by cell)
     * @param pos The Position containing the coordinates to check.
     * @return True, if the coordinates are within the maze.
     */
    public boolean withinBounds(Position pos){
        return withinBounds(pos.x(), pos.y());
    }


    /**
     * Get the cell at (x, y), represented by a byte.
     * @param x The x coordinate.
     * @param y The y coordinate
     * @return A single cell of the maze with a value of either Maze.PATH or Maze.WALL,
     *         or Maze.OUT_OF_BOUNDS if the point is out of bounds.
     */
    public byte getCell(int x, int y) {
        if(withinBounds(x,y)) return maze[x][y];
        else return OUT_OF_BOUNDS;
    }

    /**
     * Get the cell at the given Position, represented by a byte.
     * @param pos the Position to check against.
     * @return A single cell of the maze with a value of either Maze.PATH or Maze.WALL,
     *         or Maze.OUT_OF_BOUNDS if the point is out of bounds.
     */
    public byte getCell(Position pos) {
        return getCell(pos.x(),pos.y());
    }


    public void fillWithPaths() {
        fillWith(PATH);
    }

    public void fillWithWalls() {
        fillWith(WALL);
    }

    public void fillWith(byte b) {
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                maze[w][h] = b;
            }
        }
    }

    /*
     * (non-Javadoc) Uses the overloaded ToString(char, char) to build a string
     * representation of the maze using ' ' for paths and '#" for walls.
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return toBorderedString(' ', '#');
    }

    /**
     * Creates a string representation of the maze with the given char values to
     * represent a path and a wall of the maze.
     *
     * @param path The char to print for a path.
     * @param wall The char to print for a wall.
     * @return A string representation of the maze.
     */
    public String toString(char path, char wall) {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (maze[x][y] == PATH) sb.append(path);
                else if (maze[x][y] == WALL) sb.append(wall);
                else sb.append(maze[x][y]);
                if (x < width - 1) sb.append(' ');
            }

            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * Creates a string representation of the maze with the given char values to
     * represent a path and a wall of the maze.
     *
     * @param path The char to print for a path.
     * @param wall The char to print for a wall.
     * @return A string representation of the maze.
     */
    public String toBorderedString(char path, char wall) {
        StringBuilder sb = new StringBuilder();

        sb.append('+');
        for (int x = 0; x < width; x++) {
            sb.append(" -");
        }
        sb.append(" +\n");

        for (int y = 0; y < height; y++) {
            sb.append('|');
            for (int x = 0; x < width; x++) {
                sb.append(' ');
                if (maze[x][y] == PATH) sb.append(path);
                else if (maze[x][y] == WALL) sb.append(wall);
                else sb.append(maze[x][y]);
            }
            sb.append(" |\n");
        }

        sb.append('+');
        for (int x = 0; x < width; x++) {
            sb.append(" -");
        }
        sb.append(" +\n");

        return sb.toString();
    }

    // ************************************************************************
    // Static Methods
    // ************************************************************************

    /**
     * Create a random maze, with paths taht extend to the edges. If width or
     * height or not odd numbers, they will be incremented to become odd.
     *
     * @param width
     * @param height
     * @return A new randomly generated maze, using a depth-first search.
     */
    public static Maze generateRandomMaze(int width, int height) {
        if (width % 2 == 0) width++;
        if (height % 2 == 0) height++;
        Maze m = new Maze(width, height);
        m.fillWithWalls();

        ArrayList<Position> checklist = new ArrayList<Position>();
        int halfWidth = width / 2 + 1;
        int halfHeight = height / 2 + 1;
        boolean visited[][] = new boolean[halfWidth][halfHeight];

        boolean digging = true;
        Random rand = new Random();
        Position digger = new Position(0, 0);
        visited[digger.x()][digger.y()] = true;

        while (digging) {
            ArrayList<Position> neighbors = new ArrayList<Position>();
            neighbors.add(digger.above());
            neighbors.add(digger.below());
            neighbors.add(digger.left());
            neighbors.add(digger.right());

            Iterator<Position> it = neighbors.iterator();

            while (it.hasNext()) {
                Position pos = it.next();
                int x = pos.x();
                int y = pos.y();
                if (!(0 <= x && x < halfWidth && 0 <= y && y < halfHeight)
                        || visited[x][y]) it.remove();

            }

            if (neighbors.size() > 0) {
                checklist.add(new Position(digger));
                Position target = neighbors.get(rand.nextInt(neighbors.size()));

                int digX;
                if (digger.x() == target.x()) digX = digger.x() * 2;
                else digX = Math.min(digger.x(), target.x()) * 2 + 1;

                int digY;
                if (digger.y() == target.y()) digY = digger.y() * 2;
                else digY = Math.min(digger.y(), target.y()) * 2 + 1;

                m.maze[digX][digY] = PATH;
                m.maze[target.x() * 2][target.y() * 2] = PATH;

                visited[target.x()][target.y()] = true;
                digger = new Position(target);

            } else {
                checklist.remove(digger);
                if (checklist.size() == 0) return m;
                else digger = checklist.get(rand.nextInt(checklist.size()));
            }
        }
        return null;
    }

    /**
     * Creates a random maze, surrounded by a "wall" of walls. If width or
     * height or not odd numbers, they will be incremented to become odd.
     *
     * @param width
     * @param height
     * @return A new randomly generated maze, using a depth-first search .
     */
    public static Maze generateRandomWalledMaze(int width, int height) {

        // ensure odd number dimensions
        if (width % 2 == 0) width++;
        if (height % 2 == 0) height++;
        Maze m = new Maze(width, height);

        //initialize values of maze
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (y % 2 == 0) m.maze[x][y] = WALL;
                else if (x % 2 == 0) m.maze[x][y] = WALL;
                else m.maze[x][y] = PATH;
            }
        }

        //local values needed for digging out the maze
        ArrayList<Position> checklist = new ArrayList<Position>();
        int halfWidth = width / 2;
        int halfHeight = height / 2;
        boolean visited[][] = new boolean[halfWidth][halfHeight];

        boolean digging = true;
        Random rand = new Random();
        Position digger = new Position(0, 0);
        visited[digger.x()][digger.y()] = true;

        //dig out the maze
        while (digging) {

            //get neighboring positions
            ArrayList<Position> neighbors = new ArrayList<Position>();
            neighbors.add(digger.above());
            neighbors.add(digger.below());
            neighbors.add(digger.left());
            neighbors.add(digger.right());

            Iterator<Position> it = neighbors.iterator();

            //remove neighbors outside of maze
            while (it.hasNext()) {
                Position pos = it.next();
                int x = pos.x();
                int y = pos.y();

                // remove if position has already been visited, or is out of bounds
                if (!(0 <= x && x < halfWidth && 0 <= y && y < halfHeight)
                        || visited[x][y]) it.remove();

            }

            //if there exist at least one valid neighbor to dig to
            if (neighbors.size() > 0) {
                checklist.add(new Position(digger));

                //randomly select a new digging target
                Position target = neighbors.get(rand.nextInt(neighbors.size()));

                //calculate the coordinates to dig out of the maze array
                int x1 = digger.x() * 2 + 1;
                int x2 = target.x() * 2 + 1;

                int digX;
                if (x1 == x2) digX = x1;
                else digX = Math.min(x1, x2) + 1;

                int y1 = digger.y() * 2 + 1;
                int y2 = target.y() * 2 + 1;

                int digY;
                if (y1 == y2) digY = y1;
                else digY = Math.min(y1, y2) + 1;

                // dig out the path inbetween digger and target
                m.maze[digX][digY] = PATH;

                // mark as visited
                visited[target.x()][target.y()] = true;

                // set target position as new digger
                digger = new Position(target);

            }

            // otherwise
            else {

                //digger position is removed from the checklist
                checklist.remove(digger);

                // finished if the algorithm is empty; otherwise, get another random position
                if (checklist.size() == 0) return m;
                else digger = checklist.get(rand.nextInt(checklist.size()));
            }
        }
        return null;
    }

    /**
     * Creates a graph traversal of this maze.
     * @return A graph of positional data.
     */
    public Graph<Position> getPositionGraph(){
        Position start = new Position(1,1);
        HashSet<Position> visited = new HashSet<Position>();
        Queue<Position> queue = new LinkedList<Position>();
        Graph<Position> graph = new Graph<Position>();

        visited.add(start);
        queue.add(start);
        graph.addVertex(start);

        while(!queue.isEmpty()){
            Position current = queue.remove();
            for(Position neighbor : current.getNeighbors()) {

                // case 1: been here already.
                if(visited.contains(neighbor)){
                    System.out.println("already visited");
                    //Log.d(tag, "previously visited");
                    continue;
                }

                // case 2: it is a wall
                else if(getCell(neighbor.x(), neighbor.y()) == Maze.WALL){
                    System.out.println("found wall");
                    //Log.d(tag, "found wall");
                    continue;
                }

                //case 4: it is an open path
                else{
                    System.out.println("found path from " + current + " to " + neighbor);

                    //mark this position as added
                    visited.add(neighbor);

                    graph.addVertex(neighbor);
                    graph.addDirectedEdge(current, neighbor);

                    //add this state to the state queue.
                    queue.add(neighbor);
                }
            }

        }

        return graph;
    }

    
    /**
     * Intended for use with randomly-generated mazes only.  This removes a number of
     * walls to create more connected passages, and make it easier to navigate the maze.
     */
    public void setDifficulty(Difficulty difficulty)
    {
        // TODO make me remove walls in various ways.
    	
    	// instantiate the accumulator
    	MazeBranchAccumulator wallAccumulator = new MazeBranchAccumulator(this, WALL);
    	wallAccumulator.countBranchDistances(0,0,width);
    	PriorityQueue<MazeBranchAccumulator.Cell> walls = wallAccumulator.getRemovableWallsPriorityQueue();
        //used to calculate how many paths to remove
        int multiplier = 0;

        //set addedPaths according to difficulty level
        switch (difficulty){
            case EASY: multiplier = 2;
                break;
            case NORMAL: multiplier = 1;
                break;
            case HARD: multiplier = 0;
                break;
        }

        //create a list of valid cells to iterate over
        //remove from only what were the original "connecting walls"
        int addedPaths = ( (width) / 4 ) * multiplier;
        
        //Iterate up to the amount of added paths
        for (int i = 0; i < addedPaths; i ++){
        	// 1. get a wall from the priority queue
        	MazeBranchAccumulator.Cell cell = walls.poll();
        	System.out.println("Remove: " + cell + " " + cell.distance);
        	
            // 2. make it a path
            maze[cell.x][cell.y] = PATH;
            cell.type = Maze.PATH;
            // 3. fix the neighboring walls to the cell with only one neighbor
            for(MazeBranchAccumulator.Cell neighbor : cell.getNeighbors()){
            	if(neighbor.getNeighbors().size() == 1){
            		Set<MazeBranchAccumulator.Cell> changed = wallAccumulator.countBranchDistances(neighbor.x, neighbor.y, 0);
            		wallAccumulator.removeAndAddToPriorityQueue(changed);
            	}
            }

        }
        //removeIslands();
    }

    /**
     * Intended for use with randomly-generated mazes only.  This removes a number of
     * walls to create more connected passages, and make it easier to navigate the maze.
     */
    public void setDifficultyOLD(Difficulty difficulty)
    {
        //used to calculate how many paths to remove
        int multiplier = 0;

        //set addedPaths according to difficulty level
        switch (difficulty){
            case EASY: multiplier = 2;
                break;
            case NORMAL: multiplier = 1;
                break;
            case HARD: multiplier = 0;
                break;
        }

        //create a list of valid cells to iterate over
        ArrayList<Position> checkList = new ArrayList<Position>();
        for (int x = 1; x < width - 1; x ++){
            for (int y = 1; y < height - 1; y ++){
                //if the coordinate is a wall
                if(maze[x][y]==1){
                    //add it to the list to iterate over
                    checkList.add(new Position(x,y));
                }
            }
        }
        int addedPaths = ( (width + height) / 4 ) * multiplier;
        Random random = new Random();

        //Iterate up to the amount of added paths
        for (int i = 0; i < addedPaths; i ++){
            int x = 1, y = 1;
            boolean validPanel = false;
            while (!validPanel && checkList.size() != 0){

                //generate random number within the outer border
                Position temp = checkList.get(random.nextInt(checkList.size()));
                x = temp.x();
                y = temp.y();

                /*
                 * if the panel is a wall, and it is between two other panels on either
                 * the x or y axis that are both paths ...
                 */

                validPanel = (maze[x][y+1] == 0 && maze[x][y-1] == 0)|| (maze[x+1][y] == 0 && maze[x-1][y] == 0);
                checkList.remove(temp);
            }

            //remove the panel if is a valid location
            if(validPanel) maze[x][y] = PATH;

        }
        removeIslands();
    }

    /**
     * Removes panels that is surrounded by whitespace, or removes a panel if it only has
     * one other panel in one of the four corners.
     */
    private void removeIslands()
    {
        for (int x = 1; x < width - 1; x ++){
            for (int y = 1; y < height - 1; y ++){
                int counter = 0;
                //if the position is a wall
                if (
                        (maze[x-1][y+1] == 0 && maze[x][y+1] == 0 && maze[x+1][y+1] == 0 &&
                                maze[x-1][ y ] == 0 && maze[x][ y ] == 1 && maze[x+1][ y ] == 0 &&
                                maze[x-1][y-1] == 0 && maze[x][y-1] == 0 && maze[x+1][y-1] == 0 )
                                ||
                                (maze[x-1][y+1] == 1 && maze[x][y+1] == 0 && maze[x+1][y+1] == 0 &&
                                        maze[x-1][ y ] == 0 && maze[x][ y ] == 1 && maze[x+1][ y ] == 0 &&
                                        maze[x-1][y-1] == 0 && maze[x][y-1] == 0 && maze[x+1][y-1] == 0 )
                                ||
                                (maze[x-1][y+1] == 0 && maze[x][y+1] == 0 && maze[x+1][y+1] == 1 &&
                                        maze[x-1][ y ] == 0 && maze[x][ y ] == 1 && maze[x+1][ y ] == 0 &&
                                        maze[x-1][y-1] == 0 && maze[x][y-1] == 0 && maze[x+1][y-1] == 0 )
                                ||
                                (maze[x-1][y+1] == 0 && maze[x][y+1] == 0 && maze[x+1][y+1] == 0 &&
                                        maze[x-1][ y ] == 0 && maze[x][ y ] == 1 && maze[x+1][ y ] == 0 &&
                                        maze[x-1][y-1] == 1 && maze[x][y-1] == 0 && maze[x+1][y-1] == 0 )
                                ||
                                (maze[x-1][y+1] == 0 && maze[x][y+1] == 0 && maze[x+1][y+1] == 0 &&
                                        maze[x-1][ y ] == 0 && maze[x][ y ] == 1 && maze[x+1][ y ] == 0 &&
                                        maze[x-1][y-1] == 0 && maze[x][y-1] == 0 && maze[x+1][y-1] == 1 )
                        ){
                    maze[x][y] = PATH;
                }
            }
        }
    }
    
	/**
	 * Create a new Dungeon with the given parameters.
	 * @param width The width in tiles.
	 * @param height The height in tiles.
	 * @param rooms The number of rectangular rooms to randomly generate in the
	 * space.
	 * @return a new dungeon of given size with the given number of
	 * interconnected rooms.
	 */
	public static final Maze createWalledDungeon(int width, int height, int rooms){

		// 0. make dungeon with room for an outer wall.
		Maze m = new Maze(width + 2, height + 2);
				
		// 4. Depth first search through the dungeon like the maze.
		/*
		//local values needed for digging out the maze
        ArrayList<Position> checklist = new ArrayList<Position>();
        int halfWidth = width / 2;
        int halfHeight = height / 2;
        boolean visited[][] = new boolean[halfWidth][halfHeight];

        boolean digging = true;
        Random rand = new Random();
        Position digger = new Position(0, 0);
        visited[digger.x()][digger.y()] = true;

        //dig out the maze
        while (digging) {

            //get neighboring positions
            ArrayList<Position> neighbors = new ArrayList<Position>();
            neighbors.add(digger.above());
            neighbors.add(digger.below());
            neighbors.add(digger.left());
            neighbors.add(digger.right());

            Iterator<Position> it = neighbors.iterator();

            //remove neighbors outside of maze
            while (it.hasNext()) {
                Position pos = it.next();
                int x = pos.x();
                int y = pos.y();

                // remove if position has already been visited, or is out of bounds
                if (!(0 <= x && x < halfWidth && 0 <= y && y < halfHeight)
                        || visited[x][y]) it.remove();

            }

            //if there exist at least one valid neighbor to dig to
            if (neighbors.size() > 0) {
                checklist.add(new Position(digger));

                //randomly select a new digging target
                Position target = neighbors.get(rand.nextInt(neighbors.size()));

                //calculate the coordinates to dig out of the maze array
                int x1 = digger.x() * 2 + 1;
                int x2 = target.x() * 2 + 1;

                int digX;
                if (x1 == x2) digX = x1;
                else digX = Math.min(x1, x2) + 1;

                int y1 = digger.y() * 2 + 1;
                int y2 = target.y() * 2 + 1;

                int digY;
                if (y1 == y2) digY = y1;
                else digY = Math.min(y1, y2) + 1;

                // dig out the path inbetween digger and target
                m.maze[digX][digY] = PATH;

                // mark as visited
                visited[target.x()][target.y()] = true;

                // set target position as new digger
                digger = new Position(target);

            }

            // otherwise
            else {

                //digger position is removed from the checklist
                checklist.remove(digger);

                // finished if the algorithm is empty; otherwise, get another random position
                if (checklist.size() == 0) return m;
                else digger = checklist.get(rand.nextInt(checklist.size()));
            }
        }
		 */
		// 5. Done.
		return m;		
	}
	
public static Maze createRandomWalledRoomedMaze(int width, int height, int rooms){
    	
    	// step 1: create a walled maze.
    	Maze m = generateRandomWalledMaze(width, height);
    	
    	Random random = new Random();
		//boolean[][] visited = new boolean[width + 2][height + 2];		
		ArrayList<Quad> quads = new ArrayList<Quad>(rooms);
		
		int minRoomSize = 3;
		int maxRoomSize = 10;
		int offsetWidth  = Math.max(maxRoomSize, width / 4) - minRoomSize;
		int offsetHeight = Math.max(maxRoomSize, height / 4 - minRoomSize);
		
		//System.out.printf("maxWidth: %d maxHeight: %d\n", maxWidth, maxHeight);
		
		
		// 2. remove rooms and make as paths and visited.
		for(int i = 0; i < rooms; i++){
			int y1  = 1 + random.nextInt(height - minRoomSize);
			int y2  = Math.min(height, y1 + minRoomSize + random.nextInt(offsetWidth));
			int x1  = 1 + random.nextInt(width - minRoomSize);
			int x2  =  Math.min(width, x1 + minRoomSize + random.nextInt(offsetHeight));
			double xSize = Math.abs(x1 - x2);
			double ySize = Math.abs(y1 - y2);
			double hw = xSize / 2;
			double hh = xSize / 2;
			double cx = Math.min(x1, x2) + hw;
			double cy = Math.min(y1, y2) + hh;
			
			Quad quad = new Quad(cx, cy, hw, hh);
			for(Quad q : quads){
				quad.fixIfCollides(q);
			}
			if (!quad.offScreen(width, height)){
				quads.add(quad);
				for(int x = (int)quad.getLeftX(); x < quad.getRightX(); x++){
					for(int y = (int)quad.getTopY(); y < quad.getBottomY(); y++){
						m.maze[x][y] = PATH;
					}	
				}
			}
		}
		
		// 3. Add 1 - 2 unvisited paths on the perimeter of each room.
		for(int i = 0; i < quads.size(); i++){
				Quad q = quads.get(i);
				int k = random.nextInt(4);
				int x = 0, y = 0;
				switch(k){
				// add to left
				case 0:
					x = (int) q.getLeftX() - 1;
					y = random.nextInt((int)q.getHeight()) + (int)q.getTopY();
					break;

					// add to right
				case 1:
					x = (int) q.getRightX() + 1;
					y = random.nextInt((int)q.getHeight()) + (int)q.getTopY();
					break;
					
					// add to above
				case 2:
					y = (int) q.getTopY() - 1;
					x = random.nextInt((int)q.getWidth()) + (int)q.getLeftX();
					break;
					
					// add to below
				case 3:
					y = (int) q.getBottomY() + 1;
					x = random.nextInt((int)q.getWidth()) + (int)q.getLeftX();
					break;
				}
				
				m.maze[x][y] = PATH;
		}

		return m;
    	
    }
    
    /**
     * Generate a room that randomly grows until a percentage is reached.
     * @param width The width of the cells to save.
     * @param height The height of the cells to save.
     * @param percent
     * @return
     */
	public static Maze generateRandomShapedRoom(int width, int height, double percent, boolean tunnels){
		
		Maze m = new Maze(width, height, WALL);
		Random r = new Random();
		boolean[][] visited = new boolean[width][height];
		
		// dig out the center square.
		Position digger = new Position(width / 2, height / 2);
		m.maze[digger.x()][digger.y()] = PATH;
		visited[digger.x()][digger.y()] = true;
		int count = 1;
								
		int max = width * height;
		
		// create a room of max size of 
		while (count / max < percent){	
			count++;
			// 1. find a bordering neighbor.
			// dig out any unoccupied neighbors.
			List<Position> neighbors = new ArrayList<Position>();
			neighbors.add(digger.above());
			neighbors.add(digger.below());
			neighbors.add(digger.left());
			neighbors.add(digger.right());
			neighbors.add(new Position(digger.x() - 1, digger.y() - 1));
			neighbors.add(new Position(digger.x() - 1, digger.y() + 1));
			neighbors.add(new Position(digger.x() + 1, digger.y() - 1));
			neighbors.add(new Position(digger.x() + 1, digger.y() + 1));
			
			for(int i = neighbors.size() - 1; i >= 0; i--){
				Position next = neighbors.get(i);
				if(m.withinBounds(next)){
					if(visited[next.x()][next.y()]){
						if (tunnels) neighbors.remove(i);
						continue;
					}
					else if (m.maze[next.x()][next.y()] == WALL){
						digger = next;
						m.maze[next.x()][next.y()] = PATH;
						visited[next.x()][next.y()] = true;					
						count ++;
					}
				}
			}
			
			if(neighbors.isEmpty()) break;
			 digger = neighbors.get(r.nextInt(neighbors.size()));
			
		}
		
		
		return m;
	}

    /**
     * Convenience method for hard-coding generated mazes in code.
     * @param path The string to represent a path as.
     * @param wall The string to represent a wall as.
     * @return a 2d array block {{}}.
     */
    public String getTwoDimensionalArrayText(String path, String wall){
    	StringBuilder sb = new StringBuilder();
		sb.append('{');
    	for(int y = 0; y < height; y++){
    		sb.append('{');
        	for(int x = 0; x < width; x++){
        		if(maze[x][y] == Maze.PATH) sb.append(path);
        		else sb.append(wall);
        		sb.append(", ");
        	}
        	sb.append("},\n");
    	}
		sb.append('}');
    	return sb.toString();
    }
    

    private static void testRandom(int width, int height) {
        Maze m;
        m = Maze.createWalledDungeon(width, height, 20);
        //m = Maze.generateRandomWalledMaze(width, height);
        //m.setDifficulty(Difficulty.EASY);
        System.out.println(m.toString());
        
    }
    
    // ************************************************************************
    // MAIN
    // ************************************************************************

    /**
     * Used for testing.
     *
     * @param args
     */
    public static void main(String[] args) {
        // Try main args first
        if (args.length == 2) {
            try {
                int width = Integer.parseInt(args[0]);
                int height = Integer.parseInt(args[1]);
                testRandom(width, height);
            } catch (NumberFormatException ex) {
                System.out.print("Invalid input; ");
                System.out.println("please pass 2 ints for width and height.");
            }
        }

        // use default values
        else {
            //testRandom(100, 100);
        	boolean tunnels = true;
        	boolean cavern = false;
        	Maze m;
        	for (double p = 0.1; p < 1.0; p += 0.1){
        		long t = System.currentTimeMillis();
        		System.out.printf("\n%2.2f%% Cavern\n", p);
        		m = generateRandomShapedRoom(50,50, p, cavern);
                System.out.println(m.toString());   

        		System.out.printf("\n%2.2f%% Tunnels\n", p);
        		m = generateRandomShapedRoom(50,50, p, tunnels);
                System.out.println(m.toString());   
                System.out.printf("elapsed: %.5f s.\n", (System.currentTimeMillis() - t) / 1000.0);
        	}

        	
    	
        }
    }



}
