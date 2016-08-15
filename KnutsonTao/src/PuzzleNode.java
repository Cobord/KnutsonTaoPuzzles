import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.tree.TreeNode;


public class PuzzleNode implements TreeNode {

	private PuzzleNode parent;
	private Puzzle mine;
	private ArrayList<PuzzleNode> children;
	
	PuzzleNode(Puzzle toSet,PuzzleNode parent)
	{
		mine=toSet;
		this.parent=parent;
		children=new ArrayList<PuzzleNode>();
		ArrayList<MyPlacement> boundary=mine.getBottom();
		MyPlacement a;
		boolean success;
		if(mine.full()||boundary.isEmpty())
		{
			System.out.println("Found a leaf");
			new Tester(this.mine);
			try {
				 Thread.sleep(10000);                 //1000 milliseconds is one second.
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
		}
		else{
			int boundarySize=boundary.size();
			for(int j=0;j<boundarySize;j++){
				a=boundary.get(j);
				for(int i=1;i<8;i++)
				{
					System.out.println("Trying "+ i + " at "+ a.toString());
					Puzzle possibleChild=(Puzzle) mine.copy();
					
					success=possibleChild.addPiece(i, a);
					
					if(success)
					{
						System.out.println("Possible Child");
						new Tester(possibleChild);
						try {
						 Thread.sleep(10000);                 //1000 milliseconds is one second.
						} catch(InterruptedException ex) {
						    Thread.currentThread().interrupt();
						}
						children.add(new PuzzleNode(possibleChild,this));
					}
					else
						possibleChild=null;
					
				}
			}
			System.out.println("Finished this branch");
		}
	}
	
	@Override
	public Enumeration<PuzzleNode> children() {
		// TODO Auto-generated method stub
		return (Enumeration<PuzzleNode>) children;
	}

	@Override
	public boolean getAllowsChildren() {
		// TODO Auto-generated method stub
		return !mine.full();
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		// TODO Auto-generated method stub
		return children.get(childIndex);
	}

	@Override
	public int getChildCount() {
		// TODO Auto-generated method stub
		return children.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		// TODO Auto-generated method stub
		return children.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		// TODO Auto-generated method stub
		return this.parent;
	}

	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return getChildCount()==0;
	}
	
	public boolean isBottomLeaf(){
		return isLeaf() && mine.full();
	}

}
