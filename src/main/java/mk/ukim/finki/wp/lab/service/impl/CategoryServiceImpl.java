package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Category;
import mk.ukim.finki.wp.lab.repository.jpa.CategoryRepository;
import mk.ukim.finki.wp.lab.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> listAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }
}