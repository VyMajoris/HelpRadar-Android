package br.com.fiap.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.google.android.gms.drive.internal.r;
import com.google.gson.Gson;






import android.app.ProgressDialog;
import android.graphics.Canvas.EdgeType;
import android.os.AsyncTask;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;
import br.com.fiap.helpradar.CenterActivity;
import br.com.fiap.helpradar.MainActivity;
import br.com.fiap.helpradar.R;
import br.com.fiap.helpradar.CenterActivity.MapaFragment;
import br.com.fiap.helpradar.variables.Variables;
import br.com.helpradar.entity.TipoUsuario;
import br.com.helpradar.entity.Usuario;
import br.com.helpradar.entity.UsuarioGPS;

public class BuscarAssistentesGPSRest extends AsyncTask<String, Void, String>{

	@Override
	protected void onPostExecute (String result){
		if (!result.equals("ERRO")) {
			System.out.println("4");
			try {
				if (result.equals("")) {
				}else{
					System.out.println("aaa");
					JSONObject jsonObject = new JSONObject(result);
					Variables.getListaAssistenteGPS().clear();
					
					JSONArray jsonArray = jsonObject.getJSONArray("Assistentes");
					
					System.out.println(jsonArray.toString());
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject innerObject = jsonArray.getJSONObject(i);
						UsuarioGPS usuariogps =  new Gson().fromJson(innerObject.toString(), UsuarioGPS.class);
						System.out.println(usuariogps.getUserId());
						System.out.println(usuariogps.getLat());
						System.out.println(usuariogps.getLongi());
						Variables.getListaAssistenteGPS().add(usuariogps);
					}
					
				
					for (int i = 0; i < Variables.getListaAssistenteGPS().size(); i++) {
						System.out.println(Variables.getListaAssistenteGPS().get(i).getUserId());
						
					}
					
					MapaFragment.showAssistente();
					
					System.out.println("ccc");
				}

			} catch (Exception e) {
				// TODO: handle exception	
			}	
		}
		System.out.println(result);
	}

	@Override
	protected String doInBackground(String... params) {
		try {

			System.out.println("1");
			System.out.println("******");
			System.out.println(params[0]);
			System.out.println("******");
			HttpClient client = new DefaultHttpClient();
			String url = "http://192.168.1.31:8081/HelpRadar_JSF2.2/rest/assistente/buscarAssistentePorEspGPS/"
					+ params[0];





			HttpGet get = new HttpGet(url);
			// Define o tipo de retorno aceito
			get.setHeader("Accept", "application/json");

			HttpResponse response = client.execute(get);
			StatusLine status = response.getStatusLine();

			System.out.println("2");
			System.out.println(status.getStatusCode());
			System.out.println(response);
			System.out.println(response.getEntity());
			if (status.getStatusCode() == 200) {
				HttpEntity resposta = response.getEntity();
				String TextPlainResposta = EntityUtils.toString(resposta);
				return TextPlainResposta;
			}


			System.out.println("3");


		} catch (Exception e) {
			e.printStackTrace();
		}

		return "ERRO";



	}










}
