package app;

import java.util.ArrayList;

import dao.AtraccionDAO;
import dao.ClienteDAO;
import dao.DAOFactory;
import dao.ItinerarioDAO;
import dao.PromocionDAO;
import paqueteTurismoTM.Atraccion;
import paqueteTurismoTM.Cliente;
import paqueteTurismoTM.Oferta;
import paqueteTurismoTM.Promocion;

public class App {
	
	public static ArrayList<Atraccion> atracciones = new ArrayList<Atraccion>();
	public static ArrayList<Oferta> ofertas = new ArrayList<Oferta>();
	public static void main(String[] args) {
		/*************** APP AtraccionDAO ****************/
		AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
		
		atracciones.addAll(atraccionDAO.findAll());
		ofertas.addAll(atracciones);
//		System.out.println(atracciones);
		
		Atraccion unaAtraccion = new Atraccion(12,"Rivendell",20,4, 9,"PAISAJE");
		atraccionDAO.updateCupo(unaAtraccion);
		atracciones.indexOf(unaAtraccion);
		
		System.out.println(atraccionDAO.findAtraccionPorNombre(unaAtraccion.getNombre()));
		
		/*************** APP PromocionDAO ****************/
		PromocionDAO promocionDAO = DAOFactory.getPromocionDAO();
		String promo = "PromocionAbsoluta 1";
		System.out.println(promocionDAO.listarAtraccionesIncluidas(promo,atracciones));
//		System.out.println(promocionDAO.findAll());		
//		
		ofertas.addAll(promocionDAO.findAll(atracciones));
//
//		System.out.println(promocionDAO.findPromocionPorNombre("Promocion AxB1"));
//		
//		/*************** APP ClienteDAO ****************/
//		ClienteDAO clienteDAO = DAOFactory.getClienteDAO();
//		System.out.println(clienteDAO.findAll());
//		
		Cliente unCliente = new Cliente(7,"Pippin","DEGUSTACION", 65, 10);
//		clienteDAO.update(unCliente);
//		
//		System.out.println(clienteDAO.findAll());
//		
//		/*************** APP ItinerarioDAO ****************/
		ItinerarioDAO itinerarioDAO = DAOFactory.getItinerarioDAO();
		System.out.println(itinerarioDAO.findItinerarioPorCliente(unCliente.getId_cliente()));
		Promocion unaPromocion = (Promocion) ofertas.get(13);
		System.out.println("----"+unaPromocion);
		System.out.println("----"+unaAtraccion);
		itinerarioDAO.insertAtraccion(unCliente.getId_cliente(), unaAtraccion);
		itinerarioDAO.insertPromocion(unCliente.getId_cliente(), unaPromocion);
		System.out.println("+++"+itinerarioDAO.findItinerarioPorCliente(unCliente.getId_cliente())); //cambiar TurismoTM.ofertas, para que funcione
	}
		
		
}
