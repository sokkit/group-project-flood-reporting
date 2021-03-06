package cf.ac.uk.wrackreport.data.jpa.repositories;

import cf.ac.uk.wrackreport.data.jpa.entities.CategoryEntity;
import cf.ac.uk.wrackreport.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    ArrayList<CategoryEntity> findAll();

    Optional<CategoryEntity> getById(short id);

    CategoryEntity save(CategoryEntity category);
}
