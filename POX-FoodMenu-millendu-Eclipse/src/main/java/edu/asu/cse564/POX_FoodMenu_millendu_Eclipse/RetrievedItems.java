package edu.asu.cse564.POX_FoodMenu_millendu_Eclipse;

import java.util.List;

public class RetrievedItems {

	private List<Item> Item;
	private List<InvalidItem> InvalidItem;
	
	//setter
	public void setItem(List<Item> item) {
		this.Item = item;
	}
	
	//getter
	public List<Item> getItem() {
		return Item;
	}
	
	//setter
	public void setInvalidItem(List<InvalidItem> invalidItem) {
		this.InvalidItem = invalidItem;
	}
	
	//getter
	public List<InvalidItem> getInvalidItem() {
		return InvalidItem;
	}
	
}
