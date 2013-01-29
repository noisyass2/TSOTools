package ph.asaboi.droidz.tsotools;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class NotificationsFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//return super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.noti_settings, container,false);
		
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Button btn = (Button) getActivity().findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),NotificationsActivity.class);
				startActivity(intent);
				
			}
		});
	}
	
	
	public void openNotis(View view){
		
	}
}
