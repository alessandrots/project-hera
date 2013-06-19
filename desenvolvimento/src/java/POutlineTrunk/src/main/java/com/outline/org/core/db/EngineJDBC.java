package com.outline.org.core.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import com.outline.org.app.domain.Calendario;
import com.outline.org.util.Utils;

public class EngineJDBC {
	
	private static Connection conn;
	private static EngineJDBC engineDB;
	
	//Parâmetros de Conexão
	private String url;
	private String user;
	private String pwd;
	private String driver;
	
	public static EngineJDBC getInstancia(){
		if (engineDB == null){
			engineDB = new EngineJDBC();
		}
		
		return engineDB;
	}

	/**
	 *
	 * driver  = "org.postgresql.Driver";
	 * url 	= "jdbc:postgresql://localhost:5432/outline";
	 * user 	= "postgres";
	 * pwd 	= "123456";
	 * 
	 * driver  = "org.postgresql.Driver";
	 * url 	= "jdbc:postgresql://localhost:5432/outline";
	 * user 	= "outline";
	 * pwd 	= "outline";
	 * 
	 * driver  = "com.mysql.jdbc.Driver";
	 * url = "jdbc:mysql://128.1.30.58/dbsroii";
	 * user 	= "root
	 * pwd 	= "";//ou jv121206
	 * 
	 */
	private EngineJDBC() {
		this("jdbc:postgresql://127.0.0.1:5432/outline", "outline", "outline", "org.postgresql.Driver");
	}

	private EngineJDBC(String url, String user, String pwd, String driver) {
		this.url = url;
		this.user = user;
		this.pwd = pwd;
		this.driver = driver;		
		gerarConexao();
	}

