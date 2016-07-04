/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		
		// Initialize graphical class constructor,
		//it creates a new FacePamphletCanvas object and adds that object to the display,
		canvas = new FacePamphletCanvas();
		add(canvas);
		
		
		//set interactors
		name = new JLabel("Name");
		empty = new JLabel(EMPTY_LABEL_TEXT);
		changeStatusButton = new JButton("Change Status");
		changePictureButton = new JButton ("Change Picture");
		addFriendButton = new JButton ("Add Friend");
		add = new JButton ("Add");
		delete = new JButton("Delete");
		lookUp = new JButton ("Lookup");
		nameField = new JTextField (TEXT_FIELD_SIZE);
		
		changeStatusField = new JTextField (TEXT_FIELD_SIZE);
		changeStatusField.setActionCommand("status");
		changePictureField = new JTextField (TEXT_FIELD_SIZE);
		changePictureField.setActionCommand("picture");
		addFriendField = new JTextField (TEXT_FIELD_SIZE);
		addFriendField.setActionCommand("friend");
		
		//add Interactors to Canvas
		add(name, NORTH);
		add(nameField, NORTH);
		add(add, NORTH);
		add(delete,NORTH);
		add(lookUp, NORTH);
		add(changeStatusField,WEST);
		add(changeStatusButton , WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(changePictureField,WEST);
		add(changePictureButton , WEST);
		add(empty, WEST);
		add(addFriendField,WEST);
		add(addFriendButton  , WEST);
		
		//call actionPerformed method
		addActionListeners();
		nameField.addActionListener(this); 
		changeStatusField.addActionListener(this);
		changePictureField.addActionListener(this);
		addFriendField.addActionListener(this);
		

		
    }
    
  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
    	String cmd = e.getActionCommand();
    	//add profile
    	if (cmd.equals("Add")){
    		if (!db.containsProfile(nameField.getText())) {
    			//If profile doesn't exist
    			db.addProfile((new FacePamphletProfile(nameField.getText())));
    			currentProfile = db.getProfile(nameField.getText());
				canvas.displayProfile(currentProfile);
				canvas.showMessage("New profile created");
    		} else {
    			//If profile exists
    			currentProfile = db.getProfile(nameField.getText());
				canvas.displayProfile(currentProfile);
				canvas.showMessage("A profile with the name " + currentProfile.getName() + " already exists");
    		}
    		
    	}
    			
    	
    	if (cmd.equals("Delete")){
    		//delete profile
    		if (db.containsProfile(nameField.getText())) {
    			//If profile exists
    			db.deleteProfile(nameField.getText());
    			currentProfile = null;
        		canvas.displayProfile(currentProfile);
        		canvas.showMessage("Profile of " + nameField.getText() + " deleted");
    		} else {
    			//If profile doesn't exist
    			currentProfile = null;
    			canvas.displayProfile(currentProfile);
        		canvas.showMessage("A Profile with the name " + nameField.getText() + " doesn't exists");
    		}

    			
	
    	}
    	
    	if (cmd.equals("Lookup")){
    		//looks up the current name in the database to see if it exists
    		if (db.containsProfile(nameField.getText())) {
    			//If profile exists
    			currentProfile = db.getProfile(nameField.getText());
    			canvas.displayProfile(currentProfile);
    			canvas.showMessage("Displaying " + currentProfile.getName());
    		} else {
    			//If profile doesn't exist
    			currentProfile = null;
    			canvas.displayProfile(currentProfile);
        		canvas.showMessage("A Profile with the name " + nameField.getText() + " doesn't exists");
    		}
    	

    	}
    	
    	if (cmd.equals("Change Status")|| e.getSource() == changeStatusField){
    		//looks up in the profile database to see if the profile with the name from namefield exists
    		if (db.containsProfile(nameField.getText())) {
    			//If profile exists
    			currentProfile = db.getProfile(nameField.getText());
    			currentProfile.setStatus(changeStatusField.getText());
				canvas.displayProfile(currentProfile);
				canvas.showMessage("Status updated to " + changeStatusField.getText());
    		} else {
    			//If profile doesn't exist
    			    	currentProfile = null;
				canvas.displayProfile(currentProfile);
				canvas.showMessage("Please select a profile to change status");
    		}
    		

    	}
    	
    	if (cmd.equals("Change Picture")|| e.getSource() == changePictureField){
    		//looks up in the profile database to see if the profile with the name from namefield exists
    		if (db.containsProfile(nameField.getText())) {
    			//If a profile exists
    			currentProfile = db.getProfile(nameField.getText());
    			GImage image = null;
    			try {
    			image = new GImage(changePictureField.getText());
    			} catch (ErrorException ex) {	
					canvas.displayProfile(currentProfile);
					canvas.showMessage("Unable to open file: " + changePictureField.getText());
    			}
    			if (image != null) {
    				currentProfile.setImage(image);
					canvas.displayProfile(currentProfile);
					canvas.showMessage("Picture updated");
    			}
    		} else {
    			//If profile doesn't exist
    			    	currentProfile = null;
				canvas.displayProfile(currentProfile);
				canvas.showMessage("Please select a profile to update picture");
    		}
    		

    		
    	}
    		
    		
    	if (cmd.equals("Add Friend")|| e.getSource() == addFriendField){
    		//checks in the profile database to see if the profile with the name from namefield exists
    		if (db.containsProfile(nameField.getText())) {
    			//If a profile exists
    			currentProfile = db.getProfile(nameField.getText());
    			//If a profile with the name typed in addFriendFiled exists
    			if (db.containsProfile(addFriendField.getText())) {
    				if (currentProfile.addFriend(addFriendField.getText())) {
    					FacePamphletProfile friendProfile = db.getProfile(addFriendField.getText());
    					friendProfile.addFriend(nameField.getText());
    					canvas.displayProfile(currentProfile);
    					canvas.showMessage(addFriendField.getText() + " added as a friend " );
    				} else {
    					canvas.displayProfile(currentProfile);
    					canvas.showMessage("Such a friend already exists " );
    				}
    			} else {
    				canvas.displayProfile(currentProfile);
    				canvas.showMessage("Profile with a name " + addFriendField.getText() + " does not exist" );
    			}
    				
    		} else {
    			//If profile doesn't exist
    			currentProfile = null;
    			canvas.showMessage("Please select a profile to add a friend to " );
    		}
		
    		
    	}
	}
    
    //private inst var
    private JTextField nameField;
    private JTextField changeStatusField;
    private JTextField changePictureField;
    private JTextField addFriendField;
    private JButton changeStatusButton;
    private JButton changePictureButton;
    private JButton addFriendButton;
    private JButton add;
    private JButton delete;
    private JButton lookUp;
    private JLabel name;
    private JLabel empty;
    
    private FacePamphletDatabase db = new FacePamphletDatabase();
    private FacePamphletProfile currentProfile; 
    private FacePamphletCanvas canvas;
    
    

}
