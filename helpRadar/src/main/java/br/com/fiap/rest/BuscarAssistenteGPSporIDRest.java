package br.com.fiap.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import br.com.helpradar.entity.Avaliacao;
import br.com.helpradar.entity.Especialidade;
import br.com.helpradar.entity.TipoUsuario;
import br.com.helpradar.entity.Usuario;
import br.com.helpradar.entity.UsuarioGPS;

public class BuscarAssistenteGPSporIDRest extends AsyncTask<String, Void, String>{

	@Override
	protected void onPostExecute (String result){
		if (!result.equals("ERRO")) {
			System.out.println("4");
			try {
				if (result.equals("")) {
				}else{
					
					Usuario assistente = new Usuario();
					
					//ainda não há espaço para mostrar na tela.
					Set<Especialidade> especialidade = new HashSet<Especialidade>();
					
					Set<Avaliacao> avaliacaoSet = new HashSet<Avaliacao>();
					
					System.out.println("aaa");
					JSONObject assitObj = new JSONObject(result);
					JSONArray avalArray = assitObj.getJSONArray("avaliacoes");
					
					
					
				    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy'  'HH:mm");
				   
					
					for (int i = 0; i < avalArray.length(); i++) {
						System.out.println("ENTROU NO FOR LOL");
						
						JSONObject avalObj = avalArray.getJSONObject(i);
						Avaliacao avaliacao = new Avaliacao();
						
						Calendar cal = Calendar.getInstance();
						cal.setTime(sdf.parse(avalObj.getString("data")));		
						avaliacao.setDataAvaliacao(cal);
						
						avaliacao.setDescricao(avalObj.getString("descricao"));
						avaliacao.setNota(Integer.parseInt(avalObj.getString("nota")));
						avaliacao.setTitulo(avalObj.getString("titulo"));
						
						avaliacaoSet.add(avaliacao);
					}
					assistente.setNome(assitObj.getString("nome"));
					assistente.setAvaliacao(avaliacaoSet);
					
					Variables.setAssistenteNoCartao(assistente);
					
					
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
			String url = "http://192.168.1.31:8081/HelpRadar_JSF2.2/rest/assistente/buscarAssistentePorId/"
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
