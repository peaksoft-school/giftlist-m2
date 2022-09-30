package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.CategoryRequest;
import kg.giftlist.giftlistm2.controller.payload.CategoryResponse;
import kg.giftlist.giftlistm2.db.entity.Category;
import kg.giftlist.giftlistm2.db.repository.CategoryRepository;
import kg.giftlist.giftlistm2.enums.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = new Category();
        if (request.getElectronic()!=null){
        category.setElectronic(Electronic.valueOf(request.getElectronic()));}
        if (request.getClothes()!=null){
        category.setClothes(Clothes.valueOf(request.getClothes()));}
        if (request.getClothes() != null) {
        category.setSchool(School.valueOf(request.getSchool()));}
        if (request.getHouseAndGarden() != null) {
        category.setHouseAndGarden(HouseAndGarden.valueOf(request.getHouseAndGarden()));}
        if (request.getShoe() != null) {
        category.setShoe(Shoe.valueOf(request.getShoe()));}
        if (request.getTransportation() != null) {
        category.setTransportation(Transportation.valueOf(request.getTransportation()));}
        categoryRepository.save(category);
        return mapToResponse(category);
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id).get();
        if (request.getElectronic()!=null){
            category.setElectronic(Electronic.valueOf(request.getElectronic()));}
        if (request.getClothes()!=null){
            category.setClothes(Clothes.valueOf(request.getClothes()));}
        if (request.getClothes() != null) {
            category.setSchool(School.valueOf(request.getSchool()));}
        if (request.getHouseAndGarden() != null) {
            category.setHouseAndGarden(HouseAndGarden.valueOf(request.getHouseAndGarden()));}
        if (request.getShoe() != null) {
            category.setShoe(Shoe.valueOf(request.getShoe()));}
        if (request.getTransportation() != null) {
            category.setTransportation(Transportation.valueOf(request.getTransportation()));}
        categoryRepository.save(category);
        return mapToResponse(category);
    }

    private CategoryResponse mapToResponse(Category category) {
        if (category == null) {
            return null;
        }
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setElectronic(category.getElectronic());
        categoryResponse.setClothes(category.getClothes());
        categoryResponse.setSchool(category.getSchool());
        categoryResponse.setHouseAndGarden(category.getHouseAndGarden());
        categoryResponse.setShoe(category.getShoe());
        categoryResponse.setTransportation(category.getTransportation());
        return categoryResponse;
    }
}
