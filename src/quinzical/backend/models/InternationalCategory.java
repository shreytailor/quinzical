package quinzical.backend.models;

import quinzical.backend.database.GameDatabase;

/**
 * This class is a special version of the Class Category, and is only used for containing 
 * international questions
 *
 * @author Shrey Tailor, Jason Wang
 */
public class InternationalCategory extends Category{
	
	/**
	 * This is a constructor that will create an empty InternationalCategory object using the 
	 * constructor of Category, with fixed name "International"
	 */
	public InternationalCategory(){
		super("International");
	}
	
	/**
	 * This is a constructor that will create an InternationalCategory object, converted from 
	 * the Category object passed in to the constructor.
	 */
	public InternationalCategory(Category cate) {
		super("International");
		for(int i = 0; i < cate.getClueSize(); i++) {
			this.addClue(cate.getClue(i));
		}
	}
	
	/**
	 * This method is used to check if the InternationalCategory is locked from the user
	 * @return boolean if the InternationalCategory is locked 
	 */
	public boolean isLocked() {
		int count = 0;
		for (int i = 0; i < GameDatabase.getInstance().getCateSize(); i++) {
			if (GameDatabase.getInstance().getCategory(i).getClueSize() == 0) {
				count++;
			}
		}
		if(count >= 2) {
			return false;
		}else {
			return true;
		}		
	}
}
