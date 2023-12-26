package model.solvers;

import controller.TileUpdate;
import controller.ViewUpdatePacket;
import model.*;
import utilities.Constants;

import java.util.*;


public class FloodFillSearchSolver extends Solver{

    private int[][] intGrid = new int[16][16];

    private VirtualCell currentVirtualCell = startPoint;

    private RealityCell currentRealityCell = this.getRealityGrid().getRealityCell(startPoint.getxPos(), startPoint.getyPos());
    public FloodFillSearchSolver(RealityGrid grid) {
        super(grid);
    }


    public FloodFillSearchSolver(RealityGrid grid, RealityCell startPoint, ArrayList<RealityCell> endPoints){
        super(grid, startPoint, endPoints);
    }
    public VirtualCell getCurrentVirtualCell() {
        return currentVirtualCell;
    }

    public RealityCell getCurrentRealityCell() {return currentRealityCell;}
    public void fill(){
        Queue<VirtualCell> cellQueue = new LinkedList<>();
        HashMap<VirtualCell, Integer> cellToIndexMap = new HashMap<>();
        HashSet<VirtualCell> queueSet = new HashSet<>();
        for(VirtualCell endPoints: this.getEndPoints()){
            cellQueue.add(endPoints);
            cellToIndexMap.put(endPoints, 0);
            queueSet.add(endPoints);
        }
        while(!cellQueue.isEmpty()){
            VirtualCell c = cellQueue.poll();
            c.setTraversed(true);
            queueSet.remove(c);
            for(VirtualCell neighbors : getUntraversedReachableNeighbors(c)){
                boolean isAnEndpoint = false;
                for(VirtualCell ep : this.getEndPoints()){
                    if (neighbors.equals(ep)) {
                        isAnEndpoint = true;
                        break;
                    }
                }
                if(!isAnEndpoint){
                    if(!queueSet.contains(neighbors)){
                        cellQueue.add(neighbors);
                        queueSet.add(neighbors);
                        cellToIndexMap.put(neighbors, cellToIndexMap.get(c) + 1);
                    }
                }
            }
        }
        for(Cell cell : cellToIndexMap.keySet()){
            intGrid[cell.getyPos()][cell.getxPos()] = cellToIndexMap.get(cell);
        }
        for(int i = 0; i < 16; i++){
            for(int j = 0; j < 16; j++){
                if(intGrid[i][j] == 0 && !endPoints.contains(this.getVirtualGrid().getVirtualCell(i,j))){
                    intGrid[i][j] = 694;
                }
            }
        }
    }

    @Override
    public ViewUpdatePacket makeViewUpdatePacket() {
        ViewUpdatePacket updatePacket = new ViewUpdatePacket(new ArrayList<>(300));


        for (int x = Constants.minCellIndex; x <= Constants.maxCellIndex; x++) {
            for (int y = Constants.minCellIndex; y <= Constants.maxCellIndex; y++) {

                Cell cell = this.getRealityGrid().getRealityCell(x, y);


                TileUpdate tileUpdate = Cell.makeTileUpdateFromCell(cell, false, false);
                updatePacket.addTileUpdate(tileUpdate);
            }
        }

        // Add the current cell at the end, will override its earlier addition
        if (this.getCurrentRealityCell() != null) {
            TileUpdate tileUpdate = Cell.makeTileUpdateFromCell(this.getCurrentRealityCell(), true, false);
            updatePacket.addTileUpdate(tileUpdate);
        }

        return updatePacket;
    }

    public int[][] getIntGrid(){
        return intGrid;
    }

    public void iterate(){
        if(this.isDone()){

        }
        else if(this.atDestination(currentVirtualCell)){
            this.setDone(true);
        }
        else{
            this.updateVirtualGrid(currentRealityCell);
            this.fill();
            int currentValue = intGrid[currentVirtualCell.getyPos()][currentVirtualCell.getxPos()];
            int currentXPos = currentVirtualCell.getxPos();
            int currentYPos = currentVirtualCell.getyPos();
            int i = 10;
            if(currentYPos<15){
                if(intGrid[currentYPos+1][currentXPos] == currentValue-1) {
                    currentVirtualCell = this.getVirtualGrid().getVirtualCell(currentXPos, currentYPos+1);
                    i = 0;
                }
            }
            if(currentYPos>0){
                if(intGrid[currentYPos-1][currentXPos] == currentValue-1) {
                    currentVirtualCell = this.getVirtualGrid().getVirtualCell(currentXPos, currentYPos-1);
                    i = 1;
                }
            }
            if(currentXPos<15){
                if(intGrid[currentYPos][currentXPos+1] == currentValue-1) {
                    currentVirtualCell = this.getVirtualGrid().getVirtualCell(currentXPos+1, currentYPos);
                    i = 2;
                }
            }
            if(currentXPos>0){
                if(intGrid[currentYPos][currentXPos-1] == currentValue-1) {
                    currentVirtualCell = this.getVirtualGrid().getVirtualCell(currentXPos-1, currentYPos);
                    i = 3;
                }
            }
            currentRealityCell = this.getRealityGrid().getRealityCell(currentVirtualCell.getxPos(), currentVirtualCell.getyPos());
            if(i==0){
                System.out.println("went down");
            }
            if(i==1){
                System.out.println("went up");
            }
            if(i==2){
                System.out.println("went right");
            }
            if(i==3){
                System.out.println("went left");
            }
        }

    }

    public void finish(){
        while (!this.isDone()) {
            this.iterate();
        }
    }
}
