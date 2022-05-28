import com.db4o.*;
import com.db4o.query.Constraint;
import com.db4o.query.Query;

import java.util.List;


public class EjemploQuery extends Util {
	
	final static String DB4OFILENAME = System.getProperty("user.home") + "/formula1.db4o4";
	
    public static void main(String[] args) {
        ObjectContainer db=Db4o.openFile(Db4o
				.newConfiguration(), DB4OFILENAME);
        try {
            guardarPrimerParticipante(db);
            guardarSegundoParticipante(db);
            guardarTercerParticipante(db);
            obtenerParticipantePorNombre(db);
            obtenerParticipantePorPuntosExactos(db);
            retrieveByNegation(db);
            retrieveByConjunction(db);
            retrieveByDisjunction(db);
            retrieveByComparison(db);
            retrieveByDefaultFieldValue(db);
            obtenerlosOrdenados(db);
            limpiarBBDD(db);
        }
        finally {
            db.close();
        }
    }

    public static void guardarPrimerParticipante(ObjectContainer db) {
        Participante participante1 =new Participante("Michael Schumacher",100);
        db.store(participante1);
        System.out.println("Stored "+ participante1);
    }

    public static void guardarSegundoParticipante(ObjectContainer db) {
        Participante participante2 =new Participante("Rubens Barrichello",99);
        db.store(participante2);
        System.out.println("Stored "+ participante2);
    }

    public static void guardarTercerParticipante(ObjectContainer db) {
        Query query=db.query();
        query.constrain(Participante.class);
        ObjectSet result=query.execute();
        listResult((List<?>) result);
    }

    public static void obtenerParticipantePorNombre(ObjectContainer db) {
        Query query=db.query();
        query.constrain(Participante.class);
        query.descend("name").constrain("Michael Schumacher");
        ObjectSet result=query.execute();
        listResult((List<?>) result);
    }
    
    public static void obtenerParticipantePorPuntosExactos(
            ObjectContainer db) {
        Query query=db.query();
        query.constrain(Participante.class);
        query.descend("points").constrain(100);
        ObjectSet result=query.execute();
        listResult((List<?>) result);
    }

    public static void retrieveByNegation(ObjectContainer db) {
        Query query=db.query();
        query.constrain(Participante.class);
        query.descend("name").constrain("Michael Schumacher").not();
        ObjectSet result=query.execute();
        listResult((List<?>) result);
    }

    public static void retrieveByConjunction(ObjectContainer db) {
        Query query=db.query();
        query.constrain(Participante.class);
        Constraint constr=query.descend("name")
                .constrain("Michael Schumacher");
        query.descend("points")
                .constrain(99).and(constr);
        ObjectSet result=query.execute();
        listResult((List<?>) result);
    }

    public static void retrieveByDisjunction(ObjectContainer db) {
        Query query=db.query();
        query.constrain(Participante.class);
        Constraint constr=query.descend("name")
                .constrain("Michael Schumacher");
        query.descend("points")
                .constrain(99).or(constr);
        ObjectSet result=query.execute();
        listResult((List<?>) result);
    }

    public static void retrieveByComparison(ObjectContainer db) {
        Query query=db.query();
        query.constrain(Participante.class);
        query.descend("points")
                .constrain(99).greater();
        ObjectSet result=query.execute();
        listResult((List<?>) result);
    }

    public static void retrieveByDefaultFieldValue(
                    ObjectContainer db) {
        Participante somebody=new Participante("Somebody else",0);
        db.store(somebody);
        Query query=db.query();
        query.constrain(Participante.class);
        query.descend("points").constrain(0);
        ObjectSet result=query.execute();
        listResult((List<?>) result);
        db.delete(somebody);
    }
    
    public static void obtenerlosOrdenados(ObjectContainer db) {
        Query query=db.query();
        query.constrain(Participante.class);
        query.descend("name").orderAscending();
        ObjectSet result=query.execute();
        listResult((List<?>) result);
        query.descend("name").orderDescending();
        result=query.execute();
        listResult((List<?>) result);
    }

    public static void limpiarBBDD(ObjectContainer db) {
        ObjectSet result=db.queryByExample(Participante.class);
        while(result.hasNext()) {
            db.delete(result.next());
        }
    }
}
