package br.com.davileitao.debtregistervraptor.model;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.triadworks.javaweb.dao.CaloteiroDAO;

public class AdicionaCaloteiroLogica implements Logica {
	
	public void executa(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String devendo = request.getParameter("devendo");
		String dataDivida = request.getParameter("dataDivida");
		Calendar dataDividaConvertida = null;
		try {
			Date data = new SimpleDateFormat("dd/MM/yyyy").parse(dataDivida);
			dataDividaConvertida = Calendar.getInstance();
			dataDividaConvertida.setTime(data);
		} catch (ParseException e) {
			throw new RuntimeException();
		}
		
		Caloteiro caloteiro = new Caloteiro();
		
		caloteiro.setNome(nome);
		caloteiro.setEmail(email);
		caloteiro.setDevendo(new Double(devendo));
		caloteiro.setDataDivida(dataDividaConvertida);
		
		CaloteiroDAO dao = new CaloteiroDAO((Connection)request.getAttribute("conexao"));
		dao.adiciona(caloteiro);
		HttpSession session = request.getSession();
		session.setAttribute("caloteiroCadastrado", caloteiro);
		response.sendRedirect("caloteiroAdicionado.jsp");
	}
}
