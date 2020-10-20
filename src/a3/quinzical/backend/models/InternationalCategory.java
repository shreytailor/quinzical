package a3.quinzical.backend.models;

import java.util.List;

import a3.quinzical.backend.database.GameDatabase;

public class InternationalCategory extends Category{
	
	public InternationalCategory(){
		super("International");
	}
	
	public InternationalCategory(Category cate) {
		super("International");
		for(int i = 0; i < cate.getClueSize(); i++) {
			this.addClue(cate.getClue(i));
		}
	}
	
	public boolean isLocked() {
		if(GameDatabase.getInstance().getCateSize() <=3 ) {
			return false;
		}else {
			return true;
		}		
	}
}
