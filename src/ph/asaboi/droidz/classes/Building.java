package ph.asaboi.droidz.classes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Building {

	public String _name;

	public int _pTime;	
	public int _bTime;
	public int _level;
	public int _multi;
	public int _mineAmount;
	public int _image;
	public boolean _active;
	public String _resources;

	public ArrayList<Resource> BaseResource = new ArrayList<Resource>();
	public ArrayList<Resource> ResourceOutput = new ArrayList<Resource>();


	public Building()
	{
		_name = ""; 
		_bTime = 0;
		_level = 1;
		_multi = 1;
		_active = true;
		BaseResource = new ArrayList<Resource>();
		ResourceOutput = new ArrayList<Resource>();
	}
	public Building(String _name, int _bTime) {
		super();
		this._name = _name;
		this._bTime = _bTime;	
		this._active = true;
	}
	public Building(String _name, int _bTime, ArrayList<Resource> bResources) {
		super();
		this._name = _name;
		this._bTime = _bTime;	
		this._active = true;
		this.BaseResource = bResources;
	}

	public Building(String _name, int _bTime, String bRsourcesText){
		this._name = _name;
		this._bTime = _bTime;
		this.BaseResource = new ArrayList<Resource>();
		this._level =1;
		this._multi = 1;
		this._pTime = _bTime;
		this._active = true;
		
		int bIndex = Arrays.asList(Defaults.BuildText).indexOf(_name);
		this._image =  Defaults.BuildImgs[bIndex];
		
		//		Log.d("building",bRsourcesText);
		_resources = bRsourcesText;
		List<String> rList = Arrays.asList(Defaults.ResourceText);
		
		//		Log.d("TSO","R:" + Integer.toString(Defaults.ResourceText.length));
		//		Log.d("TSO","T:" + Integer.toString(rList.size()));
		//		
		for (String rStr : bRsourcesText.split(",")) {
			String rName = rStr.split(":")[0];
			float rAmt = Float.parseFloat(rStr.split(":")[1]);
			//int rImg = Integer.parseInt(rStr.split(":")[2]);
			int iIndx = rList.indexOf(rName);
			//Log.d("TSO","U:" + rName);
			if(iIndx >= 0){
				int rImg = Defaults.ResourceImgs[iIndx];
				//_resources +=  rName + " : " + Float.toString(rAmt) + " ";

				Resource newResource = new Resource(rName, rAmt, rImg);
				BaseResource.add(newResource);
			}else {
				//Log.d("TSO","F:" + rName );
			}
		}

		if(this._name.contains("Mine")){
			this._mineAmount = getMineAmount(this._name);
		}
	}

	private int getMineAmount(String _name2) {
		int mId = Arrays.asList(Defaults.MineNames).indexOf(_name2);
		if(mId >=0){
			return Defaults.MineAmounts[mId];
		}
		return 0;
	}


	

	public void UpdateResourceOutput(){
		ResourceOutput = new ArrayList<Resource>();
		for (Resource r : BaseResource) {
			float baseMultiplier = r._amount / _bTime;

			float newAmount = baseMultiplier * _pTime * _level;
			if(newAmount > 0 ) newAmount *= _multi;
			Resource newResource= new Resource(r._name, newAmount, r._image);
			ResourceOutput.add(newResource);
		}		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		//return super.toString();
		return this._name + "|"
		+ Integer.toString(this._bTime) + "|"
		+ Integer.toString(this._level) + "|"
		+ Integer.toString(this._multi) + "|"
		+ this._resources;
	}

	public static Building newInstance(String bString){

		String[] splt = bString.split("\\|");
		//		Log.d("TSO", bString);
		//		Log.d("TSO", splt[0]);
		String bName = splt[0];
		int bTime = Integer.parseInt(splt[1]);
		int lvl = Integer.parseInt(splt[2]);
		int buf = Integer.parseInt(splt[3]);		
		String rsources = splt[4];

		Building b = new Building(bName,bTime,rsources);
		b._bTime = bTime;
		b._level = lvl;
		b._multi = buf;
		
		int bIndex = Arrays.asList(Defaults.BuildText).indexOf(bName);
		b._image =  Defaults.BuildImgs[bIndex];
		
		b.UpdateResourceOutput();
		return b;		
	}

	public String getStat(){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH/mm");
		
		if(this._name.contains("Mine")){
			Calendar now = Calendar.getInstance();

			String stat = "";
			//if(this._name.contains("Iron")){								
			int timeS = _bTime * (_mineAmount/_level);
			now.add(Calendar.SECOND, timeS);
			//				stat = Integer.toString(_bTime)+ " " + Integer.toString(timeS);				
			stat = "An " + _name + " will expire @ approx " + Defaults.secs_to_string(timeS) + " from now." + sdf.format(now.getTime());				
			//}

			return stat;
		}else
		{
			return "";
		}
	}

	private Resource getResource(String rName){
		for (Resource r : this.ResourceOutput) {
			if(r._name == rName){
				return r;
			}
		}
		return null;
	}
}
