package br.com.fiap.rest;

import java.util.ArrayList;
import java.util.List;

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

import com.google.gson.Gson;





import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;
import br.com.fiap.helpradar.CenterActivity;
import br.com.fiap.helpradar.MainActivity;
import br.com.helpradar.entity.Usuario;

public class UploadGPSAssistenteRest extends AsyncTask<String, Void, String>{
	private int userID;


	@Override
	protected void onPostExecute (String result){
		System.out.println("onPostExecute");
		System.out.println(result);
	}
	@Override
	protected void onPreExecute (){
		System.out.println("onPreExecute");
		
	}

@Override
protected String doInBackground(String... params) {
	try {
		HttpClient client = new DefaultHttpClient();
		String url = "http://192.168.1.31:8081/HelpRadar_JSF2.2/rest/assistente/atualizarAssistente/";

		HttpPost post = new HttpPost(url);
		// Define o tipo de retorno aceito
		post.setHeader("Content-type", "text/plain");
		
		
		String userID = params[0];
		String lat = params[1];
		String longi = params[2];
		String gpsData = userID+">"+lat+">"+longi;
		
		
		
		HttpEntity entity = new StringEntity(gpsData);
		post.setEntity(entity);
		HttpResponse response = client.execute(post);
		
		String resposta = EntityUtils.toString(response.getEntity());
		
		return resposta;
		
		
		
	} catch (Exception e) {
		e.printStackTrace();
	}

	return "ERRO";



}








public int getId() {
	return userID;
}




public void setId(int id) {
	this.userID = id;
}




}
