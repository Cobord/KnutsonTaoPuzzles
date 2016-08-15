import java.util.ArrayList;


/**
 * 
 */

/**
 * @author Owner
 *
 */
public class Puzzle implements Cloneable{

	/**
	 * @param args
	 */
	private int size;
	private ArrayList<MyPlacement> bottom;
	private int[] edgelabels;
	
	Puzzle(int setsize)
	{
		size=setsize;
		setEdgelabels(new int[edgeLabelSize(size)]);
		setBottom(new ArrayList<MyPlacement>());
	}
	
	Puzzle(int setsize,int[] lambdaMuNu)
	{
		this(setsize);
		int lambda,mu,nu;
		int lambdaPos,muPos,nuPos;
		for(int i=0;i<setsize;i++)
		{
			lambda=lambdaMuNu[i];
			mu=lambdaMuNu[setsize+i];
			nu=lambdaMuNu[2*setsize+i];
			lambdaPos=getIndexFromRowColumn(setsize-i-1,true,0);
			muPos=getIndexFromRowColumn(i,true,2*i+1);
			nuPos=getIndexFromRowColumn(setsize-1,false,i);
			edgelabels[lambdaPos]=lambda;
			edgelabels[muPos]=mu;
			edgelabels[nuPos]=nu;
			bottom.add(new MyPlacement(setsize-i-1,true,0));
			bottom.add(new MyPlacement(i,true,2*i+1));
			bottom.add(new MyPlacement(setsize-1,false,i));
			
		}
	}
	Puzzle(Puzzle toCopy)
	{
		this(toCopy.getSize());
		ArrayList<MyPlacement> temp = toCopy.getBottom();
		for(int i=0;i<temp.size();i++)
		{
			MyPlacement p=temp.get(i);
			this.bottom.add(new MyPlacement(p));
			//System.out.print(i+" "+p.toString());
		}
		//System.out.println("Next ");
		for(int i=0;i<toCopy.edgelabels.length;i++)
		{
			this.edgelabels[i]=toCopy.getEdgelabel(i);
		}
	}
	
	public int getSize()
	{
		return size;
	}
	
	private static int edgeLabelSize(int size)
	{
		if(size==0)
			return 0;
		else
			return edgeLabelSize(size-1)+3*size;
	}

	public ArrayList<MyPlacement> getBottom() {
		return bottom;
	}

	private void setBottom(ArrayList<MyPlacement> bottom) {
		this.bottom = bottom;
	}

	public int[] getEdgelabels() {
		return edgelabels;
	}
	
	public int getEdgelabel(int which)
	{
		try{
			return edgelabels[which];
		}
		catch(Exception e)
		{
			System.out.println(which);
			return 100;
		}
	}
	public int getEdgelabel(int row,boolean midLine,int column)
	{
		return getEdgelabel(getIndexFromRowColumn(row, midLine, column));
	}

	private void setEdgelabels(int[] edgelabels) {
		this.edgelabels = edgelabels;
	}
	
	public static int getIndexFromRowColumn(int row,boolean midLine,int column)
	{
		//row is the size of the completed triangle above you, midLine is true when not a horizantal line
		//column indexes the placement in that row starting with 0
		int temp=edgeLabelSize(row);
		if (midLine)
			return temp+column;
		else
			return temp+2*row+2+column;
	}
	public static int getIndexFromRowColumn(MyPlacement p)
	{
		return getIndexFromRowColumn(p.row,p.midLine,p.column);
	}
	
	public boolean addPiece(int pieceType,MyPlacement p)
	{
		return addPiece(pieceType,p.row,p.midLine,p.column);
	}
	
