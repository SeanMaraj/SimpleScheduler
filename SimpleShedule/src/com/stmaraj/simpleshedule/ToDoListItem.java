package com.stmaraj.simpleshedule;

public class ToDoListItem {

	private boolean checked;
	private String text;
	
	public ToDoListItem(boolean checked, String text) {
		super();
		this.checked = checked;
		this.text = text;
	}
	
	public boolean getChecked() {
		return checked;
	}
	public String getText() {
		return text;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public void setChecked(boolean value) {
		this.checked=value;
	}
}
