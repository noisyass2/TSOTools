package ph.asaboi.droidz.tsotools;

import java.util.ArrayList;
import java.util.Arrays;

import ph.asaboi.droidz.classes.Building;
import ph.asaboi.droidz.classes.Defaults;
import ph.asaboi.droidz.classes.Island;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BuildingsFragment extends Fragment {
	TextView btnAddNew;
	ListView lvBuildings;
	LayoutInflater pubInflater;
	TextView btnLoadFile;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//return super.onCreateView(inflater, container, savedInstanceState);
		pubInflater=inflater;
		return inflater.inflate(R.layout.eco_mybuildings, container,false);

	}

	@Override
	public void onStart() {
		
		super.onStart();
		FragmentActivity act = getActivity();
		btnAddNew = (TextView) act.findViewById(R.id.btnAddNew);
		btnAddNew.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				NewBuilding();
			}
		});

		lvBuildings = (ListView) act.findViewById(R.id.lvBuildings);
		lvBuildings.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				showBuildingSetting(pos);
			}
		});
		UpdateBuildings();
		
//		btnLoadFile = (TextView) act.findViewById(R.id.btnLoadFile);
//		btnLoadFile.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				
//				Defaults.newIsland.LoadIsland(getActivity());
//				UpdateBuildings();
//			}
//		});
	}

	protected void showBuildingSetting(int pos) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.putExtra("index", pos);		
		intent.setClass(getActivity(), BuildingSettingsActivity.class);
		startActivityForResult(intent, 2);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		//super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			if(resultCode == Activity.RESULT_OK){
				String result=data.getStringExtra("result");
				Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
				AddBuilding(result);
			
		}else if(requestCode == 2){
			if(resultCode == Activity.RESULT_OK){
				Toast.makeText(getActivity(), "Building Updated", Toast.LENGTH_SHORT).show();
				UpdateBuildings();
			}
			}else {
				UpdateBuildings();
				Toast.makeText(getActivity(), "Building Removed", Toast.LENGTH_SHORT).show();				
			}
		}
	}

	public class LVBuildingAdapter extends ArrayAdapter<String>{

		String[] bTexts;
		Building lvSelBuilding;
		ArrayList<Building> lBuildings;

		public LVBuildingAdapter(Context context, int textViewResourceId,
				String[] objects, ArrayList<Building> buildings) {
			super(context, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
			bTexts = objects;
			lBuildings = buildings;			
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			return getCustomView(position, convertView, parent);

		}

		private View getCustomView(int position, View convertView,
				ViewGroup parent) {
			
			
			lvSelBuilding = lBuildings.get(position);
			
			final View row=pubInflater.inflate(R.layout.eco_mybuildings_row, parent, false);
			
			TextView txtImg = (TextView) row.findViewById(R.id.txtImg);
			TextView txtName = (TextView)row.findViewById(R.id.txtName);
			TextView txtTime = (TextView)row.findViewById(R.id.txtTime);
			TextView btnActive = (TextView) row.findViewById(R.id.btnActive);
			
			TextView txtLvl = (TextView)row.findViewById(R.id.txtLvl);
			TextView txtBuff = (TextView)row.findViewById(R.id.txtBuff);

			
			txtName.setText(lvSelBuilding._name);
			txtTime.setText(Defaults.secs_to_string(lvSelBuilding._bTime));
			txtLvl.setText(Integer.toString(lvSelBuilding._level));										
			txtBuff.setText(Integer.toString(lvSelBuilding._multi));
			
			txtTime.setTextSize(8);
			txtTime.setTextColor(Color.GRAY);
			txtImg.setBackgroundResource(lvSelBuilding._image);

			btnActive.setTag(position);
			btnActive.setBackgroundResource(lvSelBuilding._active ? R.drawable.ic_play : R.drawable.ic_pause);
			row.setBackgroundColor(lvSelBuilding._active ? Color.TRANSPARENT: Color.TRANSPARENT);
			btnActive.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					
					TextView tview = (TextView)view;
					Building sBuilding = (Building)lBuildings.get((Integer) tview.getTag());
					sBuilding._active = !sBuilding._active;
					String msg = sBuilding._name;
					if(sBuilding._active){
						msg += " activated";
						tview.setBackgroundResource(R.drawable.ic_play);
						row.setBackgroundColor(Color.TRANSPARENT);
					}else {
						msg += " deactivated";
						tview.setBackgroundResource(R.drawable.ic_pause);
						row.setBackgroundColor(Color.LTGRAY);
					}
					sBuilding.UpdateResourceOutput();
					//UpdateBuildings();
					if(MainActivity.rFrag != null){
						MainActivity.rFrag.UpdateResources();
					}
					mbox(msg);

				}
			});
			
			txtLvl.setTextSize(10);
			txtLvl.setTag(position);
			txtLvl.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					
					TextView tview = (TextView)view;
					Building sBuilding = (Building)lBuildings.get((Integer) tview.getTag());
					if(sBuilding._level + 1 > 5){
						sBuilding._level = 1;
					}else {
						sBuilding._level = sBuilding._level + 1;
					}
					String msg = sBuilding._name + " changed level to " + Integer.toString(sBuilding._level);
					mbox(msg);
					sBuilding.UpdateResourceOutput();
					//UpdateBuildings();
					if(MainActivity.rFrag != null){
						MainActivity.rFrag.UpdateResources();
					}
					tview.setText(Integer.toString(sBuilding._level));
				}
			});
			
			txtBuff.setTextSize(10);
			txtBuff.setTag(position);
			txtBuff.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					
					TextView tview = (TextView)view;
					Building sBuilding = (Building)lBuildings.get((Integer) tview.getTag());
					if(sBuilding._multi + 1 > 3){
						sBuilding._multi = 1;
					}else {
						sBuilding._multi= sBuilding._multi + 1;
					}
					tview.setText(Integer.toString(sBuilding._multi));
					String msg = sBuilding._name + " changed buff to " + Integer.toString(sBuilding._multi);
					mbox(msg);
					sBuilding.UpdateResourceOutput();
					//UpdateBuildings();
					if(MainActivity.rFrag != null){
						MainActivity.rFrag.UpdateResources();
					}
				}
			});
			
			
			return row;
		}

	}
	
	private void UpdateBuildings() {
		Island newIsland = Defaults.newIsland;

		// TODO Auto-generated method stub
		Log.d("TSO",Integer.toString(newIsland.Buildings.size()));
		String[] bTxts = null;

		lvBuildings = (ListView)getActivity().findViewById(R.id.lvBuildings);
		if(!newIsland.Buildings.isEmpty()){
			ArrayList<String> bs = new ArrayList<String>();

			for(Building b: newIsland.Buildings){
				bs.add(b._name);
			}

			bTxts= (String[]) bs.toArray(new String[bs.size()]);
			lvBuildings.setAdapter(new LVBuildingAdapter(getActivity(), R.layout.eco_mybuildings_row,bTxts, newIsland.Buildings));
		}

		newIsland.SaveIsland(getActivity());
		
		
		if(MainActivity.rFrag != null){
			MainActivity.rFrag.UpdateResources();
		}
	}

	protected void NewBuilding() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(getActivity(),SelectBuildingActivity.class);
		startActivityForResult(intent, 1);

	}
	private void AddBuilding(String bStr){
		Island newIsland = Defaults.newIsland;
		int index = Arrays.asList(Defaults.BuildText).indexOf(bStr);
		String _name = Defaults.BuildText[index];
		int _bTime = Defaults.BuildTimes[index];
		String bRsourcesText = Defaults.BuildResc[index];
		Log.d("TSO",bRsourcesText);
		Building newBuilding = new Building(_name, _bTime, bRsourcesText);
		newIsland.Buildings.add(newBuilding);
		UpdateBuildings();
	}

	private void mbox(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
	}
}
