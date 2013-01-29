package ph.asaboi.droidz.tsotools;

import java.util.Arrays;

import ph.asaboi.droidz.classes.Building;
import ph.asaboi.droidz.classes.Defaults;
import ph.asaboi.droidz.classes.Island;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class BuildingSettingsActivity extends Activity {

	private int mCurrentPosition;
	private TextView tvName;
	private TextView tvResc;
	private TextView tvBtime;
	private SeekBar sbLvl;
	private SeekBar sbMins;
	private SeekBar sbBuff;
	private SeekBar sbSecs;
	private TextView tvMins;
	private TextView tvSecs;
	private TextView txtLvl;
	private TextView txtBuff;
	private ImageView imgL;
	protected Building selBuilding;
	protected Island newIsland;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eco_mybuildings_settings);

		if (savedInstanceState != null) {
			mCurrentPosition = savedInstanceState.getInt("index");
		}else{
			mCurrentPosition = 0;
		}
		tvName = (TextView) findViewById(R.id.txtBuildingName);
		tvResc = (TextView) findViewById(R.id.txtResources);
		tvBtime = (TextView) findViewById(R.id.txtBaseTime);
		sbLvl = (SeekBar) findViewById(R.id.sbLvl);
		sbBuff = (SeekBar) findViewById(R.id.sbBuff);
		sbMins = (SeekBar) findViewById(R.id.sbMins);
		sbSecs = (SeekBar) findViewById(R.id.sbSecs);

		tvMins = (TextView) findViewById(R.id.tvMins);
		tvSecs = (TextView) findViewById(R.id.tvSecs);
		txtLvl = (TextView) findViewById(R.id.txtvLvl);
		txtBuff = (TextView) findViewById(R.id.txtvBuff);
		imgL = (ImageView) findViewById(R.id.imgb_row);

		Bundle args = getIntent().getExtras();
		if(args != null){
			mCurrentPosition = args.getInt("index");
		}
		UpdateSetting();

		sbLvl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

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
				Island newIsland = Defaults.newIsland;
				if(newIsland.Buildings.size() > 0){
					selBuilding = newIsland.Buildings.get(mCurrentPosition);
					if(selBuilding != null){
						selBuilding._level = pos;
						txtLvl.setText("Level " + Integer.toString(pos));
						Log.d("TSO",Integer.toString(pos));
					}
				}
			}
		});

		sbBuff.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

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
				Island newIsland = Defaults.newIsland;
				if(newIsland.Buildings.size() > 0){
					selBuilding = newIsland.Buildings.get(mCurrentPosition);
					if(selBuilding != null){
						selBuilding._multi = pos;
						txtBuff.setText("Buff " + Integer.toString(pos));

						Log.d("TSO",Integer.toString(pos));
					}
				}
			}
		});

		sbMins.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

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
				Island newIsland = Defaults.newIsland;
				if(newIsland.Buildings.size() > 0){
					selBuilding = newIsland.Buildings.get(mCurrentPosition);
					if(selBuilding != null){

						int s = sbSecs.getProgress();
						int m = pos;
						int ts = s + (m*60);
						selBuilding._bTime = ts;
						tvMins.setText(Integer.toString(m) + " mins");
						Log.d("TSO",Integer.toString(ts));
					}
				}
			}
		});

		sbSecs.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

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
				Island newIsland = Defaults.newIsland;
				if(newIsland.Buildings.size() > 0){
					selBuilding = newIsland.Buildings.get(mCurrentPosition);
					if(selBuilding != null){
						int s = pos;
						int m = sbMins.getProgress();;
						int ts = s + (m*60);
						selBuilding._bTime = ts;
						tvSecs.setText(Integer.toString(s) + " secs");
						Log.d("TSO",Integer.toString(ts));

					}
				}
			}



		});

		TextView btnSaves = (TextView)findViewById(R.id.btnSave);

		btnSaves.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Island newIsland = Defaults.newIsland;
				if(newIsland.Buildings.size() > 0){
					selBuilding = newIsland.Buildings.get(mCurrentPosition);
					if(selBuilding != null){
						selBuilding.UpdateResourceOutput();
					}
				}
				finish();

			}
		});

		TextView btnDelete = (TextView)findViewById(R.id.btnDelete);
		btnDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Island newIsland = Defaults.newIsland;
				if(newIsland.Buildings.size() > 0){
					selBuilding = newIsland.Buildings.get(mCurrentPosition);
					if(selBuilding != null){
						Defaults.newIsland.Buildings.remove(selBuilding);
						setResult(RESULT_CANCELED);
						finish();
					}
				}
			}
		});
	}
	public void UpdateSetting() {
		// TODO Auto-generated method stub
		Island newIsland = Defaults.newIsland;
		if(newIsland.Buildings.size() > 0){
			Building selBuilding = newIsland.Buildings.get(mCurrentPosition);

			if(selBuilding!= null){
				tvName.setText(selBuilding._name);
				tvResc.setText(selBuilding._resources);
				tvBtime.setText(Defaults.secs_to_string(selBuilding._bTime));
				sbLvl.setProgress(selBuilding._level);
				sbBuff.setProgress(selBuilding._multi);
				txtLvl.setText("Level " + Integer.toString(selBuilding._level));
				txtBuff.setText("Buff " + Integer.toString(selBuilding._multi));
				int bdIndx = Arrays.asList(Defaults.BuildText).indexOf(selBuilding._name);			
				imgL.setImageResource(Defaults.BuildImgs[bdIndx]);

				int m = selBuilding._bTime / 60;
				int s = selBuilding._bTime % 60;
				sbMins.setProgress(m);
				sbSecs.setProgress(s);
				tvSecs.setText(Integer.toString(s) + " secs");
				tvMins.setText(Integer.toString(m) + " mins");
			}else{
				Log.d("TSO","null");
			}
		}
	}
}
