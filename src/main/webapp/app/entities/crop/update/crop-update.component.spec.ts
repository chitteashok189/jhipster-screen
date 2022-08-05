import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CropService } from '../service/crop.service';
import { ICrop, Crop } from '../crop.model';
import { IPlant } from 'app/entities/plant/plant.model';
import { PlantService } from 'app/entities/plant/service/plant.service';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { PlantFactoryService } from 'app/entities/plant-factory/service/plant-factory.service';
import { ITool } from 'app/entities/tool/tool.model';
import { ToolService } from 'app/entities/tool/service/tool.service';
import { ISeason } from 'app/entities/season/season.model';
import { SeasonService } from 'app/entities/season/service/season.service';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';

import { CropUpdateComponent } from './crop-update.component';

describe('Crop Management Update Component', () => {
  let comp: CropUpdateComponent;
  let fixture: ComponentFixture<CropUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cropService: CropService;
  let plantService: PlantService;
  let plantFactoryService: PlantFactoryService;
  let toolService: ToolService;
  let seasonService: SeasonService;
  let productService: ProductService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CropUpdateComponent],
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
      .overrideTemplate(CropUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CropUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cropService = TestBed.inject(CropService);
    plantService = TestBed.inject(PlantService);
    plantFactoryService = TestBed.inject(PlantFactoryService);
    toolService = TestBed.inject(ToolService);
    seasonService = TestBed.inject(SeasonService);
    productService = TestBed.inject(ProductService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Plant query and add missing value', () => {
      const crop: ICrop = { id: 456 };
      const plantID: IPlant = { id: 80491 };
      crop.plantID = plantID;

      const plantCollection: IPlant[] = [{ id: 8780 }];
      jest.spyOn(plantService, 'query').mockReturnValue(of(new HttpResponse({ body: plantCollection })));
      const additionalPlants = [plantID];
      const expectedCollection: IPlant[] = [...additionalPlants, ...plantCollection];
      jest.spyOn(plantService, 'addPlantToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ crop });
      comp.ngOnInit();

      expect(plantService.query).toHaveBeenCalled();
      expect(plantService.addPlantToCollectionIfMissing).toHaveBeenCalledWith(plantCollection, ...additionalPlants);
      expect(comp.plantsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PlantFactory query and add missing value', () => {
      const crop: ICrop = { id: 456 };
      const plantFactoryID: IPlantFactory = { id: 48684 };
      crop.plantFactoryID = plantFactoryID;

      const plantFactoryCollection: IPlantFactory[] = [{ id: 1005 }];
      jest.spyOn(plantFactoryService, 'query').mockReturnValue(of(new HttpResponse({ body: plantFactoryCollection })));
      const additionalPlantFactories = [plantFactoryID];
      const expectedCollection: IPlantFactory[] = [...additionalPlantFactories, ...plantFactoryCollection];
      jest.spyOn(plantFactoryService, 'addPlantFactoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ crop });
      comp.ngOnInit();

      expect(plantFactoryService.query).toHaveBeenCalled();
      expect(plantFactoryService.addPlantFactoryToCollectionIfMissing).toHaveBeenCalledWith(
        plantFactoryCollection,
        ...additionalPlantFactories
      );
      expect(comp.plantFactoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Tool query and add missing value', () => {
      const crop: ICrop = { id: 456 };
      const toolID: ITool = { id: 67795 };
      crop.toolID = toolID;

      const toolCollection: ITool[] = [{ id: 57001 }];
      jest.spyOn(toolService, 'query').mockReturnValue(of(new HttpResponse({ body: toolCollection })));
      const additionalTools = [toolID];
      const expectedCollection: ITool[] = [...additionalTools, ...toolCollection];
      jest.spyOn(toolService, 'addToolToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ crop });
      comp.ngOnInit();

      expect(toolService.query).toHaveBeenCalled();
      expect(toolService.addToolToCollectionIfMissing).toHaveBeenCalledWith(toolCollection, ...additionalTools);
      expect(comp.toolsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Season query and add missing value', () => {
      const crop: ICrop = { id: 456 };
      const seasonID: ISeason = { id: 26175 };
      crop.seasonID = seasonID;

      const seasonCollection: ISeason[] = [{ id: 56890 }];
      jest.spyOn(seasonService, 'query').mockReturnValue(of(new HttpResponse({ body: seasonCollection })));
      const additionalSeasons = [seasonID];
      const expectedCollection: ISeason[] = [...additionalSeasons, ...seasonCollection];
      jest.spyOn(seasonService, 'addSeasonToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ crop });
      comp.ngOnInit();

      expect(seasonService.query).toHaveBeenCalled();
      expect(seasonService.addSeasonToCollectionIfMissing).toHaveBeenCalledWith(seasonCollection, ...additionalSeasons);
      expect(comp.seasonsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Product query and add missing value', () => {
      const crop: ICrop = { id: 456 };
      const productID: IProduct = { id: 23491 };
      crop.productID = productID;

      const productCollection: IProduct[] = [{ id: 87223 }];
      jest.spyOn(productService, 'query').mockReturnValue(of(new HttpResponse({ body: productCollection })));
      const additionalProducts = [productID];
      const expectedCollection: IProduct[] = [...additionalProducts, ...productCollection];
      jest.spyOn(productService, 'addProductToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ crop });
      comp.ngOnInit();

      expect(productService.query).toHaveBeenCalled();
      expect(productService.addProductToCollectionIfMissing).toHaveBeenCalledWith(productCollection, ...additionalProducts);
      expect(comp.productsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const crop: ICrop = { id: 456 };
      const plantID: IPlant = { id: 66457 };
      crop.plantID = plantID;
      const plantFactoryID: IPlantFactory = { id: 40134 };
      crop.plantFactoryID = plantFactoryID;
      const toolID: ITool = { id: 67119 };
      crop.toolID = toolID;
      const seasonID: ISeason = { id: 24365 };
      crop.seasonID = seasonID;
      const productID: IProduct = { id: 63969 };
      crop.productID = productID;

      activatedRoute.data = of({ crop });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(crop));
      expect(comp.plantsSharedCollection).toContain(plantID);
      expect(comp.plantFactoriesSharedCollection).toContain(plantFactoryID);
      expect(comp.toolsSharedCollection).toContain(toolID);
      expect(comp.seasonsSharedCollection).toContain(seasonID);
      expect(comp.productsSharedCollection).toContain(productID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Crop>>();
      const crop = { id: 123 };
      jest.spyOn(cropService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ crop });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: crop }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(cropService.update).toHaveBeenCalledWith(crop);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Crop>>();
      const crop = new Crop();
      jest.spyOn(cropService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ crop });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: crop }));
      saveSubject.complete();

      // THEN
      expect(cropService.create).toHaveBeenCalledWith(crop);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Crop>>();
      const crop = { id: 123 };
      jest.spyOn(cropService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ crop });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cropService.update).toHaveBeenCalledWith(crop);
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

    describe('trackPlantFactoryById', () => {
      it('Should return tracked PlantFactory primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPlantFactoryById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackToolById', () => {
      it('Should return tracked Tool primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackToolById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSeasonById', () => {
      it('Should return tracked Season primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSeasonById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackProductById', () => {
      it('Should return tracked Product primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProductById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
