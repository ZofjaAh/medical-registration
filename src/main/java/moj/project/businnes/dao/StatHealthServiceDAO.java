package moj.project.businnes.dao;

import moj.project.domain.StatBenefit;
import moj.project.domain.StatSection;

import java.util.List;

public interface StatHealthServiceDAO {
    List<StatSection> getSectionsJGP();

    List<StatBenefit> getBenefits(String benefit, String catalog, String section, int page);
}
