package br.com.davileitao.debtregistervraptor.unit.test;

import java.io.IOException;
import java.sql.Connection;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.mockito.Mockito;

import br.com.triadworks.javaweb.dao.ConnectionFactory;
import br.com.triadworks.javaweb.modelo.Caloteiro;
import br.com.triadworks.javaweb.servlets.CaloteiroServletException;
import br.com.triadworks.javaweb.servlets.ServletSistema;

public class ServletSistemaFalsoTest extends Mockito {

	@Test(expected=CaloteiroServletException.class)
	public void deveCausarUmaClassNotFoundException() throws ServletException, IOException {
		
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		when(request.getParameter("logica")).thenReturn("NaoExiste");
		
		new ServletSistema().service(request, response);
	}
	
	@Test
	public void deveExecutarUmaLogicaListaCaloteiro() throws ServletException, IOException {
		
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		RequestDispatcher rd = mock(RequestDispatcher.class);
		when(request.getRequestDispatcher("/listaCaloteiro.jsp")).thenReturn(rd);
		
		Connection conexao = new ConnectionFactory().getConnection();
		
		when(request.getParameter("logica")).thenReturn("ListaCaloteiro");
		when(request.getAttribute("conexao")).thenReturn(conexao);
		
		request.setAttribute("conexao", conexao);
		new ServletSistema().service(request, response);
	}
	
	@Test
	public void deveExecutarLogicaAdicionaCaloteiro() throws ServletException, IOException {
		
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		
		Connection conexao = new ConnectionFactory().getConnection();

		when(request.getParameter("logica")).thenReturn("AdicionaCaloteiro");
		when(request.getParameter("nome")).thenReturn("Joao");
		when(request.getParameter("email")).thenReturn("email@google.com");
		when(request.getParameter("devendo")).thenReturn("300");
		when(request.getParameter("dataDivida")).thenReturn("20/12/2016");
		when(request.getAttribute("conexao")).thenReturn(conexao);
		when(request.getSession()).thenReturn(session);
		
		Caloteiro caloteiro = new Caloteiro();
		caloteiro.setNome("Joao");
		caloteiro.setEmail("email@google.com");
		caloteiro.setDevendo((double) 300);
		caloteiro.setDataDivida(Calendar.getInstance());
		
		request.setAttribute("conexao", conexao);
		session.setAttribute("caloteirocadastrado", caloteiro);
		new ServletSistema().service(request, response);
	}
}
