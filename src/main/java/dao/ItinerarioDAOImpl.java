package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import java.util.List;

import app.App;
import jdbc.ConnectionProvider;
import paqueteTurismoTM.Atraccion;

import paqueteTurismoTM.Itinerario;
import paqueteTurismoTM.Oferta;
import paqueteTurismoTM.Promocion;
import paqueteTurismoTM.TurismoTM;

public class ItinerarioDAOImpl implements ItinerarioDAO {

	@Override
	public ArrayList<Oferta> findItinerarioPorCliente(int id_cliente) {
		ArrayList<Oferta> comprasCliente = new ArrayList<Oferta>();
		try {
			String sql = "SELECT coalesce(promociones.nombre, atracciones.nombre)AS compras " 
					+ "FROM itinerarios "
					+ "LEFT JOIN \"promociones\" ON \"promociones\".id_promocion = itinerarios.fk_promocion "
					+ "LEFT JOIN \"atracciones\" ON \"atracciones\".id_atraccion = itinerarios.fk_atraccion "
					+ "LEFT JOIN \"clientes\" ON \"clientes\".id_cliente=itinerarios.fk_cliente "
					+ "WHERE clientes.id_cliente = ?;";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id_cliente);
			ResultSet resultados = statement.executeQuery();

			ArrayList<String> compras = new ArrayList<>();
			while (resultados.next()) {
				compras.add(resultados.getString("compras"));

			}
			for (String compra : compras) {
				for (Oferta unaOferta : App.ofertas) {
					if (compra.equals(unaOferta.getNombre())) {
						comprasCliente.add(unaOferta);
					}
				}
			}

			return comprasCliente;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int insertAtraccion(int id_cliente, Atraccion unaAtraccion) {
		try {
			AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
			String nombreAtraccion = unaAtraccion.getNombre();
			int fk_atraccion = atraccionDAO.findIdPorNombre(nombreAtraccion);
			String sql = "INSERT INTO itinerarios (fk_cliente, fk_atraccion, costo, tiempo) VALUES (?, ?, ?, ?);";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id_cliente);
			statement.setInt(2, fk_atraccion);
			statement.setInt(3, unaAtraccion.getCosto());
			statement.setDouble(4, unaAtraccion.getTiempo());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int insertPromocion(int id_cliente, Promocion unaPromocion) {
		try {
			PromocionDAO promocionDAO = DAOFactory.getPromocionDAO();
			int fk_promocion = promocionDAO.findIdPorNombre(unaPromocion.getNombre());
			String sql = "INSERT INTO itinerarios (fk_cliente, fk_promocion, costo, tiempo) VALUES (?, ?, ?, ?);";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id_cliente);
			statement.setInt(2, fk_promocion);
			statement.setInt(3, unaPromocion.getCosto());
			statement.setDouble(4, unaPromocion.getTiempo());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	@Override
	public List<Itinerario> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}