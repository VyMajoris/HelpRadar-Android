package br.com.fiap.helpradar;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import br.com.fiap.helpradar.variables.Variables;
import br.com.fiap.rest.BuscarAssistenteGPSporIDRest;
import br.com.helpradar.entity.Avaliacao;
import br.com.helpradar.entity.Especialidade;
import br.com.helpradar.entity.Usuario;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
// ...

public class CartaoDialog extends DialogFragment {

	TextView nomeAssistente;
	ImageView avatarAssistente;
	View v;

	TextView avalData;
	TextView avalNomeEspecialidade;
	TextView avalTitulo;
	TextView avalDescricao;
	RatingBar avalRating;
	LayoutInflater factory;

	View avalInflater;

	public CartaoDialog() {
		// Empty constructor required for DialogFragment

	}

	public void detectAvaliacao(){

	}

	private class GetFacebookAvatar extends AsyncTask<String, Void, Void> {
		Bitmap mIcon1;
		@Override
		protected Void doInBackground(String... strings) {
			URL img_value = null;
			try {
				img_value = new URL(
						"https://graph.facebook.com/719626554739485/picture?type=large");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				mIcon1 = BitmapFactory.decodeStream(img_value
						.openConnection().getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result){

			avatarAssistente.setImageBitmap(mIcon1);
		}
	}

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
							avaliacao.setNomeEspecialidade(avalObj.getString("titulo"));


							avaliacaoSet.add(avaliacao);
							addAvaliacao(avalObj.getString("nomeespecialidade"), avalObj.getString("titulo"), 
										 Integer.parseInt(avalObj.getString("nota")), avalObj.getString("descricao"),
										avalObj.getString("data"));
							
						}
						assistente.setNome(assitObj.getString("nome"));
						assistente.setAvaliacao(avaliacaoSet);

						nomeAssistente.setText(assitObj.getString("nome"));
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


	public static CartaoDialog newInstance(String title) {
		CartaoDialog frag = new CartaoDialog();
		Bundle args = new Bundle();
		args.putString("title", title);
		frag.setArguments(args);
		return frag;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		System.out.println(">>>>>>>>>>"+getTag()+"<<<<<<<<");
		LayoutInflater i = getActivity().getLayoutInflater();
		v = i.inflate(R.layout.fragment_cartao_assistente_dialog,null);	
		factory = getActivity().getLayoutInflater();
		avalInflater = factory.inflate(R.layout.fragment_avaliacao_cartao, null);
		
		
		GetFacebookAvatar getFacebookAvatar = new GetFacebookAvatar();
		getFacebookAvatar.execute("null");

		avatarAssistente = (ImageView) v.findViewById(R.id.avatarAssistente);
		nomeAssistente = (TextView) v.findViewById(R.id.nomeAssistente);



		BuscarAssistenteGPSporIDRest buscarAssistenteGPSporIDRest = new BuscarAssistenteGPSporIDRest();
		buscarAssistenteGPSporIDRest.execute(getTag());
		//nomeAssistente.setText(Variables.getAssistenteNoCartao().getNome());
		


		AlertDialog.Builder b =  new  AlertDialog.Builder(getActivity())
		.setNegativeButton("Voltar",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.dismiss();
			}
		}
				);


		b.setView(v);
		
		return b.create();

	}






	public void addAvaliacao(String especialidade, String titulo, int rating, String comentario, String data){


		HorizontalScrollView avalScollView = (HorizontalScrollView) v.findViewById(R.id.avaliacaoScroll);

		LinearLayout linearScrollViewContainer = (LinearLayout) v.findViewById(R.id.linearLayoutAvaliacaoContainer);


		final LayoutInflater factory = getActivity().getLayoutInflater();

		final View avalInflater = factory.inflate(R.layout.fragment_avaliacao_cartao, null);

		avalData = (TextView) avalInflater.findViewById(R.id.avalData);
		avalData.setText(data);
		avalNomeEspecialidade = (TextView) avalInflater.findViewById(R.id.avalEspecialdiade);
		avalNomeEspecialidade.setText(especialidade);
		avalTitulo = (TextView) avalInflater.findViewById(R.id.avalTitulo);
		avalTitulo.setText(titulo);
		avalDescricao = (TextView) avalInflater.findViewById(R.id.avalComentario);
		avalDescricao.setText(comentario);
		avalRating = (RatingBar) avalInflater.findViewById(R.id.avalRating);
		avalRating.setRating(rating);
		



		v.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		int widht = v.getMeasuredWidth();


		View aval = avalInflater.findViewById(R.id.avaliacaoCartao);

		aval.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
				)
				);
		aval.getLayoutParams().width = widht;



		linearScrollViewContainer.addView(aval);



	}







}

















