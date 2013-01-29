package ph.asaboi.droidz.tsotools;


import ph.asaboi.droidz.classes.Building;
import ph.asaboi.droidz.classes.Defaults;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StatisticsFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//return super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.eco_stats, container,false);
	}
	

	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		LinearLayout llStats = (LinearLayout) getActivity().findViewById(R.id.llStats);
		for (Building b : Defaults.newIsland.Buildings) {
			String stat =b.getStat();
			if(stat != ""){
				TextView tv = new TextView(getActivity());
				tv.setText(stat);
				tv.setTextSize(10);
				tv.setCompoundDrawablesWithIntrinsicBounds(b._image, 0, 0, 0);
				llStats.addView(tv);
				
			}
		}
	}
}

