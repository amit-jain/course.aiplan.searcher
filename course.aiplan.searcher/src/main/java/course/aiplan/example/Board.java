/*
 * Genric search project for submission of Creative Challenge for 
 * AIPLAN Coursera course 
 * 
 * @author Amit Jain
 * @see https://www.coursera.org/course/aiplan
 * 
 */
package course.aiplan.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import course.aiplan.search.Node;
import course.aiplan.search.SearchExecutor;
import course.aiplan.search.SearchType;

/** 
 * The 8-puzzle Board.
 */
public class Board implements Node
{
    private short[][] blocks;

    private int dimension;

    private int hamming;

    private int manhattan;

    private int spacePos;

    private short[][] getBlocks()
    {
        return blocks;
    }

    private void setBoard(short[][] initialBlocks, boolean process)
    {
        short[][] blocks = deepCopy(initialBlocks);
        this.blocks = blocks;
        dimension = blocks.length;

        if (process)
        {
            calculateHammingDistance();
            calculateManhattanDistance();
        }
    }

    private void setBoard(int[][] initialBlocks, boolean process)
    {
        short[][] blocks = deepCopy(initialBlocks);
        this.blocks = blocks;
        dimension = blocks.length;

        if (process)
        {
            calculateHammingDistance();
            calculateManhattanDistance();
        }
    }  

    /**
     * Instantiates a new board from an N-by-N array of blocks
     * (where blocks[i][j] = block in row i, column j).
     *
     * @param blocks the blocks
     */
    public Board(int[][] initialBlocks)
    {
        setBoard(initialBlocks, true);
    }

    private Board(short[][] initialBlocks, boolean process)
    {
        if(initialBlocks == null)
            throw new NullPointerException();

        setBoard(initialBlocks, process);
    }

    /**
     * Board Dimension.
     *
     * @return the int
     */
    public int dimension()
    {
        return dimension;

    }

    /**
     * Hamming - Number of blocks out of place.
     *
     * @return the int
     */
    public int hamming()
    {
        return hamming;

    }

    /**
     * Sets the hamming distance.
     *
     * @param hamming the new hamming
     */
    private void setHamming(int hamming)
    {
        this.hamming = hamming;
    }

    /**
     * Manhattan - Sum of Manhattan distances between blocks and goal.
     *
     * @return the int
     */
    public int manhattan()
    {
        return manhattan;

    }

    /**
     * Sets the manhattan.
     *
     * @param manhattan the new manhattan
     */
    private void setManhattan(int manhattan)
    {
        this.manhattan = manhattan;
    }

    /**
     * Checks if board is goal board.
     *
     * @return true, if is goal
     */
    public boolean isGoal()
    {
        return (manhattan == 0);
    }

