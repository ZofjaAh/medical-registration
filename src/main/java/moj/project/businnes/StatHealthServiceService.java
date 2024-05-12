package moj.project.businnes;

import lombok.AllArgsConstructor;
import moj.project.businnes.dao.StatHealthServiceDAO;
import moj.project.domain.StatBenefit;
import moj.project.domain.StatSection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class StatHealthServiceService {
    private final StatHealthServiceDAO statHealthServiceDAO;
    @Transactional
public List<StatSection> getSections(){
    return statHealthServiceDAO.getSectionsJGP();
}
@Transactional
public List<StatBenefit> getBenefits(String benefit, String catalog, String section, int page){
    return statHealthServiceDAO.getBenefits(benefit, catalog, section, page);
}
}
