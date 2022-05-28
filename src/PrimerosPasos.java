import java.io.*;
import java.util.List;

import com.db4o.*;


public class PrimerosPasos extends Util {
	final static String DB4OFILENAME = System.getProperty("user.home")
			+ "/formula1.db4o";

	public static void main(String[] args) {
		accessDb4o();
		new File(DB4OFILENAME).delete();
		ObjectContainer db = Db4o.openFile(Db4o
				.newConfiguration(), DB4OFILENAME);
		try {
			guardarPrimerParticipante(db);
			guardarSegundoParticipante(db);
			consultarTodosParticipantes(db);
			conseguirParticipantePorNombre(db);
			actualizarParticipante(db);
			conseguirParticipantePorPuntosExactos(db);
			borrarPrimerParticipantePorNombre(db);
			borrarSegundoParticipantePorNombre(db);
		} finally {
			db.close();
		}
	}

	public static void accessDb4o() {
		ObjectContainer db = Db4o.openFile(Db4o
				.newConfiguration(), DB4OFILENAME);
		try {
			// do something with db4o
		} finally {
			db.close();
		}
	}

	public static void guardarPrimerParticipante(ObjectContainer db) {
		Participante participante1 = new Participante("Michael Schumacher", 100);
		db.store(participante1);
		System.out.println("Stored " + participante1);
	}

	public static void guardarSegundoParticipante(ObjectContainer db) {
		Participante participante2 = new Participante("Rubens Barrichello", 99);
		db.store(participante2);
		System.out.println("Stored " + participante2);
	}

	public static void consultarTodosParticipantesQBE(ObjectContainer db) {
		Participante proto = new Participante(null, 0);
		ObjectSet result = db.queryByExample(proto);
		listResult((List<?>) result);
	}

	public static void consultarTodosParticipantes(ObjectContainer db) {
		ObjectSet result = db.queryByExample(Participante.class);
		listResult((List<?>) result);
	}

	public static void conseguirParticipantePorNombre(ObjectContainer db) {
		Participante proto = new Participante("Michael Schumacher", 0);
		ObjectSet result = db.queryByExample(proto);
		listResult((List<?>) result);
	}

	public static void conseguirParticipantePorPuntosExactos(ObjectContainer db) {
		Participante proto = new Participante(null, 100);
		ObjectSet result = db.queryByExample(proto);
		listResult((List<?>) result);
	}

	public static void actualizarParticipante(ObjectContainer db) {
		ObjectSet result = db
				.queryByExample(new Participante("Michael Schumacher", 0));
		Participante found = (Participante) result.next();
		found.addPoints(11);
		db.store(found);
		System.out.println("Added 11 points for " + found);
		consultarTodosParticipantes(db);
	}

	public static void borrarPrimerParticipantePorNombre(ObjectContainer db) {
		ObjectSet result = db
				.queryByExample(new Participante("Michael Schumacher", 0));
		Participante found = (Participante) result.next();
		db.delete(found);
		System.out.println("Deleted " + found);
		consultarTodosParticipantes(db);
	}

	public static void borrarSegundoParticipantePorNombre(ObjectContainer db) {
		ObjectSet result = db
				.queryByExample(new Participante("Rubens Barrichello", 0));
		Participante found = (Participante) result.next();
		db.delete(found);
		System.out.println("Deleted " + found);
		consultarTodosParticipantes(db);
	}
}

