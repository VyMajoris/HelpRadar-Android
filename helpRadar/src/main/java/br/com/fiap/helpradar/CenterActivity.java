package br.com.fiap.helpradar;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.com.fiap.helpradar.variables.Variables;
import br.com.fiap.rest.CadastroAssistenteRest;
import br.com.fiap.rest.CadastroEspecialidadeRest;
import br.com.fiap.rest.UploadGPSAssistenteRest;
import br.com.helpradar.entity.Especialidade;
import br.com.helpradar.entity.TipoUsuario;
import br.com.helpradar.entity.Usuario;

import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

public class CenterActivity extends ActionBarActivity implements
NavigationDrawerFragment.NavigationDrawerCallbacks{
	static Fragment currentFragment = null;
	static Fragment mapaFragmentShow = null;
	static Fragment cadastroSplashShow = null;
	static Fragment cadastroFormShow  = null;
	static Fragment ajudaShow  = null;
	LocationManager locationManager;
	LocationListener locationListener ;
	Timer timer;
	TimerTask timerTask;
	final Handler handler = new Handler();
	UploadGPSAssistenteRest uploadGPSAssistenteRest = new UploadGPSAssistenteRest();
	double longitude;
	double latitude;
	static boolean isAssist = false;
	boolean isBroadcasting = false;;
	boolean mapaFragmentLodaded = false;
	boolean cadastroFragmentLodaded = false;
	boolean ajudaFragmentLoaded = false;

	FragmentManager fragmentManager = getSupportFragmentManager();
	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_center);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments


		FragmentManager fragmentManager = getSupportFragmentManager();

		switch(position) {
		case 0:


			if (mapaFragmentLodaded) {
				fragmentManager
				.beginTransaction()
				.hide(currentFragment)
				.show(mapaFragmentShow)
				.commit();
			}else{
				System.out.println("1");
				fragmentManager
				.beginTransaction()
				.replace(R.id.containerFragment, MapaFragment.newInstance(position+1))
				.commit();
				mapaFragmentLodaded = true;
			}


			break;
		case 1:


			if (Variables.getTipoUsuario() == TipoUsuario.DEFAULT) {

				if (cadastroFragmentLodaded) {
					fragmentManager
					.beginTransaction()
					.hide(currentFragment)
					.hide(mapaFragmentShow)
					.show(cadastroSplashShow)
					.commit();
				}else{
					System.out.println("1");
					fragmentManager
					.beginTransaction()
					.hide(currentFragment)
					.hide(mapaFragmentShow)
					.add(R.id.containerFragment, CadastroSplashFragment.newInstance(position + 1))

					.commit();
					mapaFragmentLodaded = true;
				}

			}
			else{
				if (ajudaFragmentLoaded) {
					fragmentManager
					.beginTransaction()
					.hide(currentFragment)
					.hide(mapaFragmentShow)

					.show(ajudaShow)
					.commit();
				}else{
					System.out.println("1");
					fragmentManager
					.beginTransaction()
					.hide(currentFragment)
					.hide(mapaFragmentShow)
					.add(R.id.containerFragment, AjudaFragment.newInstance(position + 1))

					.commit();
					ajudaFragmentLoaded = true;
				}
			}


			break;
		case 2:
			//DESLOGAR DO FACEBOOK

		}

		/*
		fragmentManager.beginTransaction()
        .replace(R.id.container, fragment)
        .commit();
		 */

	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.help_radar);
			break;
		case 2:
			mTitle = getString(R.string.cadastrar_assistente);
			break;
		case 3:
			mTitle = getString(R.string.ajuda);
			break;
		case 4:
			mTitle = getString(R.string.sair);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.center, menu);
			if (Variables.getTipoUsuario() == TipoUsuario.ASSISTENTE) {
				MenuItem item = menu.findItem(R.id.ActionGPSBroadCast);
				item.setVisible(true);
			}

			restoreActionBar();
			return true;
		}


		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean 	onPrepareOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		Log.i("********onPrepareOptionsMenu","onPrepareOptionsMenu********");




		return true;
	}

	public void startTimer() {
		//set a new Timer
		timer = new Timer();

		//initialize the TimerTask's job
		initializeTimerTask();

		//schedule the timer, after the first 0ms the TimerTask will run every 10000ms
		timer.schedule(timerTask, 0, 10000); //
	}

	public void stoptimertask() {
		//stop the timer, if it's not already null
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	public void initializeTimerTask() {

		timerTask = new TimerTask() {
			public void run() {

				//use a handler to run a toast that shows the current timestamp
				handler.post(new Runnable() {
					public void run() {
						uploadGPSAssistenteRest.execute(Variables.getUserId().toString(), Double.toString(latitude), Double.toString(longitude) );
						Toast.makeText(getApplicationContext(), "Compartilhamento de posição enterrompido!",  Toast.LENGTH_LONG).show();
					}
				});
			}
		};
	}

	public void startBroadCastGPS(){
		startTimer();
		locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				// Called when a new location is found by the network location provider.
				Log.i("GPS", location.getLatitude()+" "+location.getLongitude());
				longitude = location.getLongitude();
				latitude = location.getLatitude();
			}

			public void onStatusChanged(String provider, int status, Bundle extras) {}

			public void onProviderEnabled(String provider) {}

			public void onProviderDisabled(String provider) {}
		};

		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

	}

	public void stopBroadCastGPS(){
		locationManager.removeUpdates(locationListener);
		stoptimertask();
		Log.i("GPS", "stopBroadCastGPS");

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		switch (id) {
		case R.id.action_settings:

			break;




		case R.id.ActionGPSBroadCast:



			if (!isBroadcasting) {
				item.setIcon(R.drawable.ic_action_location_found);
				startBroadCastGPS();
				isBroadcasting = true;
				Toast.makeText(this, "Compartilhando posição GPS!",  Toast.LENGTH_LONG).show();




			}else{
				item.setIcon(R.drawable.ic_action_location_searching);

				stopBroadCastGPS();
				isBroadcasting = false;
				Toast.makeText(this, "Compartilhamento de posição enterrompido!",  Toast.LENGTH_LONG).show();


			}





			break;
		case R.id.ActoinSearchAssist:


			FragmentManager fm = getSupportFragmentManager();
			FilterDialog filterDialog = FilterDialog.newInstance("Busca de Assistente");
			filterDialog.show(fm, "fragment_edit_name");

			return true;



		default:
			break;
		}




		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class CadastroSplashFragment extends Fragment implements OnClickListener {

		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static CadastroSplashFragment newInstance(int sectionNumber) {
			CadastroSplashFragment fragment = new CadastroSplashFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public CadastroSplashFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_cadastro_splash,
					container, false);
			currentFragment = this;
			cadastroSplashShow = this;

			Button btnContinuar = (Button) rootView.findViewById(R.id.btnContinuar);
			btnContinuar.setOnClickListener(this);


			return rootView;
		}
		@Override
		public void onClick(View v) {
			FragmentManager fragmentManager = getFragmentManager();
			//do what you want to do when button is clicked
			switch (v.getId()) {
			case R.id.btnContinuar:

				fragmentManager
				.beginTransaction()
				.hide(mapaFragmentShow)
				.hide(cadastroSplashShow)
				.hide(currentFragment)
				.add(R.id.containerFragment, CadstroFormFragment.newInstance(1))
				.commit();
				break;

			}
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((CenterActivity) activity).onSectionAttached(getArguments()
					.getInt(ARG_SECTION_NUMBER));
		}
	}
	//////////////////////////
	public static class CadstroFormFragment extends Fragment implements OnClickListener {

		EditText editNome;
		EditText editEspPrim ;
		EditText editEspSec ;
		EditText editEspTerc ;
		EditText editCelular ;
		EditText editEmail ;

		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static CadstroFormFragment newInstance(int sectionNumber) {
			CadstroFormFragment fragment = new CadstroFormFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public CadstroFormFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_cadastro_form,
					container, false);
			currentFragment = this;
			cadastroFormShow = this;

			editNome = (EditText) rootView.findViewById(R.id.editNome);
			editEspPrim = (EditText) rootView.findViewById(R.id.editEspPrim);
			editEspSec = (EditText) rootView.findViewById(R.id.editEspSec);
			editEspTerc = (EditText) rootView.findViewById(R.id.editEspTerc);
			editCelular = (EditText) rootView.findViewById(R.id.editCelular);
			editEmail = (EditText) rootView.findViewById(R.id.editEmail);

			Button btnContinuar = (Button) rootView.findViewById(R.id.btnCadastrar);
			btnContinuar.setOnClickListener(this);

			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((CenterActivity) activity).onSectionAttached(getArguments()
					.getInt(ARG_SECTION_NUMBER));
		}


		private String cadastrarEspecialidade(Especialidade... params)
				throws JSONException, UnsupportedEncodingException, IOException,
				ClientProtocolException {
			HttpClient client = new DefaultHttpClient();
			String url = "http://192.168.1.31:8081/HelpRadar_JSF2.2/rest/especialidade/cadastrarEspecialidade/";

			HttpPost post = new HttpPost(url);
			// Define o tipo de retorno aceito
			post.setHeader("Content-type", "application/json");


			Gson gson = new Gson();
			gson.toJson(params[0]);

			JSONObject json = new JSONObject(gson.toJson(params[0]));
			System.out.println(json.toString());
			HttpEntity entity = new StringEntity(json.toString());
			post.setEntity(entity);
			HttpResponse response = client.execute(post);

			String resposta = EntityUtils.toString(response.getEntity());
			return resposta;
		}

		@Override
		public void onClick(View v) {




			FragmentManager fragmentManager = getFragmentManager();
			//do what you want to do when button is clicked
			switch (v.getId()) {
			case R.id.btnCadastrar:
				final ProgressDialog assitCadastroDialog = ProgressDialog.show(getActivity(), "Agurade um momento", "Cadastrando Assistente...", true);
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							//Se isso aqui funcionar... Meua migo

							Usuario assistente = new Usuario();

							CadastroEspecialidadeRest cadastroEspecialidadeRest = new CadastroEspecialidadeRest();
							CadastroAssistenteRest cadastroAssistenteRest = new CadastroAssistenteRest(); 

							assistente.setUserId(Variables.getUserId());
							assistente.setTipoUsuario(TipoUsuario.ASSISTENTE);

							boolean esp = false;



							if (!editCelular.getText().toString().equals("")) {
								assistente.setTelefone(editCelular.getText().toString());
							}

							if (!editEmail.getText().toString().equals("")) {
								assistente.setEmail(editEmail.getText().toString());
							}

							if (!editNome.getText().toString().equals("")) {
								assistente.setNome(editNome.getText().toString());
							}
							Set<Especialidade> listaEspecialidade = new HashSet<Especialidade>(); 

							if (!editEspPrim.getText().toString().equals("")) {
								Especialidade especialdiade1 = new Especialidade();

								especialdiade1.setNomeEspecialidade(editEspPrim.getText().toString());
								listaEspecialidade.add(especialdiade1);
								cadastrarEspecialidade(especialdiade1);
								esp = true;
							}
							if (!editEspSec.getText().toString().equals("")) {
								Especialidade especialdiade2 = new Especialidade();
								especialdiade2.setNomeEspecialidade(editEspSec.getText().toString());
								listaEspecialidade.add(especialdiade2);
								esp = true;
							}
							if (!editEspTerc.getText().toString().equals("")) {
								Especialidade especialdiade3 = new Especialidade();
								especialdiade3.setNomeEspecialidade(editEspTerc.getText().toString());
								listaEspecialidade.add(especialdiade3);
								cadastrarEspecialidade(especialdiade3);
								esp = true;
							}


							assistente.setEspecialidade(listaEspecialidade);



							cadastroAssistenteRest.execute(assistente).get();







						} catch (Exception e) {
							e.printStackTrace();
						}
						assitCadastroDialog.dismiss();
					}
				}).start();







				fragmentManager
				.beginTransaction()
				.hide(mapaFragmentShow)
				.hide(cadastroSplashShow)
				.hide(cadastroFormShow)
				.hide(currentFragment)
				.show(mapaFragmentShow)


				.commit();



				break;

			}

		}
	}

	//////////////////////////////////////////////////////////////////

	public static class AjudaFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static AjudaFragment newInstance(int sectionNumber) {
			AjudaFragment fragment = new AjudaFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public AjudaFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_ajuda,
					container, false);
			currentFragment = this;

			ajudaShow = this;
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((CenterActivity) activity).onSectionAttached(getArguments()
					.getInt(ARG_SECTION_NUMBER));
		}
	}

	//////////////////////////

	public static class CartaoAssistenteFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static CartaoAssistenteFragment newInstance(int sectionNumber) {
			CartaoAssistenteFragment fragment = new CartaoAssistenteFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public CartaoAssistenteFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_cadastro_form,
					container, false);
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((CenterActivity) activity).onSectionAttached(getArguments()
					.getInt(ARG_SECTION_NUMBER));
		}
	}

	//////////////////////////	

	public static class MapaFragment extends Fragment implements OnMarkerClickListener {
		private SupportMapFragment map;
		private static GoogleMap mMap;
		Fragment  fragment;

		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static MapaFragment newInstance(int sectionNumber) {
			MapaFragment fragment = new MapaFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}



		public MapaFragment() {
		}





		public static void clearMap() {
			mMap.clear();

		}







		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_mapa,
					container, false);


			System.out.println("onCreateVIew");

			currentFragment = this;
			mapaFragmentShow = this;
			mMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.mapFrame)).getMap();
			mMap.setOnMarkerClickListener(this);




			return rootView;

		}
		public static void showAssistente() {
			System.out.println("showAssistente()");
			for (int i = 0; i < Variables.getListaAssistenteGPS().size(); i++) {
				System.out.println(Variables.getListaAssistenteGPS().get(i).getLat());
				mMap.addMarker(new MarkerOptions()
			
				.position(new LatLng(Double.parseDouble(Variables.getListaAssistenteGPS().get(i).getLat()), 
						Double.parseDouble(Variables.getListaAssistenteGPS().get(i).getLongi())))
						.title("Hello world")
						.snippet(""+Variables.getListaAssistenteGPS().get(i).getUserId())



						);
			}

		}

		@Override
		public boolean onMarkerClick(final Marker marker) {
			System.out.println("onMarkerClick");
			FragmentManager fm = getFragmentManager();
			CartaoDialog cartaoDialog = CartaoDialog.newInstance("Cartão de assistente");
			cartaoDialog.show(fm, marker.getSnippet());
		

			return true;
		}










		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((CenterActivity) activity).onSectionAttached(getArguments()
					.getInt(ARG_SECTION_NUMBER));
		}






		// TODO Auto-generated method stub
	}	
}
