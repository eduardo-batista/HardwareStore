package br.unitins.hardwarestore.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.hardwarestore.application.Util;
import br.unitins.hardwarestore.model.Categoria;
import br.unitins.hardwarestore.model.Produto;

public class ProdutoDAO implements DAO<Produto> {

	@Override
	public void inserir(Produto obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ");
		sql.append("produtos ");
		sql.append("  (nome, descricao, estoque, data_recebimento, categoria, preco) ");
		sql.append("VALUES ");
		sql.append("  ( ?, ?, ?, ?, ?, ?) ");
		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getDescricao());
			stat.setInt(3, obj.getEstoque());
			// convertendo um obj LocalDate para sql.Date
			if (obj.getDataDeRecebimento() != null)
				stat.setDate(4, Date.valueOf(obj.getDataDeRecebimento()));
			else
				stat.setDate(4, null);
			// ternario java
			stat.setObject(5, (obj.getCategoria() == null ? null : obj.getCategoria().getId()));
			stat.setDouble(6, obj.getPreco());

			stat.execute();
			// efetivando a transacao
			conn.commit();

		} catch (SQLException e) {

			System.out.println("Erro ao realizar um comando sql de insert.");
			e.printStackTrace();
			// cancelando a transacao
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("Erro ao realizar o rollback.");
				e1.printStackTrace();
			}
			exception = new Exception("Erro ao inserir");

		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;

	}

	@Override
	public void alterar(Produto obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE produtos SET ");
		sql.append("  nome = ?, ");
		sql.append("  descricao = ?, ");
		sql.append("  estoque = ?, ");
		sql.append("  data_recebimento = ?, ");
		sql.append("  categoria = ?, ");
		sql.append("  preco = ? ");
		sql.append("WHERE ");
		sql.append("  id = ? ");

		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getDescricao());
			stat.setInt(3, obj.getEstoque());
			// convertendo um obj LocalDate para sql.Date
			if (obj.getDataDeRecebimento() != null)
				stat.setDate(4, Date.valueOf(obj.getDataDeRecebimento()));
			else
				stat.setDate(4, null);
			// ternario java
			stat.setObject(5, (obj.getCategoria() == null ? null : obj.getCategoria().getId()));
			stat.setDouble(6, obj.getPreco());
			stat.setInt(7, obj.getId());

			stat.execute();
			// efetivando a transacao
			conn.commit();

		} catch (SQLException e) {

			System.out.println("Erro ao realizar um comando sql de insert.");
			e.printStackTrace();
			// cancelando a transacao
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("Erro ao realizar o rollback.");
				e1.printStackTrace();
			}
			exception = new Exception("Erro ao inserir");

		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;

	}

	@Override
	public void excluir(Produto obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM produtos WHERE id = ?");

		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, obj.getId());
			stat.execute();
			// efetivando a transacao
			conn.commit();

		} catch (SQLException e) {

			System.out.println("Erro ao realizar um comando sql de insert.");
			e.printStackTrace();
			// cancelando a transacao
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("Erro ao realizar o rollback.");
				e1.printStackTrace();
			}
			exception = new Exception("Erro ao inserir");

		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;

	}

	@Override
	public List<Produto> obterTodos() throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		List<Produto> listaProduto = new ArrayList<Produto>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  u.id, ");
		sql.append("  u.nome, ");
		sql.append("  u.descricao, ");
		sql.append("  u.estoque, ");
		sql.append("  u.data_recebimento, ");
		sql.append("  u.categoria, ");
		sql.append("  u.preco ");
		sql.append("FROM  ");
		sql.append("  produtos u ");
		sql.append("ORDER BY u.nome ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Produto produto = new Produto();
				produto.setId(rs.getInt("id"));
				Date data = rs.getDate("data_recebimento");
				produto.setDataDeRecebimento(data == null ? null : data.toLocalDate());
				produto.setCategoria(Categoria.valueOf(rs.getInt("categoria")));
				produto.setNome(rs.getString("nome"));
				produto.setEstoque(rs.getInt("estoque"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setPreco(rs.getDouble("preco"));

				listaProduto.add(produto);
			}

		} catch (SQLException e) {
			Util.addErrorMessage("Não foi possivel buscar os dados do Produto.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em ProdutoDAO.");
		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;

		return listaProduto;
	}
	
	@Override
	public Produto obterUm(Produto obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		
		Produto produto = null;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  u.id, ");
		sql.append("  u.nome, ");
		sql.append("  u.descricao, ");
		sql.append("  u.estoque, ");
		sql.append("  u.data_recebimento, ");
		sql.append("  u.categoria, ");
		sql.append("  u.preco ");
		sql.append("FROM  ");
		sql.append("  produtos u ");
		sql.append("WHERE u.id = ? ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, obj.getId());

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				produto = new Produto();
				produto.setId(rs.getInt("id"));
				Date data = rs.getDate("data_recebimento");
				produto.setDataDeRecebimento(data == null ? null : data.toLocalDate());
				produto.setCategoria(Categoria.valueOf(rs.getInt("categoria")));
				produto.setNome(rs.getString("nome"));
				produto.setEstoque(rs.getInt("estoque"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setPreco(rs.getDouble("preco"));
			}

		} catch (SQLException e) {
			Util.addErrorMessage("Não foi possivel buscar os dados do produto.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em ProdutoDAO.");
		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;

		return produto;
	}
}