	public boolean addPiece(int pieceType,int positionrow,boolean positionMidLine,int positionColumn)
	{
		MyPlacement[] placesToAdd;
		int[] whatToPut;
		MyPlacement position1, position2,position3,position4, position5;
		if(pieceType==1 && positionMidLine && positionColumn%2==0){
			// 0 triangle pointing up starts with top left read clockwise
			whatToPut=new int[] { -1,-1,-1 };
			position1=new MyPlacement(positionrow,true,positionColumn);
			position2=new MyPlacement(positionrow,true,positionColumn+1);
			position3=new MyPlacement(positionrow,false,positionColumn/2);
			placesToAdd=new MyPlacement[] {position1,position2 , position3};
		}
		else if(pieceType==2 && !positionMidLine && positionrow<size-1)
		{	// 0 triangle pointing down starts with top read clockwise
			whatToPut=new int[] {-1,-1,-1};
			position1=new MyPlacement(positionrow,false,positionColumn);
			position2=new MyPlacement(positionrow,true,2*positionColumn+2);
			position3=new MyPlacement(positionrow,true,2*positionColumn+1);
			placesToAdd=new MyPlacement[] {position1,position2 , position3};
		}
		else if(pieceType==3 && positionMidLine && positionColumn%2==0)
		{	// 1 triangle pointing up starts with top left read clockwise
			whatToPut=new int[] {1,1,1};
			position1=new MyPlacement(positionrow,true,positionColumn);
			position2=new MyPlacement(positionrow,true,positionColumn+1);
			position3=new MyPlacement(positionrow,false,positionColumn/2);
			placesToAdd=new MyPlacement[] {position1,position2 , position3};
		}
		else if(pieceType==4 && !positionMidLine && positionrow<size-1)
		{
			// 1 triangle pointing down starts with top c.w.
			whatToPut=new int[] {1,1,1};
			position1=new MyPlacement(positionrow,false,positionColumn);
			position2=new MyPlacement(positionrow,true,2*positionColumn+2);
			position3=new MyPlacement(positionrow,true,2*positionColumn+1);
			placesToAdd=new MyPlacement[] {position1,position2 , position3};
		}
		else if(pieceType==5 && positionMidLine && positionColumn%2==0 && positionrow<size-1){
			// diamond starts with topLeft edge c.w. and last is middle of piece
			whatToPut=new int[] {1,-1,1,-1,10};
			position1=new MyPlacement(positionrow,true,positionColumn);
			position2=new MyPlacement(positionrow,true,positionColumn+1);
			position3=new MyPlacement(positionrow+1,true,positionColumn+2);
			position4=new MyPlacement(positionrow+1,true,positionColumn+1);
			position5=new MyPlacement(positionrow,false,positionColumn/2);
			placesToAdd=new MyPlacement[] {position1,position2 , position3,position4,position5};
		}
		else if(pieceType==6 && !positionMidLine && positionrow<size-1){
			// rhombus slanted left starts with top edge and last is middle of piece
			whatToPut=new int[] {-1,1,-1,1,10};
			position1=new MyPlacement(positionrow,false,positionColumn);
			position2=new MyPlacement(positionrow+1,true,2*positionColumn+3);
			position3=new MyPlacement(positionrow+1,false,positionColumn+1);
			position4=new MyPlacement(positionrow+1,true,2*positionColumn+1);
			position5=new MyPlacement(positionrow+1,true,2*positionColumn+2);
			placesToAdd=new MyPlacement[] {position1,position2 , position3,position4,position5};
		}
		else if(pieceType==7 && !positionMidLine && positionrow<size-1){
			// rhombus slanted right starts with top edge and last is middle of piece
			whatToPut=new int[] {1,-1,1,-1,10};
			position1=new MyPlacement(positionrow,false,positionColumn);
			position2=new MyPlacement(positionrow+1,true,2*positionColumn+2);
			position3=new MyPlacement(positionrow+1,false,positionColumn);
			position4=new MyPlacement(positionrow+1,true,2*positionColumn);
			position5=new MyPlacement(positionrow+1,true,2*positionColumn+1);
			placesToAdd=new MyPlacement[] {position1,position2 , position3,position4,position5};
		}
		else{
			return false;
		}
		return tryAdding(whatToPut,placesToAdd);
	}
	private boolean tryAdding(int[] whatToPut,MyPlacement[] placesToAdd)
	{
		int size=whatToPut.length;
		int currentValue;
		int currentSite;
		boolean aChange=false;
		if(placesToAdd.length!=size)
			return false;
		for(int i=0;i<size;i++)
		{
			try{
				currentSite=Puzzle.getIndexFromRowColumn(placesToAdd[i]);
				currentValue=edgelabels[currentSite];
			}
			catch(Exception e)
			{
				//System.out.println("Trying to add at nonexistent");
				return false;
			}
			if (currentValue==0 || currentValue==whatToPut[i])
			{
				aChange=aChange || (currentValue==0);
			}
			else return false;
		}
		if(!aChange)
			return false;
		for(int i=0;i<size;i++)
		{
			currentSite=Puzzle.getIndexFromRowColumn(placesToAdd[i]);
			edgelabels[currentSite]=whatToPut[i];
			if(bottom.contains(placesToAdd[i]))
				bottom.remove(placesToAdd[i]);
			else if(i!=4)
				bottom.add(placesToAdd[i]);
		}
		if (this.full()){
			System.out.println("Full up and cleared boundary");
			bottom.clear();
		}
		return true;
	}
	public Puzzle copy()
	{
		return new Puzzle(this);
	}
	
	public boolean full()
	{
		int count=0;
		for(int i=0;i<edgelabels.length;i++)
		{
			if(edgelabels[i]==0)
				count++;
		}
		System.out.println(count);
		return (count==0);
	}
	
	public boolean isBorder(int row,boolean mid,int column)
	{
		return this.bottom.contains(new MyPlacement(row,mid,column));
	}
	
}