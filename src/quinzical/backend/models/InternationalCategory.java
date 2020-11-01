package quinzical.backend.models;
import quinzical.backend.database.GameDatabase;

/**
 * This class is extends the regular Category, and is only used for storing the International clues.
 *
 * @author Shrey Tailor, Jason Wang
 */
public class InternationalCategory extends Category{
	
	/**
	 * This constructor will create an empty InternationalCategory object by using the constructor
	 * of the superclass to have a default name of "International".
	 */
	public InternationalCategory(){
		super("International");
	}
	
	/**
	 * This constructor will create an InternationalCategory object from the Category object passed
	 * into it..
	 */
	public InternationalCategory(Category cate) {
		super("International");

		// Setup all the questions individually.
		for(int i = 0; i < cate.getClueSize(); i++) {
			this.addClue(cate.getClue(i));
		}
	}
	
	/**
	 * This method is used to check if the InternationalCategory is currently locked.
	 * @return boolean true if the International category is current locked, else false.
	 */
	public boolean isLocked() {
		int count = 0;
		for (int i = 0; i < GameDatabase.getInstance().getCateSize(); i++) {
			if (GameDatabase.getInstance().getCategory(i).getClueSize() == 0) {
				count++;
			}
		}

		if(count >= 2) {
			// If the number of categories completed are more than two, then unlocked.
			return false;
		}else {
			// Otherwise it would still be locked for the user.
			return true;
		}		
	}
}
