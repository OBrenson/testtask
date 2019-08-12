import com.haulmont.testtask.DAO.DoctorDAO;
import com.haulmont.testtask.DAO.EntityManagerUtil;
import com.haulmont.testtask.entities.Doctor;

import java.util.List;

public class Test {
    public static void main(String[] args){
        Doctor doc = new Doctor();
        doc.setName("Oleg");
        doc.setSurname("Brenev");
        doc.setPatronymic("Igorevich");
        doc.setSpecialization("Proctolog");
        DoctorDAO.insertDoctor(doc);
        List<Doctor> d = DoctorDAO.selectDoctorByFIO("Oleg", "Brenev", "Igorevich");
        System.out.println(d.get(0));
    }
}
