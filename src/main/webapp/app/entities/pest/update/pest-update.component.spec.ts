import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PestService } from '../service/pest.service';
import { IPest, Pest } from '../pest.model';
import { IScouting } from 'app/entities/scouting/scouting.model';
import { ScoutingService } from 'app/entities/scouting/service/scouting.service';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { PlantFactoryService } from 'app/entities/plant-factory/service/plant-factory.service';

import { PestUpdateComponent } from './pest-update.component';

describe('Pest Management Update Component', () => {
  let comp: PestUpdateComponent;
  let fixture: ComponentFixture<PestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pestService: PestService;
  let scoutingService: ScoutingService;
  let plantFactoryService: PlantFactoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PestUpdateComponent],
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
      .overrideTemplate(PestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pestService = TestBed.inject(PestService);
    scoutingService = TestBed.inject(ScoutingService);
    plantFactoryService = TestBed.inject(PlantFactoryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Scouting query and add missing value', () => {
      const pest: IPest = { id: 456 };
      const scoutingID: IScouting = { id: 77617 };
      pest.scoutingID = scoutingID;

      const scoutingCollection: IScouting[] = [{ id: 5158 }];
      jest.spyOn(scoutingService, 'query').mockReturnValue(of(new HttpResponse({ body: scoutingCollection })));
      const additionalScoutings = [scoutingID];
      const expectedCollection: IScouting[] = [...additionalScoutings, ...scoutingCollection];
      jest.spyOn(scoutingService, 'addScoutingToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pest });
      comp.ngOnInit();

      expect(scoutingService.query).toHaveBeenCalled();
      expect(scoutingService.addScoutingToCollectionIfMissing).toHaveBeenCalledWith(scoutingCollection, ...additionalScoutings);
      expect(comp.scoutingsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PlantFactory query and add missing value', () => {
      const pest: IPest = { id: 456 };
      const plantFactoryID: IPlantFactory = { id: 17004 };
      pest.plantFactoryID = plantFactoryID;

      const plantFactoryCollection: IPlantFactory[] = [{ id: 87170 }];
      jest.spyOn(plantFactoryService, 'query').mockReturnValue(of(new HttpResponse({ body: plantFactoryCollection })));
      const additionalPlantFactories = [plantFactoryID];
      const expectedCollection: IPlantFactory[] = [...additionalPlantFactories, ...plantFactoryCollection];
      jest.spyOn(plantFactoryService, 'addPlantFactoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pest });
      comp.ngOnInit();

      expect(plantFactoryService.query).toHaveBeenCalled();
      expect(plantFactoryService.addPlantFactoryToCollectionIfMissing).toHaveBeenCalledWith(
        plantFactoryCollection,
        ...additionalPlantFactories
      );
      expect(comp.plantFactoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const pest: IPest = { id: 456 };
      const scoutingID: IScouting = { id: 967 };
      pest.scoutingID = scoutingID;
      const plantFactoryID: IPlantFactory = { id: 30454 };
      pest.plantFactoryID = plantFactoryID;

      activatedRoute.data = of({ pest });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(pest));
      expect(comp.scoutingsSharedCollection).toContain(scoutingID);
      expect(comp.plantFactoriesSharedCollection).toContain(plantFactoryID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Pest>>();
      const pest = { id: 123 };
      jest.spyOn(pestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pest }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(pestService.update).toHaveBeenCalledWith(pest);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Pest>>();
      const pest = new Pest();
      jest.spyOn(pestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pest }));
      saveSubject.complete();

      // THEN
      expect(pestService.create).toHaveBeenCalledWith(pest);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Pest>>();
      const pest = { id: 123 };
      jest.spyOn(pestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pestService.update).toHaveBeenCalledWith(pest);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackScoutingById', () => {
      it('Should return tracked Scouting primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackScoutingById(0, entity);
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
  });
});
