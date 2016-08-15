public class MyPlacement
	{
		int row;
		boolean midLine;
		int column;
		MyPlacement(int row,boolean midLine,int column)
		{
			this.row=row;
			this.midLine=midLine;
			this.column=column;
		}
		MyPlacement(MyPlacement p)
		{
			this.row=p.row;
			this.midLine=p.midLine;
			this.column=p.column;
		}
		public String toString()
		{
			return "Pos: "+Puzzle.getIndexFromRowColumn(this);
		}
		public boolean equals(Object objother)
		{
			if (objother instanceof MyPlacement){
				MyPlacement other=(MyPlacement) objother;
				return (row==other.row && midLine==other.midLine && column==other.column);
			}
			else return false;
		}
		
	}