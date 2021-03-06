package com.frc2410.scoutingapplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import android.os.Environment;

public class FileStorageHelper 
{
    private boolean mExternalStorageAvailable;
    private boolean mExternalStorageWriteable;
    MatchScoutData matchData;
    PitScoutData pitData;
    
	public FileStorageHelper()
	{
		
	}
	
	public FileStorageHelper(MatchScoutData md)
	{
		matchData = md;		
	}
	
	public FileStorageHelper(PitScoutData md)
	{
		pitData = md;		
	}
	
	 private void checkExternalMedia()
	 {
	    String state = Environment.getExternalStorageState();

	    if (Environment.MEDIA_MOUNTED.equals(state)) 
	    {
	        // Can read and write the media
	        mExternalStorageAvailable = mExternalStorageWriteable = true;
	    } 
	    else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) 
	    {
	        // Can only read the media
	        mExternalStorageAvailable = true;
	        mExternalStorageWriteable = false;
	    } 
	    else 
	    {
	        // Can't read or write
	        mExternalStorageAvailable = mExternalStorageWriteable = false;
	    }   
	}
	 
	 public boolean canWeWrite()
	 {
		 //Check for Write ability
		 boolean canWrite = false;
		 checkExternalMedia();
		 if(mExternalStorageAvailable && mExternalStorageWriteable)
		 {
			 canWrite = true;
		 }
		 return canWrite;
	 }
	 
	 public boolean canWeRead()
	 {
		//Check for Read ability
		 boolean canRead = false;
		 checkExternalMedia();
		 if(mExternalStorageAvailable)
		 {
			 canRead = true;
		 }
		 return canRead;
	 }
	 
	 public boolean deleteMatchFile(String fN)
	 {
		 //Delete Match File Specified by Parameter
		 boolean errorStatus = false;
		 File rootPath = new File(Environment.getExternalStorageDirectory(),"scoutingData");
		 File matchFile = new File(rootPath,fN);
		 errorStatus = matchFile.delete();
		 return errorStatus;
	 }
	 
	 public boolean deletePitFile(String fN)
	 {
		 //Delete Pit File Specified by Parameter
		 boolean errorStatus = false;
		 File rootPath = new File(Environment.getExternalStorageDirectory(),"scoutingData");
		 File pitFile = new File(rootPath,fN);
		 errorStatus = pitFile.delete();
		 return errorStatus;
	 }
	 
	 public boolean writeMatchFile(String matchType)
	 {
		 boolean noErrors = false;
		 //Create File Name
		 StringBuilder sb = new StringBuilder();
		 sb.append(matchType);
		 sb.append("_");
		 sb.append(matchData.getMatchNumber());
		 sb.append("_");
		 sb.append(matchData.getTeamNumber());
		 sb.append("_");
		 sb.append(matchData.getDate());
		 String fileName = sb.toString() + ".txt";
		 
		 //Save File
		 File rootPath = new File(Environment.getExternalStorageDirectory(),"scoutingData");
		 if(!rootPath.exists())
		 {
			 rootPath.mkdirs();
		 }
		 File newScoutingFile = new File(rootPath,fileName);
		 try
		 {
			 FileWriter outFile = new FileWriter(newScoutingFile);
			 BufferedWriter outStream = new BufferedWriter(outFile);
			 
			 //Write Initial Text Data
			 outStream.write(String.valueOf(matchData.getMatchNumber()));
			 outStream.write("-");
			 outStream.write(String.valueOf(matchData.getTeamNumber()));
			 outStream.write("-");
			 outStream.write(matchData.getAllianceColor());
			 outStream.write("-");
			 outStream.write(String.valueOf(matchData.getPointsScored()));
			 outStream.write("-");
			 outStream.write(String.valueOf(matchData.getAllianceScore()));
			 outStream.write("-");
			 outStream.write(String.valueOf(matchData.getTeamPenalties()));
			 outStream.write("-");
			 outStream.write(String.valueOf(matchData.getAlliancePenalties()));
			 outStream.write("-");
			 
			 //Write Shooting Data
			 boolean[] shootingDataB = matchData.getShootingBooleans();
			 for(int k = 0;k<=4;k++)
			 {
				 outStream.write(booleanToString(shootingDataB[k]));
				 outStream.write("-");
			 }
			 int[] scoringDataI = matchData.getShootingInts();
			 for(int k = 0;k<=9;k++)
			 {
				 outStream.write(String.valueOf(scoringDataI[k]));
				 outStream.write("-");
			 }
			 
			 //Write Climbing Data
			 boolean[] climbingDataB = matchData.getClimbingBooleans();
			 for(int k = 0;k<=3;k++)
			 {
				 outStream.write(booleanToString(climbingDataB[k]));
				 outStream.write("-");
			 }
			 int[] climbingDataI = matchData.getClimbingInts();
			 for(int k = 0;k<=3;k++)
			 {
				 outStream.write(String.valueOf(climbingDataI[k]));
				 outStream.write("-");
			 }
			 
			 //Write Autonomous Data
			 boolean[] autonomousDataB = matchData.getAutonomousBooleans();
			 for(int k = 0;k<=4;k++)
			 {
				 outStream.write(booleanToString(autonomousDataB[k]));
				 outStream.write("-");
			 }
			 int[] autonomousDataI = matchData.getAutonomousInts();
			 for(int k = 0;k<=9;k++)
			 {
				 outStream.write(String.valueOf(autonomousDataI[k]));
				 outStream.write("-");
			 }
			 
			 //Write Special Features Data
			 boolean[] specialFeaturesB = matchData.getSpecialFeaturesBooleans();
			 for(int k = 0;k<=2;k++)
			 {
				 outStream.write(booleanToString(specialFeaturesB[k]));
				 outStream.write("-");
			 }
			 int[] specialFeaturesI = matchData.getSpecialFeaturesInts();
			 for(int k = 0;k<=3;k++)
			 {
				 outStream.write(String.valueOf(specialFeaturesI[k]));
				 outStream.write("-");
			 }
			 
			 //Write End Text Data
			 outStream.write(matchData.getMovementDescription());
			 outStream.write("-");
			 outStream.write(matchData.getComments());
			 outStream.write("@");
			 outStream.close();
			 noErrors = true;
		 }
		 catch(IOException e)
		 {
			 e.printStackTrace();
		 }
		 return noErrors;
	 }
	 
	 public boolean writePitFile(String matchType)
	 {
		 boolean noErrors = false;
		 //Create File Name
		 StringBuilder sb = new StringBuilder();
		 sb.append(matchType);
		 sb.append("_");
		 sb.append(pitData.getTeamNumber());
		 sb.append("_");
		 sb.append(pitData.getDate());
		 String fileName = sb.toString() + ".txt";
		 
		 //Save File
		 File rootPath = new File(Environment.getExternalStorageDirectory(),"scoutingData");
		 if(!rootPath.exists())
		 {
			 rootPath.mkdirs();
		 }
		 File newScoutingFile = new File(rootPath,fileName);
		 try
		 {
			 FileWriter outFile = new FileWriter(newScoutingFile);
			 BufferedWriter outStream = new BufferedWriter(outFile);
			 
			 //Write Initial Text Data
			 outStream.write(String.valueOf(pitData.getTeamNumber()));
			 outStream.write("-");
			 outStream.write(String.valueOf(pitData.getTeamName()));
			 outStream.write("-");
			 outStream.write(String.valueOf(pitData.getStrategy()));
			 outStream.write("-");
			 
			 //Write Shooter Data
			 outStream.write(String.valueOf(pitData.getShooterStatus()));
			 outStream.write("-");
			 outStream.write(String.valueOf(pitData.getShooterDescription()));
			 outStream.write("-");
			 outStream.write(String.valueOf(pitData.getShooterFinalSpeed()));
			 outStream.write("-");
			 outStream.write(String.valueOf(pitData.getShooterAngle()));
			 outStream.write("-");
			 outStream.write(String.valueOf(pitData.getShooterAdjustable()));
			 outStream.write("-");
			 outStream.write(String.valueOf(pitData.getShooterPickUp()));
			 outStream.write("-");
			 outStream.write(String.valueOf(pitData.getShooterSystemSpeed()));
			 outStream.write("-");
			 
			 //Write Drivetrain Data
			 outStream.write(String.valueOf(pitData.getDrivetrainDescription()));
			 outStream.write("-");
			 outStream.write(String.valueOf(pitData.getDrivetrainWheels()));
			 outStream.write("-");
			 outStream.write(String.valueOf(pitData.getDrivetrainSpeed()));
			 outStream.write("-");
			 outStream.write(String.valueOf(pitData.getDrivetrainSpecial()));
			 outStream.write("-");
			 
			 //Write Climbing Data
			 outStream.write(String.valueOf(pitData.getClimbingStatus()));
			 outStream.write("-");
			 outStream.write(String.valueOf(pitData.getClimbingSpeed()));
			 outStream.write("-");
			 outStream.write(String.valueOf(pitData.getClimbingLevel()));
			 outStream.write("-");
			 outStream.write(String.valueOf(pitData.getSideClimb()));
			 outStream.write("-");
			 outStream.write(String.valueOf(pitData.getCornerClimb()));
			 outStream.write("-");
			 
			 //Write Misc. Data
			 outStream.write(String.valueOf(pitData.getMaintenanceTime()));
			 outStream.write("-");
			 outStream.write(String.valueOf(pitData.getAbleToDefend()));
			 outStream.write("-");
			 outStream.write(String.valueOf(pitData.getColoredDiscs()));
			 outStream.write("-");
			 outStream.write(String.valueOf(pitData.getVisionTargeting()));
			 outStream.write("-");
			 outStream.write(String.valueOf(pitData.getEstimatedScore()));
			 outStream.write("-");
			 outStream.write(String.valueOf(pitData.getTripTime()));
			 outStream.write("-");
			 
			 //Write Autonomous Data
			 outStream.write(String.valueOf(pitData.getAutonomousStatus()));
			 outStream.write("-");
			 outStream.write(String.valueOf(pitData.getAutonomousPlacement()));
			 outStream.write("-");
			 outStream.write(String.valueOf(pitData.getAutonomousNumPreloaded()));
			 outStream.write("-");
			 outStream.write(String.valueOf(pitData.getAutonomousLevelAimed()));
			 outStream.write("-");
			 outStream.write(String.valueOf(pitData.getAutonomousFloorPickup()));
			 
			 //Write File Closing Statements
			 outStream.write("@");
			 outStream.close();
			 noErrors = true;
		 }
		 catch(IOException e)
		 {
			 e.printStackTrace();
		 }
		 return noErrors;
	 }
	 
	 public String booleanToString(boolean a)
	 {
		 //Convert boolean to String for Saving
		 String converted = "";
		 if(a == false)
		 {
			 converted = "false";
		 }
		 else
		 {
			 converted = "true";
		 }
		 return converted;
	 }
	 
	 public File[] getFileList()
	 {
		 //Variable to Store File List
		 File[] fileList = null;
		 
		 //Get path to Folder of Files
		 File rootPath = new File(Environment.getExternalStorageDirectory(),"scoutingData");
		 if(!rootPath.exists())
		 {
			 //Create Directory
			 rootPath.mkdirs();
			 //Returns null if this is true 
		 }
		 else
		 {
			 //Get File List
			 fileList = rootPath.listFiles();
		 }
		 
		 return fileList;
	 }
}
