import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RecipeService } from '../service/recipe.service';
import { IRecipe, Recipe } from '../recipe.model';
import { IPlant } from 'app/entities/plant/plant.model';
import { PlantService } from 'app/entities/plant/service/plant.service';

import { RecipeUpdateComponent } from './recipe-update.component';

describe('Recipe Management Update Component', () => {
  let comp: RecipeUpdateComponent;
  let fixture: ComponentFixture<RecipeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let recipeService: RecipeService;
  let plantService: PlantService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RecipeUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(RecipeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RecipeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    recipeService = TestBed.inject(RecipeService);
    plantService = TestBed.inject(PlantService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Plant query and add missing value', () => {
      const recipe: IRecipe = { id: 456 };
      const plantID: IPlant = { id: 96320 };
      recipe.plantID = plantID;

      const plantCollection: IPlant[] = [{ id: 12096 }];
      jest.spyOn(plantService, 'query').mockReturnValue(of(new HttpResponse({ body: plantCollection })));
      const additionalPlants = [plantID];
      const expectedCollection: IPlant[] = [...additionalPlants, ...plantCollection];
      jest.spyOn(plantService, 'addPlantToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ recipe });
      comp.ngOnInit();

      expect(plantService.query).toHaveBeenCalled();
      expect(plantService.addPlantToCollectionIfMissing).toHaveBeenCalledWith(plantCollection, ...additionalPlants);
      expect(comp.plantsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const recipe: IRecipe = { id: 456 };
      const plantID: IPlant = { id: 80996 };
      recipe.plantID = plantID;

      activatedRoute.data = of({ recipe });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(recipe));
      expect(comp.plantsSharedCollection).toContain(plantID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Recipe>>();
      const recipe = { id: 123 };
      jest.spyOn(recipeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ recipe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: recipe }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(recipeService.update).toHaveBeenCalledWith(recipe);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Recipe>>();
      const recipe = new Recipe();
      jest.spyOn(recipeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ recipe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: recipe }));
      saveSubject.complete();

      // THEN
      expect(recipeService.create).toHaveBeenCalledWith(recipe);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Recipe>>();
      const recipe = { id: 123 };
      jest.spyOn(recipeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ recipe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(recipeService.update).toHaveBeenCalledWith(recipe);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPlantById', () => {
      it('Should return tracked Plant primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPlantById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
