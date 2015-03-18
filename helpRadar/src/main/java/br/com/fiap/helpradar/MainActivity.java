package br.com.fiap.helpradar;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;





import br.com.fiap.helpradar.variables.Variables;
import br.com.fiap.rest.CadastroEspecialidadeRest;
import br.com.fiap.rest.CadastroUsuarioRest;
import br.com.fiap.rest.VerificarUsuarioRest;

import br.com.helpradar.entity.Especialidade;
import br.com.helpradar.entity.TipoUsuario;
import br.com.helpradar.entity.Usuario;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;

import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	private String userId;
	private String userFirstName;
	private String userLastName;
	private boolean gambiarraState = true;
	VerificarUsuarioRest verificarUsuarioRest = new VerificarUsuarioRest();
	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChanged(session, state, exception);
		}
	};

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);
		
		
		
		LoginButton lb = (LoginButton) findViewById(R.id.fbLogin);
		lb.setPublishPermissions(Arrays.asList("email", "public_profile", "user_friends"));
	}
	
	

	@Override
	protected void onResume() {
		super.onResume();
		
		Session session = Session.getActiveSession();
		if(session != null && (session.isClosed() || session.isOpened())){
			onSessionStateChanged(session, session.getState(), null);
		}
		
		uiHelper.onResume();
	}
	
	
	@Override
	protected void onPause() {
		super.onPause();
		uiHelper.onPause();
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}
	
	
	@Override
	protected void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		uiHelper.onSaveInstanceState(bundle);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}
	
	public void gambiarraDeLogin(){
		gambiarraState = false;
		System.out.println("GAMBIA76RA");
		try {
			if (verificarUsuarioRest.execute(userId).get().equals("")) {

				Variables.setUserId(Long.parseLong(userId));
				Usuario usuario = new Usuario();
				usuario.setUserId(Long.parseLong(userId));
				usuario.setNome(userFirstName+" "+userLastName);
				
				usuario.setTipoUsuario(TipoUsuario.DEFAULT);
				CadastroUsuarioRest cadastroUsuarioRest = new CadastroUsuarioRest();
				
				//.get do AsyncTask transforma o AsyncTask em um "SyncTask", basicamente...
				//Isso para dar tempo de inserir o usuário na tabela antes de carregar a próxima atividade
				cadastroUsuarioRest.execute(usuario).get();
				
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Intent intent = new Intent(MainActivity.this, CenterActivity.class);
		startActivity(intent);

		
	}
	
	// METHODS FACEBOOK
	public void onSessionStateChanged(final Session session, SessionState state, Exception exception){
		if(session != null && session.isOpened()){
			Log.i("Script", "Usuário conectado");
			Request.newMeRequest(session, new Request.GraphUserCallback() {
				@Override
				public void onCompleted(GraphUser user, Response response) {
					if(user != null){
						
						Log.i("facebook", user.getFirstName());
						Log.i("facebook", user.getId());
						
						userId = user.getId();
						userFirstName = user.getFirstName();
						userLastName = user.getLastName();
						
							System.out.println("da");
							
							if (gambiarraState) {
								gambiarraDeLogin();
							}
							
							
						
						
						
						
					}
					
				}
			}).executeAsync();
		}
		else{
			Log.i("Script", "Usuário não conectado");
		}
	}
	
	
	

	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
}
