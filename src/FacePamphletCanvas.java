/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;


public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		
		setBackground(Color.WHITE);
		
		lName=new GLabel("");
		lName.setFont(PROFILE_NAME_FONT);
		lName.setColor(Color.blue);
		
		lStatus=new GLabel("",LEFT_MARGIN,TOP_MARGIN + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN);
		lStatus.setFont(PROFILE_STATUS_FONT);
		
		lAppStatus=new GLabel("");
		lAppStatus.setFont(MESSAGE_FONT);
		
		
		lFriends=new GLabel("");
		lFriends.setFont(PROFILE_FRIEND_LABEL_FONT);
		
		lFriendList=new GLabel("");
		lFriendList.setFont(PROFILE_STATUS_FONT);
		
		
		
		
		
		add(lFriends);add(lStatus);add(lAppStatus);add(lFriends);add(lName);add(lFriendList);
		
		
	}
	public void clearCanvas(){
		lName.setLabel("");
		lFriends.setLabel("");
		lStatus.setLabel("");
		
		image=new GImage("images/plain.JPG");
		image.setLocation(LEFT_MARGIN,IMAGE_MARGIN+lName.getHeight()+TOP_MARGIN);
		image.setBounds(LEFT_MARGIN, IMAGE_MARGIN+lName.getHeight()+TOP_MARGIN, IMAGE_WIDTH,IMAGE_HEIGHT);
		add(image);
		
		lAppStatus.setLabel("");
		lFriendList.setLabel("");
	}
	public void changeImage(FacePamphletProfile profile){
		
		image=profile.getImage();
		image.setLocation(LEFT_MARGIN,IMAGE_MARGIN+lName.getHeight()+TOP_MARGIN);
		image.setBounds(LEFT_MARGIN, IMAGE_MARGIN+lName.getHeight()+TOP_MARGIN, IMAGE_WIDTH,IMAGE_HEIGHT);
		add(image);
		}
	public void initialiseMe(int x,int y){
		
		
		lName.setLocation(LEFT_MARGIN,TOP_MARGIN);
		lFriends.setLocation((x-lFriends.getWidth())/2,TOP_MARGIN);
		lStatus.setLocation(LEFT_MARGIN,TOP_MARGIN+lName.getHeight()+IMAGE_HEIGHT+IMAGE_MARGIN+STATUS_MARGIN);
		lAppStatus.setLocation((x-lAppStatus.getWidth())/2,y-BOTTOM_MESSAGE_MARGIN-lAppStatus.getHeight());
		lFriendList.setLocation((x-lFriends.getWidth())/2,TOP_MARGIN+lFriends.getHeight());
		}
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously a
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		lAppStatus.setLabel(msg);
		
		}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		
		lName.setLabel(profile.getName());
		
		lFriends.setLabel("FRIENDS:");
		
		if(!((profile.getStatus()).equals("")))
		
		lStatus.setLabel(profile.getName()+" is "+profile.getStatus());
		
		
		Iterator<FacePamphletProfile> itr=profile.getFriends();
		
		String msg="";
		while(itr.hasNext()){
			
			FacePamphletProfile fprofile=itr.next();
			msg+=fprofile.getName()+",";
			lFriendList.setLabel(msg);
			}
		
		changeImage(profile);
	}
	
	
	/**
	 * Private Constatnts
	 */
	
	private GLabel lName,lFriends,lStatus,lAppStatus,lFriendList;
	private GImage image;
	
	
}
