package br.com.fiap.helpradar.variables;



import java.util.ArrayList;
import java.util.List;

import br.com.helpradar.entity.TipoUsuario;
import br.com.helpradar.entity.Usuario;
import br.com.helpradar.entity.UsuarioGPS;

public class Variables {
	private static Long userId;
	private static TipoUsuario tipoUsuario = TipoUsuario.DEFAULT;
	private static List<UsuarioGPS> listaAssistenteGPS = new ArrayList<UsuarioGPS>();
	private static Usuario assistenteNoCartao;


	public static TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public static void setTipoUsuario(TipoUsuario tipoUsuario) {
		Variables.tipoUsuario = tipoUsuario;
	}

	public static Long getUserId() {
		return userId;
	}

	public static void setUserId(Long userId) {
		Variables.userId = userId;
	}

	public static List<UsuarioGPS> getListaAssistenteGPS() {
		return listaAssistenteGPS;
	}

	public static void setListaAssistenteGPS(List<UsuarioGPS> listaAssistenteGPS) {
		Variables.listaAssistenteGPS = listaAssistenteGPS;
	}

	public static Usuario getAssistenteNoCartao() {
		return assistenteNoCartao;
	}

	public static void setAssistenteNoCartao(Usuario assistenteNoCartao) {
		Variables.assistenteNoCartao = assistenteNoCartao;
	}



	

}