	protected void gerarConexao() {
		try {
			Class.forName(this.driver);
			conn = DriverManager.getConnection(this.url, this.user, this.pwd);
			conn.setAutoCommit(false);
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}

	public static Connection getConn() {
		return conn;
	}
	
	public static void recuperarTodasTarefas() {
		Connection conexao 		= getConn();
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select icodigo, snome from negocio.tb_tarefa ");
			PreparedStatement ps = conexao.prepareStatement(sql.toString());			
			//setando filtros
			
			ResultSet rs = ps.executeQuery();
			
			//Montando a lista de objetos
			while (rs.next()){
				System.out.println(" CÓDIGO = " + rs.getInt("icodigo") + " - NOME = " + rs.getString("snome"));
//				t.setCodigo(rs.getInt("icodigo"));
//				t.setNome(rs.getString("snome"));				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void cadastrarUsuario() {
		Connection conexao 		= getConn();
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" Insert into negocio.tb_usuario (snome,susername, semail, spassword) VALUES ");
			sql.append(" (?,?, ?, ?) ");
			PreparedStatement ps = conexao.prepareStatement(sql.toString());
						
			//setando filtros
			ps.setString(1, "Alessandro T. Santos");
			ps.setString(2, "alessandrots");
			ps.setString(3, "alessandro.teixeira@gmail.com");
			ps.setString(4, DigestUtils.sha256Hex("123456"));
			
			//Adicionando linha
			int linha = ps.executeUpdate();

			conexao.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<Calendario> recuperarCalendarioPorLogin(String login) {
		Connection conexao 		= getConn();
		
		List<Calendario> lista = null;
		
		boolean bUnicaVez = true; 
		
		try {
			StringBuffer sql = new StringBuffer();			
			sql.append(" select cal.icodigo,  ");
			sql.append("  cal.dia_domingo,  ");
			sql.append("  cal.dia_segunda,  ");
			sql.append("  cal.dia_terca, ");
			sql.append("  cal.dia_quarta, ");
			sql.append("  cal.dia_quinta, ");
			sql.append("  cal.dia_sexta, ");
			sql.append("  cal.dia_sabado ");
			sql.append(" from negocio.tb_calendario cal ");
			sql.append(" inner join negocio.tb_recurso rec ");
			sql.append(" on cal.irecurso = rec.icodigo ");
			sql.append(" inner join negocio.tb_usuario usu ");
			sql.append(" on rec.iusuario = usu.icodigo ");
			sql.append(" where usu.susername = ? ");
			
			PreparedStatement ps = conexao.prepareStatement(sql.toString());			
			
			//setando filtros
			ps.setString(1, login);
			
			ResultSet rs = ps.executeQuery();
			
			//Montando a lista de objetos
			while (rs.next()){
				if (bUnicaVez){
					bUnicaVez = false;
					lista = new ArrayList<Calendario>();
				}
				
				Calendario cal = new Calendario();
				cal.setCodigo(rs.getLong("icodigo"));
				cal.setDomingo(rs.getFloat("dia_domingo"));
				cal.setSabado(rs.getFloat("dia_sabado"));
				cal.setSegunda(rs.getFloat("dia_segunda"));
				cal.setTerca(rs.getFloat("dia_terca"));
				cal.setQuarta(rs.getFloat("dia_quarta"));
				cal.setQuinta(rs.getFloat("dia_quinta"));
				cal.setSexta(rs.getFloat("dia_sexta"));
				lista.add(cal);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lista;
	}

	public static void main(String[] args) {
		
		EngineJDBC.getInstancia();
		
//		recuperarTodosTesteTmp();//OK
		
//		recuperarTodasTarefas();//OK
		
//		cadastrarUsuario();//OK
		
		sincronizarTarefas();
	}

	/**
	 * Simulando a sincronização das tarefas de acordo com o calendário.
	 * 
	 */
	private static void sincronizarTarefas() {
		String dataInicio = "04/06/2013";
		String duracaoOrigem = "7";
		String duracaoDestino = "2";
		
		List<Calendario> listaCalendario = recuperarCalendarioPorLogin("alessandrots");
		
		Calendario calObjeto = listaCalendario.get(0);
		
		atualizarRelacionamentoTerminoInicio(dataInicio, duracaoOrigem, duracaoDestino, calObjeto);
	}
	
	private static void atualizarRelacionamentoTerminoInicio(String dataInicioOrigem_, 
															 String duracaoOrigem, 
															 String duracaoDestino, 
															 Calendario calObjeto) {
		
		//CALCULANDO AS DATAS DA TAREFA DE ORIGEM
		Calendar calDataInicioTarefaOrigem = GregorianCalendar.getInstance();
		String[] dataInicioPartes = Utils.getInstance().quebrarDataEmPartes(dataInicioOrigem_);
		
		//Recuperando a data de início e duração na Tarefa de Origem
		int duracaoTarefaOrigemDataInicio = Integer.parseInt(duracaoOrigem);
		calDataInicioTarefaOrigem.set(Integer.parseInt(dataInicioPartes[2]), 
									  Integer.parseInt(dataInicioPartes[1])-1, 
									  Integer.parseInt(dataInicioPartes[0]), 
									  0, 0, 0);
		
		
		Date dataInicioOrigem = calDataInicioTarefaOrigem.getTime();
		
		//Criando a data fim baseado na data início
		Date dataTerminoOrigem = new Date(dataInicioOrigem.getTime()); 
		Calendar calDataTerminoTarefaOrigem = GregorianCalendar.getInstance();
		calDataTerminoTarefaOrigem.setTime(dataTerminoOrigem);
		
		//Retorna a carga horária do dia específico
		while (duracaoTarefaOrigemDataInicio > 0){			
			float cargaHoraria = retornarCargaHorariaPorDia(calDataTerminoTarefaOrigem.get(Calendar.DAY_OF_WEEK), calObjeto);
			
			//so incrementa um dia de trabalho qdo a cargahoraria for > 0, senão continua procurando o próximo dia
			if (cargaHoraria > 0){
				System.out.println(" duração decrementada... ");
				duracaoTarefaOrigemDataInicio--;
			}
			
			/*
			 * incrementa cada dia, mesmo não sendo dia de carga horária útil, pq ?
			 * 
			 * Pq, só vai considerar dia útil o dia que retorna uma valor > 0, então se for 1 dia p/ semana, vai contabilizar
			 * 
			 * todos os outros dias até chegar ao final da duração.
			 * 
			 * Outro ponto importante... independente das horas q o recurso faça por dia, isto é considerado 1 dia de trabalho.
			 * 
			 */
			calDataTerminoTarefaOrigem.add(Calendar.DATE, 1);
			
			System.out.println(" data termino atualizada = " + Utils.getInstance().transformarDateToString(calDataTerminoTarefaOrigem.getTime()));
		}
		
		
		//Recalculando a data fim da origem (a data fim = dataInicio + duração -1)		
		calDataTerminoTarefaOrigem.add(Calendar.DATE, duracaoTarefaOrigemDataInicio-1);		
		
		//Atualizando as datas na Tarefa de Origem
		String dataTerminoOrigem_ = Utils.getInstance().transformarDateToString(calDataTerminoTarefaOrigem.getTime());
		
		System.out.println(" dataTerminoOrigem = " + dataTerminoOrigem_);
		
//		//CALCULANDO AS DATAS DA TAREFA DE DESTINO		
//		//A data de inicio da tarefa de destino é (dataInicioOrigem + duracaoTarefaOrigem)
//		Calendar calDataInicioTarefaDestino = GregorianCalendar.getInstance();
//		calDataInicioTarefaDestino.setTime(dataInicioOrigem);		
//		calDataInicioTarefaDestino.add(Calendar.DATE, duracaoTarefaOrigemDataInicio);
//		
//		int duracaoTarefaDestinoDataInicio = Integer.parseInt(duracaoDestino);
//		Date dataInicioDestino = calDataInicioTarefaDestino.getTime();
//		
//		//Criando a data fim baseado na data início
//		Calendar calDataTerminoTarefaDestino = GregorianCalendar.getInstance();
//		Date dataTerminoDestino = new Date(dataInicioDestino.getTime()); 
//		calDataTerminoTarefaDestino.setTime(dataTerminoDestino);
//		
//		//Recalculando a data fim da origem (a data fim = dataInicio + duração -1)		
//		calDataTerminoTarefaDestino.add(Calendar.DATE, duracaoTarefaDestinoDataInicio-1);		
//		
//		//Atualizando as datas (inicio e fim) na Tarefa de Destino
//		String dataInicioDestino_  = Utils.getInstance().transformarDateToString(calDataInicioTarefaDestino.getTime());
//		String dataTerminoDestino_ = Utils.getInstance().transformarDateToString(calDataTerminoTarefaDestino.getTime());	
		
//		
//		System.out.println(" dataInicioDestino_ = " + dataInicioDestino_);
//		
//		System.out.println(" dataTerminoDestino_ = " + dataTerminoDestino_);
	}
	
	public static float retornarCargaHorariaPorDia(int dia, Calendario cal) {
		float cargaHoraria = 0;
		
		switch (dia) {
			case 1:
				cargaHoraria = cal.getDomingo();
			break;
			
			case 2:
				cargaHoraria = cal.getSegunda();
			break;
			
			case 3:
				cargaHoraria = cal.getTerca();
			break;
			
			case 4:
				cargaHoraria = cal.getQuarta();
			break;
			
			case 5:
				cargaHoraria = cal.getQuinta();
			break;
			
			case 6:
				cargaHoraria = cal.getSexta();
			break;
			
			case 7:
				cargaHoraria = cal.getSabado();	
			break;			

			default:
				cargaHoraria = 0;
			break;
		} 
		
		return cargaHoraria;
	}
	
	/**
	SQLs importantes :
	select * from negocio.tb_usuario
	
	select * from negocio.tb_tipo_recurso
	
	select * from negocio.tb_recurso
	
	select * from negocio.tb_calendario
	
	select * from negocio.tb_tarefa
	
	--
	delete from negocio.tb_tarefa
	
	delete from negocio.tb_calendario
	
	insert into negocio.tb_calendario (dia_domingo, dia_segunda, dia_terca,dia_quarta,dia_quinta,dia_sexta,dia_sabado,irecurso)
	values (0,8,8,8,8,8,0, 1) 
	
	insert into negocio.tb_calendario (dia_domingo, dia_segunda, dia_terca,dia_quarta,dia_quinta,dia_sexta,dia_sabado,irecurso)
	values (0,8,0,0,0,0,0, 1)	
	--
	
	 select cal.*
	 from negocio.tb_calendario cal
	 inner join negocio.tb_recurso rec
	 on cal.irecurso = rec.icodigo
	 inner join negocio.tb_usuario usu
	 on rec.iusuario = usu.icodigo
	 where usu.susername = 'alessandrots' 
  
*/
	
}
