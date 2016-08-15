import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.tree.DefaultTreeModel;

public class Tester extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Puzzle displayed;
	public Tester(Puzzle mine) {
		displayed=mine;
	    JFrame frame = new JFrame("Puzzle");  
	    frame.add(this);

	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	    frame.setSize(400,400);  
	    frame.setLocationRelativeTo(null);  
	    frame.setVisible(true);  
	  }

	  public void paint(Graphics g) {
	    super.paint( g );
	    int[] x;
	    int[] y;
	    
	    for(int i=0;i<displayed.getSize();i++){
	    	for(int j=-i;j<=i;j+=2){
	    		y = new int[] {50*i,50+50*i,50+50*i};
	    		x = new int[] {200+29*j,200+29*(j-1),200+29*(j+1)};
	    		g.setColor(chooseColor(i,j,1));
	    		g.drawLine(x[1], y[1], x[2], y[2]);
	    		g.setColor(chooseColor(i,j,2));
	    		g.drawLine(x[1], y[1], x[0], y[0]);
	    		g.setColor(chooseColor(i,j,3));
	    		g.drawLine(x[0], y[0], x[2], y[2]);
	    	}
	    }
	  }
	  
	  private MyPlacement whichCell(int x, int y)
	  {
		  int i,j;
		  boolean b=true;
		  i=Math.floorDiv(y, 50);
		  j=Math.floorDiv(x-200, 29);
		  return new MyPlacement(i,b,j);
		  //which triangle you are in
	  }
	  
	  private Color chooseColor(int i,int j,int k)
	  {
		  int edgeLabel=0;
		  boolean isBoundary=false;
		  if(k==1){
			  int col=(j+i)/2;
			  edgeLabel=displayed.getEdgelabel(i,false,col);
			  isBoundary=displayed.isBorder(i,false,col);
		  }
		  else if(k==2){
			  int col=(j+i);
			  edgeLabel=displayed.getEdgelabel(i,true,col);
			  isBoundary=displayed.isBorder(i,true,col);
		  }
		  else{
			  int col=(j+i)+1;
			  edgeLabel=displayed.getEdgelabel(i,true,col);
			  isBoundary=displayed.isBorder(i,true,col);
		  }
		  
		  switch(edgeLabel)
		  {
		  case 0:
			  return Color.GRAY;
		  case 1:
			  if(isBoundary)
				  return Color.CYAN;
			  else
				  return Color.BLUE;
		  case -1:
			  if(isBoundary)
				  return Color.MAGENTA;
			  else
				  return Color.RED;
		  case 10:
			  return Color.GREEN;
		  default:
			  return Color.ORANGE;	 
		  }
	  }

	
	public static void main(String[] args)
	{
		int[] boundarydata={1,1,-1,-1,1,1,1,1,-1};
		Puzzle mine=new Puzzle(3,boundarydata);
		//System.out.println(mine.getSize());
		int temp;
		/*for(int i=0;i<3;i++)
		{
			for(int j=0;j<2*i+2;j++)
			{
				temp=Puzzle.getIndexFromRowColumn(i, true, j);
				System.out.print(mine.getEdgelabel(temp));
			}
			System.out.println("");
			for(int j=0;j<i+1;j++)
			{
				temp=Puzzle.getIndexFromRowColumn(i, false, j);
				System.out.print(mine.getEdgelabel(temp));
			}
			System.out.println("");
		}*/
		
		Tester test = new Tester(mine);
		System.out.println(mine.getBottom().toString()+": 1");
		mine.addPiece(1, 0, true, 0);
		test.repaint();
		System.out.println(mine.getBottom().toString()+": 2");
		try {
			 Thread.sleep(0000);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		
		
		mine.addPiece(3, 1, true, 0);
		test.repaint();
		System.out.println(test.displayed.getBottom().toString()+": 3");
		try {
			 Thread.sleep(0000);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		
		test.displayed.addPiece(6, 0, false, 0);
		test.repaint();
		System.out.println(test.displayed.getBottom().toString()+": 4");
		try {
			 Thread.sleep(0000);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		
		test.displayed.addPiece(6, 0, false, 0);
		test.repaint();
		System.out.println(test.displayed.getBottom().toString()+": 5");
		try {
			 Thread.sleep(0000);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		System.out.println("Done");
		
		
		new DefaultTreeModel(new PuzzleNode(mine,null));
		
		System.out.println("Done");
	}

}
