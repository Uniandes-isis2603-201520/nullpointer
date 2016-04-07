package co.edu.uniandes.csw.tripulator.api;
import co.edu.uniandes.csw.tripulator.entities.DiaEntity;
import co.edu.uniandes.csw.tripulator.exceptions.BusinessLogicException;
import java.util.List;

public interface IDiaLogic {

    public List<DiaEntity> getDias();

    public DiaEntity getDia(Long id) throws BusinessLogicException;

    public DiaEntity createDia(DiaEntity dia);

    public DiaEntity updateDia(DiaEntity dia);

    public void deleteDia(Long id);
}
