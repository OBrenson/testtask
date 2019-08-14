import com.haulmont.testtask.DAO.DoctorDAO;
import com.haulmont.testtask.UI.DoctorsView;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.exceptions.SelectNullReturnException;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.WrappedSession;

import javax.servlet.http.Cookie;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Test {
    public static void main(String[] args){
        Doctor doc = new Doctor();
        doc.setName("Oleg");
        doc.setSurname("Brenev");
        doc.setPatronymic("Igorevich");
        doc.setSpecialization("Proctolog");
        DoctorDAO.insertDoctor(doc);
        List<Doctor> d = null;
        try {
            d = DoctorDAO.selectAllDoctors();
        } catch (SelectNullReturnException e) {
            e.printStackTrace();
        }

    }
}
