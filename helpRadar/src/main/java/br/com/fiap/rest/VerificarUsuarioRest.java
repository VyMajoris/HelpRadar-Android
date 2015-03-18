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
import br.com.fiap.helpradar.variables.Variables;
import br.com.helpradar.entity.TipoUsuario;
import br.com.helpradar.entity.Usuario;

public class VerificarUsuarioRest extends AsyncTask<String, Void, String>{
	
	
						
	
	@Override
	protected void onPostExecute (String result){
		if (!result.equals("ERRO")) {

			System.out.println("4");
			try {
				if (result.equals("")) {
					
				}else{

					System.out.println("5");
					String[] parts = result.split("-");
					
					Variables.setUserId(Long.parseLong(parts[0]));
					

					System.out.println("6");
					if (parts[1].equals("ASSISTENTE")) {
						Variables.setTipoUsuario(TipoUsuario.ASSISTENTE);
					}
					//Variables seta o TipoUsuario para default automaticamente
					

					System.out.println("7");
					
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
		String url = "http://192.168.1.31:8081/HelpRadar_JSF2.2/rest/usuario/verificarUsuario/"
				+ params[0];
		
		
		HttpGet get = new HttpGet(url);
		// Define o tipo de retorno aceito
		get.setHeader("Accept", "text/plain");
		
		HttpResponse response = client.execute(get);
		StatusLine status = response.getStatusLine();

		System.out.println("2");
		System.out.println(status.getStatusCode());
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
