package ph.asaboi.droidz.tsotools;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ph.asaboi.droidz.classes.Building;
import ph.asaboi.droidz.classes.Defaults;
import ph.asaboi.droidz.classes.Island;
import ph.asaboi.droidz.classes.Resource;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class ResourcesFragment extends Fragment {
	SeekBar sbMinute;
	GridView gvResources;
	Island newIsland;
	TextView tvVal;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//return super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.eco_myresources, container, false);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		tvVal = (TextView) getActivity().findViewById(R.id.txtMinuteValue);
		sbMinute = (SeekBar) getActivity().findViewById(R.id.sbMinute);
		int maxDays = 1, defHrs = 1;
		int maxMins = maxDays * 24 * 60;		
		int defMins = defHrs * 60;
		sbMinute.setMax(maxMins);
		sbMinute.setProgress(defMins);
		tvVal.setText(Defaults.mins_to_string(defMins));
		newIsland = Defaults.newIsland;
		newIsland.UpdateProductionTime(defMins*60);

		sbMinute.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar arg0, int pos, boolean arg2) {
				// TODO Auto-generated method stub
				newIsland = Defaults.newIsland;

				tvVal.setText(Defaults.mins_to_string(pos));
				newIsland.UpdateProductionTime(pos*60);
				UpdateResources();
			}
		});

		gvResources = (GridView) getActivity().findViewById(R.id.gvResources);
		UpdateResources();

		gvResources.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), newIsland.Resources.get(pos)._name, Toast.LENGTH_SHORT).show();				
			}
		});
	}

	protected void UpdateResources() {
		// TODO Auto-generated method stub

		newIsland = Defaults.newIsland;

		newIsland.Resources = new ArrayList<Resource>();
//		Log.d("TSO", Integer.toString(newIsland.Resources.size()));
		ArrayList<String> sRsrcrs = new ArrayList<String>();

		for (Building b : newIsland.Buildings) {
			b.UpdateResourceOutput();
			if(b._active){
				for (Resource r : b.ResourceOutput) {
					boolean isFound = false;
//					Log.d("TSO",r._name);
					for (Resource rr : newIsland.Resources) {
						if(rr._name.equals(r._name)){
							rr._amount += r._amount;						
							isFound = true;
							break;
						}
					}
					if(!isFound){
						Resource newR = new Resource(r._name, r._amount, r._image);
						newIsland.Resources.add(newR);
						sRsrcrs.add(r._name);
					}
				}
			}

		}
		String[] rsrcs = sRsrcrs.toArray(new String[sRsrcrs.size()]);

		if(rsrcs != null && getActivity() != null){
//			Log.d("TSO","may resource");
			gvResources.setAdapter(new ResourceAdapter(getActivity(),R.id.gvResources,rsrcs, newIsland.Resources));
		}
		//		LinearLayout llR = (LinearLayout)getActivity().findViewById(R.id.llResources);
		//		llR.removeAllViews();
		//		for (Resource r : newIsland.Resources) {
		//			ImageView rImg = new ImageView(getActivity());
		//			rImg.setImageResource(r._image);	
		//			llR.addView(rImg);
		//
		//			TextView tVamt = new TextView(getActivity());
		//			tVamt.setText(formatter.format(r._amount));
		//			if(r._amount<0){
		//				tVamt.setTextColor(Color.rgb(155, 0, 0));
		//
		//			}else{
		//				tVamt.setTextColor(Color.rgb(0, 155, 0));
		//			}
		//			llR.addView(tVamt);
		//		}
	}

	public class ResourceAdapter extends ArrayAdapter<String>{

		Context mContext;
		private ArrayList<Resource> rSources;
		DecimalFormat formatter = new DecimalFormat("'+'0.##;'-'0.##");

		public ResourceAdapter(Context context, int resource,
				String[] rTexts, ArrayList<Resource> resources) {
			super(context, resource, rTexts);
//			Log.d("TSO","may resource2");
			mContext = context;
			rSources = resources;
//			Log.d("TSO","may resource3");
			// TODO Auto-generated constructor stub
		}



		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return getCustomView(position, convertView, parent);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return getCustomView(position, convertView, parent);

		}

		private View getCustomView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub


			//			View row=getLayoutInflater().inflate(R.layout.eco_selectbuilding_row, parent, false);
			//
			//			TextView label=(TextView)row.findViewById(R.id.txtName);
			//			label.setText(txts[position]);
			//			label.setCompoundDrawablesWithIntrinsicBounds(0, imgs[position], 0, 0);


			TextView row = new TextView(mContext);
			//row.setWidth(61);

			Resource resource = rSources.get(position);
			if(resource!= null){
				row.setCompoundDrawablesWithIntrinsicBounds(resource._image,0,0,0);

				row.setText(formatter.format(resource._amount));
				row.setTextColor(resource._amount >=0 ? Color.parseColor("#669900") : Color.parseColor("#FF4444"));
				
				row.setGravity(Gravity.CENTER_HORIZONTAL);		
				row.setPadding(10, 10, 10, 10);
			}
			return row;
		}
	}

}
