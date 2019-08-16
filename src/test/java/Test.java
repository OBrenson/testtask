import com.haulmont.testtask.DAO.DoctorDAO;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.exceptions.SelectNullReturnException;

import java.util.List;

public class Test {
    public static void main(String[] args){
/*        Doctor doc = new Doctor();
        doc.setName("Oleg");
        doc.setSurname("Brenev");
        doc.setPatronymic("Igorevich");
        doc.setSpecialization("Proctolog");
        DoctorDAO.insertDoctor(doc);*/
        List<Doctor> d = null;
        try {
            d = DoctorDAO.selectAllDoctors();
        } catch (SelectNullReturnException e) {
            e.printStackTrace();
        }

    }
}
