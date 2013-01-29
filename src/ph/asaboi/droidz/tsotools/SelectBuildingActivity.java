package ph.asaboi.droidz.tsotools;

import ph.asaboi.droidz.classes.Defaults;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class SelectBuildingActivity extends Activity {
	
	GridView lv;
	public String[] txts;
	public int[] imgs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eco_selectbuilding);
		
		lv = (GridView) findViewById(R.id.lvSelectBuilding);
		
		lv.setAdapter(new BuildingAdapter(this,R.id.lvSelectBuilding,Defaults.BuildText, Defaults.BuildImgs));
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				Intent returnIntent = new Intent();
				returnIntent.putExtra("result",Defaults.BuildText[pos]);
				setResult(RESULT_OK,returnIntent);     
				Log.d("TSO", txts[pos]);
				//Toast.makeText(SelectBuildingActivity.this, txts[pos],Toast.LENGTH_SHORT ).show();
				finish();
			}
		});
	}
	
	
	public class BuildingAdapter extends ArrayAdapter<String>{
		
		Context mContext;
		
		public BuildingAdapter(Context context, int resource,
				String[] buildText, int[] buildImgs) {
			super(context, resource, buildText);
			mContext = context;
			txts=buildText;
			imgs = buildImgs;
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
			row.setWidth(61);
			
			row.setText(txts[position]);
			row.setCompoundDrawablesWithIntrinsicBounds(0,imgs[position],0,0);
			row.setGravity(Gravity.CENTER_HORIZONTAL);		
			row.setPadding(10, 10, 10, 10);
			
			return row;
		}
	}

}
