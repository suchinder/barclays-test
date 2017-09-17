package main.dev;

import java.util.ArrayList;

/**
 * Created by kssuc on 9/14/2017.
 */
public class Row {
    ArrayList<Section> sections = new ArrayList<Section>();
    int rowNumber=0;
    
    public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public ArrayList<Section> getSections() {
        return this.sections;
      }
    
    public void setSections(ArrayList<Section> sections) {
    	  this.sections = sections;
    	}
    
    public void addSection(Section section) {
  	  this.sections.add(section);
  	}
}
