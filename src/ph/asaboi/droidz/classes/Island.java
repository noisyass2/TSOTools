package ph.asaboi.droidz.classes;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;

public class Island {
	String FILENAME = "island";

	public ArrayList<Resource> Resources = new ArrayList<Resource>();
	public ArrayList<Building> Buildings = new ArrayList<Building>();

	public Island(){		
		Buildings = new ArrayList<Building>();		
		//
	}

	public void UpdateResources() {
		// TODO Auto-generated method stub
		for (Building b : Buildings) {
			for (Resource rb : b.ResourceOutput) {
				Resource exist = null;
				for(Resource r : Resources){
					if(r._name==rb._name){
						exist = r;
						break;
					}
				}
				if(exist == null){
					Resource newResource = new Resource(rb._name, rb._amount, rb._image);
					Resources.add(newResource);	
				}
			}
		}

	}

	public void UpdateProductionTime(int secs){
		for (Building b : this.Buildings) {
			b._pTime = secs;			
		}
	}

	public void SaveIsland(Context context)
	{
		//Open File for Saving

		String string = "";

		//Save Buildings
		for (Building b : this.Buildings) {
			string += b.toString() + "\n";
		}

		FileOutputStream fos;
		try {
			fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			fos.write(string.getBytes());
			fos.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void LoadIsland(Context context)
	{
		//Open File 
		try {
			this.Buildings = new ArrayList<Building>();
			FileInputStream fis = context.openFileInput(FILENAME);

			if(fis != null){
				BufferedReader rdr = new BufferedReader(new InputStreamReader(fis));

				boolean notEof = true;
				while(notEof){
					String line = rdr.readLine();
					//Log.d("TSO",line);
					if(line != null){						
						Building newBuilding = Building.newInstance(line);
						this.Buildings.add(newBuilding);
					}else
					{
						notEof= false;
					}
				}
				fis.close();
			}


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
