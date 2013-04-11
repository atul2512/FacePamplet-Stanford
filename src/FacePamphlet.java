/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;

import javax.swing.*;


public class FacePamphlet extends Program 
					implements FacePamphletConstants {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		setSize(APPLICATION_WIDTH,APPLICATION_HEIGHT);
		createInteractors();
		addActionListeners();
		data=new FacePamphletDatabase();
		canvas=new FacePamphletCanvas();
		
		add(canvas,CENTER);
		
		profile=new FacePamphletProfile();
		imageAddress="images/";	
		image=new GImage("images/default.JPG");
			//can't add canvas in othr Method...Dunno y!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }
    
	/**
	 * This method create the interactors JText,JButton,JTextField
	 */
	public void createInteractors(){
		
		bStatus =new JButton("Change Status");bPicture=new JButton("Change Picture"); bFriend=new JButton("Add Friend");bAdd=new JButton("Add");bDelete=new JButton("Delete");bLookup=new JButton("Lookup");
		tStatus=new JTextField(TEXT_FIELD_SIZE);tPicture=new JTextField(TEXT_FIELD_SIZE);tFriend=new JTextField(TEXT_FIELD_SIZE);tName=new JTextField(TEXT_FIELD_SIZE);
	//	tStatus.setActionCommand(bStatus.getActionCommand());tPicture.setActionCommand(bPicture.getActionCommand());tFriend.setActionCommand(bFriend.getActionCommand());tName.setActionCommand(bAdd.getActionCommand());
		lName=new JLabel("Name ");
		add(tStatus,WEST);
		add(bStatus,WEST);
		add(new JLabel(EMPTY_LABEL_TEXT),WEST);
		add(tPicture,WEST);
		add(bPicture,WEST);
		add(new JLabel(EMPTY_LABEL_TEXT),WEST);
		add(tFriend,WEST);
		add(bFriend,WEST);
		add(lName,NORTH);
		add(tName,NORTH);
		add(bAdd,NORTH);
		add(bDelete,NORTH);
		add(bLookup,NORTH);
		
		
	}
	
	//public void uploadPhoto(File file){
		
	//}
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    @SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
    	if(count==0){
    		
    		canvas.initialiseMe(canvas.getWidth(),canvas.getHeight());
    		count++;
    	}
    	
    	//Change Status	
    	if(e.getActionCommand()==bStatus.getActionCommand()){
    		canvas.clearCanvas();
    		if(data.containsProfile(tName.getText())){
    				profile=data.getProfile(tName.getText());
    				profile.setStatus(tStatus.getText());
    				canvas.displayProfile(profile);
    				canvas.showMessage("Status Updated");
    			}
    			else{
    				canvas.showMessage("Select profile First");
    				
    			}
    		}
    		
    		
    	//Change Picture	
    	else if(e.getActionCommand()==bPicture.getActionCommand()){
    			canvas.clearCanvas();
    			canvas.showMessage("Picture Updated");
    			if(data.containsProfile(tName.getText())){
    				profile=data.getProfile(tName.getText());
    			try {
    				
    				if(((tPicture.getText().equals("")))){
    					try{
    						
    						FileDialog fd = new FileDialog(new JFrame(),"Open", FileDialog.LOAD);
    						
    						fd.show();
    						if(fd.getFile() == null){
    								canvas.showMessage("U didnot select anything");
    							}
    						else{
    								String d = (fd.getDirectory() + fd.getFile());                       //  public boolean accept(File dir, String name) {
    				                    																//	return name.endsWith(".jpg") || name.endsWith(".jpeg");
    				                Toolkit toolkit = Toolkit.getDefaultToolkit();						//http://stackoverflow.com/questions/12558413/how-to-filter-file-type-in-filedialog
    								Image image1 = toolkit.getImage(d);
    								image=new GImage(image1);
    								canvas.showMessage("Picture Updated");
    							}
    						}
    					catch(Exception ex){
    							canvas.showMessage("Dorry Something Went worng..Try Again");
    					}
    				}
    				
    				else{
    					image = new GImage(imageAddress+tPicture.getText());
    					canvas.showMessage("Picture Updated");
    					}
    					
    					profile.setImage(image);
    					canvas.displayProfile(profile);
    					
    				} 
    			catch (Exception ex) {
    				canvas.showMessage("Invalid Image File");
    				canvas.displayProfile(profile);
    			}
    			}
    			
    	}
    		
    		
    	//Add Friend
    	else if(e.getActionCommand()==bFriend.getActionCommand()){
    				canvas.clearCanvas();
					if((data.containsProfile(tName.getText()) && (!(tName.getText()).equals(tFriend.getText()))) && data.containsProfile(tFriend.getText())){
    				profile=data.getProfile(tName.getText());

    				boolean isFriendAdded=profile.addFriend(tFriend.getText());
    				if(isFriendAdded){
    					canvas.displayProfile(profile);
    					canvas.showMessage(tFriend.getText()+"  is added to your FriendList");
    					(data.getProfile(tFriend.getText())).addFriend(profile.getName());
    					
    				}
					}
    				else {
    					
    					canvas.displayProfile(profile);
    					canvas.showMessage(tFriend.getText()+" is not in the network");
    				}
    				tFriend.setText("");
    		
				}
    		
    	
    	//Add Profile
    	else if(e.getActionCommand()==bAdd.getActionCommand()){
    				
    				canvas.clearCanvas();
    				if(!(tName.getText().equals(""))){
    					
    					data.addProfile(new FacePamphletProfile(tName.getText()));
    					profile=data.getProfile(tName.getText());
    					canvas.displayProfile(profile);
    					canvas.showMessage(profile.getName()+" is Added to the network");
    		   			
    		    		}
    				else{
    						canvas.showMessage("No profile to Add");
    					}
    				setTextMessage("");
    		} 
    		
    	
    	//Delete Profile
    	else if(e.getActionCommand()==bDelete.getActionCommand()){
    			canvas.clearCanvas();
    			if(data.containsProfile(tName.getText())){
    				//remove from every profile the deleted profile
    				profile=data.getProfile(tName.getText());
    				Iterator<FacePamphletProfile> itr =profile.getFriends();
    				while(itr.hasNext()){
    						(itr.next()).fremove(profile);
    				}
    				data.deleteProfile(tName.getText());
    				canvas.showMessage(tName.getText()+" Profile is Deleted");
    				
    			}
    			else{
    				canvas.showMessage("No Valid Profile Mentioned.");
    			}
    			setTextMessage("");
    		}
    		
    	
    	//Lookup Profile
    	else if(e.getActionCommand()==bLookup.getActionCommand()){
    			canvas.clearCanvas();
    			if(data.containsProfile(tName.getText())){
    				profile=data.getProfile(tName.getText());
    				canvas.displayProfile(profile);
    				canvas.showMessage("Displaying "+tName.getText()+" Profile");
    			}
    			else{
    				
    				canvas.showMessage("No such Profile exist");
    			}
    			setTextMessage("");
    		}
    	
	}
    
   
     
    public void setTextMessage(String msg){
    	tStatus.setText(msg);
    	tPicture.setText(msg);
    	tFriend.setText(msg);
    	
    }
    
   
    /**
     * Private Constants 
     */
    private JButton bStatus,bPicture,bFriend,bAdd,bDelete,bLookup;
	private JTextField tStatus,tPicture,tFriend,tName;
	private JLabel lName;
	private FacePamphletProfile profile;
	public static FacePamphletDatabase data;
	private FacePamphletCanvas canvas;
	private GImage image;
	private String imageAddress;
	private int count=0;
}