    private void setSpacePos(int spacePos)
    {
        this.spacePos = spacePos;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object y)
    {
        if (y == null)
            return false;

        Class yClass = y.getClass();    
        if (!this.getClass().equals(yClass))
            return false;

        Board that = (Board) y;
        short[][] thatBlocks = that.getBlocks();

        if(blocks.length != thatBlocks.length)
            return false;

        for (int idx = 0; idx < thatBlocks.length; idx++)
        {
            if(blocks[idx].length != thatBlocks[idx].length)
                return false;

            for (int jdx = 0; jdx < thatBlocks.length; jdx++)
            {
                if (blocks[idx][jdx] != thatBlocks[idx][jdx])
                {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int code = 0;
        for (int idx = 0; idx < blocks.length; idx++)
        {
            for (int jdx = 0; jdx < blocks.length; jdx++)
            {
                code += (blocks[idx][jdx]) * getTargetValue(idx, jdx);
            }
        }
        return (31 * code);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append(dimension() + "\n");

        for (int i = 0; i < dimension(); i++) 
        {
            for (int j = 0; j < dimension(); j++) 
            {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    private class BoardIterable implements Iterable<Node>
    {
        @Override
        public Iterator<Node> iterator()
        {
            return new BoardIterator();
        }
    }

    private class BoardIterator implements Iterator<Node>
    {
        private List<Board> boards;

        private int currentIdx;

        private int size;

        public BoardIterator()
        {
            currentIdx = 0;
            boards = new ArrayList<Board>();

            int spaceRow = getRowIndex(spacePos);
            int spaceCol = getColIndex(spacePos);

            if(spaceRow + 1 < blocks.length)
            {
                boards.add(getNeighbour(spaceRow, spaceCol, (spaceRow + 1), spaceCol));        
            }
            if(spaceCol + 1 < blocks.length)
            {
                boards.add(getNeighbour(spaceRow, spaceCol, spaceRow, (spaceCol + 1)));
            }

            if(spaceCol - 1 >= 0)
            {
                boards.add(getNeighbour(spaceRow, spaceCol, spaceRow, (spaceCol - 1)));        
            }
            if (spaceRow - 1 >= 0)
            {
                boards.add(getNeighbour(spaceRow, spaceCol, (spaceRow - 1), spaceCol));        
            }

            size = boards.size();
        }

        private Board getNeighbour(int spaceRow, int spaceCol, int targetRow, int targetCol)
        {
            Board neighbour = new Board(getBlocks(), false); 
            short[][] topNeighbour = neighbour.getBlocks();
            int negManhattanDelta = neighbour.deltaManhattanIdx(targetRow, targetCol);
            int negHammingDelta = neighbour.deltaHammingIdx(targetRow, targetCol);

            swap(topNeighbour, spaceRow, spaceCol, targetRow, targetCol);

            int posManhattanDelta = neighbour.deltaManhattanIdx(spaceRow, spaceCol);
            int posHammingDelta = neighbour.deltaHammingIdx(spaceRow, spaceCol);
            neighbour.setHamming((hamming() - negHammingDelta + posHammingDelta));
            neighbour.setManhattan((manhattan() - negManhattanDelta + posManhattanDelta));
            neighbour.setSpacePos(getTargetValue(targetRow, targetCol));

            return neighbour;
        }

        @Override
        public boolean hasNext()
        {
            return (currentIdx < size);
        }

        @Override
        public Board next()
        {
            return boards.get(currentIdx++);
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

    private void swap(short [][] blocks, int srcRow, int srcCol, int targetRow, int targetCol)
    {
        int temp = blocks[srcRow][srcCol];
        blocks[srcRow][srcCol] = blocks[targetRow][targetCol];
        blocks[targetRow][targetCol] = (short) temp;
    }

    private int getTargetValue(int row, int col)
    {
        int value = 0;
        value = row * dimension + col;
        return value;
    }

    private int getRowIndex(int targetVal)
    {
        return ((targetVal) / dimension);
    }

    private int getColIndex(int targetVal)
    {
        return ((targetVal) % dimension);
    }

    private void calculateManhattanDistance()
    {
        for (int row = 0; row < dimension; row++)
        {
            for (int col = 0; col < dimension; col++)
            {
                manhattan += deltaManhattanIdx(row, col);
                if (blocks[row][col] == 0)
                {
                    spacePos = getTargetValue(row, col);
                }
            }
        }
    }

    private int deltaManhattanIdx(int row, int col)
    {
        int delta = 0;
        int goalVal = getTargetValue(row, col);
        if ((blocks[row][col] != 0) && 
                (blocks[row][col] != goalVal))
        {
            int goalRow = getRowIndex(blocks[row][col]);
            int goalCol = getColIndex(blocks[row][col]);
            delta = Math.abs(goalRow - row) + Math.abs(goalCol - col);
        }
        return delta;
    }

    private void calculateHammingDistance()
    {
        for (int row = 0; row < dimension; row++)
        {
            for (int col = 0; col < dimension; col++)
            {
                hamming += deltaHammingIdx(row, col);
            }
        }
    }

    private int deltaHammingIdx(int row, int col)
    {
        int delta = 0;
        if ((blocks[row][col] != 0) && 
                (blocks[row][col] != getTargetValue(row, col)))
        {
            delta++;
        }

        return delta;
    }

    private short[][] deepCopy(short[][] initialBlocks)
    {
        short[][] blocks = new short [initialBlocks.length][];

        for (int row = 0; row < initialBlocks.length; row++)
        {
            System.arraycopy(
                    initialBlocks[row], 0, 
                    (blocks[row] = new short[initialBlocks[row].length]), 0, initialBlocks[row].length);
        }
        return blocks;
    }

    private short[][] deepCopy(int[][] initialBlocks)
    {
        short[][] blocks = new short [initialBlocks.length][];

        for (int row = 0; row < initialBlocks.length; row++)
        {
            blocks[row] = new short[initialBlocks[row].length];
            for(int col = 0; col < initialBlocks.length; col++)
            {
                blocks[row][col] = (short) initialBlocks[row][col];
            }
        }
        return blocks;
    }

    @Override
    public int getHueristicVal()
    {
        return manhattan();
    }

    @Override
    public Iterable<Node> getNeighbours()
    {
        return new BoardIterable();
    }

    @Override
    public int getNeighbourCost(Node neighbour)
    {
        return 1;
    }
    
    public static void main(String argv[])
    {
        try
        {
            FileReader reader = new FileReader(argv[0]);
            BufferedReader buffer = new BufferedReader(reader);
            String line = null;
            
            int boardSize = 0;
            int[][] blocks = null;;
            int count = 0;
            
            while((line = buffer.readLine()) != null)
            {
                if(count == 0)
                {
                    boardSize = Integer.parseInt(line);
                    blocks = new int[boardSize][boardSize];
                }
                else
                {
                    String[] tokens = (String[]) line.split(" ");
                    int idx = 0;
                    for(String token : tokens)
                    {
                        blocks[count-1][idx] = Integer.parseInt(token);
                        idx++;
                    }
                }
                count++;
            }
            SearchExecutor exec = new SearchExecutor();
            
            Node problem = new Board(blocks);
            exec.search(SearchType.ASTAR, problem);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
