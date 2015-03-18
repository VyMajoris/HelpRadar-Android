package br.com.fiap.helpradar;





import java.util.HashSet;
import java.util.Set;

import br.com.fiap.helpradar.CenterActivity.MapaFragment;
import br.com.fiap.helpradar.seekArc.SeekArc;
import br.com.fiap.helpradar.seekArc.SeekArc.OnSeekArcChangeListener;
import br.com.fiap.helpradar.variables.Variables;
import br.com.fiap.rest.BuscarAssistentesGPSRest;
import br.com.fiap.rest.CadastroAssistenteRest;
import br.com.fiap.rest.CadastroEspecialidadeRest;
import br.com.helpradar.entity.Especialidade;
import br.com.helpradar.entity.TipoUsuario;
import br.com.helpradar.entity.Usuario;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
// ...
import android.widget.EditText;
import android.widget.TextView;

public class FilterDialog extends DialogFragment {


	private EditText mEditText;
	private SeekArc mSeekArc;
	private TextView mSeekArcProgress;
	private EditText txtEspecialidade;

	public FilterDialog() {
		// Empty constructor required for DialogFragment
	}

	public static FilterDialog newInstance(String title) {
		FilterDialog frag = new FilterDialog();
		Bundle args = new Bundle();
		args.putString("title", title);
		frag.setArguments(args);
		return frag;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		LayoutInflater i = getActivity().getLayoutInflater();
		View v = i.inflate(R.layout.fragment_filterdialog,null);
		txtEspecialidade = (EditText) v.findViewById(R.id.txtEspecialidade);


		mSeekArc = (SeekArc) v.findViewById(R.id.seekArc);
		mSeekArcProgress = (TextView) v.findViewById(R.id.seekArcProgress);
		mSeekArc.setOnSeekArcChangeListener(new OnSeekArcChangeListener() {
			String sProgress;
			@Override
			public void onProgressChanged(SeekArc seekArc, int progress,
					boolean fromUser) {
				progress = progress / 100;
				progress = progress * 100;
				if (progress < 1000) {
					mSeekArcProgress.setText(String.valueOf(progress) + "m");
				}else{
					sProgress = (String.valueOf(progress).substring(0,1) + "," +  String.valueOf(progress).substring(1,2) + "km" );
					Log.i("seek", sProgress);

					mSeekArcProgress.setText(sProgress);
				}

			}

			@Override
			public void onStartTrackingTouch(SeekArc seekArc) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStopTrackingTouch(SeekArc seekArc) {
				// TODO Auto-generated method stub
			}

		});


		AlertDialog.Builder b=  new  AlertDialog.Builder(getActivity())
		.setTitle("Nova Pesquisa")
		.setPositiveButton("Pesquisar",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

				final ProgressDialog pesquisaProgressDialog = ProgressDialog.show(getActivity(), "Agurade um momento", "Pesquisando por Assistentes", true);
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {

							//MapaFragment.clearMap();
							


							BuscarAssistentesGPSRest buscarAssistentesGPSRest = new BuscarAssistentesGPSRest();

							buscarAssistentesGPSRest.execute(txtEspecialidade.getText().toString());
							



						} catch (Exception e) {
							e.printStackTrace();
						}
						pesquisaProgressDialog.dismiss();
					}
				}).start();
				dialog.dismiss();



				








			}
		}
				)
				.setNegativeButton("Cancelar",
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				}
						);

		b.setView(v);
		return b.create();
	}
}